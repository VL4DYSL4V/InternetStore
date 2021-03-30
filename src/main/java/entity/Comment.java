package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Table
@Entity(name = "comment")
public final class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "time_of_post", nullable = false)
    private Timestamp timeOfPost;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="item_id", nullable = false)
    private Long itemId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "reply", referencedColumnName = "comment_id")
    Collection<Comment> replies = new HashSet<>();

    public Comment() {
    }

    public Comment(Timestamp timeOfPost, String commentText, Long userId, Long itemId, Collection<Comment> replies) {
        this.timeOfPost = timeOfPost;
        this.commentText = commentText;
        this.userId = userId;
        this.itemId = itemId;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeOfPost() {
        return timeOfPost;
    }

    public void setTimeOfPost(Timestamp timeOfPost) {
        this.timeOfPost = timeOfPost;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Collection<Comment> getReplies() {
        return replies;
    }

    public void setReplies(Collection<Comment> replies) {
        this.replies = replies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(timeOfPost, comment.timeOfPost) &&
                Objects.equals(commentText, comment.commentText) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(itemId, comment.itemId) &&
                Objects.equals(replies, comment.replies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeOfPost, commentText, userId, itemId, replies);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", timeOfPost=" + timeOfPost +
                ", commentText='" + commentText + '\'' +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", replies=" + replies +
                '}';
    }
}
