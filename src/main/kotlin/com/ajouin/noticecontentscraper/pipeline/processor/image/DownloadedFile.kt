package com.ajouin.noticecontentscraper.pipeline.processor.image

data class DownloadedFile(
    val extension: String,
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadedFile

        if (extension != other.extension) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = extension.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
