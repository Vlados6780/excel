package com.example.excel.controller;

import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;
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
        model.addAttribute("info", new ProductAndProviderInfo());
        return "upload";
    }


   @PostMapping("/save")
    public String saveAfterClickingOK(
            @ModelAttribute("info") ProductAndProviderInfo info
    ) throws IOException {

        this.excelService.save(info);

        return "redirect:/excel";
   }


    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
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
