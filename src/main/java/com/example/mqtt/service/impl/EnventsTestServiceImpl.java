package com.example.mqtt.service.impl;

import com.example.mqtt.channal.ChannelName;
import com.example.mqtt.model.request.CommonTopicReceiver;
import com.example.mqtt.model.request.RequestsReply;
import com.example.mqtt.model.response.CommonTopicResponse;
import com.example.mqtt.service.IMessageSenderService;
import com.example.mqtt.service.EnventsTestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;


/**
 * @author sean
 * @version 1.2
 * @date 2022/7/29
 */
@Service
@Slf4j
public class EnventsTestServiceImpl implements EnventsTestService {

    @Autowired
    private IMessageSenderService messageSenderService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @ServiceActivator(inputChannel = ChannelName.INBOUND_TASK_TEST1, outputChannel = ChannelName.OUTBOUND_TEST)
    public CommonTopicReceiver handleInboundTest1(CommonTopicReceiver receiver, MessageHeaders headers) {
        String dockSn  = receiver.getGateway();
        log.info("handleInboundTest1");
        log.info("dockSn:{}",dockSn);
        log.info("receiver:{}",receiver);
        log.info("headers:{}",headers);
        return receiver;
    }

    @Override
    @ServiceActivator(inputChannel = ChannelName.INBOUND_TASK_TEST2, outputChannel = ChannelName.OUTBOUND_TEST_REPLY)
    public CommonTopicReceiver handleInboundTest1Reply(CommonTopicReceiver receiver, MessageHeaders headers) {
        String dockSn  = receiver.getGateway();
        log.info("handleInboundTest1");
        log.info("dockSn:{}",dockSn);
        log.info("receiver:{}",receiver);
        log.info("headers:{}",headers);
        return receiver;
    }

    @ServiceActivator(inputChannel = ChannelName.OUTBOUND_TEST)
    @Override
    public void handleOutboundTest(CommonTopicReceiver receiver, MessageHeaders headers) {
        log.info("handleOutboundTest 处理不用回复消息的通道消息");
        log.info("receiver:{}",receiver);
        log.info("headers:{}",headers);
    }


    @ServiceActivator(inputChannel = ChannelName.OUTBOUND_TEST_REPLY)
    @Override
    public void handleOutboundTestReply(CommonTopicReceiver receiver, MessageHeaders headers) {
        log.info("handleOutboundTest");
        log.info("receiver:{}",receiver);
        log.info("headers:{}",headers);
        CommonTopicResponse<Object> build = CommonTopicResponse.builder()
                .tid("receiver.getTid()")
                .bid("receiver.getBid()")
                .method("reply")
                .timestamp(System.currentTimeMillis())
                .data(RequestsReply.success())
                .build();
        messageSenderService.publish("envents_test/response", build);
    }


}
