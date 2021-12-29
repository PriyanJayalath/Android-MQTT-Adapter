package com.priyan.mqtt.adapters;

import android.app.Activity;

import com.priyan.mqtt.adapters.callbacks.ConnectionListenerCallback;
import com.priyan.mqtt.adapters.callbacks.DisconnectListenerCallback;
import com.priyan.mqtt.adapters.callbacks.PublishCallback;
import com.priyan.mqtt.adapters.callbacks.SubscriptionCallback;

public class MQTTAdapterImpl implements MQTTAdapter {

    private MQTTInterface adaptee;

    public MQTTAdapterImpl(MQTTInterface adaptee){
        this.adaptee = adaptee;
    }

    @Override
    public void connect(Activity activity, ConnectionListenerCallback callback, String broker) {
        this.adaptee.connect(activity,callback,broker);
    }

    @Override
    public void subscribe(Activity activity, SubscriptionCallback callback, String topic) {
        this.adaptee.subscribe(activity,callback,topic);
    }

    @Override
    public void publish(Activity activity, PublishCallback callback, String topic, String payload) {
        this.adaptee.publish(activity,callback,topic,payload);
    }

    @Override
    public void disconnect(Activity activity, DisconnectListenerCallback callback) {
        this.adaptee.disconnect(activity,callback);
    }
}
