package org.mascheraveneziana.zitan.repository;

import java.util.List;
import java.util.Optional;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
}
