package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CartDTO;
import com.project.shopapp.models.cart.Cart;
import com.project.shopapp.responses.CartResponse;
import com.project.shopapp.services.cart.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ModelMapper modelMapper;
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> createCart(@Valid @RequestBody CartDTO cartDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();

                return ResponseEntity.badRequest().body(errorMessages);
            }
            Cart cart = cartService.createCart(cartDTO);
            return ResponseEntity.ok(CartResponse.fromCart(cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<?> getCarts(@PathVariable("id") Long id) {
        try {
            List<Cart> carts = cartService.findByUserId(id);
            List<CartResponse> cartResponses = carts.stream()
                    .map(CartResponse::fromCart)
                    .toList();
            return ResponseEntity.ok(cartResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCart(@PathVariable("id") Long id) {
        try {
            Cart cart = cartService.getCart(id);
            return ResponseEntity.ok(CartResponse.fromCart(cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    private ResponseEntity<?> getCurrentCartByUser() {
        try {
            Cart cart = cartService.getCartCurrentByUser();
            return ResponseEntity.ok(CartResponse.fromCart(cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> updateCart(@PathVariable("id") Long id, @Valid @RequestBody CartDTO cartDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Cart cart = cartService.updateCart(id, cartDTO);
            return  ResponseEntity.ok(CartResponse.fromCart(cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> deleteCart(@PathVariable("id") Long id){
        try {
            cartService.deleteCart(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
