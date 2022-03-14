package com.sirma.demo.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class UserEntity {

    @CsvBindByName(column = "EmpID")
    private Long EmpID;

    @CsvBindByName(column = "ProjectID")
    private Long ProjectID;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "DateFrom")
    private LocalDate startDate;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "DateTo")
    private LocalDate endDate;
}
