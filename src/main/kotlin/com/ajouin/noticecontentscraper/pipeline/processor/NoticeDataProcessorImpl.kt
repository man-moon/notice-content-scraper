package com.ajouin.noticecontentscraper.pipeline.processor

import com.ajouin.noticecontentscraper.config.EventQueuesProperties
import com.ajouin.noticecontentscraper.dto.ContentRequest
import com.ajouin.noticecontentscraper.dto.ParsingResponse
import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.global.exception.EntityNotFoundException
import com.ajouin.noticecontentscraper.pipeline.exception.ScraperNotFoundException
import com.ajouin.noticecontentscraper.pipeline.processor.image.ImageDownloader
import com.ajouin.noticecontentscraper.pipeline.processor.image.S3ImageUploader
import com.ajouin.noticecontentscraper.scraper.NoticeScraper
import com.ajouin.noticecontentscraper.scraper.ScraperFactory
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.stereotype.Component

@Component
class NoticeDataProcessorImpl(
    private val objectMapper: ObjectMapper,
    private val scraperFactory: ScraperFactory,
    private val imageDownloader: ImageDownloader,
    private val s3ImageUploader: S3ImageUploader,
    private val sqsTemplate: SqsTemplate,
    private val eventQueuesProperties: EventQueuesProperties,
) : NoticeDataProcessor {

    override suspend fun processContentRequest(message: String) {

        val result: ParsingResponse = parseRawNoticeData(message)

        if(result.images.isEmpty()) {
            sqsTemplate.send { to ->
                to.queue(eventQueuesProperties.summaryRequestQueue)
                    .payload("asdf")
            }
        }

        val images: List<ByteArray?> = imageDownloader.download(result.images)
        val imageKeys: List<String> = s3ImageUploader.upload(images)

        result.html = updateHtmlWithImageLinks(result.html, imageKeys)

        // 이미지가 없는 경우, summary-request 큐에 삽입(contents 내용, 게시글 fetch id)

        // 이미지가 있는 경우, ocr-request 큐에 삽입(이미지 링크 배열, 게시글 fetch id)



    }

    private fun parseRawNoticeData(data: String): ParsingResponse {

        val contentRequest = objectMapper.readValue(data, ContentRequest::class.java)
        val content = updateNoticeType(contentRequest)
        val scraper = scraperFactory.getScraper(content.noticeType)
            ?: throw ScraperNotFoundException(content.noticeType.toString())

        return scrapeData(scraper, contentRequest)
    }

    private fun updateNoticeType(contentRequest: ContentRequest): ContentRequest {

        val noticeTypeString = contentRequest.noticeType.toString().replace(Regex("[0-9]"), "")
        contentRequest.noticeType = NoticeType.valueOf(noticeTypeString)

        return contentRequest
    }

    private fun scrapeData(scraper: NoticeScraper, contentRequest: ContentRequest): ParsingResponse =
        scraper.fetch(contentRequest).let { doc -> scraper.parse(doc) }

    private fun updateHtmlWithImageLinks(html: String, newImageLinks: List<String>): String {
        // HTML 내의 모든 <img> 태그를 찾아 src 속성을 새 이미지 링크로 교체
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