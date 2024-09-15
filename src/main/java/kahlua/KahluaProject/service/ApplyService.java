package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.ApplyConverter;
import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.domain.apply.Preference;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.apply.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.apply.response.*;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final MailService mailService;

    @Transactional
    public ApplyCreateResponse createApply(ApplyCreateRequest applyCreateRequest) {

        if(applyRepository.existsByPhoneNum(applyCreateRequest.getPhone_num())) {
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_APPLICANT);
        }

        Apply apply = ApplyConverter.toApply(applyCreateRequest);
        applyRepository.save(apply);

        mailService.sendApplicantEmail(apply); // 보컬과 나머지 세션으로 구분하여 지원 확인 메일 발송

        ApplyCreateResponse applyCreateResponse = ApplyConverter.toApplyCreateResponse(apply);

        return applyCreateResponse;
    }

    public ApplyGetResponse getApply(Long applyId) {

        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.APPLICANT_NOT_FOUND));

        ApplyGetResponse applyGetResponse = ApplyConverter.toApplyGetResponse(apply);
        return applyGetResponse;
    }

    public ApplyAdminGetResponse getApplyAdmin(Long applyId) {

        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.APPLICANT_NOT_FOUND));

        ApplyAdminGetResponse applyAdminGetResponse = ApplyConverter.toApplyAdminGetResponse(apply);
        return applyAdminGetResponse;
    }

    public ApplyListResponse getApplyList(User user) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Apply> applies = applyRepository.findAll();
        List<ApplyItemResponse> applyItemResponses = new ArrayList<>();
        Long total = applyRepository.count();

        for (Apply apply : applies) {
            ApplyItemResponse applyItemResponse = ApplyItemResponse.builder()
                    .id(apply.getId())
                    .name(apply.getName())
                    .phone_num(apply.getPhoneNum())
                    .birth_date(apply.getBirth_date())
                    .gender(apply.getGender())
                    .address(apply.getAddress())
                    .major(apply.getMajor())
                    .first_preference(apply.getFirstPreference())
                    .second_preference(apply.getSecondPreference())
                    .build();

            applyItemResponses.add(applyItemResponse);
        }

        ApplyListResponse applyListResponse = ApplyListResponse.builder()
                .total(total)
                .applies(applyItemResponses)
                .build();

        return applyListResponse;
    }

    public ApplyListResponse getApplyListByPreference(User user, Preference preference) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Apply> firstPreferenceApplies = applyRepository.findAllByFirstPreference(preference);
        List<Apply> secondPreferenceApplies = applyRepository.findAllBySecondPreference(preference);

        List<ApplyItemResponse> applyItemResponses = new ArrayList<>();

        for (Apply apply : firstPreferenceApplies) {
            ApplyItemResponse applyItemResponse = ApplyItemResponse.builder()
                    .id(apply.getId())
                    .name(apply.getName())
                    .phone_num(apply.getPhoneNum())
                    .birth_date(apply.getBirth_date())
                    .gender(apply.getGender())
                    .address(apply.getAddress())
                    .major(apply.getMajor())
                    .first_preference(apply.getFirstPreference())
                    .second_preference(apply.getSecondPreference())
                    .build();

            applyItemResponses.add(applyItemResponse);
        }

        for (Apply apply : secondPreferenceApplies) {
            ApplyItemResponse applyItemResponse = ApplyItemResponse.builder()
                    .id(apply.getId())
                    .name(apply.getName())
                    .phone_num(apply.getPhoneNum())
                    .birth_date(apply.getBirth_date())
                    .gender(apply.getGender())
                    .address(apply.getAddress())
                    .major(apply.getMajor())
                    .first_preference(apply.getFirstPreference())
                    .second_preference(apply.getSecondPreference())
                    .build();

            applyItemResponses.add(applyItemResponse);
        }

        ApplyListResponse applyListResponse = ApplyListResponse.builder()
                .total((long)applyItemResponses.size())
                .applies(applyItemResponses)
                .build();

        return applyListResponse;
    }
}
