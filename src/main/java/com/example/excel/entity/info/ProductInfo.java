package com.example.excel.entity.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private String descriptionOfProduct;
    private MultipartFile imageOfProduct;

    // new fields
    private String price;  // Price
    private String ctn; //CTN
    private String moq;  //MOQ
}
