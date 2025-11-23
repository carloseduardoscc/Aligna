package br.com.carlos.projeto.application.service.useCase;

import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.mapper.ServiceMapper;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FindServiceByIdUseCase {

    private ServiceMapper mapper;
    private ServiceRepository repo;

    @Transactional
    public ServiceDTO execute(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(()-> new NoSuchElementException("Serviço com id: " + id + " não encontrado.")));
    }
}
