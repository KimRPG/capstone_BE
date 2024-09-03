package com.capstonexjapan.line_backend.config;

import com.linecorp.bot.client.LineMessagingClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineApiConfiguration {
    @Bean
    public LineMessagingClient lineMessagingClient(
            @Value("${line.channel.token}") String channelToken) {
        return LineMessagingClient.builder("PXUSKTc86Ub6gZL5RXggwQMxIRKmkWYH3yBPiJzBsmzCTp6YnNiPzkLe7H0bU/7GWJOAlEorA3mImujS0b7nM0DSYH4MoSjag/vq+S7DdZbjPjHTsmwIuav0q6sFmFwnN886GzPuuAXKus0pdHgTVAdB04t89/1O/w1cDnyilFU=").build();

    }
}
