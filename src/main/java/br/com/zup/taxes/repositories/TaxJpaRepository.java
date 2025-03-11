package br.com.zup.taxes.repositories;

import br.com.zup.taxes.models.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxJpaRepository extends JpaRepository<Tax, Long> {
}
