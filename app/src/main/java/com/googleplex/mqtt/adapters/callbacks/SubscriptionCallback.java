package com.googleplex.mqtt.adapters.callbacks;

import com.googleplex.mqtt.model.GenericMQTTMessage;

public interface SubscriptionCallback {
    void onSubscriptionSuccess();
    void onSubscriptionFailed();
    void onMessageReceived(GenericMQTTMessage message);
}
