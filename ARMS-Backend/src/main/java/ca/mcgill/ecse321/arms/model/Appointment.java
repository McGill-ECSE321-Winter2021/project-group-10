package ca.mcgill.ecse321.arms.model;

import javax.persistence.*;

@Entity
public class Appointment {
    private int appointmentID;

    public void setAppointmentID(int value) {
        this.appointmentID = value;
    }

    @Id
    public int getAppointmentID() {
        return this.appointmentID;
    }

    private Car car;

    @ManyToOne(optional = false)
    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Service service;

    @ManyToOne(optional = false)
    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private TimeSlot timeSlot;

    @OneToOne(optional = false,cascade=CascadeType.ALL)
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

}