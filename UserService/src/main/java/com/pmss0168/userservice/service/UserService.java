package com.pmss0168.userservice.service;

import com.pmss0168.userservice.data.User;
import com.pmss0168.userservice.model.UserDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User saveUser(User user);

    UserDTO login(String username, String password);
}
