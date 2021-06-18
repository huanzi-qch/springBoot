package cn.huanzi.qch.springbootredis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * redis 订阅、监听
 */
@Configuration
public class Listeners {

    /**
     * 订阅
     */
    @Bean
    public RedisMessageListenerContainer container(
            MessageListenerAdapter listenerAdapter1,
            MessageListenerAdapter listenerAdapter2,
            RedisConnectionFactory connectionFactory
            ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //订阅频道
        container.addMessageListener(listenerAdapter1, new PatternTopic("topic1"));
        container.addMessageListener(listenerAdapter2, new PatternTopic("topic1"));

        container.addMessageListener(listenerAdapter2, new PatternTopic("topic2"));
        return container;
    }

    /**
     * 监听
     */
    @Bean
    MessageListenerAdapter listenerAdapter1(Receiver1 receiver){
        return new MessageListenerAdapter(receiver);
    }
    @Bean
    MessageListenerAdapter listenerAdapter2(Receiver2 receiver){
        return new MessageListenerAdapter(receiver);
    }

    /**
     * 接收器
     */
    @Component
    class Receiver1 {
        public void handleMessage(String message) {
            System.out.println("Receiver1接收器："+message);
        }
    }
    @Component
    class Receiver2 {
        public void handleMessage(String message) {
            System.out.println("Receiver2接收器："+message);
        }
    }
}
