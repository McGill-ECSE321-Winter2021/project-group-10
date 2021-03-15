package ca.mcgill.ecse321.arms.controller;

import ca.mcgill.ecse321.arms.dao.AppointmentRepository;
import ca.mcgill.ecse321.arms.dto.AppointmentDto;
import ca.mcgill.ecse321.arms.dto.ServiceDto;
import ca.mcgill.ecse321.arms.model.Appointment;
import ca.mcgill.ecse321.arms.model.Car;
import ca.mcgill.ecse321.arms.model.Service;
import ca.mcgill.ecse321.arms.model.TimeSlot;
import ca.mcgill.ecse321.arms.service.AppointmentService;
import org.hibernate.loader.custom.ScalarResultColumnProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping(value={"/appointment","/appointment/"})
    public AppointmentDto createAppointment(
            @RequestParam("service") Service service,
            @RequestParam("car") Car car,
            @RequestParam("time slot") TimeSlot timeSlot
    ){
        ca.mcgill.ecse321.arms.model.Appointment appointment = appointmentService.createAppointment(service, car, timeSlot);
        return convertToDto(appointment);
    }

    @PutMapping(value = {"/updateAppointment","/updateAppointment/"})
    public AppointmentDto updateAppointment(
            @RequestParam("appointment ID") int appointmentID,
            @RequestParam("service") Service service,
            @RequestParam("car") Car car,
            @RequestParam("time slot") TimeSlot timeSlot
    ){
        Appointment appointment = appointmentService.updateService(appointmentID,service,car,timeSlot);
        return convertToDto(appointment);
    }

    @GetMapping(value = {"/appointment","/appointment/"})
    public List<AppointmentDto> getAllAppointments(){
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for(Appointment appointment : appointmentService.getAllAppointments()){
            appointmentDtos.add(convertToDto(appointment));
        }
        return appointmentDtos;
    }

    @DeleteMapping(value = {"/deleteAppointment", "/deleteAppointment/"})
    public void deleteAppointment(
            @RequestParam("appointment ID") int appointmentID
    ){
        appointmentService.deleteAppointment(appointmentID);
    }

    private AppointmentDto convertToDto(Appointment appointment){
        if(appointment == null){
            throw new IllegalArgumentException("There is no such appointment");
        }
        AppointmentDto appointmentDto = new AppointmentDto(appointment.getService(),appointment.getCar(),appointment.getTimeSlot());
        return appointmentDto;
    }

}
