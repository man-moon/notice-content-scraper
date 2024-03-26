package com.ajouin.noticecontentscraper.global.exception

open class EntityNotFoundException(
    errorCode: ErrorCode = ErrorCode.ENTITY_NOT_FOUND
) : BusinessException(errorCode)