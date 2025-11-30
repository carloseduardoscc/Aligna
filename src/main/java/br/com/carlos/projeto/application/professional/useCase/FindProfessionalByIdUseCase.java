package br.com.carlos.projeto.application.professional.useCase;

import br.com.carlos.projeto.application.professional.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.professional.mapper.ProfessionalMapper;
import br.com.carlos.projeto.infra.repository.ProfessionalProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class FindProfessionalByIdUseCase {
    ProfessionalMapper mapper;
    ProfessionalProfileRepository Repo;

    @Transactional
    public ProfessionalProfileDTO execute(Long id) {
        return mapper.toDTO(Repo.findById(id).orElseThrow(() -> new NoSuchElementException("Perfil profissional com id: " + id + " não encontrado.")));
    }
}
