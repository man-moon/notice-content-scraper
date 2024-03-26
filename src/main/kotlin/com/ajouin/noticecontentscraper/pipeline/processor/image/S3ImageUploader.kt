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

    fun upload(images: List<ByteArray?>): List<String> {
        val imageKeys = mutableListOf<String>()

        images.forEach { image ->
            image?.let {
                val requestBody = RequestBody.fromBytes(image)

                val fileKey = UUID.randomUUID().toString()
                val putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileKey)
                    .build()

                imageKeys.add(fileKey)
                s3Client.putObject(putObjectRequest, requestBody)
            }
        }

        return imageKeys
    }
}