package com.zerozae.voucher.controller;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.voucher.VoucherController;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VoucherControllerTest {
    VoucherService voucherService = mock(VoucherService.class);
    VoucherController voucherController = new VoucherController(voucherService);

    @Test
    @DisplayName("바우처 컨트롤러 : 바우처 생성 메서드 성공 반환 테스트")
    void createVoucherSuccessTest(){
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(10, VoucherType.FIXED);

        // When
        Response response = voucherController.createVoucher(voucherRequest);

        // Then
        assertTrue(response.isSuccess());
    }

    @Test
    @DisplayName("모든 바우처 조회 테스트")
    void findAllCustomersSuccessTest() {
        // Given
        List<VoucherResponse> voucherList = List.of(
                new VoucherResponse(UUID.randomUUID().toString(), 10L , VoucherType.FIXED, UseStatusType.AVAILABLE),
                new VoucherResponse(UUID.randomUUID().toString(), 20L, VoucherType.PERCENT, UseStatusType.AVAILABLE)
        );

        when(voucherService.findAllVouchers()).thenReturn(voucherList);

        // When
        Response<List<VoucherResponse>> response = voucherController.findAllVouchers();
        List<VoucherResponse> data = response.getData();

        // Then
        assertTrue(response.isSuccess());
        assertEquals(2, data.size());
        assertEquals(data, voucherList);
    }
}
