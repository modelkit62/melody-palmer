package com.example.melodypalmer.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.melodypalmer.models.Product;
import com.example.melodypalmer.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(classes = CartController.class)
public class CartControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CartService cartService;

  @Test
  public void shouldReturnListOfItems() throws Exception {
    ArrayList<Product> products = new ArrayList<>();

    products.add(new Product("Apple", 60));
    products.add(new Product("Orange", 25));

    when(cartService.getProducts()).thenReturn(products);

    this.mockMvc
        .perform(get("/items")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(content().json("{\"items\":[{\"itemName\":\"Apple\",\"price\":60},{\"itemName\":\"Orange\",\"price\":25}]}"));
  }

}
