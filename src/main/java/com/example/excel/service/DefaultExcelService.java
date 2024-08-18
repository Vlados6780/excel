package com.example.excel.service;

import com.example.excel.dao.ProductAndProviderInfoDAO;
import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.List;

import static com.example.excel.service.compressor.ImageCompressor.compressImage;
import static com.example.excel.service.imageCell.ImageToAdd.addImageToCell;

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

    /// excel part
    @Override
    public byte[] generateExcel(List<ProductInfoWithBytes> data, String nameOfProvider, MultipartFile imageOfProvider) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Provider - "+ nameOfProvider);

            // set column widths
            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(7, 20 * 256);
            sheet.setColumnWidth(8, 20 * 256);
            sheet.setColumnWidth(9, 20 * 256);
            sheet.setColumnWidth(11, 20 * 256);
            sheet.setColumnWidth(12, 20 * 256);
            sheet.setColumnWidth(13, 20 * 256);

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Provider Name");
            headerRow.createCell(1).setCellValue("Provider Image");
            headerRow.createCell(5).setCellValue("Comments");
            headerRow.createCell(7).setCellValue("Product Image");
            headerRow.createCell(11).setCellValue("Price");
            headerRow.createCell(12).setCellValue("MOQ");
            headerRow.createCell(13).setCellValue("CTN");

            Row headerRow2 = sheet.createRow(rowNum++);
            headerRow2.createCell(0).setCellValue(nameOfProvider);
            // image of provider in bytes
            InputStream inputStream = imageOfProvider.getInputStream();
            byte[] imageOfProviderInBytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            byte[] compressedImageOfProvider = compressImage(imageOfProviderInBytes, 300, 300, 0.75f);

            addImageToCell(workbook, sheet, compressedImageOfProvider, 1, 1);


            for (ProductInfoWithBytes info : data) {
                Row row = sheet.createRow(rowNum);

                // product description
                row.createCell(5).setCellValue(info.getDescription());  // product description in column 5

                // product image
                byte[] compressedImageOfProduct = compressImage(info.getImageOfProductWithBytes(), 300, 300, 0.75f);
                addImageToCell(workbook, sheet, compressedImageOfProduct, rowNum, 7);  // product image in column 7

                // new fields
                row.createCell(11).setCellValue(Double.parseDouble(info.getPrice().replace(",", ".")));
                row.createCell(12).setCellValue(info.getMoq());
                row.createCell(13).setCellValue(info.getCtn());

                rowNum += 15;  // move to the next set of rows (14 for image + 1 for spacing)
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

}
