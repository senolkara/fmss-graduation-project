package com.senolkarakurt.advertisementservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.advertisementservice.client.service.CustomerClientService;
import com.senolkarakurt.advertisementservice.client.service.PackageClientService;
import com.senolkarakurt.advertisementservice.client.service.UserClientService;
import com.senolkarakurt.advertisementservice.component.FakeDataComponent;
import com.senolkarakurt.advertisementservice.model.CPackage;
import com.senolkarakurt.advertisementservice.model.Customer;
import com.senolkarakurt.advertisementservice.model.User;
import com.senolkarakurt.advertisementservice.service.impl.AdvertisementServiceImpl;
import com.senolkarakurt.dto.request.*;
import com.senolkarakurt.enums.AdvertisementType;
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

import java.math.BigDecimal;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdvertisementController.class)
public class AdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementServiceImpl advertisementService;

    @MockBean
    private CustomerClientService customerClientService;

    @MockBean
    private UserClientService userClientService;

    @MockBean
    private PackageClientService packageClientService;

    @MockBean
    private FakeDataComponent fakeDataComponent;

    @Test
    public void testGetAdvertisementListByCustomerId() throws Exception {
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(new Customer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(new User());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(new CPackage());
        mockMvc.perform(get("/api/v1/advertisements/customerId/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
        verify(advertisementService, times(1)).getAllByCustomerId(Mockito.any());
    }

    @Test
    public void testCreateAdvertisement() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareAdvertisementRequestDto());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/advertisements")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                ;

        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).save(Mockito.any(AdvertisementRequestDto.class));
    }

    private AdvertisementRequestDto prepareAdvertisementRequestDto(){
        AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto();
        advertisementRequestDto.setAdvertisementType(AdvertisementType.PURCHASEABLE);
        advertisementRequestDto.setBuildingRequestDto(prepareBuildingRequestDto());
        advertisementRequestDto.setPrice(BigDecimal.TEN);
        advertisementRequestDto.setCustomerPackageRequestDto(prepareCustomerPackageRequestDto());
        return advertisementRequestDto;
    }

    private CustomerPackageRequestDto prepareCustomerPackageRequestDto() {
        CustomerPackageRequestDto customerPackageRequestDto = new CustomerPackageRequestDto();
        customerPackageRequestDto.setId(1L);
        return customerPackageRequestDto;
    }

    private BuildingRequestDto prepareBuildingRequestDto(){
        BuildingRequestDto buildingRequestDto = new BuildingRequestDto();
        buildingRequestDto.setId(1L);
        return buildingRequestDto;
    }

    private CustomerRequestDto prepareCustomerRequestDto(){
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setId(1L);
        return customerRequestDto;
    }

    private PackageRequestDto preparePackageRequestDto() {
        PackageRequestDto packageRequestDto = new PackageRequestDto();
        packageRequestDto.setId(1L);
        return packageRequestDto;
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
}
