package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RequestReserveCommand;
import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.Mapper;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class MeService {

    AuthenticationService auth;
    UserRepository uRepo;
    ServiceRepository sRepo;
    ReserveRepository rRepo;
    Mapper mapper;

    @Transactional
    public ReserveDTO requestReserve(RequestReserveCommand cmd) {
        User currentUser = auth.getCurrentAuthenticatedUser();
        Service service = sRepo.findById(cmd.service_id())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + cmd.service_id()));;

        Reserve reserve = new Reserve(cmd.dateTime(), currentUser, service);
        currentUser.addReserve(reserve);
        service.addReserve(reserve);

        reserve = rRepo.save(reserve);

        return mapper.toDTO(reserve);

    }

    @Transactional
    public Page<ReserveDTO> getMyReserves(Pageable pageable) {
        User currentUser = auth.getCurrentAuthenticatedUser();
        return rRepo.findAllByApplicant_Id(currentUser.getId(), pageable).map(mapper::toDTO);
    }

    @Transactional
    public UserDTO me() {
        return mapper.toDTO(auth.getCurrentAuthenticatedUser());
    }
}
