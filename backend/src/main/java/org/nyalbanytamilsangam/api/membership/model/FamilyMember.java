package org.nyalbanytamilsangam.api.membership.model;

import lombok.Data;

@Data
public class FamilyMember {
    private String firstName;
    private String lastName;
    private String relationship;
    private String dateOfBirth;
}
