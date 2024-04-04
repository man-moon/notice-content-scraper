package com.ajouin.noticecontentscraper.pipeline.processor.publisher

import com.ajouin.noticecontentscraper.config.EventQueuesProperties
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.processor.event.OcrRequestCreatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.stereotype.Service

@Service
class OcrRequestEventPublisher(
    private val sqsTemplate: SqsTemplate,
    private val eventQueuesProperties: EventQueuesProperties,
    private val objectMapper: ObjectMapper,
    ) {

    fun publish(event: OcrRequestCreatedEvent) {

        val messagePayload = objectMapper.writeValueAsString(event)

        sqsTemplate.send { to ->
            to.queue(eventQueuesProperties.ocrRequestQueue)
                .payload(messagePayload)
        }

        logger.info { "Message sent with payload ${event.id}" }

    }
}