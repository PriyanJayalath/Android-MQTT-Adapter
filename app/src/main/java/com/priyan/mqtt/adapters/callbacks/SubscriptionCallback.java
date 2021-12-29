package com.priyan.mqtt.adapters.callbacks;

import com.priyan.mqtt.model.GenericMQTTMessage;

public interface SubscriptionCallback {
    void onSubscriptionSuccess();
    void onSubscriptionFailed();
    void onMessageReceived(GenericMQTTMessage message);
}
