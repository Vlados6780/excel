package com.example.excel.service;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    void save(ProductInfo productInfo) throws IOException;

    void clearAllInfo();

    List<ProductInfoWithBytes> getAllInfoProducts();
}
