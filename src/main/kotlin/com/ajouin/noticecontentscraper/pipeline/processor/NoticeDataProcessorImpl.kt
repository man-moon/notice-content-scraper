package com.ajouin.noticecontentscraper.pipeline.processor

import com.ajouin.noticecontentscraper.dto.ContentRequest
import com.ajouin.noticecontentscraper.dto.ParsingResponse
import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.entity.TempNotice
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.ScraperNotFoundException
import com.ajouin.noticecontentscraper.pipeline.processor.event.OcrRequestCreatedEvent
import com.ajouin.noticecontentscraper.pipeline.processor.event.SummaryRequestCreatedEvent
import com.ajouin.noticecontentscraper.pipeline.processor.image.DownloadedFile
import com.ajouin.noticecontentscraper.pipeline.processor.image.ImageDownloader
import com.ajouin.noticecontentscraper.pipeline.processor.image.S3ImageUploader
import com.ajouin.noticecontentscraper.pipeline.processor.publisher.OcrRequestEventPublisher
import com.ajouin.noticecontentscraper.pipeline.processor.publisher.SummaryRequestEventPublisher
import com.ajouin.noticecontentscraper.repository.TempNoticeRepository
import com.ajouin.noticecontentscraper.scraper.NoticeScraper
import com.ajouin.noticecontentscraper.scraper.ScraperFactory
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class NoticeDataProcessorImpl(
    private val objectMapper: ObjectMapper,
    private val scraperFactory: ScraperFactory,
    private val imageDownloader: ImageDownloader,
    private val s3ImageUploader: S3ImageUploader,
    private val ocrRequestEventPublisher: OcrRequestEventPublisher,
    private val summaryRequestEventPublisher: SummaryRequestEventPublisher,
    private val tempNoticeRepository: TempNoticeRepository,
) : NoticeDataProcessor {

    @Transactional
    override fun processContentRequest(message: String) {
        val request = objectMapper.readValue(message, ContentRequest::class.java)
        logger.info { "Received message: ${request.id}" }
        val result: ParsingResponse = parseRawNoticeData(request)
        val id = UUID.randomUUID()

        if (result.images.isEmpty()) {
            summaryRequestEventPublisher.publish(
                SummaryRequestCreatedEvent(
                    id = id,
                    content = result.content,
                )
            )

        } else {
            val images: List<DownloadedFile> = imageDownloader.download(result.images)
            val imageKeys: List<String> = s3ImageUploader.upload(images)
            result.html = updateHtmlWithImageLinks(result.html, imageKeys)

            // 큐 지연시간 10초
            ocrRequestEventPublisher.publish(
                OcrRequestCreatedEvent(
                    id = id,
                    imageUrl = imageKeys
                )
            )
        }


        val tempNotice = TempNotice(
            id = id,
            isTopFixed = request.isTopFixed,
            fetchId = request.fetchId,
            title = request.title,
            content = result.content,
            html = result.html,
            originalUrl = request.link,
            noticeType = request.noticeType,
        )

        tempNoticeRepository.save(tempNotice)

    }

    private fun parseRawNoticeData(request: ContentRequest): ParsingResponse {

        val content = updateNoticeType(request)
        val scraper = scraperFactory.getScraper(content.noticeType)
            ?: throw ScraperNotFoundException(content.noticeType.toString())

        return scrapeData(scraper, request)
    }

    private fun updateNoticeType(contentRequest: ContentRequest): ContentRequest {

        val noticeTypeString = contentRequest.noticeType.toString().replace(Regex("[0-9]"), "")
        contentRequest.noticeType = NoticeType.valueOf(noticeTypeString)

        return contentRequest
    }

    private fun scrapeData(scraper: NoticeScraper, contentRequest: ContentRequest): ParsingResponse =
        scraper.fetch(contentRequest).let { doc -> scraper.parse(doc) }

    // HTML 내의 모든 <img> 태그를 찾아 src 속성을 새 이미지 링크로 교체
    private fun updateHtmlWithImageLinks(html: String, newImageLinks: List<String>): String {
        var updatedHtml = html
        val pattern = "<img\\s+(?:[^>]*?\\s+)?src=[\"']([^\"']+)[\"']".toRegex()
        val matches = pattern.findAll(html).toList()

        if (matches.isNotEmpty() && matches.size == newImageLinks.size) {
            matches.forEachIndexed { index, matchResult ->
                val oldImageSrc = matchResult.groups[1]?.value ?: ""
                if (index < newImageLinks.size) {
                    updatedHtml = updatedHtml.replace(oldImageSrc, newImageLinks[index])
                }
            }
        }

        return updatedHtml
    }
}