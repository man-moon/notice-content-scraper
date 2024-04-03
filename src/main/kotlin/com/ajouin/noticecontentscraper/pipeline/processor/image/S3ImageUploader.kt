package com.ajouin.noticecontentscraper.pipeline.processor.image

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.ByteArrayInputStream
import java.util.*
import kotlin.io.path.Path

@Component
class S3ImageUploader(
    @Value("\${s3.bucket-name}")
    private val bucket: String,
    @Value("\${spring.cloud.aws.region.static}")
    private val region: String,
    private val s3Client: S3Client,
) {

    fun upload(images: List<DownloadedFile>): List<String> {
        val imageKeys = mutableListOf<String>()

        images.forEach { image ->
            image.let {
                val requestBody = RequestBody.fromBytes(image.bytes)
                val extension = getContentType(image.extension)

                val fileKey = UUID.randomUUID().toString()
                val putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .contentType(extension)
                    .key(fileKey)
                    .build()

                imageKeys.add("https://$bucket.s3.$region.amazonaws.com/$fileKey")
                s3Client.putObject(putObjectRequest, requestBody)
            }
        }

        return imageKeys
    }

    private fun getContentType(extension: String) = when (extension.lowercase(Locale.getDefault())) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "bmp" -> "image/bmp"
        "tiff", "tif" -> "image/tiff"
        "webp" -> "image/webp"
        "svg" -> "image/svg+xml"
        else -> "application/octet-stream"
    }
}