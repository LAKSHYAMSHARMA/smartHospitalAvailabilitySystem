package com.narcissisticengineer.smartHospitalAvailabilitySystem.service;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.UserDTO;

import java.util.List;

public interface UserServices {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
}