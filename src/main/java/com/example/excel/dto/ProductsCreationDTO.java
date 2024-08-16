package com.example.excel.dto;

import com.example.excel.entity.ProductAndProviderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsCreationDTO {

    private List<ProductAndProviderInfo> products = new ArrayList<>();

}
