package com.example.excel.service;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GenerationExcelService {
    byte[] generateExcel(List<ProductInfoWithBytes> data, String nameOfProvider, MultipartFile imageOfProvider) throws IOException;

}
