package ru.focus.start.loan.features.loan.model

import java.math.BigDecimal

data class LoanRequest(
	val firstName: String,
	val lastName: String,
	val phoneNumber: String,
	val amount: Long,
	val percent: Double,
	val period: Int,
)