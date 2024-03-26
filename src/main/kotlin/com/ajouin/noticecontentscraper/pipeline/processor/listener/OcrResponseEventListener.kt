package com.ajouin.noticecontentscraper.pipeline.processor.listener

import com.ajouin.noticecontentscraper.pipeline.processor.NoticeDataProcessor
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class OcrResponseEventListener {
    @SqsListener("\${events.queues.ocr-response-queue}")
    fun receiveContentRequest(message: String) {
        // ocr-request 큐에 삽입
    }
}