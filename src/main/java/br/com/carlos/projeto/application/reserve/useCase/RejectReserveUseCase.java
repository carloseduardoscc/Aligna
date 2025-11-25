package br.com.carlos.projeto.application.reserve.useCase;

import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class RejectReserveUseCase {

    ServiceRepository sRepo;
    ReserveRepository rRepo;
    ReserveMapper mapper;

    @Transactional
    public ReserveDTO execute(Long serviceId, Long reserveId) {
        Service service = sRepo.findById(serviceId).get();

        Reserve reserve = service.getReserves().stream().filter(r -> r.getId().equals(reserveId)).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Reserva com ID " + reserveId + " não encontrada para o serviço com ID " + serviceId)
        );
        reserve.reject();

        reserve = rRepo.save(reserve);
        return mapper.toDTO(reserve);
    }
}
