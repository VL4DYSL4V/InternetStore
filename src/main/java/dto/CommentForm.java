package dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class CommentForm {

    private LocalDateTime timeOfPost;

    private String commentText;

    private Long userId;

    private Long itemId;

    public CommentForm() {

    }

    public CommentForm(LocalDateTime timeOfPost, String commentText, Long userId, Long itemId) {
        this.timeOfPost = timeOfPost;
        this.commentText = commentText;
        this.userId = userId;
        this.itemId = itemId;
    }

    public LocalDateTime getTimeOfPost() {
        return timeOfPost;
    }

    public void setTimeOfPost(LocalDateTime timeOfPost) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentForm that = (CommentForm) o;
        return Objects.equals(timeOfPost, that.timeOfPost) &&
                Objects.equals(commentText, that.commentText) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeOfPost, commentText, userId, itemId);
    }

    @Override
    public String toString() {
        return "CommentForm{" +
                "timeOfPost=" + timeOfPost +
                ", commentText='" + commentText + '\'' +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
