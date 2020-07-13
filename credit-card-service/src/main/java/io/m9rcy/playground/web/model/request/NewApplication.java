
package io.m9rcy.playground.web.model.request;

import lombok.Data;

import java.util.List;

@Data
public class NewApplication {

    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;
    private Address address;
    private List<PhoneNumber> phoneNumber = null;

}
