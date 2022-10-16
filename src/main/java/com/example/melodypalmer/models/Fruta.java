package com.example.melodypalmer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fruta {

    @Pattern(regexp="^[a-zA-Z]{3}", message="length must be 3")
    private String nombre;
}
