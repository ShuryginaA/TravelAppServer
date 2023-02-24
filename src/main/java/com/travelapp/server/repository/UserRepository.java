package com.travelapp.server.repository;

import com.travelapp.server.entity.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface UserRepository extends CrudRepository<User, Long> {

    @Nullable
    User findByUsername(String username);

    @Nullable
    List<User> findByEmail(String email);

    @Nullable
    Long deleteByUsername(String username);
}
