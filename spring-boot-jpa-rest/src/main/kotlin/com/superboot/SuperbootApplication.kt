package com.superboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication


@SpringBootApplication
class SuperbootApplication{
}

fun main(args: Array<String>) {
    runApplication<SuperbootApplication>(*args)
}
