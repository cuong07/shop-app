package com.project.shopapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.project.shopapp.dtos.UserAddressDTO;
import com.project.shopapp.models.user.UserAddress;
import com.project.shopapp.services.user.UserAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/address")
@AllArgsConstructor
public class UserAddressController {
    private final UserAddressService userAddressService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> createAddress(
            @Valid @RequestBody UserAddressDTO userAddressDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            UserAddress userAddress = userAddressService.createUserAddress(userAddressDTO);
            return ResponseEntity.ok(userAddress);
        }catch (Exception e) {
            return  ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id){
        try {
            userAddressService.deleteUserAddress(id);
            return ResponseEntity.ok("Delete successfully");
        }catch  (Exception e){
            return ResponseEntity.badRequest().body("Delete failed");
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateAddress(
            @PathVariable("id") Long id,
                                           @RequestBody UserAddressDTO userAddressDTO,
                                           BindingResult result
                                           ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            UserAddress userAddress = userAddressService.updateUserAddress(id, userAddressDTO);
            return ResponseEntity.ok(userAddress);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
