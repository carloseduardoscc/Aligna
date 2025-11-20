package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.application.mapper.Mapper;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ServiceService {

    ServiceRepository repo;
    Mapper mapper;

    @Transactional
    public Page<ServiceDTO> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional
    public ServiceDTO findById(long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(() -> new NoSuchElementException("Serviço com id: " + id + " não encontrado.")));
    }

}
