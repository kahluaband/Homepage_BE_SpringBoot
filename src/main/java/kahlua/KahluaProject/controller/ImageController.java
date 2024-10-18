package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.dto.image.request.ImageNameDto;
import kahlua.KahluaProject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/image")
@Tag(name = "이미지", description = "이미지 업로드/삭제/조회 등 이미지와 관련된 API")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/presigned-url")
    @Operation(summary = "이미지 업로드용 presigned url 발급", description = "이미지 업로드용 presigned url을 발급합니다.")
    public ResponseEntity<String> saveImage(@RequestBody ImageNameDto imageNameDto) {
        String preSignedUrl = imageService.getPreSignedUrl("kahlua", imageNameDto.imageName());
        return ResponseEntity.ok(preSignedUrl);
    }
}
