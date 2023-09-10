package com.example.mqtt.config;

import com.example.mqtt.channal.ChannelName;
import com.example.mqtt.config.model.MqttClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * Client configuration for inbound messages.
 *
 * @author sean.zhou
 * @version 0.1
 * @date 2021/11/10
 */
@Slf4j
@Configuration
@IntegrationComponentScan
public class MqttInboundConfiguration {

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Resource(name = ChannelName.INBOUND)
    private MessageChannel inboundChannel;

    /**
     * Clients of inbound message channels.
     *
     * @return
     */
    @Bean(name = "adapter")
    public MessageProducerSupport mqttInbound() {
        MqttClientOptions options = MqttConfiguration.getBasicClientOptions();
        // 此处在初始化的时候，初始化时，默认订阅了配置文件中的已经写定的 topic
        // 如果后期有需要再增加的订阅主题，调用 addTopic() 即可
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        options.getClientId() + "_consumer_" + System.currentTimeMillis(),
                        mqttClientFactory, options.getInboundTopic().split(","));

        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        // use byte types uniformly
        converter.setPayloadAsBytes(true);
        // 设置消息转换器
        adapter.setConverter(converter);
        adapter.setQos(1);
        // 设置在接收已经订阅的主题信息后，发送给那个通道，具体的发送方法需要翻上层的抽象类
        adapter.setOutputChannel(inboundChannel);
        return adapter;
    }

    /**
     * Define a default channel to handle messages that have no effect.
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = ChannelName.DEFAULT)
    public MessageHandler defaultInboundHandler() {
        return message -> {
            log.info("The default channel does not handle messages." +
                    "\nTopic: " + message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC) +
                    "\nPayload: " + message.getPayload());
        };
    }
}
