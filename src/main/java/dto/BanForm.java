package dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class BanForm {

    private Long userId;

    private Long commentId;

    private LocalDateTime whenBanned;

    private Integer banTypeId;

    public BanForm() {
    }

    public BanForm(Long userId, Long commentId, LocalDateTime whenBanned, Integer banTypeId) {
        this.userId = userId;
        this.commentId = commentId;
        this.whenBanned = whenBanned;
        this.banTypeId = banTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public LocalDateTime getWhenBanned() {
        return whenBanned;
    }

    public void setWhenBanned(LocalDateTime whenBanned) {
        this.whenBanned = whenBanned;
    }

    public Integer getBanTypeId() {
        return banTypeId;
    }

    public void setBanTypeId(Integer banTypeId) {
        this.banTypeId = banTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanForm banForm = (BanForm) o;
        return Objects.equals(userId, banForm.userId) &&
                Objects.equals(commentId, banForm.commentId) &&
                Objects.equals(whenBanned, banForm.whenBanned) &&
                Objects.equals(banTypeId, banForm.banTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commentId, whenBanned, banTypeId);
    }

    @Override
    public String toString() {
        return "BanForm{" +
                "userId=" + userId +
                ", commentId=" + commentId +
                ", whenBanned=" + whenBanned +
                ", banType=" + banTypeId +
                '}';
    }
}
