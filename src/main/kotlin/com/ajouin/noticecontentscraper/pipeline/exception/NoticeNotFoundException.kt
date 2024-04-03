package com.ajouin.noticecontentscraper.pipeline.exception

import com.ajouin.noticecontentscraper.global.exception.EntityNotFoundException
import com.ajouin.noticecontentscraper.global.exception.ErrorCode

open class NoticeNotFoundException() : EntityNotFoundException(ErrorCode.NOTICE_NOT_FOUND)