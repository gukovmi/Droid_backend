package ru.focus.start.loan.features.loan.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.focus.start.loan.features.auth.extensions.getUser
import ru.focus.start.loan.features.loan.model.Loan
import ru.focus.start.loan.features.loan.model.LoanConditions
import ru.focus.start.loan.features.loan.model.LoanRequest
import ru.focus.start.loan.features.loan.service.LoanService

@RestController
@RequestMapping("/loans")
class LoanController(
        private val loanService: LoanService,
) {

    @ApiOperation("Get loans list")
    @GetMapping("/all")
    fun getLoans(): List<Loan> {
        val user = getUser()
        return user.loans
    }

    @ApiOperation("Get loan conditions")
    @GetMapping("/conditions")
    fun getConditions(): LoanConditions {
        val conditions = loanService.createDraft(getUser())
        return conditions
    }

    @ApiOperation("Create new loan")
    @PostMapping("")
    fun saveLoan(@RequestBody request: LoanRequest): Loan {
        val loan = loanService.createLoan(request, getUser())
        return loan
    }

    @ApiOperation("Get loan data")
    @GetMapping("/{id}")
    fun getLoan(@PathVariable id: Long): ResponseEntity<Loan> {
        val loan = loanService.getLoanById(id)

        return if (loan != null) {
            ResponseEntity.ok(loan)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}