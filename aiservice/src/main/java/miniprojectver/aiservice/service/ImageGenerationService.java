package miniprojectver.aiservice.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageGenerationService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String generateCoverImage(String title, String author) {
        OpenAiService service = new OpenAiService(openaiApiKey);

        String prompt = "Beautiful and attractive book cover for a book titled '" + title + "' by " + author;
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt)
                .size("512x512")
                .n(1)
                .build();

        Image image = service.createImage(request).getData().get(0);
        return image.getUrl();
    }
}
