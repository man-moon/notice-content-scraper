package com.ajouin.noticecontentscraper.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class SummaryResponse @JsonCreator constructor (
    @JsonProperty("id") val id: UUID,
    @JsonProperty("content") val content: String,
)