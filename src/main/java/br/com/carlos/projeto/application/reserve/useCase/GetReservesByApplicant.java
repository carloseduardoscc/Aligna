package br.com.carlos.projeto.application.reserve.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetReservesByApplicant {
    GetLoggedUserUseCase auth;
    ReserveRepository rRepo;
    ReserveMapper mapper;

    @Transactional
    public Page<ReserveDTO> execute(Pageable pageable) {
        User currentUser = auth.execute();
        return rRepo.findAllByApplicant_Id(currentUser.getId(), pageable).map(mapper::toDTO);
    }
}
