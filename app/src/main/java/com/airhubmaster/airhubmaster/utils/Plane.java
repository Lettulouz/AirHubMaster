package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Plane {
    private List<PlaneService> planeService = new ArrayList<>();

    private List<Staff> staffList = new ArrayList<>();

    private LocalDateTime availableAt;

    public Plane(){
        addEnginesService();
        addTiresService();
        addWingsService();
    }

    public void setStaffList(List<Staff> staffList) { this.staffList = staffList; }
    public void resetStaffList(List<Staff> staffList) { this.staffList = new ArrayList<>(); }
    public void addStaff (Staff staff) { this.staffList.add(staff); }
    public void removeStaff (Staff staff) { this.staffList.remove(staff); }
    public List<Staff> getStaffList () { return staffList; }

    public void addTiresService(){
        planeService.add(new Tires());
    }

    public void addWingsService(){
        planeService.add(new Wings());
    }

    public void addEnginesService(){
        planeService.add(new Engines());
    }

    public void setTiresService(Tires tires){
        addIfNewUpdateIfRecordAlreadyExist(tires, tires.getClass());
    }

    public void setWingsService(Wings wings){
        addIfNewUpdateIfRecordAlreadyExist(wings, wings.getClass());
    }

    public void setEnginesService(Engines engines){
        addIfNewUpdateIfRecordAlreadyExist(engines, engines.getClass());
    }

    public void addIfNewUpdateIfRecordAlreadyExist(PlaneService service, Class<?> clazz){
        for(int i = 0; i < planeService.size(); i++){
            if (clazz.isInstance(planeService.get(i))) {
                planeService.set(i, service);
                return;
            }
        }
        planeService.add(service);
    }

    public List<PlaneService> getPlaneService() {
        return planeService;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(LocalDateTime availableAt) {
        this.availableAt = availableAt;
    }

    public abstract int getBasicExp();

    public abstract int getStaffMin();

    public abstract int getMaxHoursOfFlight();
    public abstract double getBasicPrizeMultiplier();
}
