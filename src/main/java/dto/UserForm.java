package dto;

import java.util.Objects;

public final class UserForm {

    private String name;

    private String password;

    private String passwordMatch;

    private String phoneNumber;

    private String email;

    public UserForm() {
    }

    public UserForm(String name, String password, String passwordMatch, String phoneNumber, String email) {
        this.name = name;
        this.password = password;
        this.passwordMatch = passwordMatch;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordMatch() {
        return passwordMatch;
    }

    public void setPasswordMatch(String passwordMatch) {
        this.passwordMatch = passwordMatch;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        UserForm userForm = (UserForm) o;
        return Objects.equals(name, userForm.name) &&
                Objects.equals(password, userForm.password) &&
                Objects.equals(passwordMatch, userForm.passwordMatch) &&
                Objects.equals(phoneNumber, userForm.phoneNumber) &&
                Objects.equals(email, userForm.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, passwordMatch, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordMatch='" + passwordMatch + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
