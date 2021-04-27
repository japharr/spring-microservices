package com.japharr.grpc.car.dto;

public class CarDto {
    private Long id;
    private String name;
    private DriverDto driverDto;

    public CarDto() {}

    public CarDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CarDto(Long id, String name, DriverDto driverDto) {
        this.id = id;
        this.name = name;
        this.driverDto = driverDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DriverDto getDriverDto() {
        return driverDto;
    }

    public void setDriverDto(DriverDto driverDto) {
        this.driverDto = driverDto;
    }
}
