package br.com.carlos.projeto.application.professional.useCase;

import br.com.carlos.projeto.application.professional.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.professional.mapper.ProfessionalMapper;
import br.com.carlos.projeto.infra.repository.ProfessionalProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindAllProfessionalUseCase {
    ProfessionalProfileRepository repo;
    ProfessionalMapper mapper;

    @Transactional
    public Page<ProfessionalProfileDTO> execute(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDTO);
    }
}
