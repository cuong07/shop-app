package com.project.shopapp.services;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CartDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Cart;
import com.project.shopapp.models.CartDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CartDetailRepository;
import com.project.shopapp.repositories.CartRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.utils.MessageKeys;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;

@Service
public class CartDetailService implements ICartDetailService {
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final LocalizationUtils localizationUtils;
    private final CartRepository cartRepository;

    public CartDetailService(CartDetailRepository cartDetailRepository, ProductRepository productRepository, LocalizationUtils localizationUtils, CartRepository cartRepository) {
        this.cartDetailRepository = cartDetailRepository;
        this.productRepository = productRepository;
        this.localizationUtils = localizationUtils;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDetail createCartDetail(CartDetailDTO cartDetailDTO) throws Exception {

        Cart cart =  cartRepository.findById(cartDetailDTO.getCartId())
                .orElseThrow(() ->
                        new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CART_NOT_FOUND, cartDetailDTO.getCartId())));
        Product product = productRepository.findById(cartDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DateTimeException(localizationUtils.getLocalizedMessage(MessageKeys.PRODUCT_NOT_FOUND, cartDetailDTO.getProductId())));
        CartDetail cartDetail = CartDetail.builder()
                .price(cartDetailDTO.getPrice())
                .totalMoney(cartDetailDTO.getTotalMoney())
                .productOfNumber(cartDetailDTO.getNumberOfProduct())
                .color(cartDetailDTO.getColor())
                .product(product)
                .cart(cart)
                .build();
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail updateCartDetail(Long id, CartDetailDTO cartDetailDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteCartDetail(Long id) {

    }
}
