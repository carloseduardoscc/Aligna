package br.com.carlos.projeto.application.reserve.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class GetReservesByServiceIdUseCase {
    GetLoggedUserUseCase auth;
    ReserveRepository rRepo;
    ReserveMapper mapper;

    @Transactional
    public Page<ReserveDTO> execute(long serviceId, Pageable pageable) {
        User user = auth.execute();

        if (user.getProfessionalProfile() == null) {
            throw new IllegalStateException("Usuário não possui perfil profissional registrado.");
        }

        Page<Reserve> reserves = rRepo.findAllByService_Id(serviceId, pageable);

        return reserves.map(mapper::toDTO);
    }
}
