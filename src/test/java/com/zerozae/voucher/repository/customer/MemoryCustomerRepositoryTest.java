package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.domain.customer.CustomerType.BLACKLIST;
import static com.zerozae.voucher.domain.customer.CustomerType.NORMAL;
import static org.junit.jupiter.api.Assertions.*;


class MemoryCustomerRepositoryTest {

    private final CustomerRepository customerRepository;
    private Customer normalCustomer;
    private Customer blacklistCustomer;

    MemoryCustomerRepositoryTest() {
        this.customerRepository = new MemoryCustomerRepository();
    }

    @BeforeEach
    void setUp(){
        normalCustomer = new Customer(UUID.randomUUID(), "normalUser", NORMAL);
        blacklistCustomer= new Customer(UUID.randomUUID(), "blackUser", BLACKLIST);
    }

    @AfterEach
    void cleanUp(){
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void saveCustomer_Success_Test() {
        // Given

        // When
        Customer savedCustomer = customerRepository.save(normalCustomer);

        // Then
        assertEquals(savedCustomer.getCustomerName(), normalCustomer.getCustomerName());
        assertEquals(savedCustomer.getCustomerType(), normalCustomer.getCustomerType());
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    void findAllCustomers_Success_Test() {
        // Given
        customerRepository.save(normalCustomer);
        customerRepository.save(blacklistCustomer);

        // When
        List<Customer> customers = customerRepository.findAll();

        // Then
        assertTrue(customers.contains(normalCustomer));
        assertTrue(customers.contains(blacklistCustomer));
    }

    @Test
    @DisplayName("회원 아이디 조회 테스트")
    void findCustomerById_Success_Test() {
        // Given
        customerRepository.save(normalCustomer);
        customerRepository.save(blacklistCustomer);

        // When
        Optional<Customer> findCustomer = customerRepository.findById(normalCustomer.getCustomerId());

        // Then
        assertEquals(findCustomer.get().getCustomerName(), normalCustomer.getCustomerName());
        assertEquals(findCustomer.get().getCustomerType(), normalCustomer.getCustomerType());
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디 조회 테스트")
    void findCustomerBy_NotExist_Failed_Test() {
        // Given
        customerRepository.save(normalCustomer);
        customerRepository.save(blacklistCustomer);

        // When
        Optional<Customer> findCustomer = customerRepository.findById(UUID.randomUUID());

        // Then
        assertTrue(findCustomer.isEmpty());
    }

    @Test
    @DisplayName("회원 아이디로 회원 삭제 테스트")
    void deleteCustomerById_Success_Test() {
        // Given
        customerRepository.save(normalCustomer);
        customerRepository.save(blacklistCustomer);

        // When
        customerRepository.delete(normalCustomer.getCustomerId());

        // Then
        assertFalse(customerRepository.findAll().contains(normalCustomer));
        assertTrue(customerRepository.findAll().contains(blacklistCustomer));
    }

    @Test
    @DisplayName("전체 회원 삭제 테스트")
    void deleteAllCustomers_Success_Test() {
        // Given
        customerRepository.save(normalCustomer);
        customerRepository.save(blacklistCustomer);

        // When
        customerRepository.deleteAll();

        // Then
        assertFalse(customerRepository.findAll().contains(normalCustomer));
        assertFalse(customerRepository.findAll().contains(blacklistCustomer));
        assertEquals(customerRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void updateCustomer_Success_Test() {
        // Given
        customerRepository.save(normalCustomer);
        CustomerUpdateRequest customerRequest = new CustomerUpdateRequest("진상 고객", String.valueOf(BLACKLIST));

        // When
        customerRepository.update(normalCustomer.getCustomerId(), customerRequest);

        // Then
        assertEquals(normalCustomer.getCustomerName(), customerRequest.getCustomerName());
        assertEquals(normalCustomer.getCustomerType().toString(), customerRequest.getCustomerType());
    }
}
