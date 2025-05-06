package com.library.usermanagement.model;

public class RegularUser extends User {
    private String membershipType;

    public RegularUser(String id, String username, String email, String password, String membershipType) {
        super(id, username, email, password);
        this.membershipType = membershipType;
    }

    public RegularUser() {

    }

    public String getMembershipType() {
        return membershipType;
    }
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}