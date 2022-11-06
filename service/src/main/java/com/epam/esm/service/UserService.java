package com.epam.esm.service;

import com.epam.esm.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findById(Long id);

    Page<User> findAll(int page, int size);
}
