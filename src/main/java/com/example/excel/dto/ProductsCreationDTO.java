package com.example.excel.dto;

import com.example.excel.entity.info.ProductInfo;
import com.example.excel.entity.info.ProviderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsCreationDTO {

    private List<ProductInfo> products = new ArrayList<>();
    private ProviderInfo providerInfo;

}
