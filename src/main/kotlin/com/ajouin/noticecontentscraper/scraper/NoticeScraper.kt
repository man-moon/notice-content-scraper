package com.ajouin.noticecontentscraper.scraper

import com.ajouin.noticecontentscraper.dto.ContentRequest
import com.ajouin.noticecontentscraper.dto.ParsingResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

interface NoticeScraper {
    val selector: String

    fun fetch(contentRequest: ContentRequest): Document {
        return Jsoup.connect(contentRequest.link).get()
    }
    fun parse(document: Document): ParsingResponse {
        val content = document.select(selector)
        val imageTags = content.select("img")

        return ParsingResponse(
            html = content.html(),
            content = content.text(),
            images = getImageLinks(imageTags),
        )
    }

    private fun getImageLinks(images: Elements): List<String> {
        val links = mutableListOf<String>()
        for(image in images) {
            image.absUrl("src").let {
                links.add(it)
            }
        }
        return links
    }
}