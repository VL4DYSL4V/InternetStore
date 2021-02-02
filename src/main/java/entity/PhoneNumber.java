package entity;

import javax.persistence.*;
import java.util.Objects;

@Table
@Entity(name = "is_phone_number")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(String number) {
        this.number = number;
    }

    public PhoneNumber(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String phoneNumber) {
        this.number = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", phoneNumber='" + number + '\'' +
                '}';
    }
}
