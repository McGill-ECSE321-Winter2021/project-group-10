package ca.mcgill.ecse321.arms.controller;

import ca.mcgill.ecse321.arms.dao.BussinessRepository;
import ca.mcgill.ecse321.arms.dto.BusinessDto;
import ca.mcgill.ecse321.arms.dto.BusinessHourDto;
import ca.mcgill.ecse321.arms.model.Business;
import ca.mcgill.ecse321.arms.model.BusinessHour;
import ca.mcgill.ecse321.arms.model.DayOfWeek;
import ca.mcgill.ecse321.arms.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class BusinessRestController {
    @Autowired
    private BusinessService businessService;
    @Autowired
    private ControllerHelper helper;
    @Autowired
    private BussinessRepository businessRepo;

    /**
     * method that get all business in the business repository
     * @return
     */
    @GetMapping(value = { "/businesses", "/businesses/" })
    public List<BusinessDto> getAllBusiness() {
        return businessService.getAllBusiness().stream().map(p -> helper.convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * method that creates a business given name,address, phoneNumber, email
     * @author Ruixin Su
     * @param name
     * @param address
     * @param phoneNumber
     * @param email
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/business/{name}/{address}/{phoneNumber}/{email}", "/business/{name}/{address}/{phoneNumber}/{email}/" })
    public BusinessDto createBusiness(@PathVariable("name") String name, @PathVariable("address") String address,@PathVariable("phoneNumber") String phoneNumber,@PathVariable("email") String email ) throws IllegalArgumentException {
        Business business = businessService.createBusiness(name, address, phoneNumber, email);
        return helper.convertToDto(business);
    }

    /**
     * method that get a business by name
     * @param name
     * @return
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/business/{name}", "/business/{name}/" })
    public BusinessDto getBusinessByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return helper.convertToDto(businessService.getBusiness(name));
    }


    /**
     * method that get all business hour in the business repository
     * @return
     */
    @GetMapping(value = { "/businessHour", "/businessHour/" })
    public List<BusinessHourDto> getAllBusinessHour() {
        return businessService.getAllBusinessHour().stream().map(p -> helper.convertToDto(p)).collect(Collectors.toList());
    }


    /**
     * method that create a business hour given id, dayOfWeek, startTime, endTime,business name
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @param businessName
     * @return
     * @throws IllegalArgumentException
     */

    @PostMapping(value = { "/businessHour/{startDate}/{endDate}/{startTime}/{endTime}/{businessName}", "/businessHour/{startDate}/{endDate}/{startTime}/{endTime}/{businessName}/" })
    public BusinessHourDto createBusinessHour(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, @PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime, @PathVariable("businessName") String businessName ) throws IllegalArgumentException {

        Business business = businessRepo.findBusinessByName(businessName);
        System.out.println(business.getName());
        BusinessHour businessHour = businessService.createBusinessHour(startDate,endDate,startTime,endTime,business);
        System.out.println(businessHour.getBusinessHourID());
        return helper.convertToDto(businessHour);
    }

    /**
     * method that deletes a business given business name
     * @param name
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/deleteBusiness/{name}", "/deleteBusiness/{name}/"})
    public void deleteBusiness (
            @PathVariable("name") String name
    ) throws IllegalArgumentException{
        businessService.deleteBusiness(name);
    }
    /**
     * method that deletes a businessHour given business hour id
     * @param id
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/deleteBusinessHour/{id}", "/deleteBusinessHour/{id}/"})
    public void deleteService (
            @PathVariable("id") long id
    ) throws IllegalArgumentException{
        businessService.deleteBusinessHour(id);
    }

    /**
     * method that returns list of businessHour belongs to the same business
     * @param name
     * @return
     */
    @GetMapping(value = {"/businessHour/business/{name}", "/businessHour/business/{name}/"})
    public ResponseEntity<?> getBusinessHourByBusiness(@PathVariable("name") String name) {
        List<BusinessHourDto> businessHours =
                businessService.getBusinessHourByBusiness(name).stream()
                        .map(p -> helper.convertToDto(p))
                        .collect(Collectors.toList());
        if (businessHours != null) {
            return new ResponseEntity<>(businessHours, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
