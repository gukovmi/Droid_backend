package ru.focus.start.loan.features.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Service
import ru.focus.start.loan.configuration.auth.SECRET
import ru.focus.start.loan.features.auth.model.UserEntity
import java.util.Date
import java.util.concurrent.TimeUnit

@Service
class JwtService {

	fun createToken(user: UserEntity): String {
		val token = JWT.create()
			.withSubject(user.name)
			.withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)))
			.sign(Algorithm.HMAC512(SECRET))

		return "Bearer $token"
	}

	fun getUserName(token: String): String =
		JWT.require(Algorithm.HMAC512(SECRET))
			.build()
			.verify(token)
			.subject
}