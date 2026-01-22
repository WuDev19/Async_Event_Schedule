package com.example.testasync.dto.request;

public class UserCreateRequest {

    private String name;
    private String address;
    private Integer age;
    private String username;
    private String password;
    private String roleName;

    public UserCreateRequest(String name, String address, Integer age, String username, String password, String roleName) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
