package com.example.excel.entity.bytes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoWithBytes {
    private String description; // now for comments
    private byte[] imageOfProductWithBytes;

    // new fields
    private String price;  // Price
    private String ctn; //CTN
    private String moq;  //MOQ
}
