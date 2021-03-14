package ru.focus.start.loan.features.auth.extensions

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ResponseStatusException
import ru.focus.start.loan.features.auth.model.UserEntity

fun Authentication.getUser(): UserEntity =
	(principal as? UserEntity) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")

fun getUser(): UserEntity =
	SecurityContextHolder.getContext().authentication.getUser()