package dto;

import java.util.Objects;

public final class SignUpForm {

    private Long id;

    private String name;

    private String password;

    private String passwordMatch;

    private String phoneNumber;

    private String email;

    public SignUpForm() {
    }

    public SignUpForm(Long id, String name, String password, String passwordMatch, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.passwordMatch = passwordMatch;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        SignUpForm signUpForm = (SignUpForm) o;
        return Objects.equals(id, signUpForm.id) &&
                Objects.equals(name, signUpForm.name) &&
                Objects.equals(password, signUpForm.password) &&
                Objects.equals(passwordMatch, signUpForm.passwordMatch) &&
                Objects.equals(phoneNumber, signUpForm.phoneNumber) &&
                Objects.equals(email, signUpForm.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, passwordMatch, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordMatch='" + passwordMatch + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
