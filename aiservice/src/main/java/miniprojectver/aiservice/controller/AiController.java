package miniprojectver.aiservice.controller;
import org.springframework.web.servlet.view.RedirectView;
import miniprojectver.aiservice.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @GetMapping("/generate-cover") // ✅ GET으로 변경
    public RedirectView generateCover(@RequestParam String title, @RequestParam String author) {
        String imageUrl = imageGenerationService.generateCoverImage(title, author);
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
