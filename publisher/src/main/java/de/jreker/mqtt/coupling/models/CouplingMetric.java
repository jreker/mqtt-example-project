package de.jreker.mqtt.coupling.models;

public class CouplingMetric {
    public CouplingMetric(double torque, double rpm) {
        this.torque = torque;
        this.rpm = rpm;
    }

    private double torque;
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
