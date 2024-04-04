package com.ajouin.noticecontentscraper.pipeline.processor.listener

import com.ajouin.noticecontentscraper.dto.OcrResponse
import com.ajouin.noticecontentscraper.entity.TempNotice
import com.ajouin.noticecontentscraper.global.GlobalExceptionHandler
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.NoticeNotFoundException
import com.ajouin.noticecontentscraper.pipeline.processor.event.SummaryRequestCreatedEvent
import com.ajouin.noticecontentscraper.pipeline.processor.publisher.SummaryRequestEventPublisher
import com.ajouin.noticecontentscraper.repository.TempNoticeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OcrResponseEventListener(
    private val summaryRequestEventPublisher: SummaryRequestEventPublisher,
    private val tempNoticeRepository: TempNoticeRepository,
    private val objectMapper: ObjectMapper,
) {
    @SqsListener("\${events.queues.ocr-response-queue}")
    fun receiveContentResponse(message: String) {
        val response: OcrResponse = objectMapper.readValue(message, OcrResponse::class.java)
        logger.info { "Received message: ${response.id}" }

        val tempNotice: TempNotice = tempNoticeRepository.findByIdOrNull(response.id)
            ?: throw NoticeNotFoundException()
        tempNotice.content += "첨부된 이미지 내용: " + response.content.map { it }

        summaryRequestEventPublisher.publish(
            SummaryRequestCreatedEvent(
                id = tempNotice.id,
                content = tempNotice.content,
            )
        )
    }
}