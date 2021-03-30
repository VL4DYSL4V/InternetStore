package dto;

import java.util.Objects;

public final class BanTypeForm {

    private String cause;

    private boolean expires;

    private int durationInDays;

    public BanTypeForm() {
    }

    public BanTypeForm(String cause, boolean expires, int durationInDays) {
        this.cause = cause;
        this.expires = expires;
        this.durationInDays = durationInDays;
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
        BanTypeForm that = (BanTypeForm) o;
        return expires == that.expires &&
                durationInDays == that.durationInDays &&
                Objects.equals(cause, that.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cause, expires, durationInDays);
    }

    @Override
    public String toString() {
        return "BanTypeForm{" +
                "cause='" + cause + '\'' +
                ", expires=" + expires +
                ", durationInDays=" + durationInDays +
                '}';
    }
}
