package org.nyalbanytamilsangam.api.event.model;

import lombok.Data;

@Data
public class Venue {
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String mapUrl;
}
