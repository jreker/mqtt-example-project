package de.jreker.mqtt.subscribe;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.jreker.mqtt.subscribe.models.CouplingMetric;
public class DataSubscriber {
    private MqttClient mqttClient;
    org.slf4j.Logger logger = LoggerFactory.getLogger(DataSubscriber.class);
    private int messageCount = 0;
    private ObjectMapper mapper;
    MqttConnectOptions options;

    DataSubscriber(String serverURI, String clientId) throws MqttException {
        mqttClient = new MqttClient(serverURI, clientId);
        mapper = new ObjectMapper();
    }

    public void subscribe() {
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        try{
            mqttClient.connect(options);
            mqttClient.subscribe("measure/customer1/+", 0);
        } catch(MqttException ex) {
            logger.info(ex.toString());
        }
        mqttClient.setCallback(new MqttCallback() {
        
            @Override
            public void connectionLost(Throwable cause) {
                logger.info("Connection to MQTT Broker lost" + cause.toString());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                messageCount++;
                //show only every 500 message
                if(messageCount % 500 == 0) {
                    CouplingMetric couplingMetric = mapper.readValue(String.valueOf(message), CouplingMetric.class);
                    //Here you can store the data inside a database or aggregate the data or something else..
                    System.out.println("-----------------------");
                    System.out.println("Got data from: ");
                    System.out.println("Topic: " + topic);
                    System.out.println("Coupling RPM: " + couplingMetric.getRpm() + " Torque: " + couplingMetric.getTorque());
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }


}
