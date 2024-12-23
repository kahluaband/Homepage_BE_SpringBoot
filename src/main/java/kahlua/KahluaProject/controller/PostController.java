package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostGetResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.PostService;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "게시판", description = "게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/notice/create") 
    @Operation(summary = "공지사항 작성", description = "창립제, 악기 구비 등 깔루아 전체 공지 내용을 작성합니다.")
    public ApiResponse<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, @AuthenticationPrincipal AuthDetails authDetails) {
        PostCreateResponse postCreateResponse = postService.createPost(postCreateRequest, authDetails.user());
        return ApiResponse.onSuccess(postCreateResponse);
    }

    @PostMapping("/{post_id}/create_like")
    @Operation(summary = "좋아요 생성/삭제", description = "게시글을 좋아요 생성/삭제를 진행합니다.")
    public ResponseEntity<?> cratePostLike(@PathVariable("post_id") Long post_id, @AuthenticationPrincipal AuthDetails authDetails) {
        boolean result = postService.createPostLike(authDetails.user(), post_id);
        if (result == true) return ResponseEntity.ok().body(ApiResponse.onSuccess("like_create"));
        else return ResponseEntity.ok().body(ApiResponse.onSuccess("like_delete"));
    }

    @GetMapping("/notice/{post_id}/detail")
    @Operation(summary = "공지사항 내용 조회", description = "공지 내용을 조회합니다.")
    public ApiResponse<PostGetResponse> viewPost(@PathVariable("post_id") Long post_id, @AuthenticationPrincipal AuthDetails authDetails) {
        PostGetResponse postGetResponse = postService.viewPost(post_id, authDetails.user());
        return ApiResponse.onSuccess(postGetResponse);
    }

    @DeleteMapping("/notice/{post_id}/delete")
    @Operation(summary = "공지사항 삭제", description = "선택한 공지글을 삭제합니다.")
    public ApiResponse<?> deletePost(@PathVariable("post_id") Long post_id, @AuthenticationPrincipal AuthDetails authDetails) {
        postService.delete(post_id, authDetails.user());
        return ApiResponse.onSuccess("post delete success");
    }

    @GetMapping("/list")
    @Operation(summary = "게시판 목록 조회", description = "공지사항/깔브리타임 목록을 조회합니다.")
    public ApiResponse<Page<PostGetResponse>> viewPostList(@AuthenticationPrincipal AuthDetails authDetails,
                                                           @RequestParam(value = "post_type", required = false) String searchType,
                                                           @RequestParam(value = "search_word", required = false) String searchWord,
                                                           Pageable pageable) {
        return ApiResponse.onSuccess(postService.viewPostList(authDetails.user(), searchType, searchWord, pageable));
    }
}
