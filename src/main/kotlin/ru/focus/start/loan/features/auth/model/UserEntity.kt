package ru.focus.start.loan.features.auth.model

import net.minidev.json.annotate.JsonIgnore
import ru.focus.start.loan.features.loan.model.Loan
import ru.focus.start.loan.features.loan.model.LoanConditions
import javax.persistence.*
import javax.persistence.GenerationType.AUTO

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