package com.ajouin.noticecontentscraper.dto

import com.ajouin.noticecontentscraper.entity.NoticeType
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

data class ContentRequest @JsonCreator constructor (
    @JsonProperty("title") val title: String,
    @JsonProperty("link") val link: String,
    @JsonProperty("isTopFixed") @get:JsonProperty("isTopFixed")
    val isTopFixed: Boolean,
    @JsonProperty("views") val views: Long,
    @JsonProperty("noticeType") @Enumerated(EnumType.STRING)
    var noticeType: NoticeType,
    @JsonProperty("fetchId") val fetchId: Long,
    @JsonProperty("id") val id: Long?,
)
