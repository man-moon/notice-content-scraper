package com.ajouin.noticecontentscraper.repository

import com.ajouin.noticecontentscraper.entity.TempNotice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TempNoticeRepository : JpaRepository<TempNotice, UUID> {

}
