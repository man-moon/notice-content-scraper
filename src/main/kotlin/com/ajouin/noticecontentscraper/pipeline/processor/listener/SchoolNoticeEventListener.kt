package com.ajouin.noticecontentscraper.pipeline.processor.listener

import com.ajouin.noticecontentscraper.dto.ContentRequest
import com.ajouin.noticecontentscraper.dto.ParsingResponse
import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.global.GlobalExceptionHandler
import com.ajouin.noticecontentscraper.pipeline.processor.NoticeDataProcessor
import com.ajouin.noticecontentscraper.scraper.ScraperFactory
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SchoolNoticeEventListener (
    private val noticeDataProcessor: NoticeDataProcessor,
    private val globalExceptionHandler: GlobalExceptionHandler,
) {
//    @SqsListener("\${events.queues.content-request-queue}")
//    suspend fun receiveContentRequest(message: String) {
//        try {
//            noticeDataProcessor.processContentRequest(message)
//        } catch (e: Exception) {
//            globalExceptionHandler.handleException(e)
//        }
//    }
    @SqsListener("\${events.queues.content-request-queue}")
    fun receiveContentRequest(message: String) {
        try {
            noticeDataProcessor.processContentRequest(message)
        } catch (e: Exception) {
            globalExceptionHandler.handleException(e)
        }
    }
}