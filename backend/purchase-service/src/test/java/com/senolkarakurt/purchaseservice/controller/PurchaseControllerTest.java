package com.senolkarakurt.purchaseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.purchaseservice.client.service.CustomerClientService;
import com.senolkarakurt.purchaseservice.client.service.OrderClientService;
import com.senolkarakurt.purchaseservice.client.service.PackageClientService;
import com.senolkarakurt.purchaseservice.client.service.UserClientService;
import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.model.*;
import com.senolkarakurt.purchaseservice.service.impl.PurchaseServiceImpl;
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
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseServiceImpl purchaseService;

    @MockBean
    private CustomerClientService customerClientService;

    @MockBean
    private UserClientService userClientService;

    @MockBean
    private PackageClientService packageClientService;

    @MockBean
    private OrderClientService orderClientService;

    @Test
    public void testCreatePurchase() throws Exception {
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(new Customer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(new User());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(new CPackage());
        when(orderClientService.getOrderById(Mockito.any())).thenReturn(new Order());
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(preparePurchaseSaveRequestDto());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/purchases/save")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                ;

        resultActions.andExpect(status().isOk());
        verify(purchaseService, times(1)).save(Mockito.any(PurchaseSaveRequestDto.class));
    }

    @Test
    public void testGetPurchaseById() throws Exception {
        when(purchaseService.getPurchaseById(10L)).thenReturn(new Purchase());
        mockMvc.perform(get("/api/v1/purchases/id/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
        verify(purchaseService, times(1)).getPurchaseById(10L);
    }

    private PurchaseSaveRequestDto preparePurchaseSaveRequestDto() {
        PurchaseSaveRequestDto purchaseSaveRequestDto = new PurchaseSaveRequestDto();
        purchaseSaveRequestDto.setPurchase(preparePurchase());
        return purchaseSaveRequestDto;
    }

    private Purchase preparePurchase(){
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setRecordStatus(RecordStatus.ACTIVE);
        purchase.setCreateDateTime(LocalDateTime.now());
        purchase.setTotalPrice(BigDecimal.TEN);
        purchase.setOrderId(1L);
        return purchase;
    }
}
