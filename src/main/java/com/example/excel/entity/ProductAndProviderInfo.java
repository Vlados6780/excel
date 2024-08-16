package com.example.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndProviderInfo  {

    private String nameOfProvider;
    private String descriptionOfProduct;
    private MultipartFile imageOfProvider;
    private MultipartFile imageOfProduct;

    // new fields
    private int price;  // Price
    private String ctn; //CTN
    private String moq;  //MOQ

}
