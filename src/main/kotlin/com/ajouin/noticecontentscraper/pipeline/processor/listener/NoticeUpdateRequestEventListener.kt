package com.ajouin.noticecontentscraper.pipeline.processor.listener

import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.NoticeNotFoundException
import com.ajouin.noticecontentscraper.pipeline.processor.event.SchoolNoticeUpdateEvent
import com.ajouin.noticecontentscraper.repository.NoticeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NoticeUpdateRequestEventListener(
    private val objectMapper: ObjectMapper,
    private val noticeRepository: NoticeRepository,
) {

    @SqsListener("\${events.queues.notice-update-request-queue}")
    @Transactional
    fun receiveNoticeRequest(message: String) {
        val response = objectMapper.readValue(message, SchoolNoticeUpdateEvent::class.java)
        logger.info { "Received message: fetchId=${response.fetchId}, noticeType=${response.noticeType}" }

        // 학과 이름에서 숫자 제거
        val noticeTypeString = response.noticeType.toString().replace(Regex("[0-9]"), "")
        response.noticeType = NoticeType.valueOf(noticeTypeString)

        val updateNotice = noticeRepository.findByFetchIdAndNoticeType(response.fetchId, response.noticeType)
            ?: run {
                logger.error { "존재하지 않는 공지사항: fetchId=${response.fetchId}, noticeType=${response.noticeType}, isTopFixed=${response.isTopFixed}" }
                throw NoticeNotFoundException()
            }
        updateNotice.isTopFixed = response.isTopFixed

        noticeRepository.save(updateNotice)
    }
}