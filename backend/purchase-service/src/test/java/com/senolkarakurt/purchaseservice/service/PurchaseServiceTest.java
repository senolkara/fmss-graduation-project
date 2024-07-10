package com.senolkarakurt.purchaseservice.service;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.purchaseservice.client.service.CustomerClientService;
import com.senolkarakurt.purchaseservice.client.service.OrderClientService;
import com.senolkarakurt.purchaseservice.client.service.PackageClientService;
import com.senolkarakurt.purchaseservice.client.service.UserClientService;
import com.senolkarakurt.purchaseservice.dto.request.PurchaseSaveRequestDto;
import com.senolkarakurt.purchaseservice.model.*;
import com.senolkarakurt.purchaseservice.repository.InvoiceRepository;
import com.senolkarakurt.purchaseservice.repository.PurchaseRepository;
import com.senolkarakurt.purchaseservice.service.impl.PurchaseServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseServiceImpl purchaseService;

    @Mock
    private CustomerClientService customerClientService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private PackageClientService packageClientService;

    @Mock
    private OrderClientService orderClientService;

    @Test
    public void testCreatePurchase() {
        when(customerClientService.getCustomerById(Mockito.any())).thenReturn(new Customer());
        when(userClientService.getUserById(Mockito.any())).thenReturn(new User());
        when(userClientService.getAddressesByUserId(Mockito.any())).thenReturn(new HashSet<>());
        when(packageClientService.getPackageById(Mockito.any())).thenReturn(new CPackage());
        when(purchaseRepository.save(Mockito.any(Purchase.class))).thenReturn(Instancio.create(Purchase.class));
        when(invoiceRepository.save(Mockito.any(Invoice.class))).thenReturn(Instancio.create(Invoice.class));
        purchaseService.save(preparePurchaseSaveRequestDto());
        verify(purchaseRepository, times(1)).save(Mockito.any(Purchase.class));
    }

    @Test
    public void testGetPurchaseById() {
        Purchase purchase1 = preparePurchase();
        when(purchaseRepository.findById(Mockito.any())).thenReturn(Optional.of(purchase1));
        Purchase purchase = purchaseService.getPurchaseById(Mockito.any());
        assertNotEquals(purchase, null);
        assertEquals(purchase.getOrderId(), 1L);
    }

    @Test
    public void testGetInvalidOrderById() {
        when(purchaseRepository.findById(Mockito.any())).thenThrow(new CommonException("Bu id ile kay?tl? ödeme bulunamad?"));
        Exception exception = assertThrows(CommonException.class, () -> {
            purchaseService.getPurchaseById(Mockito.any());
        });
        assertTrue(exception.getMessage().contains("Bu id ile kay?tl? ödeme bulunamad?"));
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
