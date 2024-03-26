package com.ajouin.noticecontentscraper.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "events.queues")
class EventQueuesProperties (
    val contentRequestQueue: String,
    val ocrRequestQueue: String,
    val ocrResponseQueue: String,
    val summaryRequestQueue: String,
    val summaryResponseQueue: String,
)