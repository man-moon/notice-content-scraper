package com.ajouin.noticecontentscraper.repository

import com.ajouin.noticecontentscraper.entity.Notice
import com.ajouin.noticecontentscraper.entity.NoticeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : JpaRepository<Notice, Long> {
    fun findByFetchIdAndNoticeType(fetchId: Long, noticeType: NoticeType): Notice?

}