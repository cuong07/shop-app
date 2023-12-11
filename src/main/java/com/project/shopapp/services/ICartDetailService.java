package com.project.shopapp.services;

import com.project.shopapp.dtos.CartDetailDTO;
import com.project.shopapp.models.CartDetail;

public interface ICartDetailService {
    CartDetail createCartDetail(CartDetailDTO cartDetailDTO) throws Exception;
    CartDetail updateCartDetail(Long id, CartDetailDTO cartDetailDTO) throws Exception;
    void deleteCartDetail(Long id);
}
