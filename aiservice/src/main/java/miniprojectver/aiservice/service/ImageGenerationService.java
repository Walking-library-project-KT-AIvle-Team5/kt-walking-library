package miniprojectver.aiservice.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration; // ⭐ 추가

@Service
public class ImageGenerationService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String generateCoverImage(String title, String author) {
        // ✅ Timeout 60초 설정
        OpenAiService service = new OpenAiService(openaiApiKey, Duration.ofSeconds(60));

        CreateImageRequest request = CreateImageRequest.builder()
                .prompt("Book cover for '" + title + "' by " + author)
                .size("512x512")
                .n(1)
                .build();

        Image image = service.createImage(request).getData().get(0);
        return image.getUrl();
    }
} 
