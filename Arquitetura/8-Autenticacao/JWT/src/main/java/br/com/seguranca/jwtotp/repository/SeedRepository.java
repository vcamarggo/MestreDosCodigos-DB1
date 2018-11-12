package br.com.seguranca.jwtotp.repository;

import br.com.seguranca.jwtotp.dto.SeedDTO;
import org.springframework.data.repository.CrudRepository;

public interface SeedRepository extends CrudRepository<SeedDTO, String> {
}
