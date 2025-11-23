package br.com.carlos.projeto.application.reserve.useCase;


import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.reserve.command.RequestReserveCommand;
import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.reserve.mapper.ReserveMapper;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RequestReserveUseCase {
    GetLoggedUserUseCase auth;
    UserRepository uRepo;
    ServiceRepository sRepo;
    ReserveRepository rRepo;
    ReserveMapper mapper;

    @Transactional
    public ReserveDTO execute(RequestReserveCommand cmd) {
        User currentUser = auth.execute();
        br.com.carlos.projeto.domain.Service service = sRepo.findById(cmd.service_id())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + cmd.service_id()));;

        Reserve reserve = new Reserve(cmd.dateTime(), currentUser, service);
        currentUser.addReserve(reserve);
        service.addReserve(reserve);

        reserve = rRepo.save(reserve);

        return mapper.toDTO(reserve);

    }
}
