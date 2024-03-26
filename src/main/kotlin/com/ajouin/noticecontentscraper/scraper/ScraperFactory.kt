package com.ajouin.noticecontentscraper.scraper

import com.ajouin.noticecontentscraper.scraper.impl.college.NursingScraper
import com.ajouin.noticecontentscraper.scraper.impl.department.SoftwareNoticeScraper
import com.ajouin.noticecontentscraper.scraper.impl.portal.DormitoryNoticeScraper
import com.ajouin.noticecontentscraper.scraper.impl.portal.GeneralNoticeScraper
import com.ajouin.noticecontentscraper.scraper.impl.portal.ScholarshipNoticeScraper
import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.entity.NoticeType.*
import org.springframework.stereotype.Component

@Component
class ScraperFactory {
    private val scrapers: Map<NoticeType, NoticeScraper> = mapOf(
        일반공지 to GeneralNoticeScraper(),
        장학공지 to ScholarshipNoticeScraper(),
        생활관 to DormitoryNoticeScraper(),
        간호대학 to NursingScraper(),

        소프트웨어학과 to SoftwareNoticeScraper(),

    )

    fun getScraper(noticeType: NoticeType): NoticeScraper? = scrapers[noticeType]
}