package com.project.shopapp.services;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.models.Cart;
import com.project.shopapp.models.Order;

import java.util.List;

public interface ICartService {
    Cart createCart(CartDTO cartDTO) throws Exception;
    Cart getCart(Long id)throws Exception;
    Cart updateCart(Long id, CartDTO cartDTO) throws Exception;
    void deleteCart(Long id) throws Exception;
    List<Cart> findByUserId(Long userId);
}
