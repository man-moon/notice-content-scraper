package com.ajouin.noticecontentscraper.global.exception

open class InvalidValueException(
    errorCode: ErrorCode = ErrorCode.INVALID_INPUT_VALUE
): BusinessException(errorCode)