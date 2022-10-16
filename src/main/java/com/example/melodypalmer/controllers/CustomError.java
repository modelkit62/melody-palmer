package com.example.melodypalmer.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
public class CustomError {

    private String field;

    private String message;

    private String localDate;

    public CustomError(@JsonProperty("causa") String field, @JsonProperty("mensaje") String message, @JsonProperty("date") String localDate) {
        this.field = field;
        this.message = message;
        this.localDate = localDate;
    }
}
