package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.RoleEnum;

public interface RoleRepository {
    boolean existsByName(String name);
    void save(RoleEnum role);
}
