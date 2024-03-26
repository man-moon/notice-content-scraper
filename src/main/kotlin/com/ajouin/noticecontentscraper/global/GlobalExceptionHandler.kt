package com.ajouin.noticecontentscraper.global

import com.ajouin.noticecontentscraper.entity.DownloadTask
import com.ajouin.noticecontentscraper.global.exception.BusinessException
import com.ajouin.noticecontentscraper.pipeline.exception.ImageDownloadException
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.ScraperNotFoundException
import com.ajouin.noticecontentscraper.repository.DownloadTaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.net.BindException

@Service
class GlobalExceptionHandler(
    private val downloadTaskRepository: DownloadTaskRepository,
) {

    @Transactional
    fun handleException(e: ImageDownloadException) {
        logger.error { "이미지 다운로드 실패: ${e.url}" }
    }

    fun handleException(e: ScraperNotFoundException) {
        logger.error { "${e.scraper} 이름의 스크래퍼를 찾을 수 없습니다" }
    }

    fun handleException(e: BusinessException) {

    }

    fun handleException(e: Exception) {

    }

    fun handleException(e: AccessDeniedException) {

    }

    fun handleException(e: HttpRequestMethodNotSupportedException) {

    }

    fun handleException(e: MethodArgumentTypeMismatchException) {

    }
    
    fun handleException(e: BindException) {

    }
    
    fun handleException(e: MethodArgumentNotValidException) {

    }
}