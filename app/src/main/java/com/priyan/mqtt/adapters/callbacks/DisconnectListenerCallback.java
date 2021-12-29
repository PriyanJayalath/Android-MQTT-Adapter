package com.priyan.mqtt.adapters.callbacks;

public interface DisconnectListenerCallback {
    void onDisconnectionSuccess();
    void onDisconnectionFailed();
}
