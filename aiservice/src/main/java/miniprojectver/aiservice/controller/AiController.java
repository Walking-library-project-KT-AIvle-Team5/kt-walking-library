package miniprojectver.aiservice.controller;

import miniprojectver.aiservice.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @PostMapping("/generate-cover")
    public RedirectView generateCover(@RequestParam String title, @RequestParam String author) {
        // ✅ AI 이미지 생성
        String imageUrl = imageGenerationService.generateCoverImage(title, author);

        // ✅ RedirectView로 반환하면, 브라우저가 해당 URL로 이동
        return new RedirectView(imageUrl);
    }

    @GetMapping("/generate-cover-test")
    @ResponseBody
    public String generateCoverTest() {
        String title = "AI 책";
        String author = "홍길동";

        String generatedImageUrl = "https://dummyimage.com/400x600/000/fff&text=" + title;

        return "<img src='" + generatedImageUrl + "' alt='테스트 표지'/>";
    }

}
