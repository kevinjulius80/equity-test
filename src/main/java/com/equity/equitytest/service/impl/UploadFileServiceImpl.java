package com.equity.equitytest.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.equity.equitytest.helper.builder.ErrorResponseBuilder;
import com.equity.equitytest.helper.other.EmployeeHelper;
import com.equity.equitytest.helper.other.HitLogHelper;
import com.equity.equitytest.model.employee.Employee;
import com.equity.equitytest.model.transaction.Fee;
import com.equity.equitytest.model.transaction.Transaction;
import com.equity.equitytest.pojo.request.LogRequest;
import com.equity.equitytest.pojo.request.TransactionData;
import com.equity.equitytest.repository.dbemployee.EmployeeRepository;
import com.equity.equitytest.repository.dbtransaction.FeeRepository;
import com.equity.equitytest.repository.dbtransaction.TransactionRepository;
import com.equity.equitytest.service.iservice.UploadFileService;
import com.equity.equitytest.util.AppLogUtil;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeHelper employeeHelper;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private HitLogHelper hitLogHelper;

    @Override
    public ResponseEntity<String> uploadFile(String guid, String title, MultipartFile file) {
        if (file.isEmpty()) {
            return errorResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "File is empty");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String fileName = file.getOriginalFilename();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build();

            List<TransactionData> transactionDataList = new ArrayList<>();
            csvReader.readNext(); // Skip header

            String[] line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Sesuaikan format tanggal

            while ((line = csvReader.readNext()) != null) {
                TransactionData transactionData = new TransactionData();
                transactionData.setEmployeeId(Integer.parseInt(line[0]));
                transactionData.setAmount(Integer.parseInt(line[1]));
                transactionData.setTglTransaksi(LocalDate.parse(line[2], formatter));
                transactionDataList.add(transactionData);
            }

            Map<Integer, Employee> employeeMap = new HashMap<>();
            employeeRepository.findAll().forEach(employee -> {
                employeeMap.put(employee.getEmployeeId(), employee);
            });

            AppLogUtil.WriteInfoLog(guid, title + "| Processing data from CSV file");
            Integer totalRecord = transactionDataList.size();
            Integer totalRecordFailed = 0;
            Integer totalRecordSuccess = 0;
            String failedIdNotes = "";
            for (TransactionData transactionDatas : transactionDataList) {
                Employee employeeData = employeeMap.get(transactionDatas.getEmployeeId());
                if (employeeData == null) {
                    totalRecordFailed++;
                    failedIdNotes += transactionDatas.getEmployeeId() + ", Employee Data not found;";
                }else{
                    totalRecordSuccess++;
                    //get semua atasan dari employee id yang dikirim csv
                    List<Integer> superiors = employeeHelper.getAllSuperiors(transactionDatas.getEmployeeId(), employeeMap);

                    // for semua atasan, hitung jumlah bawahan
                    for (Integer superior : superiors) {
                        Integer totalSubordinates = employeeHelper.countSubordinates(employeeMap, superior);
                        
                        AppLogUtil.WriteInfoLog(guid, title+ "| processing save fee for employee id : "+ transactionDatas.getEmployeeId());
                        //hitung amount fee
                        Integer amountFee = transactionDatas.getAmount() * (1/totalSubordinates);
                        Fee fee = new Fee();
                        fee.setEmployeeId(transactionDatas.getEmployeeId());
                        fee.setAmountFee(amountFee);
                        fee.setTglFee(transactionDatas.getTglTransaksi().toString());
                        
                        //save fee ke db
                        feeRepository.save(fee);
                        AppLogUtil.WriteInfoLog(guid, title+ "| save fee for employee id : "+ transactionDatas.getEmployeeId() + "| success");
                    }
                    
                    AppLogUtil.WriteInfoLog(guid, title+ "| processing save transaction for employee id : "+ transactionDatas.getEmployeeId());
                    //parsing value ke model transaction
                    Transaction transaction = new Transaction();
                    transaction.setEmployeeId(transactionDatas.getEmployeeId());
                    transaction.setAmount(transactionDatas.getAmount());
                    transaction.setTglTransaksi(transactionDatas.getTglTransaksi().toString());
                    //save transaction ke db
                    transactionRepository.save(transaction);
                    AppLogUtil.WriteInfoLog(guid, title+ "| save transaction for employee id : "+ transactionDatas.getEmployeeId() + "| success");
                }
            }

            LogRequest logRequest = new LogRequest();
            logRequest.setCsvFilename(fileName);
            logRequest.setTotalRecord(totalRecord);
            logRequest.setTotalRecordFailed(totalRecordFailed);
            logRequest.setTotalRecordSuccess(totalRecordSuccess);
            logRequest.setFailedIdNotes(failedIdNotes);
            logRequest.setUploadDate(LocalDate.now().toString());
            AppLogUtil.WriteInfoLog(guid, title + "Sending log to API");
            //hit api log
            HttpStatus statusLog = hitLogHelper.hitLog(logRequest);
            if (statusLog != HttpStatus.OK) {
                AppLogUtil.WriteErrorLog(guid, title, "Failed sending log to API");                
            } else {
                AppLogUtil.WriteInfoLog(guid, title + "| Log successfully sent to API");
            }

            AppLogUtil.WriteInfoLog(guid, title + "| File CSV successfully uploaded and processed");

            return ResponseEntity.ok("File CSV berhasil diupload dan diproses");
        } catch (IOException | CsvException e) {
            AppLogUtil.WriteErrorLog(guid, title, "| Failed processing file CSV : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memproses file CSV: " + e.getMessage());
        }
    }
    
}
