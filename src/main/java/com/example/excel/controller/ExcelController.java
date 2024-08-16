package com.example.excel.controller;

import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;
import com.example.excel.dto.ProductsCreationDTO;
import com.example.excel.service.ExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("excel")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping()
    public String uploadPage(Model model) {
        model.addAttribute("form", new ProductsCreationDTO());
        return "upload";
    }
    @PostMapping("/save")
    public ResponseEntity<byte[]> saveAfterClickingOK(@ModelAttribute ProductsCreationDTO form) throws IOException {

        // part 1
        for (ProductAndProviderInfo productAndProviderInfo : form.getProducts()) {

            this.excelService.save(productAndProviderInfo);
        }

          // part 2
        List<ProductAndProviderInfoWithBytes> data = this.excelService.getAllInfo();

        byte[] excelContent = this.excelService.generateExcel(data);

        // clear list
        this.excelService.clearAllInfo();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelContent);
    }

}
