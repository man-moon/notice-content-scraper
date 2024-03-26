package com.ajouin.noticecontentscraper.global.exception

open class BusinessException(val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR): RuntimeException()
