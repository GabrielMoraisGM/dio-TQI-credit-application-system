package edu.dio.bootcampTQI.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import edu.dio.bootcampTQI.credit.application.system.dto.CreditDto
import edu.dio.bootcampTQI.credit.application.system.dto.CustomerDto
import edu.dio.bootcampTQI.credit.application.system.model.Customer
import edu.dio.bootcampTQI.credit.application.system.repository.CreditRepository
import edu.dio.bootcampTQI.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {

    @Autowired private lateinit var creditRepository: CreditRepository
    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object{
        const val URL= "/api/credit"
    }

    @BeforeEach fun setup() {
        customerRepository.deleteAll()
        creditRepository.deleteAll()
    }
    @AfterEach fun tearDown(){
        customerRepository.deleteAll()
        creditRepository.deleteAll()
    }

    @Test
    fun `should create a credit and return 201 status`(){

        //given
        val customerFake: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val fakeCreditDto: CreditDto = buildCreditDto(
            dayFirstOfInstallment = LocalDate.of(2023, 12, 25),
            customerId = customerFake.idCustomer!!)
        val fakeCreditDtoString: String = objectMapper.writeValueAsString(fakeCreditDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("$URL")
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCreditDtoString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value("10000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallment").value("5"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value("2023-12-25"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.idCustomer").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incomeCustomer").value("2000"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a credit with wrong customer ID and return 400 status`(){
        //given
        val fakeCreditDto: CreditDto = buildCreditDto(
            dayFirstOfInstallment = LocalDate.of(2023, 12, 25),
            customerId = 1L)
        val fakeCreditDtoString: String = objectMapper.writeValueAsString(fakeCreditDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCreditDtoString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Failed to locate entity"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class org.springframework.orm.jpa.JpaObjectRetrievalFailureException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a credit with past date ID and return 400 status`(){
        //given
        val customerFake: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val fakeCreditDto: CreditDto = buildCreditDto(
            dayFirstOfInstallment = LocalDate.now(),
            customerId = customerFake?.idCustomer!!)
        val fakeCreditDtoString: String = objectMapper.writeValueAsString(fakeCreditDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCreditDtoString))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Unprocessable entity"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("422"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find credit information by id`(){
        //given
        val customerFake: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val savedCredit = creditRepository.save(buildCreditDto(
            dayFirstOfInstallment = LocalDate.of(2023, 12, 25),
            customerId = customerFake.idCustomer!!
        ).toEntity())

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${savedCredit.creditCode}?customerId=${customerFake.idCustomer}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value("10000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallment").value("5"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value("2023-12-25"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incomeCustomer").value("2000"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find credit information with wrong id`(){
        //given
        val wrongCustomerID = 2L
        val customerFake: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val savedCredit = creditRepository.save(buildCreditDto(
            dayFirstOfInstallment = LocalDate.of(2023, 12, 25),
            customerId = customerFake.idCustomer!!
        ).toEntity())

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${savedCredit.creditCode}?customerId=$wrongCustomerID")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Failed to locate customer"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class java.util.NoSuchElementException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }
    private fun buildCustomerDto(
        firstName: String = "Gabriel",
        lastName: String = "Morais",
        cpf: String = "84622223546",
        income: BigDecimal = BigDecimal.valueOf(2000),
        email: String = "gabriel@email.com",
        password: String = "123456",
        zipCode: String = "01928",
        street: String = "Rua do GM",
    ):CustomerDto = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        income = income,
        email = email,
        password = password,
        zipCode = zipCode,
        street = street,
    )

    private fun buildCreditDto(
        creditValue: BigDecimal = BigDecimal.valueOf(10000),
        dayFirstOfInstallment: LocalDate,
        numberOfInstallment: Int = 5,
        customerId: Long
        ): CreditDto = CreditDto(
            creditValue = creditValue,
            dayFirstOfInstallment = dayFirstOfInstallment,
            numberOfInstallment = numberOfInstallment,
            customerId = customerId
    )
}