package com.project.shopapp.services.cart;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.cart.Cart;

import java.util.List;

public interface ICartService {
    Cart createCart(CartDTO cartDTO) throws Exception;
    Cart getCart(Long id)throws Exception;
    Cart getCartCurrentByUser() throws Exception;
    Cart updateCart(Long id, CartDTO cartDTO) throws Exception;
    void deleteCart(Long id) throws DataNotFoundException, Exception;
    List<Cart> findByUserId(Long userId);
}
