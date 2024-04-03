package com.ajouin.noticecontentscraper.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class OcrResponse @JsonCreator constructor (
    @JsonProperty("id") val id: UUID,
    @JsonProperty("content") val content: List<String>,
)