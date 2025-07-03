package miniprojectver.aiservice.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TextSummarizationService {

    private final OpenAiService openAiService;

    public TextSummarizationService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public String summarize(String text) {
        ChatMessage systemMessage = new ChatMessage("system", "You are a helpful assistant that summarizes text in Korean.");
        ChatMessage userMessage = new ChatMessage("user", "다음 글을 한국어로 간결하게 요약해줘:\n" + text);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(systemMessage, userMessage))
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
