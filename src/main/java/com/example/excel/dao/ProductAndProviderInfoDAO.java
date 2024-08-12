package com.example.excel.dao;

import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;

import java.io.IOException;
import java.util.List;

public interface ProductAndProviderInfoDAO {

    void addProductAndProviderInfo(ProductAndProviderInfo productAndProviderInfo) throws IOException;
    List<ProductAndProviderInfoWithBytes> getAllProductAndProviderInfo();
    void clearProductAndProviderInfoList();
}
