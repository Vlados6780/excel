package com.example.excel.controller;

import com.example.excel.dto.ProductsCreationDTO;
import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;
import com.example.excel.service.ExcelService;
import com.example.excel.service.GenerationExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("excel")
public class ExcelController {

    private final ExcelService excelService;
    private final GenerationExcelService generationExcelService;

    @GetMapping()
    public String uploadPage(Model model) {
        model.addAttribute("form", new ProductsCreationDTO());
        return "upload";
    }
    @PostMapping("/save")
    public ResponseEntity<byte[]> saveAfterClickingOK(@ModelAttribute ProductsCreationDTO form) throws IOException {

        String nameOfProvider = form.getProviderInfo().getName(); // for name of file excel

        // part 1 - save products
        for (ProductInfo productInfo : form.getProducts()) {
            this.excelService.save(productInfo);
        }

          // part 2 - generate excel
        List<ProductInfoWithBytes> dataProducts = this.excelService.getAllInfoProducts();

        byte[] excelContent = this.generationExcelService.generateExcel(dataProducts,
                form.getProviderInfo().getName(),
                form.getProviderInfo().getImageOfProvider());

        // part 3 - clear list of products
        this.excelService.clearAllInfo();

        // URL-encode the filename to support special characters
        String encodedFileName = URLEncoder.encode("Products_Info_Provider-" + nameOfProvider + ".xlsx", StandardCharsets.UTF_8.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelContent);
    }


}
