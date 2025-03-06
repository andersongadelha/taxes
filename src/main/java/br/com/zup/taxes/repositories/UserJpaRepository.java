package br.com.zup.taxes.repositories;

import br.com.zup.taxes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);
    User findByUserName(String userName);
}
