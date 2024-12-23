package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import kahlua.KahluaProject.domain.post.PostLikes;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.request.PostImageCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostGetResponse;
import kahlua.KahluaProject.dto.post.response.PostImageCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostImageGetResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(PostCreateRequest postCreateRequest, User user) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .user(user)
                .postType(postCreateRequest.getPostType())
                .build();
    }

    public static PostLikes toPostLikes(Post post, User user) {
        return PostLikes.builder()
                .post(post)
                .user(user)
                .build();
    }

    public static PostCreateResponse toPostCreateResponse(Post post, User user, List<PostImageCreateResponse> imageUrls) {
        return PostCreateResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(user.getEmail())
                .likes(post.getLikes())
                .imageUrls(imageUrls)
                .postType(post.getPostType())
                .created_at(post.getCreatedAt())
                .updated_at(post.getUpdatedAt())
                .build();
    }

    public static List<PostImage> toPostImage(List<PostImageCreateRequest> postImageCreateRequests, Post post) {
        return Optional.ofNullable(postImageCreateRequests)
                .orElse(Collections.emptyList())
                .stream()
                .map((PostImageCreateRequest imageRequest) -> PostImage.builder()
                        .url(imageRequest.getUrl())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<PostImageCreateResponse> toPostImageCreateResponse(List<PostImage> postImage) {
        List<PostImageCreateResponse> imageUrlResponses = postImage.stream()
                .map(postImage1 -> PostImageCreateResponse.builder()
                        .id(postImage1.getId())
                        .url(postImage1.getUrl())
                        .build())
                .collect(Collectors.toList());

        return imageUrlResponses;
    }

    public static PostGetResponse toPostGetResponse(Post post) {
        // imageUrls 변환 로직
        List<PostImageGetResponse> getImageUrls = post.getImageUrls().stream()
                .map(postImage2 -> PostImageGetResponse.builder()
                        .id(postImage2.getId())
                        .url(postImage2.getUrl())
                        .build())
                .collect(Collectors.toList());

        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser() != null ? post.getUser().getName() : null)
                .likes(post.getLikes())
                .isLiked(post.isLiked())
                .imageUrls(getImageUrls)
                .created_at(post.getCreatedAt())
                .created_at(post.getUpdatedAt())
                .build();
    }

    public static List<PostGetResponse> toPostListResponse(List<Post> posts) {
        return Optional.ofNullable(posts)
                .orElse(Collections.emptyList())
                .stream()
                .map(PostConverter::toPostGetResponse)
                .collect(Collectors.toList());
    }
}
