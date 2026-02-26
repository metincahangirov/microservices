package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.req.CreateProductReqDto;
import com.cybernetics.product_ms.dto.req.UpdateProductReqDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import com.cybernetics.product_ms.exception.ProductNotFound;
import com.cybernetics.product_ms.exception.SellerNotOwnerOfProductException;
import com.cybernetics.product_ms.mapper.SellerMapper;
import com.cybernetics.product_ms.repository.ProductRepository;
import com.cybernetics.product_ms.service.SellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final ProductRepository productRepository;
    private final SellerMapper sellerMapper;

    @Override
    public void addProducts(CreateProductReqDto createProductReqDto) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        var mapper = sellerMapper
                .mapProductDtoToProductEntity(createProductReqDto, userName);
        productRepository.save(mapper);
    }

    @Override
    public void updateProducts(String id, UpdateProductReqDto updateProductReqDto) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = auth.getName();

        ProductEntity productEntity =
                productRepository.findByProductId(id).orElseThrow(() -> new ProductNotFound("Product not found"));

        if (productEntity.getUserName().equals(userName)) {

            sellerMapper.mapUpdateProductDtoToProductEntity(updateProductReqDto, productEntity);

            productRepository.save(productEntity);
        } else {
            throw new SellerNotOwnerOfProductException("Seller not found");
        }


    }

    @Override
    @Transactional
    public void deleteProducts(String id) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        ProductEntity productEntity =
                productRepository.findByProductId(id).orElseThrow(() -> new ProductNotFound("Product not found"));

        if (productEntity.getUserName().equals(auth.getName())) {
            productRepository.delete(productEntity);

        } else {
            throw new SellerNotOwnerOfProductException("Seller cannot delete product");
        }

    }
}
