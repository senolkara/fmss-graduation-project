package com.senolkarakurt.customerservice.service;

import com.senolkarakurt.customerservice.client.service.AuthenticationClientService;
import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.repository.CustomerRepository;
import com.senolkarakurt.customerservice.service.impl.CustomerServiceImpl;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.exception.CommonException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private AuthenticationClientService authenticationClientService;

    @Test
    public void testCreateCustomer() {
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(authenticationClientService.register(Mockito.any())).thenReturn(prepareUser());
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(Instancio.create(Customer.class));
        customerService.save(prepareCustomerRequestDto());
        verify(customerRepository, times(1)).save(Mockito.any(Customer.class));
    }

    @Test
    public void testGetCustomerById() {
        Customer customer1 = prepareCustomer();
        when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer1));
        Customer customer = customerService.getCustomerById(Mockito.any());
        assertNotEquals(customer, null);
        assertEquals(customer.getAccountType(), AccountType.STANDARD);
    }

    @Test
    public void testGetInvalidCustomerById() {
        when(customerRepository.findById(Mockito.any())).thenThrow(new CommonException("Bu id ile kay?tl? mü?teri bulunamad?"));
        Exception exception = assertThrows(CommonException.class, () -> {
            customerService.getCustomerById(Mockito.any());
        });
        assertTrue(exception.getMessage().contains("Bu id ile kay?tl? mü?teri bulunamad?"));
    }

    @Test
    public void testChangeAccountTypeAndScore() {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(Instancio.create(Customer.class));
        customerService.changeAccountTypeAndScore(prepareCustomerUpdateRequestDto());
        verify(customerRepository, times(1)).save(Mockito.any(Customer.class));
    }

    private CustomerRequestDto prepareCustomerRequestDto(){
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setId(1L);
        customerRequestDto.setUserRequestDto(prepareUserRequestDto());
        return customerRequestDto;
    }

    private UserRequestDto prepareUserRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(1L);
        return userRequestDto;
    }

    private Customer prepareCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setScore(0);
        return customer;
    }

    private User prepareUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }

    private CustomerUpdateRequestDto prepareCustomerUpdateRequestDto(){
        CustomerUpdateRequestDto customerUpdateRequestDto = new CustomerUpdateRequestDto();
        customerUpdateRequestDto.setCustomer(prepareCustomer());
        customerUpdateRequestDto.setAccountType(AccountType.STANDARD);
        customerUpdateRequestDto.setScore(0);
        return customerUpdateRequestDto;
    }

}
