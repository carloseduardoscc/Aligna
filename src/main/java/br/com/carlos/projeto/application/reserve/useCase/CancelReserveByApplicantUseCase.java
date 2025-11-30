package br.com.carlos.projeto.application.reserve.useCase;

import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.CancellationSource;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class CancelReserveByApplicantUseCase {

    ReserveRepository rRepo;
    ReserveMapper mapper;

    @Transactional
    public ReserveDTO execute(Long reserveId) {
        Reserve reserve = rRepo.findById(reserveId).get();

        reserve.cancel(CancellationSource.APPLICANT);

        reserve = rRepo.save(reserve);
        return mapper.toDTO(reserve);
    }
}
