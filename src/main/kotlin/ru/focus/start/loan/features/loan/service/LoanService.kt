package ru.focus.start.loan.features.loan.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.focus.start.loan.features.auth.model.UserEntity
import ru.focus.start.loan.features.auth.service.UserService
import ru.focus.start.loan.features.loan.model.Loan
import ru.focus.start.loan.features.loan.model.LoanConditions
import ru.focus.start.loan.features.loan.model.LoanRequest
import ru.focus.start.loan.features.loan.model.LoanState
import ru.focus.start.loan.features.loan.repository.LoanRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@Service
class LoanService(
        private val userService: UserService,
        private val loanRepository: LoanRepository
) {

    val allPeriods = listOf(15, 30, 45, 60, 75, 90)
    val allAmounts = listOf(BigDecimal(10000.0), BigDecimal(15000.0), BigDecimal(20000.0), BigDecimal(25000.0), BigDecimal(30000.0))
    val loanStates = listOf(LoanState.APPROVED, LoanState.REJECTED)

    fun createDraft(userEntity: UserEntity): LoanConditions {
        val conditions = LoanConditions(randomPercent(), allPeriods.random(), allAmounts.random())

        val user = userEntity.copy(conditions = conditions)
        userService.save(user)
        return conditions
    }

    fun createLoan(loanRequest: LoanRequest, userEntity: UserEntity): Loan {
        validateConditions(loanRequest, userEntity)

        val previousLoan = userEntity.loans
                .firstOrNull { it.state == LoanState.REGISTERED }
                ?.let {
                    it.copy(state = getLoanStateRandom())
                }

        val loan = Loan(loanRequest.firstName,
                loanRequest.lastName,
                loanRequest.phoneNumber,
                loanRequest.amount.toBigDecimal(),
                loanRequest.percent,
                loanRequest.period,
                Date(),
                LoanState.REGISTERED,
                userEntity
        )

        val loans = listOfNotNull(previousLoan, loan)

        loanRepository.saveAll(loans)

        val updateUser = userEntity.copy(conditions = null)
        userService.save(updateUser)

        return loan
    }

    fun getLoanById(id: Long): Loan? =
            loanRepository.findByIdOrNull(id)

    private fun randomPercent(): Double {
        return (Math.random() * 10 + 5).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
    }

    private fun validateConditions(loanRequest: LoanRequest, userEntity: UserEntity) {
        when {
            userEntity.conditions == null ->
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found conditions. Please request /loans/conditions first")

            loanRequest.percent != userEntity.conditions.percent ->
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan percent=${loanRequest.percent} doesn't match conditions percent=${userEntity.conditions.percent}")

            loanRequest.period != userEntity.conditions.period ->
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan period=${loanRequest.period} doesn't match conditions period=${userEntity.conditions.period}")

            loanRequest.amount.toBigDecimal() > userEntity.conditions.maxAmount ->
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan amount=${loanRequest.amount} more than conditions max amount=${userEntity.conditions.maxAmount}")

            else -> {
            }
        }
    }

    private fun getLoanStateRandom(): LoanState =
            loanStates.random()

}