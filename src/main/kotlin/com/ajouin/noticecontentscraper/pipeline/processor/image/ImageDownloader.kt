package com.ajouin.noticecontentscraper.pipeline.processor.image

import com.ajouin.noticecontentscraper.entity.DownloadTask
import com.ajouin.noticecontentscraper.logger
import com.ajouin.noticecontentscraper.pipeline.exception.ImageDownloadException
import com.ajouin.noticecontentscraper.repository.DownloadTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class ImageDownloader(
    private val httpClient: OkHttpClient,
    private val downloadTaskRepository: DownloadTaskRepository,
) {

    suspend fun download(urls: List<String>): List<ByteArray?> = coroutineScope {
        urls.map { url ->
            async(Dispatchers.IO) {
                try {
                    val request = Request.Builder().url(url).build()
                    httpClient.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) throw ImageDownloadException(url)
                        response.body?.bytes() ?: throw ImageDownloadException(url)
                    }
                } catch (e: ImageDownloadException) {
                    imageDownloadExceptionHandler(e)
                    null
                }
            }
        }.awaitAll()
    }

    private fun imageDownloadExceptionHandler(e: ImageDownloadException) {
        logger.error { "이미지 다운로드 실패: ${e.url}" }

        val task = downloadTaskRepository.findByUrl(e.url)
            ?: DownloadTask(e.url)
        task.attempt++
        downloadTaskRepository.save(task)
    }
}