package com.japharr.grpc.car.dto;

public class DriverDto {
    private Long id;
    private String name;

    public DriverDto() {}

    public DriverDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
