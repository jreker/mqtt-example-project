package de.jreker.mqtt.coupling;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.jreker.mqtt.coupling.models.CouplingMetric;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class CouplingApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(CouplingApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final DataPublisher publisher = new DataPublisher();
		try {
			publisher.connect();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					publisher.sendData(new CouplingMetric(Math.random() * 10, Math.random() * 1000));
					if(publisher.getMessageCounter() % 500 == 0) {
						System.out.println(new SimpleDateFormat("HH:mm:ss.SSSXXX").format(new Date().getTime()) +
								" -> " + publisher.getMessageCounter());
					}
				} catch (MqttException | JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		},0, 2);
	}
}
