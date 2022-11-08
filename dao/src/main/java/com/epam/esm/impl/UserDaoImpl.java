package com.epam.esm.impl;

import com.epam.esm.AbstractDao;
import com.epam.esm.User;
import com.epam.esm.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    @Autowired
    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User create(User entity) {
        throw new UnsupportedOperationException();
    }
}
