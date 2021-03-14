package ru.focus.start.loan.features.auth.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.focus.start.loan.features.auth.model.Auth
import ru.focus.start.loan.features.auth.model.UserEntity
import ru.focus.start.loan.features.auth.service.UserService

@RestController
class AuthController(
        private val userService: UserService
) {

    @ApiOperation("Login into app")
    @PostMapping("/login")
    fun login(@RequestBody auth: Auth): String {
        val name = auth.name ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No 'name' field provided")
        val password = auth.password
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No 'password' field provided")
        return userService.login(name, password)
    }

    @ApiOperation("Register in app")
    @PostMapping("/registration")
    fun register(@RequestBody auth: Auth): UserEntity {
        val name = auth.name ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No 'name' field provided")
        val password = auth.password
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No 'password' field provided")

        return userService.register(name, password)
    }
}