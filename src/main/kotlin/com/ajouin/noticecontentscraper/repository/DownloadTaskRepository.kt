package com.ajouin.noticecontentscraper.repository

import com.ajouin.noticecontentscraper.entity.DownloadTask
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DownloadTaskRepository : JpaRepository<DownloadTask, Long> {
    fun findByUrl(url: String): DownloadTask?
}