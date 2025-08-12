package com.equity.equitytest.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LogRequest {

    @JsonProperty("csv_filename")
    private String csvFilename;

    @JsonProperty("total_record")
    private Integer totalRecord;

    @JsonProperty("total_record_failed")
    private Integer totalRecordFailed;

    @JsonProperty("total_record_success")
    private Integer totalRecordSuccess;

    @JsonProperty("failed_id_notes")
    private String failedIdNotes;

    @JsonProperty("upload_date")
    private String uploadDate;
}
