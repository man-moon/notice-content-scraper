package com.ajouin.noticecontentscraper.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfig {

    @Bean
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}