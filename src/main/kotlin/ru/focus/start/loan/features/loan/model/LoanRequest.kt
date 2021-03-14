package ru.focus.start.loan.features.loan.model

data class LoanRequest(
        val firstName: String,
        val lastName: String,
        val phoneNumber: String,
        val amount: Long,
        val percent: Double,
        val period: Int,
)