package kahlua.KahluaProject.domain.apply;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Apply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    private String name;

    @Column(name = "phone_num")
    private String phoneNum;

    private String birth_date;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private String major;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_preference")
    private Preference firstPreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_preference")
    private Preference secondPreference;

    private String experience_and_reason;

    private String play_instrument;

    private String motive;

    private String finish_time;

    // @ColumnDefault("false")
    private Boolean meeting;

    private String readiness;

    private String email; // 지원자 이메일 (지원 확인 메일 발송에 사용)
}
