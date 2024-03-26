package com.ajouin.noticecontentscraper.global.exception

open class HandleAccessException(
        errorCode: ErrorCode = ErrorCode.HANDLE_ACCESS_DENIED
) : BusinessException(errorCode)