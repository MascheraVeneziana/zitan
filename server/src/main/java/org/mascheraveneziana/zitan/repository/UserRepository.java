package org.mascheraveneziana.zitan.repository;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
