package com.example.melodypalmer.services;

import com.example.melodypalmer.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {CartService.class})
public class CartServiceTests {

  @Autowired
  public CartService cartService;

  @Test
  public void returnListOfProducts(){
    ArrayList<Product> products = new ArrayList<>();

    products.add(new Product("Apple", 60));
    products.add(new Product("Orange", 25));

    assertEquals(cartService.getProducts(), products);
  }
}
