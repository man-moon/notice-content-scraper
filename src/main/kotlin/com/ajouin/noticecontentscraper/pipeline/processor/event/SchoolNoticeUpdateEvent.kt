package com.ajouin.noticecontentscraper.pipeline.processor.event

import com.ajouin.noticecontentscraper.entity.NoticeType
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolNoticeUpdateEvent @JsonCreator constructor(
    @JsonProperty("fetchId") val fetchId: Long,
    @JsonProperty("noticeType") val noticeType: NoticeType,
    @JsonProperty("isTopFixed") val isTopFixed: Boolean,
)
