package ru.focus.start.loan

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class LoanApplication {

    @Value("{spring.datasource.url}")
    lateinit var url: String

    @PostConstruct
    fun init() {
        print("Database url = $url")
    }
}

fun main(args: Array<String>) {
    runApplication<LoanApplication>(*args)
}
