package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "cannot find category with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .description(productDTO.getDescription())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product with id: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // limit : page
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "cannot find category with id: " + productDTO.getCategoryId()));
        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail() == null ? "" : productDTO.getThumbnail());
            existingProduct.setCategory(existingCategory);
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long id,
            ProductImageDTO productImageDTO
    ) throws Exception {
        Product existingProduct = getProductById(id);
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImageRepository.findByProductId(id).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must ne <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);

        }
        return productImageRepository.save(newProductImage);
    }

}
