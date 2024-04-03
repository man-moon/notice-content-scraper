package com.ajouin.noticecontentscraper.pipeline.processor.event

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class SummaryRequestCreatedEvent @JsonCreator constructor (
    @JsonProperty("id") val id: UUID,
    @JsonProperty("content") val content: String,
)