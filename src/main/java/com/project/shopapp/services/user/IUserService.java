package com.project.shopapp.services.user;


import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.user.User;

import java.util.Optional;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException, Exception;
    String login(String phoneNumber, String password) throws Exception;
    User getUser(Long id) throws  Exception;
    User getCurrentUser() throws Exception;
}
