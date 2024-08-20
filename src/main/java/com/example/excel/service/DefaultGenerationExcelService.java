package com.example.excel.service;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.excel.service.compressor.ImageCompressor.compressImage;

@Service
public class DefaultGenerationExcelService implements GenerationExcelService {

    @Override
    public byte[] generateExcel(List<ProductInfoWithBytes> data, String nameOfProvider, MultipartFile imageOfProvider) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Provider - " + nameOfProvider);

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
            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(1);
            anchor.setRow2(1);
            XSSFPicture picture = drawing.createPicture(anchor, pictureIdx);
            picture.resize(1.0006,1.0);

            sheet.setColumnWidth(1, 10000);
            Cell providerCell = row.createCell(1);
            providerCell.setCellValue("Provider:   " + nameOfProvider);
            // center the provider's name
            XSSFColor color1 = new XSSFColor(new java.awt.Color(226, 239, 57), new DefaultIndexedColorMap());
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            centeredStyle.setFillForegroundColor(color1);
            centeredStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            centeredStyle.setBorderTop(BorderStyle.THICK);
            centeredStyle.setBorderBottom(BorderStyle.THICK);
            centeredStyle.setBorderLeft(BorderStyle.THICK);
            centeredStyle.setBorderRight(BorderStyle.THICK);
            providerCell.setCellStyle(centeredStyle);


            Row row2 = sheet.createRow(2);
            row2.setHeightInPoints(50f);
            sheet.setColumnWidth(2, 5300);
            sheet.setColumnWidth(3, 5300);
            sheet.setColumnWidth(4, 5300);

            XSSFColor color2 = new XSSFColor(new java.awt.Color(222, 208, 208, 255), new DefaultIndexedColorMap());
            CellStyle centeredStyle2 = workbook.createCellStyle();
            centeredStyle2.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
            centeredStyle2.setFillForegroundColor(color2);
            centeredStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            centeredStyle2.setBorderTop(BorderStyle.THICK);
            centeredStyle2.setBorderBottom(BorderStyle.THICK);
            centeredStyle2.setBorderLeft(BorderStyle.THICK);
            centeredStyle2.setBorderRight(BorderStyle.THICK);

            Cell productImage = row2.createCell(0);
            productImage.setCellValue("Product Image");
            productImage.setCellStyle(centeredStyle2);
            Cell productComments = row2.createCell(1);
            productComments.setCellValue("Comments");
            productComments.setCellStyle(centeredStyle2);
            Cell productPrice = row2.createCell(2);
            productPrice.setCellValue("Price");
            productPrice.setCellStyle(centeredStyle2);
            Cell productMOQ = row2.createCell(3);
            productMOQ.setCellValue("MOQ");
            productMOQ.setCellStyle(centeredStyle2);
            Cell productCTN = row2.createCell(4);
            productCTN.setCellValue("CTN");
            productCTN.setCellStyle(centeredStyle2);


            int rowNum = 3;
            for (ProductInfoWithBytes info : data) {

                Row row3 = sheet.createRow(rowNum);
                row3.setHeightInPoints(209.55f);

                byte[] compressedImageOfProduct = compressImage(info.getImageOfProductWithBytes(), 300, 300, 0.75f);
                int pictureIdx2 = workbook.addPicture(compressedImageOfProduct, Workbook.PICTURE_TYPE_JPEG);
                XSSFDrawing drawing2 = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor2 = new XSSFClientAnchor();
                anchor2.setCol1(0);
                anchor2.setRow1(rowNum);
                anchor2.setCol2(1);
                anchor2.setRow2(++rowNum);
                XSSFPicture picture2 = drawing2.createPicture(anchor2, pictureIdx2);
                picture2.resize(1.0006,1.0);


                XSSFColor color3 = new XSSFColor(new java.awt.Color(231, 229, 229, 255), new DefaultIndexedColorMap());
                CellStyle centeredStyle3 = workbook.createCellStyle();
                centeredStyle3.setAlignment(HorizontalAlignment.CENTER);
                centeredStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
                centeredStyle3.setFillForegroundColor(color3);
                centeredStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                centeredStyle3.setBorderTop(BorderStyle.THICK);
                centeredStyle3.setBorderBottom(BorderStyle.THICK);
                centeredStyle3.setBorderLeft(BorderStyle.THICK);
                centeredStyle3.setBorderRight(BorderStyle.THICK);
                centeredStyle3.setWrapText(true);

                row3.createCell(1).setCellValue(info.getDescription());
                row3.getCell(1).setCellStyle(centeredStyle3);
                String priceStr = info.getPrice().replace(",", ".");

                if (!priceStr.isEmpty()) {
                    row3.createCell(2).setCellValue(Double.parseDouble(priceStr));
                    row3.getCell(2).setCellStyle(centeredStyle3);
                } else {
                    row3.createCell(2);
                    row3.getCell(2).setCellStyle(centeredStyle3);
                }

                row3.createCell(3).setCellValue(info.getMoq());
                row3.getCell(3).setCellStyle(centeredStyle3);
                row3.createCell(4).setCellValue(info.getCtn());
                row3.getCell(4).setCellStyle(centeredStyle3);

                Row row4 = sheet.createRow(rowNum);
                CellStyle thickBorderStyle = workbook.createCellStyle();
                thickBorderStyle.setBorderTop(BorderStyle.THICK);
                thickBorderStyle.setBorderBottom(BorderStyle.THICK);
                thickBorderStyle.setBorderLeft(BorderStyle.THICK);
                thickBorderStyle.setBorderRight(BorderStyle.THICK);

                for (int i = 0; i < 5; i++) {
                    row4.createCell(i).setCellStyle(thickBorderStyle);
                }

                rowNum++;

            }

            workbook.write(out);
            return out.toByteArray();
        }
    }


}
