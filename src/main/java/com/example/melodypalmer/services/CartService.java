package com.example.melodypalmer.services;

import com.example.melodypalmer.models.Fruta;
import com.example.melodypalmer.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CartService {

    private static final Pattern ALLOW_LIST = Pattern.compile("[A-Za-z'\\-]+");
    // [Orange Apple orange apple]+ -- LAS PALABRAS EXACTAS


    private static final Pattern BLOCK_LIST = Pattern.compile("<>\\[\\]\\(\\)\\\\/\\s\\$");
    ArrayList<Product> products = new ArrayList<>();

    public CartService() {
        products.add(new Product("Apple", 60));
        products.add(new Product("Orange", 25));
    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    public double clasifyItems(List<Fruta> list) {

        List<String> apples = new ArrayList<>();
        List<String> oranges = new ArrayList<>();

        double price = 0;
        for (Fruta s : list) {

            if (!ALLOW_LIST.matcher(s.getNombre()).matches()) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Invalid Character");
            }

            if(BLOCK_LIST.matcher(s.getNombre()).matches()){
                throw new IllegalArgumentException("Invalid fruit name");
            }

            if (s.getNombre().equalsIgnoreCase("Apple")) {
                apples.add("apple");
                if (apples.size() % 2 != 0) {
                    price += 0.60;
                }
            } else if (s.getNombre().equalsIgnoreCase("Orange")) {
                oranges.add("orange");
                if (oranges.size() % 3 != 0) {
                    price += 0.25;
                }
            }
        }
        return price;
    }
}

