package ru.focus.start.loan.configuration.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.server.ResponseStatusException
import ru.focus.start.loan.features.auth.service.UserService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
        private val userService: UserService,
        authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HEADER_STRING)

        if (header?.startsWith("Bearer") == true) {
            val userName = JWT.require(Algorithm.HMAC512(SECRET))
                    .build()
                    .verify(header.removePrefix(TOKEN_PREFIX))
                    .subject

            val user = userService.findByName(userName)
                    ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")

            val auth = UsernamePasswordAuthenticationToken(user, null, listOf(SimpleGrantedAuthority(user.role.name)))
            SecurityContextHolder.getContext().authentication = auth
        }

        chain.doFilter(request, response)
    }
}