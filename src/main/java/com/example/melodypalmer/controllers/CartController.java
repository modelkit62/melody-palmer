package com.example.melodypalmer.controllers;

import com.example.melodypalmer.models.Fruta;
import com.example.melodypalmer.models.ItemsResponse;
import com.example.melodypalmer.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;

@RestController
public class CartController {

    final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/items", produces = "application/json")
    public ResponseEntity<ItemsResponse> getShopItems() {
        return ResponseEntity.ok(
                new ItemsResponse(cartService.getProducts())
        );
    }

    @PostMapping("/total")
    public ResponseEntity<String> getTotalPrice(@Valid @RequestBody List<Fruta> frutas) {

        if (frutas.size() == 0 || frutas.stream().anyMatch(i -> i.getNombre().isBlank())) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "There's a null!");
        }

        // List<String> list = Arrays.asList(frutas);

        double finalPrice = cartService.clasifyItems(frutas);
        return new ResponseEntity<>(String.format("%,.2f", finalPrice), HttpStatus.OK);
        // throw new RuntimeException("Not implemented");
    }

    @ExceptionHandler(IllegalFormatException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public CustomError handleException(IllegalFormatException e) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);

        CustomError customError = CustomError.builder()
                .field("No se ha podido castear el doble!")
                .message(e.getMessage())
                .localDate(strDate)
                .build();
        return customError;
    }
}
