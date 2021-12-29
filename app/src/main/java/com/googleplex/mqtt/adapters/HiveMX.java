package com.googleplex.mqtt.adapters;

import android.app.Activity;
import android.util.Log;

import com.googleplex.mqtt.adapters.callbacks.ConnectionListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.DisconnectListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.PublishCallback;
import com.googleplex.mqtt.adapters.callbacks.SubscriptionCallback;
import com.googleplex.mqtt.model.GenericMQTTMessage;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;


import java.util.UUID;
import java.util.function.Consumer;

public class HiveMX implements MQTTInterface{

    Mqtt5BlockingClient client;

    @Override
    public void connect(Activity activity, ConnectionListenerCallback callback, String broker) {

        try {
            client = Mqtt5Client.builder()
                    .identifier(UUID.randomUUID().toString())
                    .serverHost("broker.hivemq.com")
                    .buildBlocking();

            client.connect();
            callback.onConnectionSuccess("Sucess");
        }catch (Exception ex){
            Log.i("HiveMX",""+ex.toString());
            callback.onConnectionFailed();
        }

    }

    @Override
    public void subscribe(Activity activity, SubscriptionCallback callback, String topic) {
        try {
            client.toAsync().subscribeWith()
                    .topicFilter(topic)
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .callback(new Consumer<Mqtt5Publish>() {
                        @Override
                        public void accept(Mqtt5Publish mqtt5Publish) {
                            callback.onMessageReceived(new GenericMQTTMessage(mqtt5Publish));
                        }
                    })
                    .send();

            callback.onSubscriptionSuccess();
            Log.i("HiveMX","success");
        }catch (Exception ex){
            Log.i("HiveMX",""+ex.toString());
            callback.onSubscriptionFailed();
        }
    }

    @Override
    public void publish(Activity activity, PublishCallback callback, String topic, String payload) {
        try{
        client.publishWith().topic(topic).qos(MqttQos.AT_LEAST_ONCE).payload(payload.getBytes()).send();
            Log.i("HiveMX","published");
        }catch (Exception ex){
            Log.i("HiveMX",""+ex.toString());
        }
    }

    @Override
    public void disconnect(Activity activity, DisconnectListenerCallback callback) {
        try{
        client.disconnect();
            Log.i("HiveMX","disconnected");
        }catch (Exception ex){
            Log.i("HiveMX",""+ex.toString());
        }
    }
}
