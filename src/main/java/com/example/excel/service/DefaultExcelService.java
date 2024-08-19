package com.example.excel.service;

import com.example.excel.dao.ProductAndProviderInfoDAO;
import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DefaultExcelService implements ExcelService{

   private final ProductAndProviderInfoDAO productAndProviderInfoDAO;

    @Override
    public void save(ProductInfo productInfo) throws IOException {

        this.productAndProviderInfoDAO.addProductInfo(productInfo);

    }

    @Override
    public void clearAllInfo() {
        this.productAndProviderInfoDAO.clearProductInfoList();
    }

    @Override
    public List<ProductInfoWithBytes> getAllInfoProducts() {
        return productAndProviderInfoDAO.getAllProducts();
    }

}
