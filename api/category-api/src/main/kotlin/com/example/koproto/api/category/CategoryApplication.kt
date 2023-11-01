package com.example.koproto.api.category

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = [
        "com.example.koproto.infrastructure",
        "com.example.koproto.utility",
        "com.example.koproto.api",
    ]
)
@EnableJpaRepositories
@EntityScan(
    basePackages = [
        "com.example.koproto.infrastructure.database"
    ]
)
class CategoryApplication

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args)
}
