package com.ajouin.noticecontentscraper.pipeline.processor.listener

import com.ajouin.noticecontentscraper.dto.SummaryResponse
import com.ajouin.noticecontentscraper.entity.Notice
import com.ajouin.noticecontentscraper.entity.TempNotice
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.NoticeNotFoundException
import com.ajouin.noticecontentscraper.repository.NoticeRepository
import com.ajouin.noticecontentscraper.repository.TempNoticeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SummaryResponseEventListener(
    private val objectMapper: ObjectMapper,
    private val tempNoticeRepository: TempNoticeRepository,
    private val noticeRepository: NoticeRepository,
) {
    @SqsListener("\${events.queues.summary-response-queue}")
    fun receiveContentResponse(message: String) {

        val response: SummaryResponse = objectMapper.readValue(message, SummaryResponse::class.java)
        logger.info { "Received message: ${response.id}" }

        val tempNotice: TempNotice = tempNoticeRepository.findByIdOrNull(response.id)
            ?: throw NoticeNotFoundException()

        noticeRepository.save(
            Notice(
                fetchId = tempNotice.fetchId,
                isTopFixed = tempNotice.isTopFixed,
                title = tempNotice.title,
                html = tempNotice.html,
                summary = response.content,
                content = tempNotice.content,
                originalUrl = tempNotice.originalUrl,
                noticeType = tempNotice.noticeType,
            )
        )

    }
}