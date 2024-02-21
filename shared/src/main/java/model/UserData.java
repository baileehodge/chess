package model;

import java.util.Objects;

public class UserData {
    String password;
    String email;
    String username;

    public UserData(String newUsername, String newPassword, String newEmail) {
        username = newUsername;
        password = newPassword;
        email = newEmail;
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(password, userData.password) && Objects.equals(email, userData.email) && Objects.equals(username, userData.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, email, username);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
