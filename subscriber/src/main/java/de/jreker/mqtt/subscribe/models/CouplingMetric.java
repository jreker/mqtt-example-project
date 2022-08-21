package de.jreker.mqtt.subscribe.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouplingMetric {
    public CouplingMetric(double torque, double rpm) {
        this.torque = torque;
        this.rpm = rpm;
    }
    public CouplingMetric() {}
    @JsonProperty("torque")
    private double torque;
    @JsonProperty("rpm")
    private double rpm;

    public double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }
}
