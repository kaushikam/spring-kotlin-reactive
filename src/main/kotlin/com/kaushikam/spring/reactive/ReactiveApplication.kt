package com.kaushikam.spring.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
class ReactiveApplication


fun main(args: Array<String>) {
    runApplication<ReactiveApplication>(*args)
}