package ru.focus.start.loan.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import ru.focus.start.loan.configuration.auth.AuthorizationFilter
import ru.focus.start.loan.features.auth.service.UserService

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
        private val userService: UserService,
        private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {


    override fun configure(http: HttpSecurity?) {
        http ?: return
        super.authenticationManagerBean()

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration", "/login").not().fullyAuthenticated()
                .antMatchers(
                        "/",
                        "/actuator/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/api-docs/**",
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(AuthorizationFilter(userService, authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }
}