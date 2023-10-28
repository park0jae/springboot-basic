package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(UUID customerID);
    void delete(UUID customerId);
    void deleteAll();
    void update(UUID customerId, CustomerUpdateRequest customerRequest);
}
