package dto;

import java.util.Objects;

public final class SignInForm {

    private String name;

    private String password;

    public SignInForm() {
    }

    public SignInForm(String name, String password) {
        this.name = name;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInForm that = (SignInForm) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    @Override
    public String toString() {
        return "SignInForm{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
