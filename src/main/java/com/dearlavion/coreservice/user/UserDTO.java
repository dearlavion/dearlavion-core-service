package com.dearlavion.coreservice.user;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String image;
    private Date createdAt;
    private Date updatedAt;

    public UserDTO() {}

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
