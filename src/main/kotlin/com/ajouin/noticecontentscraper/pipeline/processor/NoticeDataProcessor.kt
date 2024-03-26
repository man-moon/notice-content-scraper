package com.ajouin.noticecontentscraper.pipeline.processor

interface NoticeDataProcessor {

    suspend fun processContentRequest(message: String)
}