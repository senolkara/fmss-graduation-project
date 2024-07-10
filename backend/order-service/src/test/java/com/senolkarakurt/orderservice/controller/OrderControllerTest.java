package com.senolkarakurt.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.OrderRequestDto;
import com.senolkarakurt.dto.request.PackageRequestDto;
import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.orderservice.client.service.CustomerClientService;
import com.senolkarakurt.orderservice.client.service.PackageClientService;
import com.senolkarakurt.orderservice.client.service.PurchaseClientService;
import com.senolkarakurt.orderservice.client.service.UserClientService;
import com.senolkarakurt.orderservice.dto.request.OrderSearchRequestDto;
import com.senolkarakurt.orderservice.model.*;
import com.senolkarakurt.orderservice.service.impl.OrderServiceImpl;
import com.senolkarakurt.util.GenerateRandomUnique;
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

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private CustomerClientService customerClientService;

    @MockBean
    private UserClientService userClientService;

    @MockBean
    private PackageClientService packageClientService;

    @MockBean
    private PurchaseClientService purchaseClientService;

    @Test
    public void testGetOrderListByCustomerId() throws Exception {
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(new Customer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(new User());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(new CPackage());
        mockMvc.perform(get("/api/v1/orders/customerId/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
        verify(orderService, times(1)).getAllByCustomerId(10L);
    }

    @Test
    public void testGetOrderById() throws Exception {
        when(orderService.getOrderById(10L)).thenReturn(new Order());
        mockMvc.perform(get("/api/v1/orders/id/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
        verify(orderService, times(1)).getOrderById(10L);
    }

    @Test
    public void testCreateOrder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareOrderRequestDto());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/orders")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                ;

        resultActions.andExpect(status().isOk());
        verify(orderService, times(1)).save(Mockito.any(OrderRequestDto.class));
    }

    @Test
    void testSearch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareOrderSearchRequestDto());

        mockMvc.perform(get("/api/v1/orders/search")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
        verify(orderService, times(1)).search(Mockito.any(OrderSearchRequestDto.class));
    }

    private OrderSearchRequestDto prepareOrderSearchRequestDto() {
        OrderSearchRequestDto orderSearchRequestDto = new OrderSearchRequestDto();
        orderSearchRequestDto.setOrderCode(GenerateRandomUnique.createRandomOrderCode());
        orderSearchRequestDto.setOrderStatus(OrderStatus.INITIAL);
        orderSearchRequestDto.setCreateDate(LocalDate.now());
        orderSearchRequestDto.setSort("DESC");
        orderSearchRequestDto.setPage(0);
        orderSearchRequestDto.setSize(1);
        return orderSearchRequestDto;
    }

    private OrderRequestDto prepareOrderRequestDto(){
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerRequestDto(prepareCustomerRequestDto());
        orderRequestDto.setPackageRequestDto(preparePackageRequestDto());
        return orderRequestDto;
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
}
