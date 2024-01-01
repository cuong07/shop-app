package com.project.shopapp.services.user;


import com.project.shopapp.dtos.UserAddressDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.user.User;
import com.project.shopapp.models.user.UserAddress;
import com.project.shopapp.repositories.UserAddressRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAddressService implements IUserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    @Override
    public UserAddress createUserAddress(UserAddressDTO userAddressDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        UserAddress userAddress = UserAddress.builder()
                .addressOne(userAddressDTO.getAddressOne())
                .addressSecond(userAddressDTO.getAddressSecond())
                .city(userAddressDTO.getCity())
                .country(userAddressDTO.getCountry())
                .user(user)
                .province(userAddressDTO.getProvince())
                .build();
        return userAddressRepository.save(userAddress);
    }

    @Override
    public List<UserAddress> getListUserAddres(Long userId) throws Exception {
        return null;
    }

    @Override
    public UserAddress updateUserAddress(Long id, UserAddressDTO userAddressDTO) throws Exception {
        UserAddress existUserAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("address not found with id: " + id));
        existUserAddress.setAddressOne(userAddressDTO.getAddressOne());
        existUserAddress.setAddressSecond(userAddressDTO.getAddressSecond());
        existUserAddress.setCity(userAddressDTO.getCity());
        existUserAddress.setProvince(userAddressDTO.getProvince());
        existUserAddress.setCountry(userAddressDTO.getCountry());
        return userAddressRepository.save(existUserAddress);
    }

    @Override
    public UserAddress getUserAddressById(Long id) throws Exception {
        return userAddressRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Address for use not found"));
    }

    @Override
    public void deleteUserAddress(Long id) throws Exception {
        userAddressRepository.deleteById(id);
    }
}
