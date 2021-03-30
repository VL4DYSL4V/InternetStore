package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Table
@Entity(name = "ban")
public final class Ban {

    @Id
    @Column(name = "ban_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "when_banned", nullable = false)
    private Timestamp whenBanned;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private BanType banType;

    public Ban() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getWhenBanned() {
        return whenBanned;
    }

    public void setWhenBanned(Timestamp whenBanned) {
        this.whenBanned = whenBanned;
    }

    public BanType getBanType() {
        return banType;
    }

    public void setBanType(BanType banType) {
        this.banType = banType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ban ban = (Ban) o;
        return Objects.equals(id, ban.id) &&
                Objects.equals(userId, ban.userId) &&
                Objects.equals(commentId, ban.commentId) &&
                Objects.equals(whenBanned, ban.whenBanned) &&
                Objects.equals(banType, ban.banType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, commentId, whenBanned, banType);
    }

    @Override
    public String toString() {
        return "Ban{" +
                "id=" + id +
                ", userId=" + userId +
                ", commentId=" + commentId +
                ", whenBanned=" + whenBanned +
                ", banType=" + banType +
                '}';
    }
}
