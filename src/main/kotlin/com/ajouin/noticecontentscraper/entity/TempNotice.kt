package com.ajouin.noticecontentscraper.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
class TempNotice(
    val fetchId: Long,
    val isTopFixed: Boolean,
    val title: String,

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    var content: String,

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    val html: String,
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    val originalUrl: String,

    @Enumerated(EnumType.STRING)
    val noticeType: NoticeType,
    @Id
    val id: UUID,
)