package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.mapper.Mapper;
import br.com.carlos.projeto.infra.repository.ProfessionalProfileRepository;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProfessionalProfileService {

    AuthenticationService auth;
    ProfessionalProfileRepository pRepo;
    UserRepository uRepo;
    Mapper mapper;

    @Transactional
    public ProfessionalProfileDTO findById(Long id) {
        return mapper.toDTO(pRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Perfil profissional com id: " + id + " não encontrado.")));
    }

    @Transactional
    public Page<ProfessionalProfileDTO> findAll(Pageable pageable) {
        return pRepo.findAll(pageable).map(mapper::toDTO);
    }
}
