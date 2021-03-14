package ru.focus.start.loan.configuration

import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.http.MediaType

class RestConfiguration : RepositoryRestConfigurer {

	override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
		config.defaultMediaType = MediaType.APPLICATION_JSON
		config.useHalAsDefaultJsonMediaType(false)
	}
}