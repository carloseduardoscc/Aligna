package br.com.carlos.projeto.application.reserve.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FindByIdUseCase {
    ReserveRepository rRepo;
    ReserveMapper mapper;
    GetLoggedUserUseCase auth;

    @Transactional
    public ReserveDTO executeAsApplicant(Long id) {
        User user = auth.execute();
        Reserve reserve = rRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Reserva não encontrada"));
        if (!reserve.getApplicant().getId().equals(user.getId())) {
            throw new NoSuchElementException("Reserva não encontrada para o usuário autenticado");
        }
        return mapper.toDTO(reserve);
    }

    @Transactional
    public ReserveDTO executeAsProfessional(Long serviceID, Long reserveId) {
        User user = auth.execute();

        Reserve reserve = rRepo.findById(reserveId).orElseThrow(() -> new NoSuchElementException("Reserva não encontrada"));
        if (!reserve.getService().getProfessionalProfile().getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException("Reserva não encontrada para o usuário autenticado");
        }
        if (!reserve.getService().getId().equals(serviceID)) {
            throw new NoSuchElementException("Reserva não encontrada para o serviço informado");
        }

        return mapper.toDTO(reserve);
    }
}
