package kahlua.KahluaProject.dto.post.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostGetResponse {

    @Schema(description = "아이디(번호)", example = "1")
    private Long id;

    @Schema(description = "게시글 제목", example = "2024년 9월 정기공연")
    private String title;

    @Schema(description = "게시글 내용", example = "안녕하세요 깔루아 기장입니다. 감사합니다.")
    private String content;

    @Schema(description = "게시글 작성자", example = "관리자")
    private String writer;

    @Schema(description = "게시글 좋아요 수", example = "13")
    private int likes;

    @Schema(description = "게시글 좋아요 여부", example = "TRUE")
    private boolean isLiked;

    //게시글 댓글 수

    @Schema(description = "게시글 사진 리스트", example = "https://bucketname.s3.region.amazonaws.com/image1.jpg")
    private List<PostImageGetResponse> imageUrls;

    @Schema(description = "작성한 날짜", example = "2024-08-01T00:00:00")
    private LocalDateTime created_at;

    @Schema(description = "수정한 날짜", example = "2024-08-10T00:00:00")
    private LocalDateTime updated_at;
}
