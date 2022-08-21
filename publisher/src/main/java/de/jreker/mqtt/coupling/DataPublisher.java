package de.jreker.mqtt.coupling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jreker.mqtt.coupling.models.CouplingMetric;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;

public class DataPublisher {
    private static String IDENT = "001123123";
    private static String TOPIC = "measure/customer1/" + IDENT;
    private double messageCounter = 0;
    private IMqttClient publisher;
    private MqttConnectOptions options;
    ObjectMapper mapper;
    DataPublisher() {
        try {
            options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher = new MqttClient("tcp://localhost:1883",IDENT);
            mapper = new ObjectMapper();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() throws MqttException {
        publisher.connect(options);
    }

    public void sendData(CouplingMetric couplingMetric) throws MqttException, JsonProcessingException {
        MqttMessage message = new MqttMessage( mapper.writeValueAsString(couplingMetric).getBytes(StandardCharsets.UTF_8));
        message.setQos(0);
        message.setRetained(true);
        publisher.publish(TOPIC, message);
        messageCounter++;
    }

    public double getMessageCounter() {
        return messageCounter;
    }

    public void setMessageCounter(double messageCounter) {
        this.messageCounter = messageCounter;
    }
}
