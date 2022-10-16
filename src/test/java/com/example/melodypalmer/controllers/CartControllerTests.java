package com.example.melodypalmer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.melodypalmer.models.Fruta;
import com.example.melodypalmer.models.Product;
import com.example.melodypalmer.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(classes = CartController.class)
public class CartControllerTests {

    public static final String EXPECTED_RESPONSE = "1.70";
    public static final int EXPECTED_HTTP_STATUS = 200;
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

    @Test
    public void testGetTotalPrice() throws Exception {

        Fruta fruta_1 = new Fruta("Orange");
        Fruta fruta_2 = new Fruta("Orange");
        Fruta fruta_3 = new Fruta("Apple");
        Fruta fruta_4 = new Fruta("Apple");
        Fruta fruta_5 = new Fruta("Apple");

        List<Fruta> frutas = Arrays.asList(fruta_1, fruta_2, fruta_3, fruta_4, fruta_5);

        when(cartService.clasifyItems(frutas)).thenReturn(1.70);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/total")
                        .content("[\n" +
                                "  {\n" +
                                "    \"nombre\": \"Orange\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"nombre\": \"Orange\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"nombre\": \"Apple\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"nombre\": \"Apple\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"nombre\": \"Apple\"\n" +
                                "  }\n" +
                                "]")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(EXPECTED_HTTP_STATUS, resultActions.andReturn().getResponse().getStatus());
        assertEquals(EXPECTED_RESPONSE, resultActions.andReturn().getResponse().getContentAsString());
    }

}
