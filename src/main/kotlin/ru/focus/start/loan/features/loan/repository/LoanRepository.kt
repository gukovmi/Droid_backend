package ru.focus.start.loan.features.loan.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import ru.focus.start.loan.features.loan.model.Loan

@Repository
@RepositoryRestResource(exported = false)
interface LoanRepository : CrudRepository<Loan, Long>