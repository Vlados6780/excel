package com.example.excel.service;

import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    byte[] generateExcel(List<ProductAndProviderInfoWithBytes> data) throws IOException;

    void save(ProductAndProviderInfo productAndProviderInfo) throws IOException;
    List<ProductAndProviderInfoWithBytes> getAllInfo();

    void clearAllInfo();
}
