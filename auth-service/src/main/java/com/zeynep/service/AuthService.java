package com.zeynep.service;

import com.zeynep.dto.request.*;
import com.zeynep.dto.response.RegisterResponseDto;
import com.zeynep.exception.AuthManagerException;
import com.zeynep.exception.ErrorType;
import com.zeynep.manager.UserManager;
import com.zeynep.mapper.AuthMapper;
import com.zeynep.repository.AuthRepository;
import com.zeynep.repository.entity.Auth;
import com.zeynep.utility.CodeGenerator;
import com.zeynep.utility.JwtTokenManager;
import com.zeynep.utility.ServiceManager;
import com.zeynep.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final AuthRepository authRepository;
    private final UserManager userManager;
    private final JwtTokenManager tokenManager;



    public AuthService(AuthRepository authRepository, UserManager userManager, JwtTokenManager tokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.fromRegisterRequestToAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        userManager.createUser(AuthMapper.INSTANCE.fromAuthToUserCreateRequestDto(auth));
        return AuthMapper.INSTANCE.fromAuthToRegisterResponse(auth);
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        } else if (auth.get().getStatus() == EStatus.ACTIVE) {
            Optional<String> token = tokenManager.createToken(auth.get().getId(), auth.get().getRole());
//            if (token.isEmpty()){
//                throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
//            }
            return token.orElseThrow(() -> {
                throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
            });
        } else
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
    }
    public Boolean activateStatus(ActivationRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if(auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if(dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            //            auth.get().setUpdateDate(System.currentTimeMillis());
            //            authRepository.save(auth.get());
            //userManager.activateStatus(auth.get().getId());
            userManager.activateStatus2(ActivateStatusRequestDto.builder().authId(dto.getId()).build());
            return true;
        } else {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_ERROR);
        }
    }
    public Boolean updateUser(UserUpdateRequestDto dto) {
        Optional<Long> id = tokenManager.getIdFromToken(dto.getToken());
        if (id.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userManager.updateUser(dto);
    }

    public Boolean updateEmailOrUsername(UpdateEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        update(auth.get());
        return true;
    }


    public Boolean deleteUser(Long id) {
        Optional<Auth> auth = authRepository.findById(id);
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        userManager.deleteUser(id);
        return true;
    }
}
