package com.ajouin.noticecontentscraper.global.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력"),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메소드"),
    ENTITY_NOT_FOUND(400, "C003", "대상을 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 에러"),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입"),
    HANDLE_ACCESS_DENIED(403, "C006", "해당 리소스에 권한이 없습니다"),
    NOT_VERIFIED(403, "C007", "인증되지 않은 사용자"),
    INVALID_TOKEN(401, "C008", "유효하지 않은 토큰"),
    TOKEN_EXPIRED(401, "C009", "만료된 토큰"),

    // scraping
    IMAGE_DOWNLOAD_FAILED(400, "I001", "이미지 다운로드 실패"),
    SCRAPER_NOT_FOUND(400, "S001", "존재하지 않는 스크래퍼"),
}