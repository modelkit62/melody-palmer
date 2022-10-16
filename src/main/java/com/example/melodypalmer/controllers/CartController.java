package com.example.melodypalmer.controllers;

import com.example.melodypalmer.models.ItemsResponse;
import com.example.melodypalmer.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

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
  public double getTotalPrice(@RequestBody String[] items) {

    if (items.length == 0 || Arrays.stream(items).anyMatch(i -> i.isBlank())) {
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "There's a null!");
    }

    List<String> list = Arrays.asList(items);

    double finalPrice = cartService.clasifyItems(list);

    return finalPrice;
    // throw new RuntimeException("Not implemented");
  }
}
