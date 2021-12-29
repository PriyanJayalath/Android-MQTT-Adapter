package com.googleplex.mqtt.adapters.callbacks;

public interface PublishCallback {
    void onPublishSuccess();
    void onPublishFailed(String error);
}
