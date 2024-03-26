package com.ajouin.noticecontentscraper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class NoticeContentScraperApplication

fun main(args: Array<String>) {
	runApplication<NoticeContentScraperApplication>(*args)
}
