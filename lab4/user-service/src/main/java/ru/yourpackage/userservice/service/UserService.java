package ru.yourpackage.userservice.service;

import org.springframework.stereotype.Service;
import ru.yourpackage.userservice.dto.UserCreateDto;
import ru.yourpackage.userservice.dto.UserResponseDto;
import ru.yourpackage.userservice.entity.User;
import ru.yourpackage.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDto create(UserCreateDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User savedUser = repository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    public UserResponseDto get(Long id) {
        User user = repository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }
}
