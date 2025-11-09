package com.dearlavion.coreservice.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "user")
public class User {
    private String id; // Primary key
    private String username; // Unique username
    private String email; // Unique email
    private String phone; // Phone number
    private String password; // Hashed password
    private String image; // Profile image URL
    private Date createdAt; // Created timestamp
    private Date updatedAt; // Updated timestamp

    public User() {}

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getImage() { return this.image; }

    public void setImage(String image) { this.image = image; }

    public Date getCreatedAt() { return this.createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
