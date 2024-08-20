package com.example.excel.service;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.excel.service.compressor.ImageCompressor.compressImage;
import static com.example.excel.service.imageCell.ImageToAdd.addImageToCell;

@Service
public class DefaultGenerationExcelService implements GenerationExcelService {

    @Override
    public byte[] generateExcel(List<ProductInfoWithBytes> data, String nameOfProvider, MultipartFile imageOfProvider) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Provider - " + nameOfProvider);

            // set column widths
            for ( int i =0; i < 8; i++) {
                sheet.setColumnWidth(i, 20 * 256);
            }

            int rowNum = 0;

            // create a merged region for the provider name (b to c)
            Row providerRow = sheet.createRow(5);
            Cell providerCell = providerRow.createCell(1); // start at column b
            providerCell.setCellValue("Provider:   " + nameOfProvider);

            // merge b and c
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(1, 14, 3, 5));

            // center the provider's name
            XSSFColor greenAccentColor = new XSSFColor(new java.awt.Color(146, 208, 80), new DefaultIndexedColorMap());
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setFillForegroundColor(greenAccentColor);
            centeredStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            providerCell.setCellStyle(centeredStyle);

            // add the provider's image in column d
            InputStream inputStream = imageOfProvider.getInputStream();
            byte[] imageOfProviderInBytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            byte[] compressedImageOfProvider = compressImage(imageOfProviderInBytes, 300, 300, 0.75f);

            // place image starting in column d
            addImageToCell(workbook, sheet, compressedImageOfProvider, 1, 3);

            rowNum += 16; // adjust rowNum to leave space for the image

            //style
            CellStyle style = workbook.createCellStyle();

            // set fill foreground color to Blue-Gray, Text 2, Lighter 80% (XSSFColor)
            XSSFColor blueGrayColor = new XSSFColor(new java.awt.Color(224, 230, 233), new DefaultIndexedColorMap());
            style.setFillForegroundColor(blueGrayColor);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(HorizontalAlignment.CENTER);

            // header for product details
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Comments");
            headerRow.getCell(0).setCellStyle(style);
            headerRow.createCell(1).setCellValue("Product Image");
            headerRow.getCell(1).setCellStyle(style);

            headerRow.createCell(2);
            headerRow.getCell(2).setCellStyle(style);
            headerRow.createCell(3);
            headerRow.getCell(3).setCellStyle(style);

            headerRow.createCell(4).setCellValue("Price");
            headerRow.getCell(4).setCellStyle(style);
            headerRow.createCell(5).setCellValue("MOQ");
            headerRow.getCell(5).setCellStyle(style);
            headerRow.createCell(6).setCellValue("CTN");
            headerRow.getCell(6).setCellStyle(style);

            // add product information
            for (ProductInfoWithBytes info : data) {
                Row row = sheet.createRow(rowNum);

                // product description
                row.createCell(0).setCellValue(info.getDescription());

                // product image
                byte[] compressedImageOfProduct = compressImage(info.getImageOfProductWithBytes(), 300, 300, 0.75f);
                addImageToCell(workbook, sheet, compressedImageOfProduct, rowNum, 1); // place image starting in column b

                // new fields
                String priceStr = info.getPrice().replace(",", ".");
                if (!priceStr.isEmpty()) {
                    row.createCell(4).setCellValue(Double.parseDouble(priceStr));
                } else {
                    row.createCell(4);
                }
                row.createCell(5).setCellValue(info.getMoq());
                row.createCell(6).setCellValue(info.getCtn());

                rowNum += 15; // move to the next set of rows
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Override
    public byte[] generateExcelTest(List<ProductInfoWithBytes> data, String nameOfProvider, MultipartFile imageOfProvider) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Provider - " + nameOfProvider);

            // part of code
            Row row = sheet.createRow(0);
            sheet.setColumnWidth(0, 14934);
            row.setHeightInPoints(209.55f);

            InputStream inputStream = imageOfProvider.getInputStream();
            byte[] imageOfProviderInBytes = IOUtils.toByteArray(inputStream);
            inputStream.close();
            byte[] compressedImageOfProvider = compressImage(imageOfProviderInBytes, 300, 300, 0.75f);
            int pictureIdx = workbook.addPicture(compressedImageOfProvider, Workbook.PICTURE_TYPE_JPEG);
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor();
            anchor.setCol1(0); // левая верхняя ячейка (столбец)
            anchor.setRow1(0); // левая верхняя ячейка (строка)
            anchor.setCol2(1); // правая нижняя ячейка (столбец)
            anchor.setRow2(1); // правая нижняя ячейка (строка)
            XSSFPicture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize(1.002,1.0);

            workbook.write(out);
            return out.toByteArray();
        }
    }


}
