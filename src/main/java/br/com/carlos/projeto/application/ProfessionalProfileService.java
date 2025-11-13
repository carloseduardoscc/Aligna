package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.mapper.ProfessionalProfileMapper;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.domain.repository.ProfessionalProfileRepository;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.ProfessionalProfileEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProfessionalProfileService {

    AuthenticationService auth;
    ProfessionalProfileRepository<ProfessionalProfileEntity> repo;
    UserRepository<UserEntity> userRepo;
    ProfessionalProfileMapper pMapper;
    UserMapper uMapper;

    public ProfessionalProfileService(AuthenticationService auth, ProfessionalProfileRepository<ProfessionalProfileEntity> repo, ProfessionalProfileMapper pMapper, UserMapper uMapper, UserRepository<UserEntity> userRepo) {
        this.auth = auth;
        this.repo = repo;
        this.pMapper = pMapper;
        this.uMapper = uMapper;
        this.userRepo = userRepo;
    }

    @Transactional
    public ProfessionalProfileDTO findById(Long id){
        try{
            return pMapper.toDTO(pMapper.fromEntity(repo.findById(id)));
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Perfil profissional com id: " + id+ " não encontrado.");
        }
    }

    @Transactional
    public Page<ProfessionalProfileDTO> findAll(Pageable pageable){
        Page<ProfessionalProfileDTO> profiles = repo.findAll(pageable)
                .map(entity -> pMapper.toDTO(pMapper.fromEntity((ProfessionalProfileEntity) entity)));
        return profiles;
    }
}
