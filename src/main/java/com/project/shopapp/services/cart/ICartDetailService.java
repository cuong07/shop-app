package com.project.shopapp.services.cart;

import com.project.shopapp.dtos.CartDetailDTO;
import com.project.shopapp.models.cart.CartDetail;

public interface ICartDetailService {
    CartDetail createCartDetail(CartDetailDTO cartDetailDTO) throws Exception;
    CartDetail updateCartDetail(Long id, CartDetailDTO cartDetailDTO) throws Exception;

    CartDetail updateCartDetailNumberOfProduct(Long id, int numberOfProduct) throws Exception;
    void deleteCartDetail(Long id);
}
