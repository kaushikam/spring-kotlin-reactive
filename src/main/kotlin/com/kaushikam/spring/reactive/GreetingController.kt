package com.kaushikam.spring.reactive

import kotlinx.coroutines.*
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@RestController
class GreetingController {
    @GetMapping("/hello")
    suspend fun hello(): String {
        return coroutineScope {
            val list = mutableListOf<Deferred<String>>()
            for (i in 1..100) {
                list.add(async {
                    client().get().uri("/get")
                            .accept(MediaType.APPLICATION_JSON)
                            .awaitExchange().awaitBody<String>()
                })
            }
            list.map { it.await() + "\n" }.joinToString(",")
        }
    }

    private suspend fun client(): WebClient {
        return WebClient.builder()
                .baseUrl("https://httpbin.org")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }
}