package com.example.excel.service;

import com.example.excel.dao.ProductAndProviderInfoDAO;
import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultExcelService implements ExcelService{

   private final ProductAndProviderInfoDAO productAndProviderInfoDAO;

    @Override
    public void save(ProductAndProviderInfo productAndProviderInfo) throws IOException {

        this.productAndProviderInfoDAO.addProductAndProviderInfo(productAndProviderInfo);

    }

    @Override
    public List<ProductAndProviderInfoWithBytes> getAllInfo() {
        return this.productAndProviderInfoDAO.getAllProductAndProviderInfo();
    }

    @Override
    public void clearAllInfo() {
        this.productAndProviderInfoDAO.clearProductAndProviderInfoList();
    }

    /// excel part
    @Override
    public byte[] generateExcel(List<ProductAndProviderInfoWithBytes> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products info");

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

            for (ProductAndProviderInfoWithBytes info : data) {
                Row row = sheet.createRow(rowNum);

                row.createCell(0).setCellValue(info.getName());

                // provider image
                addImageToCell(workbook, sheet, info.getImageOfProviderWithBytes(), rowNum, 1);  // provider image in column 1

                // product description
                row.createCell(5).setCellValue(info.getDescription());  // product description in column 5

                // product image
                addImageToCell(workbook, sheet, info.getImageOfProductWithBytes(), rowNum, 7);  // product image in column 7

                // new fields
                row.createCell(11).setCellValue(info.getPrice());
                row.createCell(12).setCellValue(info.getMoq());
                row.createCell(13).setCellValue(info.getCtn());

                rowNum += 15;  // move to the next set of rows (14 for image + 1 for spacing)
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void addImageToCell(Workbook workbook, Sheet sheet, byte[] imageBytes, int rowNum, int colNum) throws IOException {
        int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(colNum);
        anchor.setRow1(rowNum);
        anchor.setCol2(colNum + 3);  // keep width increase by 3 columns
        anchor.setRow2(rowNum + 14);  // increase height by 14 rows

        Picture pict = drawing.createPicture(anchor, pictureIdx);

        // resize to fit the anchor area while maintaining aspect ratio
        pict.resize();

        // fine-tune size if necessary
        double widthInPixels = sheet.getColumnWidthInPixels(colNum) * 3;  // 3 columns wide
        double heightInPixels = sheet.getDefaultRowHeightInPoints() * 14 * 96 / 72;  // 14 rows high, convert points to pixels

        pict.resize(widthInPixels / pict.getImageDimension().getWidth(),
                heightInPixels / pict.getImageDimension().getHeight());
    }


}
