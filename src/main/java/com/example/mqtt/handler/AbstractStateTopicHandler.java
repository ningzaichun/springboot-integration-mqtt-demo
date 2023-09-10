package com.example.mqtt.handler;

import com.example.mqtt.model.request.CommonTopicReceiver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 写博客的时候，简化代码，此处的过滤链相关的东西，没有进行实现啦，具体可参考大疆的开源项目
 */
public abstract class AbstractStateTopicHandler {

    protected AbstractStateTopicHandler handler;

    @Autowired
    protected ObjectMapper mapper;


    protected AbstractStateTopicHandler(AbstractStateTopicHandler handler) {
        this.handler = handler;
    }

    /**
     * Passing dataNode data, using different processing methods depending on the data selection.
     * @param dataNode
     * @param stateReceiver
     * @param sn
     * @return
     * @throws JsonProcessingException
     */
    public abstract CommonTopicReceiver handleState(Map<String, Object> dataNode, CommonTopicReceiver stateReceiver, String sn) throws JsonProcessingException;
}
