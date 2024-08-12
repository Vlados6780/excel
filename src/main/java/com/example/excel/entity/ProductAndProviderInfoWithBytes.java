package com.example.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndProviderInfoWithBytes {

    private String name;
    private String description;
    private byte[] imageOfProviderWithBytes;
    private byte[] imageOfProductWithBytes;

}
