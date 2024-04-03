package com.ajouin.noticecontentscraper.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ParsingResponse @JsonCreator constructor (
    @JsonProperty("html") var html: String = "",
    @JsonProperty("content") val content: String = "",
    @JsonProperty("images") val images: List<String> = mutableListOf(),
)