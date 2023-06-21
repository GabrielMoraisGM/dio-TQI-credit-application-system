package edu.dio.bootcampTQI.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import edu.dio.bootcampTQI.credit.application.system.dto.*
import edu.dio.bootcampTQI.credit.application.system.model.Customer
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
import kotlin.random.Random


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {

    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object{
        const val URL = "/api/customer"
    }

    @BeforeEach fun setup() = customerRepository.deleteAll()
    @AfterEach fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should create a customer and return 201 status`(){
        //given
        val fakeCustomerDto: CustomerDto = buildCustomerDto();
        val fakeCustomerDtoString: String = objectMapper.writeValueAsString(fakeCustomerDto)

        //when
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCustomerDtoString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gabriel"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Morais"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("84622223546"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("2000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("01928"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do GM"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print())
        //then
    }

    @Test
    fun `should not save a customer with same CPF and return 409 status`(){
        //given
        customerRepository.save(buildCustomerDto().toEntity())
        val fakeCustomerDto: CustomerDto = buildCustomerDto();
        val fakeCustomerDtoString: String = objectMapper.writeValueAsString(fakeCustomerDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCustomerDtoString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict! Consult documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("409"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with firstName empty and return 400 status`(){
        //given
        val customerDto: CustomerDto = buildCustomerDto(firstName = "")
        val fakeCustomerDtoString: String = objectMapper.writeValueAsString(customerDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCustomerDtoString))
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
    fun `should find customer by id and return 200 status`(){
        //given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.idCustomer}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gabriel"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Morais"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("84622223546"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("2000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("01928"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do GM"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find customer with invalid id and return 404 status`(){
        //given
        val invalidId: Long = 2L
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/$invalidId")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Client not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class edu.dio.bootcampTQI.credit.application.system.exception.NotFoundException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should delete customer by id`(){
        //given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${customer.idCustomer}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete by id with wrong id and return 404 status`(){
        //given
        val invalidId: Long = Random.nextLong()
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/$invalidId")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Client not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception").value("class edu.dio.bootcampTQI.credit.application.system.exception.NotFoundException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should update a customer by id and return 200 ` (){
        //given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val fakeCustomerUpdateDtoString: String = objectMapper.writeValueAsString(customerUpdateDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=${customer.idCustomer}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCustomerUpdateDtoString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gabriel UPDATED"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Morais UPDATED"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("84622223546"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("10000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("07222"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do GM UPDATE"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `can not update customer with invalid customer ID`(){
        //given
        val invalidId: Long = Random.nextLong()
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val fakeCustomerUpdateDtoString: String = objectMapper.writeValueAsString(customerUpdateDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(fakeCustomerUpdateDtoString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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

    private fun builderCustomerUpdateDto(
        firstName: String = "Gabriel UPDATED",
        lastName: String = "Morais UPDATED",
        income: BigDecimal = BigDecimal.valueOf(10000),
        zipCode: String = "07222",
        street: String = "Rua do GM UPDATE",
    ):CustomerUpdateDto = CustomerUpdateDto(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street,
    )
}