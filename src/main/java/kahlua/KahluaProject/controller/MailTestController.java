package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.mail.request.MailSendDTO;
import kahlua.KahluaProject.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailTestController {

    private final MailService mailService;

    @PostMapping("/mail")
    public ApiResponse<?> sendMail(@RequestBody MailSendDTO mailSendDTO) {
        mailService.sendEmail(mailSendDTO);
        return ApiResponse.onSuccess("메일 발송 성공");
    }
}
