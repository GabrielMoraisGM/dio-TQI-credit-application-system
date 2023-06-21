package edu.dio.bootcampTQI.credit.application.system.repository

import edu.dio.bootcampTQI.credit.application.system.model.Address
import edu.dio.bootcampTQI.credit.application.system.model.Credit
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRespositoryTest {

    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit


    @BeforeEach fun setup (){
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by code`(){
        //given
        val creditCode1 = UUID.fromString("c28d9d9f-74cb-43c8-b654-d731cceb3d47")
        val creditCode2 = UUID.fromString("5f0251e7-992f-45e9-989e-a64678205503")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        //when
        val fakeCredit1: Credit = creditRepository.findByCreditCode(customer.idCustomer!!,creditCode1)!!
        val fakeCredit2: Credit = creditRepository.findByCreditCode(customer.idCustomer!!,creditCode2)!!

        // then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)
        Assertions.assertThat(fakeCredit1.customer).isSameAs(credit1.customer)
        Assertions.assertThat(fakeCredit2.customer).isSameAs(credit2.customer)
    }

    @Test
    fun `should find all credits by customer id`(){
        //given
        val customerId: Long = 1L

        //when
        val creditList: List<Credit> = creditRepository.findAllByCustomerId(customerId)

        //then
        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList.size).isEqualTo(2)
        Assertions.assertThat(creditList).contains(credit1,credit2)
    }

    private fun buildCustomer(
        firstName: String = "Gabriel",
        lastName: String = "Morais",
        cpf: String = "84622223546",
        email: String = "gm@email.com",
        password: String = "12345",
        zipCode: String = "000297",
        street: String = "Rua de teste",
        income: BigDecimal = BigDecimal.valueOf(1800.0),
    ):Customer = Customer (
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street
        ),
        income = income,
    )

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(1000.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.DECEMBER, 25),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )
}