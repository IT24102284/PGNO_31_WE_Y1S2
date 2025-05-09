package com.library.usermanagement.model;

public class RegularUser extends User {
    private UserType userType;

    public RegularUser() {
        super();
        this.userType = UserType.AUDIENCE;  // default
    }

    public RegularUser(String id, String username, String email, String password, String userTypeStr) {
        super(id, username, email, password);
        this.userType = UserType.valueOf(userTypeStr);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}