package com.project.shopapp.services.cart;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.cart.Cart;
import com.project.shopapp.models.user.User;
import com.project.shopapp.repositories.CartRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Cart createCart(CartDTO cartDTO) throws Exception {
        Cart cart = new Cart();
        User user = userRepository.findById(cartDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id = " + cartDTO.getUserId()));
        modelMapper.typeMap(CartDTO.class, Cart.class)
                .addMappings(mapper -> mapper.skip(Cart::setId));
        modelMapper.map(cartDTO, cart);
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart getCart(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.getDetailCart(id);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }
        throw new DataNotFoundException("Cannot find cart with id =" + id);
    }

    @Override
    public Cart getCartCurrentByUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        Optional<Cart> optionalCart = cartRepository.findActiveCartsByUserId(user.getId());
        if(optionalCart.isPresent()){
            return optionalCart.get();
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(user.getId());
        cartDTO.setNote("");
        cartDTO.setIsActive(true);
        Cart cart = this.createCart(cartDTO);
        return  cart;
//        throw new DataNotFoundException("Cannot find cart with id =" + user.getId());
    }

    @Override
    public Cart updateCart(Long id, CartDTO cartDTO) throws Exception {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cart not found! "));
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = " + cartDTO.getUserId()));

        if (existingCart != null) {
            modelMapper.typeMap(CartDTO.class, Cart.class)
                    .addMappings(mapper -> mapper.skip(Cart::setId));
            modelMapper.map(cartDTO, existingCart);
            existingCart.setUser(user);
            return cartRepository.save(existingCart);
        }
        return null;
    }

    @Override
    public void deleteCart(Long id) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cart not found!"));
        cart.setIsActive(false);
        cartRepository.save(cart);
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
