package com.ajouin.noticecontentscraper.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
class Notice(

    val fetchId: Long,
    var isTopFixed: Boolean,
    val title: String,
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    val html: String,
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    val summary: String,
    @CreationTimestamp val createdAt: LocalDateTime? = null,
    val originalUrl: String,
    @Enumerated(EnumType.STRING) val noticeType: NoticeType,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)