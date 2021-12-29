package com.priyan.mqtt.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import com.priyan.mqtt.adapters.callbacks.ConnectionListenerCallback;
import com.priyan.mqtt.adapters.callbacks.DisconnectListenerCallback;
import com.priyan.mqtt.adapters.callbacks.PublishCallback;
import com.priyan.mqtt.adapters.callbacks.SubscriptionCallback;
import com.priyan.mqtt.model.GenericMQTTMessage;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Paho implements MQTTInterface {

    MqttAndroidClient client;

    //String serverURL = "tcp://broker.hivemq.com:1883";
    //String topic = "mqtt/topic";
    //String sTopic = "mqtt/sensorData";

    @Override
    public void connect(final Activity activity,final ConnectionListenerCallback callback,final String broker) {

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(activity, broker, clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    callback.onConnectionSuccess(broker);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    callback.onConnectionFailed();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void subscribe(final Activity activity, final SubscriptionCallback callback,String topic) {

        try {
            if (client.isConnected()) {
                client.subscribe(topic, 0);
                Toast.makeText(activity, "Subscribed", Toast.LENGTH_SHORT).show();
                callback.onSubscriptionSuccess();
                client.setCallback(new MqttCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void connectionLost(Throwable cause) {
                        callback.onSubscriptionFailed();
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        callback.onMessageReceived(new GenericMQTTMessage(message));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                    }
                });
            }
        } catch (Exception ignored) {
        }

    }

    @Override
    public void publish(Activity activity, PublishCallback callback, String topic, String payload) {

        byte[] encodedPayload;
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
            Toast.makeText(activity, "Sent", Toast.LENGTH_SHORT).show();
            callback.onPublishSuccess();
        } catch (UnsupportedEncodingException | MqttException e) {
            callback.onPublishFailed(e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect(final Activity activity, final DisconnectListenerCallback callback) {

        try {
            IMqttToken disconnectToken = client.disconnect();
            disconnectToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(activity, "Disconnected", Toast.LENGTH_SHORT).show();
                    callback.onDisconnectionSuccess();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    callback.onDisconnectionFailed();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            callback.onDisconnectionFailed();
        }

    }
}
