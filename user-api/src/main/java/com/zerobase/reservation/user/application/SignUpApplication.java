package com.zerobase.reservation.user.application;

import com.zerobase.reservation.user.client.MailgunClient;
import com.zerobase.reservation.user.client.mailgun.SendMailForm;
import com.zerobase.reservation.user.domain.SignUpForm;
import com.zerobase.reservation.user.domain.exception.CustomException;
import com.zerobase.reservation.user.domain.exception.ErrorCode;
import com.zerobase.reservation.user.domain.model.Customer;
import com.zerobase.reservation.user.domain.model.Manager;
import com.zerobase.reservation.user.service.customer.SignUpCustomerService;
import com.zerobase.reservation.user.service.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final ManagerService managerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("zerobase-test@gmail.com")
                    .to(form.getEmail())
                    .subject("Verification Eamil!")
                    .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
                    .build();
            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());

            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원 가입에 성공하였습니다.";
        }

    }

    public void sellerVerify(String email, String code) {
        managerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form) {
        if (managerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Manager m = managerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("cangiecho@gmail.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(m.getEmail(), m.getName(), "manager", code))
                    .build();
            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());

            managerService.changeManagerValidateEmail(m.getId(), code);
            return "회원 가입에 성공하였습니다.";
        }
    }


    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }


    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8801/signup/" + type + "/verify/?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
