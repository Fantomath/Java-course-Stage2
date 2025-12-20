package ru.yourpackage.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yourpackage.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
