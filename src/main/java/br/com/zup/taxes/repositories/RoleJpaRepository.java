package br.com.zup.taxes.repositories;

import br.com.zup.taxes.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
