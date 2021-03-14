package ru.focus.start.loan.features.auth.service

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.focus.start.loan.features.auth.model.UserEntity
import ru.focus.start.loan.features.auth.repository.UserRepository

@Service
class UserService(
	private val jwtService: JwtService,
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder
) {

	fun findByName(name: String): UserEntity? =
		userRepository.findByName(name)

	fun save(userEntity: UserEntity) {
		userRepository.save(userEntity)
	}

	fun login(name: String, password: String): String {
		val user = userRepository.findByName(name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

		if (!passwordEncoder.matches(password, user.password)) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
		}

		return jwtService.createToken(user)
	}

	fun register(name: String, password: String): UserEntity {
		val existingUser = findByName(name)

		if (existingUser != null) {
			throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist")
		}

		val hash = passwordEncoder.encode(password)
		val user = UserEntity(name, hash)
		return userRepository.save(user)
	}
}