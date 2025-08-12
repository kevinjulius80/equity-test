package com.equity.equitytest.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_transaksi")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaksi_id")
    private Integer transaksiId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "tgl_transaksi")
    private String tglTransaksi;
}
