package com.project.shopapp.services.product;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.product.ProductImage;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(Long id) throws  Exception;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest, String keyword, Long categoryId);

    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(Long id);

    Boolean existsByName(String name);

    ProductImage createProductImage(Long id, ProductImageDTO productImageDTO) throws Exception;
}
