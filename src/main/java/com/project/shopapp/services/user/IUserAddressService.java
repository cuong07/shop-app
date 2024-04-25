package com.project.shopapp.services.user;

import com.project.shopapp.dtos.UserAddressDTO;
import com.project.shopapp.models.user.UserAddress;

import java.util.List;

public interface IUserAddressService {
    UserAddress createUserAddress(UserAddressDTO userAddressDTO) throws Exception;
    List<UserAddress> getListUserAddres(Long userId) throws Exception;
    UserAddress updateUserAddress(Long id, UserAddressDTO userAddressDTO) throws Exception;
    UserAddress getUserAddressById(Long id) throws  Exception;
    void deleteUserAddress(Long id) throws  Exception;
}
