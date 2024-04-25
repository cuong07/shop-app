package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.product.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {

    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String message;
    private String status;
    private String description;
    private Long categoryId;
    private int totalPages;
    private List<ProductImage> productImages;

    public ProductResponse() {
        // Default constructor required for deserialization
    }

    // Getters and setters for all fields

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setThumbnail(product.getThumbnail());
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setProductImages(product.getProductImages());
        productResponse.setTotalPages(0);
        productResponse.setMessage("");
        productResponse.setStatus("");
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }

}
