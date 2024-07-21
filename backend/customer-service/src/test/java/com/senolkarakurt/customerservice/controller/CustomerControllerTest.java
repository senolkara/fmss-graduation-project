package com.senolkarakurt.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.customerservice.client.service.AuthenticationClientService;
import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.service.impl.CustomerServiceImpl;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.RegistrationRequestDto;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private UserClientService userClientService;

    @MockBean
    private AuthenticationClientService authenticationClientService;

    @Test
    public void testCreateCustomer() throws Exception {
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(authenticationClientService.register(Mockito.any())).thenReturn(prepareRegistrationResponseDto());
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareUserRequestDto());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/customers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                ;

        resultActions.andExpect(status().isOk());
        verify(customerService, times(1)).save(Mockito.any(RegistrationRequestDto.class));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(Mockito.any())).thenReturn(prepareCustomer());
        mockMvc.perform(get("/api/v1/customers/id/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
        verify(customerService, times(1)).getCustomerById(Mockito.any());
    }

    @Test
    public void testChangeAccountTypeAndScore() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareCustomerUpdateRequestDto());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/customers/changeAccountTypeAndScore/10")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                ;

        resultActions.andExpect(status().isOk());
        verify(customerService, times(1)).changeAccountTypeAndScore(Mockito.any(), Mockito.any(CustomerUpdateRequestDto.class));
    }

    private UserRequestDto prepareUserRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(1L);
        return userRequestDto;
    }

    private RegistrationResponseDto prepareRegistrationResponseDto(){
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
        registrationResponseDto.setUser(prepareUser());
        return registrationResponseDto;
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
        customerUpdateRequestDto.setAccountType(AccountType.STANDARD);
        customerUpdateRequestDto.setScore(0);
        return customerUpdateRequestDto;
    }
}
