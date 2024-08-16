package com.example.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndProviderInfoWithBytes {

    private String name;
    private String description; // now for comments
    private byte[] imageOfProviderWithBytes;
    private byte[] imageOfProductWithBytes;

    // new fields
    private String price;  // Price
    private String ctn; //CTN
    private String moq;  //MOQ

}
