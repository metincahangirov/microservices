package com.cybernetics.product_ms.service;

import com.cybernetics.product_ms.dto.req.CreateProductReqDto;
import com.cybernetics.product_ms.dto.req.UpdateProductReqDto;

public interface SellerService {

    void addProducts(CreateProductReqDto createProductReqDto);

    void updateProducts(String id, UpdateProductReqDto updateProductReqDto);

    void deleteProducts(String id);

}
