package com.ajouin.noticecontentscraper.pipeline.exception

import com.ajouin.noticecontentscraper.global.exception.EntityNotFoundException

class ScraperNotFoundException(
    val scraper: String,
) : EntityNotFoundException()