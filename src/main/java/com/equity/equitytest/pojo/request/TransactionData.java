package com.equity.equitytest.pojo.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionData {
    private Integer employeeId;
    private Integer amount;
    private LocalDate tglTransaksi;
}
