package ru.focus.start.loan.features.auth.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.focus.start.loan.features.auth.model.UserEntity

@Repository
@RepositoryRestResource(exported = false)
interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findByName(name: String): UserEntity?
}