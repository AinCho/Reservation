package com.zerobase.reservation.user.service.manager;

import com.zerobase.reservation.user.domain.SignUpForm;
import com.zerobase.reservation.user.domain.exception.CustomException;
import com.zerobase.reservation.user.domain.exception.ErrorCode;
import com.zerobase.reservation.user.domain.model.Manager;
import com.zerobase.reservation.user.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public Optional<Manager> findByIdAndEmail(Long id, String email) {
        return managerRepository.findByIdAndEmail(id, email);
    }

    public Optional<Manager> findValidSeller(String email, String password) {
        return managerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }

    public Manager signUp(SignUpForm form) {
        return managerRepository.save(Manager.from(form));
    }

    public boolean isEmailExist(String email) {
        return managerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (manager.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!manager.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (manager.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        manager.setVerify(true);
    }

    @Transactional
    public LocalDateTime changeManagerValidateEmail(Long customerId, String verificationCode) {
        Optional<Manager> managerOptional = managerRepository.findById(customerId);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            manager.setVerificationCode(verificationCode);
            manager.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return manager.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);

    }
}
