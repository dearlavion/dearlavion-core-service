package com.dearlavion.coreservice.ai;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AiService {

    private final OpenAiService openAiService;
    private final boolean aiEnabled;


    public AiService(OpenAiProperties properties) {
        this.aiEnabled = properties.isEnabled();

        if (aiEnabled && properties.getApiKey() != null && !properties.getApiKey() .isBlank()) {
            this.openAiService = new OpenAiService(properties.getApiKey() );
        } else {
            this.openAiService = null; // Disabled
        }
    }

    public List<String> generateCategories(String title, String body, int n) {
        if (!aiEnabled) {
            return tokenizeCategories(title, body, n);
        }

        String prompt = String.format(
                "Analyze this wish and create %d categories / tags as JSON array:\nTitle: %s\nBody: %s",
                n, title, body
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", prompt)))
                .build();

        String response = openAiService.createChatCompletion(request)
                .getChoices().get(0)
                .getMessage().getContent();

        try {
            return new ObjectMapper().readValue(response, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public String generateShortDescription(String title, String body) {
        String prompt = String.format(
                "Create a short description (1-2 sentences) for this wish:\nTitle: %s\nBody: %s",
                title, body
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", prompt)))
                .build();

        return openAiService.createChatCompletion(request)
                .getChoices().get(0)
                .getMessage().getContent();
    }

    private List<String> tokenizeCategories(String title, String body, int limit) {

        String text = (title + " " + body).toLowerCase();

        // Remove punctuation
        text = text.replaceAll("[^a-z0-9\\s]", " ");

        String[] tokens = text.split("\\s+");

        // Basic stopwords
        List<String> stopWords = List.of(
                "the","and","for","with","this","that","have","from",
                "your","you","are","was","were","will","shall",
                "happy","wish","wishing","everyone","today","want"
        );

        return Arrays.stream(tokens)
                .filter(t -> t.length() > 2)          // remove short words
                .filter(t -> !stopWords.contains(t)) // remove stopwords
                .distinct()                          // remove duplicates
                .limit(limit)
                .toList();
    }

}
