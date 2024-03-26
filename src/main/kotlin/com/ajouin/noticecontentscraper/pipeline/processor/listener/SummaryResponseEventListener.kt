package com.ajouin.noticecontentscraper.pipeline.processor.listener

import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SummaryResponseEventListener {
    @SqsListener("\${events.queues.summary-response-queue}")
    fun receiveContentRequest(message: String) {
        // 값 저장
    }
}