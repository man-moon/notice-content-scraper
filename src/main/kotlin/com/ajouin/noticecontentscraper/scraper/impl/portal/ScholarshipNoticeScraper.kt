package com.ajouin.noticecontentscraper.scraper.impl.portal

import com.ajouin.noticecontentscraper.dto.ParsingResponse
import com.ajouin.noticecontentscraper.scraper.NoticeScraper
import org.jsoup.nodes.Document

class ScholarshipNoticeScraper : NoticeScraper {
    override val selector = ".b-content-box"
}