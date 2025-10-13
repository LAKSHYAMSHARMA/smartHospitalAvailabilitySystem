package com.narcissisticengineer.smartHospitalAvailabilitySystem.service.implementation;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.UserDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.AppUser;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.UserRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.UserServices;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServicesImplementation implements UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserServicesImplementation(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EntityExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }

        AppUser user = modelMapper.map(userDTO, AppUser.class);

        user.setPassword(userDTO.getPassword());

        AppUser savedUser = userRepository.save(user);

        UserDTO responseDTO = modelMapper.map(savedUser, UserDTO.class);

        responseDTO.setPassword(null);

        return responseDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        UserDTO responseDTO = modelMapper.map(user, UserDTO.class);
        responseDTO.setPassword(null);
        return responseDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO dto = modelMapper.map(user, UserDTO.class);
                    dto.setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}