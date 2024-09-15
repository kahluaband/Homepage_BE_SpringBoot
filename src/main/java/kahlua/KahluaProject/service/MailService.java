package kahlua.KahluaProject.service;

import jakarta.mail.internet.MimeMessage;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.domain.apply.Preference;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final ParticipantsService participantsService;

    @Async
    public void sendApplicantEmail(Apply apply){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(apply.getEmail()); // 메일 수신자
            mimeMessageHelper.setSubject("kahluaband.com 에서 발송한 메일입니다."); // 메일 제목
            mimeMessageHelper.setText(setApplicantContext(apply), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Succeeded to send Email");
        } catch (Exception e) {
            log.info("Failed to send Email");
            throw new RuntimeException(e);
        }
    }

    // thymeleaf를 통한 html 적용
    public String setApplicantContext(Apply apply) {

        Context context = new Context();
        context.setVariable("name", apply.getName());
        context.setVariable("phoneNum", apply.getPhoneNum());

        if (apply.getFirstPreference() == Preference.VOCAL) {
            return templateEngine.process("applicantVocal", context);
        }
        else {
            return templateEngine.process("applicantOther", context);
        }
    }

    @Async
    public void sendTicketEmail(Ticket ticket){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(ticket.getEmail()); // 메일 수신자
            mimeMessageHelper.setSubject("kahluaband.com 에서 발송한 메일입니다."); // 메일 제목
            mimeMessageHelper.setText(setTicketContext(ticket), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Succeeded to send Email");
        } catch (Exception e) {
            log.info("Failed to send Email");
            throw new RuntimeException(e);
        }
    }

    // thymeleaf를 통한 html 적용
    public String setTicketContext(Ticket ticket) {

        Context context = new Context();
        context.setVariable("buyer", ticket.getBuyer());
        context.setVariable("phoneNum", ticket.getPhone_num());
        context.setVariable("reservationId", ticket.getReservationId());

        if (ticket.getType() == Type.GENERAL) {
            context.setVariable("count", participantsService.getTotalGeneralTicket(ticket.getId()));
            return templateEngine.process("ticketGeneral", context);
        }
        else  {
            context.setVariable("studentId", ticket.getStudentId());
            return templateEngine.process("ticketFreshman", context);
        }
    }
}