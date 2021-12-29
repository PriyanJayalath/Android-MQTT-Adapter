package com.priyan.mqtt.adapters.callbacks;

public interface ConnectionListenerCallback {
    void onConnectionSuccess(String brokerConnected);
    void onConnectionFailed();
}
