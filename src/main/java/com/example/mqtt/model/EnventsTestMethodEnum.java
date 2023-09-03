package com.example.mqtt.model;


import com.example.mqtt.channal.ChannelName;

import java.util.Arrays;

/**
 * @author yihui wang
 * @version 1.0
 * @description: TODO
 * @date 2023/7/26 14:29
 */
public enum EnventsTestMethodEnum {

    TASK_TEST1("test1", ChannelName.INBOUND_TASK_TEST1),

    TASK_TEST2("test2", ChannelName.INBOUND_TASK_TEST2),


    TASK_TEST3("test3", ChannelName.INBOUND_TASK_TEST3),

    UNKNOWN("Unknown", ChannelName.DEFAULT);

    private String method;

    private String channelName;

    EnventsTestMethodEnum(String method, String channelName) {
        this.method = method;
        this.channelName = channelName;
    }

    public String getMethod() {
        return method;
    }

    public String getChannelName() {
        return channelName;
    }

    public static EnventsTestMethodEnum find(String method) {
        return Arrays.stream(EnventsTestMethodEnum.values())
                .filter(methodEnum -> methodEnum.method.equals(method))
                .findAny()
                .orElse(UNKNOWN);
    }
}
