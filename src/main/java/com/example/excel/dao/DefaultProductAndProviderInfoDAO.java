package com.example.excel.dao;

import com.example.excel.entity.ProductAndProviderInfo;
import com.example.excel.entity.ProductAndProviderInfoWithBytes;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultProductAndProviderInfoDAO implements ProductAndProviderInfoDAO {
    private final List<ProductAndProviderInfoWithBytes> productAndProviderInfoWithBytesList =
            new ArrayList<>();

    public void addProductAndProviderInfo(ProductAndProviderInfo productAndProviderInfo) throws IOException {

        ProductAndProviderInfoWithBytes productAndProviderInfoWithBytes =
                getProductAndProviderInfoWithBytes(productAndProviderInfo);

        this.productAndProviderInfoWithBytesList.add(productAndProviderInfoWithBytes);

    }

    public List<ProductAndProviderInfoWithBytes> getAllProductAndProviderInfo() {
        return new ArrayList<>(this.productAndProviderInfoWithBytesList);
    }

    private ProductAndProviderInfoWithBytes getProductAndProviderInfoWithBytes(
            ProductAndProviderInfo productAndProviderInfo) throws IOException {

        ProductAndProviderInfoWithBytes productAndProviderInfoWithBytes =
                new ProductAndProviderInfoWithBytes();

        // part of provider
        productAndProviderInfoWithBytes.setName(productAndProviderInfo.getNameOfProvider());

        // part of product
        productAndProviderInfoWithBytes.setDescription(productAndProviderInfo.getDescriptionOfProduct()); //comments
        productAndProviderInfoWithBytes.setPrice(productAndProviderInfo.getPrice());
        productAndProviderInfoWithBytes.setCtn(productAndProviderInfo.getCtn());
        productAndProviderInfoWithBytes.setMoq(productAndProviderInfo.getMoq());

        // part with images
        InputStream inputStream1 = productAndProviderInfo.getImageOfProvider().getInputStream();
        byte[] imageOfProviderInBytes = IOUtils.toByteArray(inputStream1);
        productAndProviderInfoWithBytes.setImageOfProviderWithBytes(imageOfProviderInBytes);
        inputStream1.close();

        InputStream inputStream2 = productAndProviderInfo.getImageOfProduct().getInputStream();
        byte[] imageOfProductInBytes = IOUtils.toByteArray(inputStream2);
        productAndProviderInfoWithBytes.setImageOfProductWithBytes(imageOfProductInBytes);
        inputStream2.close();


        return productAndProviderInfoWithBytes;
    }


    public void clearProductAndProviderInfoList() {
        this.productAndProviderInfoWithBytesList.clear();
    }


}
