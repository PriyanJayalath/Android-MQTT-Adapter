package com.priyan.mqtt.model;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class GenericMQTTMessage {

    private boolean mutable = true;
    private byte[] payload;
    private int qos = 1;
    private boolean retained = false;
    private boolean dup = false;
    private int messageId;

    public GenericMQTTMessage(Mqtt5Publish message) {

        payload = message.getPayloadAsBytes();
        qos = message.getQos().getCode();
        retained = message.isRetain();
        dup = false;
        messageId = message.hashCode();

    }

    public GenericMQTTMessage(MqttMessage message) {

        payload = message.getPayload();
        qos = message.getQos();
        retained = message.isRetained();
        dup = message.isDuplicate();
        messageId = message.getId();

    }

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public boolean isRetained() {
        return retained;
    }

    public void setRetained(boolean retained) {
        this.retained = retained;
    }

    public boolean isDup() {
        return dup;
    }

    public void setDup(boolean dup) {
        this.dup = dup;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String toString() {
        return new String(payload);
    }
}
