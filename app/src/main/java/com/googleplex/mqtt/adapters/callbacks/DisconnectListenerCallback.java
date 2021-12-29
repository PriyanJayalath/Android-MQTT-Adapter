package com.googleplex.mqtt.adapters.callbacks;

public interface DisconnectListenerCallback {
    void onDisconnectionSuccess();
    void onDisconnectionFailed();
}
