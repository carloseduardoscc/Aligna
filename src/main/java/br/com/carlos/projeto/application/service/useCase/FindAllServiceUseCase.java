package br.com.carlos.projeto.application.service.useCase;

import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.mapper.ServiceMapper;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class FindAllServiceUseCase {
    private ServiceMapper mapper;
    private ServiceRepository repo;

    @Transactional
    public Page<ServiceDTO> execute(Pageable pageable) {
        Page<Service> serviceEntities = repo.findAll(pageable);
        return serviceEntities.map(entity -> mapper.toDTO(entity));
    }
}
