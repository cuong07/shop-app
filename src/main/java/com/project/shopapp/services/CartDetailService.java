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
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Optional;

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
        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByCartIdAndProductId(
                cartDetailDTO.getCartId(), cartDetailDTO.getProductId());
        if (existingCartDetail.isPresent()) {
            return existingCartDetail.get();
        }
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
        CartDetail existingCartDetail =  cartDetailRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CART_DETAIL_NOT_FOUND, id)));

        Cart cart =  cartRepository.findById(cartDetailDTO.getCartId())
                .orElseThrow(() ->
                        new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CART_NOT_FOUND, cartDetailDTO.getCartId())));

        Product product = productRepository.findById(cartDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DateTimeException(localizationUtils.getLocalizedMessage(MessageKeys.PRODUCT_NOT_FOUND, cartDetailDTO.getProductId())));

        existingCartDetail.setProductOfNumber(existingCartDetail.getProductOfNumber() + cartDetailDTO.getNumberOfProduct());
        return cartDetailRepository.save(existingCartDetail);
    }

    @Override
    public CartDetail updateCartDetailNumberOfProduct(Long id, int numberOfProduct) throws Exception {
        CartDetail existingCartDetail =  cartDetailRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CART_DETAIL_NOT_FOUND, id)));
        existingCartDetail.setProductOfNumber(existingCartDetail.getProductOfNumber() + numberOfProduct);
        return cartDetailRepository.save(existingCartDetail);
    }

    @Override
    public void deleteCartDetail(Long id) {
        cartDetailRepository.deleteById(id);
    }
}
45