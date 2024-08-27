package com.ajouin.noticecontentscraper.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*

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
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    val content: String,
    val date: Date,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)