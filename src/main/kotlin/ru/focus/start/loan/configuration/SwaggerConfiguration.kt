package ru.focus.start.loan.configuration

import io.swagger.annotations.ApiOperation
import io.swagger.models.parameters.Parameter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.RequestParameterBuilder
import springfox.documentation.schema.ScalarType
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.ParameterType
import springfox.documentation.service.RequestParameter
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.Date

@Configuration
class SwaggerConfiguration {

	val apiKey = ApiKey("JWT", "Authorization", "header")

	@Bean
	fun userApi(): Docket =
		Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
			.build()
			.securitySchemes(listOf(apiKey))
			.securityContexts(listOf(securityContext()))
//			.globalRequestParameters(parameters())
			.directModelSubstitute(OffsetDateTime::class.java, Date::class.java)
			.directModelSubstitute(LocalDate::class.java, Date::class.java)


	private fun apiInfo(): ApiInfo =
		ApiInfoBuilder()
			.title("Focus start")
			.description("Example loan backend")
			.license("free")
			.build()

	private fun securityContext(): SecurityContext? {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.regex(".*/loans.*"))
			.build()
	}

	private fun defaultAuth(): List<SecurityReference?>? {
		val authorizationScope = AuthorizationScope("global", "accessEverything")
		val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
		authorizationScopes[0] = authorizationScope
		return listOf(SecurityReference("JWT", authorizationScopes))
	}

	private fun parameters(): List<RequestParameter> {
		val builder = RequestParameterBuilder()
			.name("Auth token")
			.description("JWT token")
			.`in`(ParameterType.HEADER)
			.required(true)
			.build()

		return listOf(builder)
	}
}