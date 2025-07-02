package miniprojectver.aiservice.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TextSummarizationService {

    private final OpenAiService openAiService;

    public TextSummarizationService(@Value("${openai.api.key}") String openaiApiKey) {
        this.openAiService = new OpenAiService(openaiApiKey);
    }

    public String summarize(String text) {
        ChatMessage systemMessage = new ChatMessage("system", "You are a helpful assistant that summarizes texts in Korean concisely.");
        ChatMessage userMessage = new ChatMessage("user", "다음 내용을 한국어로 간결하게 요약해줘:\n" + text);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(Arrays.asList(systemMessage, userMessage))
                .maxTokens(200)
                .temperature(0.5)
                .build();

        return openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent()
                .trim();
    }
}
