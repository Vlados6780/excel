package com.example.excel.dao;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;

import java.io.IOException;
import java.util.List;

public interface ProductAndProviderInfoDAO {

    void addProductInfo(ProductInfo productAndProviderInfo) throws IOException;
    List<ProductInfoWithBytes> getAllProducts();
    void clearProductInfoList();
}
