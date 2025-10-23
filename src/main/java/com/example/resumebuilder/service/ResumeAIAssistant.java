package com.example.resumebuilder.service;

import org.springframework.stereotype.Service;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;

/**
 * This class connects to the OpenAI API to generate real bullet points.
 * It automatically loads your API key from a .env file located in the project root.
 */
@Service
public class ResumeAIAssistant {

    private final String apiKey;

    // constructor loads .env file automatically
    public ResumeAIAssistant() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("OPENAI_API_KEY");

        if (this.apiKey == null || this.apiKey.isBlank()) {
            throw new RuntimeException("OPENAI_API_KEY not found in .env file or environment variables.");
        }
    }

    /**
     * Generates 3 professional resume bullet points for a given job title and program
     * using OpenAI GPT API. Requires OPENAI_API_KEY to be defined in .env.
     */
    public List<String> generateBullets(String jobTitle, String program) {
        // create OpenAI client
        OpenAiService openai = new OpenAiService(apiKey);

        // build AI prompt
        String prompt = "Write 3 concise, professional resume bullet points for a "
                + jobTitle + " role for a student studying " + program + ". "
                + "Each bullet should start with a strong action verb and highlight measurable impact or skills. "
                + "Do not include numbering or symbols.";

        // build OpenAI completion request
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct") // you can switch to "gpt-4o-mini" if enabled
                .prompt(prompt)
                .maxTokens(150)
                .temperature(0.7)
                .build();

        // send to OpenAI and get response
        CompletionResult result = openai.createCompletion(request);
        String text = result.getChoices().get(0).getText();

        // format into list of clean bullet points
        return Arrays.stream(text.split("\\n"))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> line.replaceAll("^[\\-•\\d\\.\\s]*", "").trim())
                .toList();
    }
}
