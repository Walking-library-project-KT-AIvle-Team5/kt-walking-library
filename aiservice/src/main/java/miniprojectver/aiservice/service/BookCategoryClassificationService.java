package miniprojectver.aiservice.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BookCategoryClassificationService {

    private final OpenAiService openAiService;

    public BookCategoryClassificationService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public String classifyCategory(String title, String author, String summary) {
        ChatMessage systemMessage = new ChatMessage("system", "You are a helpful assistant that classifies Korean books into categories such as 역사, 문학, 과학, 자기계발, 철학, 사회, 경제, 기술, 예술, 아동, 교육, 여행, 요리, 건강, 종교, 기타.");
        ChatMessage userMessage = new ChatMessage("user",
                String.format("다음 책의 제목, 저자, 요약을 참고하여 적절한 카테고리 하나를 한국어로만 답해주세요.\n" +
                        "제목: %s\n저자: %s\n요약: %s", title, author, summary));

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(systemMessage, userMessage))
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent().trim();
    }
}
