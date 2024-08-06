package com.pj.planjourney.domain.plandetail.config;

import com.pj.planjourney.domain.plandetail.service.RedisSubscriberPlanUpdates;
import com.pj.planjourney.domain.plandetail.service.RedisSubscriberUserInvites;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);

        return new LettuceConnectionFactory(config);
    }

    /**
     * 메시지 발송을 위한 RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }

    /**
     * 메시지 수신을 위한 설정: RedisMessageListenerContainer
     * Redis 의 channel 로부터 메시지를 수신받아 해당 MessageListenerAdapter 에게 디스패치
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerPlanUpdates,
                                                        MessageListenerAdapter messageListenerUserInvites) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerPlanUpdates, new ChannelTopic("planUpdates"));
//        container.addMessageListener(messageListenerUserInvites(), new ChannelTopic("userInvites"));

        return container;
    }

    /**
     * 메시지 수신을 위한 설정: MessageListenerAdapter
     * RedisMessageListenerContainer 로부터 메시지를 받아서 등록된 리스너(구독자)에게 전달
     */
    @Bean
    public MessageListenerAdapter messageListenerPlanUpdates(RedisSubscriberPlanUpdates redisSubscriberPlanUpdates) {
        return new MessageListenerAdapter(redisSubscriberPlanUpdates);
    }

//    @Bean
//    public MessageListenerAdapter messageListenerUserInvites() {
//        return new MessageListenerAdapter(new RedisSubscriberUserInvites());
//    }
}
