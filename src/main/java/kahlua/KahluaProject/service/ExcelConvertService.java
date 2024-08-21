package kahlua.KahluaProject.service;

import jakarta.servlet.http.HttpServletResponse;
import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import kahlua.KahluaProject.repository.ApplyRepository;
import kahlua.KahluaProject.repository.ParticipantsRepository;
import kahlua.KahluaProject.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelConvertService {

    private final ApplyRepository applyRepository;
    private final TicketRepository ticketRepository;
    private final ParticipantsRepository participantsRepository;

    public ByteArrayInputStream applyListToExcel() throws IOException {
        // 엑셀 파일 생성
        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("지원자");

        // 헤더 작성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("이름");
        headerRow.createCell(1).setCellValue("성별");
        headerRow.createCell(2).setCellValue("생년월일");
        headerRow.createCell(3).setCellValue("전화번호");
        headerRow.createCell(4).setCellValue("주소");
        headerRow.createCell(5).setCellValue("학과");
        headerRow.createCell(6).setCellValue("1지망");
        headerRow.createCell(7).setCellValue("2지망");
        headerRow.createCell(8).setCellValue("깔루아 지원 동기");
        headerRow.createCell(9).setCellValue("지원 세션에 대한 경력 및 지원 이유");
        headerRow.createCell(10).setCellValue("이외에 다룰 줄 아는 악기");
        headerRow.createCell(11).setCellValue("포부 및 각오");
        headerRow.createCell(12).setCellValue("3월 18일 스케쥴");
        headerRow.createCell(13).setCellValue("면접 후 뒷풀이 참석 여부");

        // 데이터 작성
        List<Apply> applies = applyRepository.findAll();
        int rowIndex = 1;
        for (Apply apply : applies) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(apply.getName());
            row.createCell(1).setCellValue(apply.getGender().toString());
            row.createCell(2).setCellValue(apply.getBirth_date());
            row.createCell(3).setCellValue(apply.getPhoneNum());
            row.createCell(4).setCellValue(apply.getAddress());
            row.createCell(5).setCellValue(apply.getMajor());
            row.createCell(6).setCellValue(apply.getFirstPreference().toString());
            row.createCell(7).setCellValue(apply.getSecondPreference().toString());
            row.createCell(8).setCellValue(apply.getMotive());
            row.createCell(9).setCellValue(apply.getExperience_and_reason());
            row.createCell(10).setCellValue(apply.getPlay_instrument());
            row.createCell(11).setCellValue(apply.getReadiness());
            row.createCell(12).setCellValue(apply.getFinish_time());
            row.createCell(13).setCellValue(apply.getMeeting());


        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream ticketListToExcel() throws IOException {
        // 엑셀 파일 생성
        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("전체");

        // 헤더 작성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("티켓 유형");
        headerRow.createCell(1).setCellValue("상태");
        headerRow.createCell(2).setCellValue("예매 번호");
        headerRow.createCell(3).setCellValue("예매자 이름");
        headerRow.createCell(4).setCellValue("예매자 전화번호");
        headerRow.createCell(5).setCellValue("매수");
        headerRow.createCell(6).setCellValue("학과(신입생)");
        headerRow.createCell(7).setCellValue("학번(신입생)");
        headerRow.createCell(8).setCellValue("뒷풀이 참석 여부(신입생)");

        // 데이터 작성
        List<Ticket> tickets = ticketRepository.findAllOrderByStatus();
        int rowIndex = 1;
        for (Ticket ticket : tickets) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(ticket.getType().toString());
            row.createCell(1).setCellValue(ticket.getStatus().toString());
            row.createCell(2).setCellValue(ticket.getReservationId());
            row.createCell(3).setCellValue(ticket.getBuyer());
            row.createCell(4).setCellValue(ticket.getPhone_num());

            if (ticket.getType().equals(Type.GENERAL)) {
                row.createCell(5).setCellValue(participantsRepository.countByTicket_Id(ticket.getId()) + 1);
            }
            if (ticket.getType().equals(Type.FRESHMAN)) {
                row.createCell(5).setCellValue(1);
                row.createCell(6).setCellValue(ticket.getMajor());
                row.createCell(7).setCellValue(ticket.getStudentId());
                row.createCell(8).setCellValue(ticket.getMeeting().toString());;
            }
        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
