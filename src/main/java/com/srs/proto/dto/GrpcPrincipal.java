package com.srs.proto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GrpcPrincipal implements Serializable {
    private UUID userId;
//    private String externalId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    private List<String> roles;

    private List<String> permissions = new ArrayList<>();
//    private List<Integer> divisions = new ArrayList<>();

    /**
     * For Office-based Users only to indicate which market he/she has access to. It is limited by divisions, when empty, it indicates that user has access to all market under specified divisions
     * For Public User only to indicate which market he/she has was associated to (having in-use stalls)
     */
    private List<String> marketCodes = new ArrayList<>();
    private List<String> roleIds = new ArrayList<>();

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s, %s", firstName, lastName);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Boolean hasRole(String role) {
        return this.roles.contains(role);
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getMarketCodes() {
        return marketCodes;
    }

    public void setMarketCodes(List<String> marketCodes) {
        this.marketCodes = marketCodes;
    }
}
