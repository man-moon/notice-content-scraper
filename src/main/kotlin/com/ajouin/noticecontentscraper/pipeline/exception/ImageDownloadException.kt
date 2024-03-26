package com.ajouin.noticecontentscraper.pipeline.exception

import com.ajouin.noticecontentscraper.global.exception.EntityNotFoundException
import com.ajouin.noticecontentscraper.global.exception.ErrorCode

open class ImageDownloadException(
    val url: String,
) : EntityNotFoundException(ErrorCode.IMAGE_DOWNLOAD_FAILED)