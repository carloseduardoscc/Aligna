package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.application.mapper.ServiceMapper;
import br.com.carlos.projeto.domain.repository.ServiceRepository;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ServiceService {

    ServiceRepository<ServiceEntity> repo;
    ServiceMapper mapper;

    public ServiceService(ServiceRepository<ServiceEntity> repo, ServiceMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional
    public Page<ServiceDTO> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::fromEntity).map(mapper::toDTO);
    }

    @Transactional
    public ServiceDTO findById(long id) {
        try {
            return mapper.toDTO(mapper.fromEntity(repo.findById(id)));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Serviço com id: " + id + " não encontrado.");
        }

    }

}
