package com.ajouin.noticecontentscraper.pipeline.processor.image

import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.ImageDownloadException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class ImageDownloader(
    private val httpClient: OkHttpClient,
) {

    fun download(urls: List<String>): List<DownloadedFile> {
        return urls.map { url ->
            try {
                val request = Request.Builder().url(url).build()
                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw ImageDownloadException(url)

                    val extension = url.substringAfterLast('.', "")
                    if (extension.isNotEmpty() && response.body != null) {
                        DownloadedFile(extension, response.body!!.bytes())
                    } else {
                        throw ImageDownloadException(url)
                    }
                }
            } catch (e: ImageDownloadException) {
                logger.info { "이미지 다운로드 실패: $url, 30초 이후 재시도" }
                throw e
            }
        }
    }
}