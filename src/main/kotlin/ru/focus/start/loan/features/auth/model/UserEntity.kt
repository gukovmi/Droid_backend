package ru.focus.start.loan.features.auth.model

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.focus.start.loan.features.loan.model.Loan
import ru.focus.start.loan.features.loan.model.LoanConditions
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.*
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OrderBy

@Entity(name = "users")
data class UserEntity(
	@Column(unique = true)
	val name: String,

	@JsonIgnore
	val password: String,

	@Enumerated(EnumType.STRING)
	val role: UserRole = UserRole.USER,

	@OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "userEntity", fetch = FetchType.EAGER)
	@JsonIgnore
	@OrderBy("date DESC")
	val loans: List<Loan> = listOf(),

	@Embedded
	@JsonIgnore
	val conditions: LoanConditions? = null,

	@Id
	@GeneratedValue(strategy = AUTO)
	@JsonIgnore
	val id: Long = 0
) {


}