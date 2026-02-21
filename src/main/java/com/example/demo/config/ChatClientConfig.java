package com.example.demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}


//package com.example.demo.config;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ChatClientConfig {
//
//    /**
//     * Creates ChatClient bean for Gemini AI
//     * This allows @Autowired ChatClient to work in services
//     */
//    @Bean
//    public ChatClient chatClient(VertexAiGeminiChatModel geminiChatModel) {
//        return ChatClient.builder(geminiChatModel)
//                .build();
//    }
//}