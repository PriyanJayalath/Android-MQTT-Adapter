package com.googleplex.mqtt.adapters;

import android.app.Activity;

import com.googleplex.mqtt.adapters.callbacks.ConnectionListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.DisconnectListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.PublishCallback;
import com.googleplex.mqtt.adapters.callbacks.SubscriptionCallback;

public interface MQTTInterface {

    void connect(Activity activity, ConnectionListenerCallback callback,String broker);
    void subscribe(Activity activity, SubscriptionCallback callback,String topic);
    void publish(Activity activity, PublishCallback callback, String topic, String payload);
    void disconnect(Activity activity, DisconnectListenerCallback callback);

}
