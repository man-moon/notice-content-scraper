package com.ajouin.noticecontentscraper.dto

data class ParsingResponse (
    var html: String = "",
    val content: String = "",
    val images: List<String> = mutableListOf(),
)

//table1(임시 저장용 테이블): id, title, html(content + imageUrls), createdAt, originalUrl, noticeType
//table2(source connector listening 테이블): id, content, [imageUrls]   <- source connector listening this table

// 1. 데이터 전처리 끝낸 후,
// table1에 데이터 넣어두기.

// 2. <ocr-request-queue INPUT>
// id, imageLinks

// 3. <ocr-response-queue OUTPUT>
// id, textContent

// 4. 기존의 content에서 이미지 위치를 찾아 textContent 삽입

// 5. <summary-request-queue INPUT>
// id, 기존 content + 이미지에서 뽑아낸 textContent

// 6. <summary-response-queue OUTPUT>
// id, summary


// 7. table2에 삽입
// id, title, html(content + imageUrls), summary, createdAt, originalUrl, noticeType



// 더 보기 편하도록 자체적으로 파싱해서 보여주기? 너무 변수가 많지 않을까