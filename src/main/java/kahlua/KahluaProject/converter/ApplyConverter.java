package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.dto.apply.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.apply.response.ApplyAdminGetResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyCreateResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;

public class ApplyConverter {

    public static Apply toApply(ApplyCreateRequest applyCreateRequest) {
        return Apply.builder()
                .name(applyCreateRequest.getName())
                .phoneNum(applyCreateRequest.getPhone_num())
                .birth_date(applyCreateRequest.getBirth_date())
                .gender(applyCreateRequest.getGender())
                .address(applyCreateRequest.getAddress())
                .major(applyCreateRequest.getMajor())
                .firstPreference(applyCreateRequest.getFirst_preference())
                .secondPreference(applyCreateRequest.getSecond_preference())
                .experience_and_reason(applyCreateRequest.getExperience_and_reason())
                .play_instrument(applyCreateRequest.getPlay_instrument())
                .motive(applyCreateRequest.getMotive())
                .finish_time(applyCreateRequest.getFinish_time())
                .meeting(applyCreateRequest.getMeeting())
                .readiness(applyCreateRequest.getReadiness())
                .email(applyCreateRequest.getEmail())
                .build();
    }

    public static ApplyCreateResponse toApplyCreateResponse(Apply apply) {
        return ApplyCreateResponse.builder()
                .id(apply.getId())
                .name(apply.getName())
                .phone_num(apply.getPhoneNum())
                .birth_date(apply.getBirth_date())
                .gender(apply.getGender())
                .address(apply.getAddress())
                .major(apply.getMajor())
                .first_preference(apply.getFirstPreference())
                .second_preference(apply.getSecondPreference())
                .experience_and_reason(apply.getExperience_and_reason())
                .play_instrument(apply.getPlay_instrument())
                .motive(apply.getMotive())
                .finish_time(apply.getFinish_time())
                .meeting(apply.getMeeting())
                .readiness(apply.getReadiness())
                .email(apply.getEmail())
                .build();
    }

    public static ApplyGetResponse toApplyGetResponse(Apply apply) {
        return ApplyGetResponse.builder()
                .id(apply.getId())
                .name(apply.getName())
                .phone_num(apply.getPhoneNum())
                .birth_date(apply.getBirth_date())
                .gender(apply.getGender())
                .address(apply.getAddress())
                .major(apply.getMajor())
                .first_preference(apply.getFirstPreference())
                .second_preference(apply.getSecondPreference())
                .experience_and_reason(apply.getExperience_and_reason())
                .play_instrument(apply.getPlay_instrument())
                .motive(apply.getMotive())
                .finish_time(apply.getFinish_time())
                .meeting(apply.getMeeting())
                .readiness(apply.getReadiness())
                .build();
    }

    public static ApplyAdminGetResponse toApplyAdminGetResponse(Apply apply) {
        return ApplyAdminGetResponse.builder()
                .id(apply.getId())
                .name(apply.getName())
                .gender(apply.getGender())
                .birth_date(apply.getBirth_date())
                .phone_num(apply.getPhoneNum())
                .address(apply.getAddress())
                .major(apply.getMajor())
                .first_preference(apply.getFirstPreference())
                .second_preference(apply.getSecondPreference())
                .motive(apply.getMotive())
                .experience_and_reason(apply.getExperience_and_reason())
                .play_instrument(apply.getPlay_instrument())
                .readiness(apply.getReadiness())
                .build();
    }
}
