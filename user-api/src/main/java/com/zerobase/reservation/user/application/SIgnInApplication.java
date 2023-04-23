package com.zerobase.reservation.user.application;

import com.zerobase.reservation.common.UserType;
import com.zerobase.reservation.config.JwtAuthenticationProvider;
import com.zerobase.reservation.user.domain.SignInForm;
import com.zerobase.reservation.user.domain.exception.CustomException;
import com.zerobase.reservation.user.domain.exception.ErrorCode;
import com.zerobase.reservation.user.domain.model.Customer;
import com.zerobase.reservation.user.domain.model.Manager;
import com.zerobase.reservation.user.service.customer.CustomerService;
import com.zerobase.reservation.user.service.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SIgnInApplication {
    private final CustomerService customerService;
    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        Manager m = managerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        return provider.createToken(m.getEmail(), m.getId(), UserType.MANAGER);
    }
}
