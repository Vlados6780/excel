package com.example.excel.service.imageCell;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;

public class ImageToAdd {

    public static void addImageToCell(Workbook workbook, Sheet sheet, byte[] imageBytes, int rowNum, int colNum) throws IOException {
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
