package com.ajouin.noticecontentscraper.pipeline.processor.event

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class OcrRequestCreatedEvent @JsonCreator constructor (
    @JsonProperty("id") val id: UUID,
    @JsonProperty("imageUrl") val imageUrl: List<String>,
)