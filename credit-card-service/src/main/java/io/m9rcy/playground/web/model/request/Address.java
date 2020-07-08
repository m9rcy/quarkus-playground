
package io.m9rcy.playground.web.model.request;

import lombok.Data;

@Data
public class Address {

    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;

}
