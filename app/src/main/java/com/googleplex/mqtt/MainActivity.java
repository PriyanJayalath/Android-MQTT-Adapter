package com.googleplex.mqtt;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.googleplex.mqtt.adapters.HiveMX;
import com.googleplex.mqtt.adapters.MQTTAdapter;
import com.googleplex.mqtt.adapters.MQTTAdapterImpl;
import com.googleplex.mqtt.adapters.MQTTInterface;
import com.googleplex.mqtt.adapters.Paho;
import com.googleplex.mqtt.adapters.callbacks.ConnectionListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.DisconnectListenerCallback;
import com.googleplex.mqtt.adapters.callbacks.PublishCallback;
import com.googleplex.mqtt.adapters.callbacks.SubscriptionCallback;
import com.googleplex.mqtt.model.GenericMQTTMessage;

public class MainActivity extends AppCompatActivity {

    EditText brokerAddress, textToSend, topicToSend, subscriptionTopic;
    Button connectButton, sendButton, subscribeButton,disconnectButton;
    TextView receivedMessage, connectionStatus;

    String serverURL = "tcp://broker.hivemq.com:1883";
    String topic = "mqtt/topic";

    boolean connectionFlag = false;

    private MQTTAdapter pahoAdapter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(getDrawable(R.drawable.mqtt_icon));
        }
        /////////////////////////////////////////////////////////////////
        brokerAddress = findViewById(R.id.broker_address);
        textToSend = findViewById(R.id.text_to_send);
        topicToSend = findViewById(R.id.topic_to_send);
        subscriptionTopic = findViewById(R.id.subscription_topic);
        subscribeButton = findViewById(R.id.subscribe_button);
        connectButton = findViewById(R.id.connect_to_broker_button);
        sendButton = findViewById(R.id.send_button);
        receivedMessage = findViewById(R.id.received_message);
        connectionStatus = findViewById(R.id.connection_status);

        disconnectButton = findViewById(R.id.disconnect_button);

        /////////////////////////////////////////////////////////////////

        MQTTInterface paho = new Paho();
        //MQTTInterface hivemx = new HiveMX();
        pahoAdapter = new MQTTAdapterImpl(paho);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverURL = "tcp://" + brokerAddress.getText().toString() + ":1883";
                //connectToBroker();
                pahoAdapter.connect(MainActivity.this, new ConnectionListenerCallback() {
                    @Override
                    public void onConnectionSuccess(String brokerConnected) {
                        connectionStatus.setText("Connected To " + serverURL);
                        connectionFlag = true;
                        sendButton.setEnabled(true);
                        subscribeButton.setEnabled(true);
                    }

                    @Override
                    public void onConnectionFailed() {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                },serverURL);
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        /////////////////////////////////////////////////////////////////
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = topicToSend.getText().toString();
                //sendMessage(topic);
                String payload = textToSend.getText().toString();
                pahoAdapter.publish(MainActivity.this, new PublishCallback() {
                    @Override
                    public void onPublishSuccess() {

                    }

                    @Override
                    public void onPublishFailed(String error) {

                    }
                },topic, payload);
            }
        });
        /////////////////////////////////////////////////////////////////
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pahoAdapter.subscribe(MainActivity.this, new SubscriptionCallback() {
                    @Override
                    public void onSubscriptionSuccess() {
                        connectionStatus.setText("Connection Failed");
                        connectionFlag = false;
                    }

                    @Override
                    public void onSubscriptionFailed() {

                    }

                    @Override
                    public void onMessageReceived(GenericMQTTMessage message) {
                        receivedMessage.setText(message.toString());
                    }
                },topic);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void disconnect(){

        if(pahoAdapter != null){

            if (connectionFlag) {
                pahoAdapter.disconnect(MainActivity.this, new DisconnectListenerCallback() {
                    @Override
                    public void onDisconnectionSuccess() {
                        finish();
                    }

                    @Override
                    public void onDisconnectionFailed() {
                        finish();
                    }
                });

            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}
