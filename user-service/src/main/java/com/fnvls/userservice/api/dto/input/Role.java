package com.fnvls.userservice.api.dto.input;

public enum Role {
    ADMIN("Admin"),
    TRAINER("Trainer"),
    LEARNER("Learner");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
