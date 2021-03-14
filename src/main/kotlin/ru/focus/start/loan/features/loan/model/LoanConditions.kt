package ru.focus.start.loan.features.loan.model

import java.math.BigDecimal
import javax.persistence.Embeddable

@Embeddable
data class LoanConditions(
        var percent: Double? = null,
        var period: Int? = null,
        var maxAmount: BigDecimal? = null,
)