package com.example.excel.dao;

import com.example.excel.entity.bytes.ProductInfoWithBytes;
import com.example.excel.entity.info.ProductInfo;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultProductAndProviderInfoDAO implements ProductAndProviderInfoDAO {
    private final List<ProductInfoWithBytes> productInfoWithBytesList =
            new ArrayList<>();

    public void addProductInfo(ProductInfo productInfo) throws IOException {

        ProductInfoWithBytes productInfoWithBytes =
                getProductInfoWithBytes(productInfo);

        this.productInfoWithBytesList.add(productInfoWithBytes);

    }

    public List<ProductInfoWithBytes> getAllProducts() {
        return new ArrayList<>(this.productInfoWithBytesList);
    }

    private ProductInfoWithBytes getProductInfoWithBytes(
            ProductInfo productInfo) throws IOException {

        ProductInfoWithBytes productInfoWithBytes =
                new ProductInfoWithBytes();

        // part of product
        productInfoWithBytes.setDescription(productInfo.getDescriptionOfProduct()); //comments
        productInfoWithBytes.setPrice(productInfo.getPrice());
        productInfoWithBytes.setCtn(productInfo.getCtn());
        productInfoWithBytes.setMoq(productInfo.getMoq());

        // part with product image
        InputStream inputStream2 = productInfo.getImageOfProduct().getInputStream();
        byte[] imageOfProductInBytes = IOUtils.toByteArray(inputStream2);
        productInfoWithBytes.setImageOfProductWithBytes(imageOfProductInBytes);
        inputStream2.close();


        return productInfoWithBytes;
    }


    public void clearProductInfoList() {
        this.productInfoWithBytesList.clear();
    }


}
