package entity;

import javax.persistence.*;
import java.util.Objects;

@Table
@Entity(name = "ban_type")
public final class BanType {

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cause")
    private String cause;

    @Column(name = "expires")
    private boolean expires;

    @Column(name = "duration_in_days")
    private int durationInDays;

    public BanType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public boolean isExpires() {
        return expires;
    }

    public void setExpires(boolean expires) {
        this.expires = expires;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanType banType = (BanType) o;
        return expires == banType.expires &&
                durationInDays == banType.durationInDays &&
                Objects.equals(id, banType.id) &&
                Objects.equals(cause, banType.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cause, expires, durationInDays);
    }

    @Override
    public String toString() {
        return "BanType{" +
                "id=" + id +
                ", cause='" + cause + '\'' +
                ", expires=" + expires +
                ", durationInDays=" + durationInDays +
                '}';
    }
}
