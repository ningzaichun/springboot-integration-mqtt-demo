package com.example.mqtt.config;

import com.example.mqtt.channal.ChannelName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executor;

/**
 * Definition classes for all channels
 * @author sean.zhou
 * @date 2021/11/10
 * @version 0.1
 */
@Configuration
public class MqttMessageChannel {

    @Autowired
    private Executor threadPool;
    @Bean(name = ChannelName.INBOUND)
    public MessageChannel inboundChannel() {
        return new ExecutorChannel(threadPool);
    }
    @Bean(name = ChannelName.ENVENTS_INBOUND_TEST)
    public MessageChannel enventsInboundTest() {
        return new DirectChannel();
    }

    @Bean(name = ChannelName.INBOUND_TASK_TEST1)
    public MessageChannel inboundTaskTest1() {
        return new DirectChannel();
    }
    @Bean(name = ChannelName.INBOUND_TASK_TEST2)
    public MessageChannel inboundTaskTest2() {
        return new DirectChannel();
    }
    @Bean(name = ChannelName.INBOUND_TASK_TEST3)
    public MessageChannel inboundTaskTest3() {
        return new DirectChannel();
    }

}
