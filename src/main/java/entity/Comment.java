package entity;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Table
@Entity(name = "is_comment")
public final class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "time_of_post", nullable = false)
    private Timestamp timeOfPost;

    @Column(name = "comment_text", nullable = false)
    private String commentTest;

    @JoinFormula("SELECT IF(COUNT(user_name) = 0, NULL, user_name) AS author FROM is_user INNER JOIN is_user_to_comment USING (user_id) INNER JOIN is_comment USING(comment_id)")
    private String authorName;

    @JoinFormula("SELECT IF(COUNT(item_id) = 0, NULL, item_id) AS item_id_value FROM is_item INNER JOIN is_item_to_comment USING (item_id) INNER JOIN is_comment USING(comment_id)")
    private Long itemId;

    public Comment() {
    }

    public Comment(Timestamp timeOfPost, String commentTest, String authorName, Long itemId) {
        this.timeOfPost = timeOfPost;
        this.commentTest = commentTest;
        this.authorName = authorName;
        this.itemId = itemId;
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

    public String getCommentTest() {
        return commentTest;
    }

    public void setCommentTest(String commentTest) {
        this.commentTest = commentTest;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(timeOfPost, comment.timeOfPost) &&
                Objects.equals(commentTest, comment.commentTest) &&
                Objects.equals(authorName, comment.authorName) &&
                Objects.equals(itemId, comment.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeOfPost, commentTest, authorName, itemId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", timeOfPost=" + timeOfPost +
                ", commentTest='" + commentTest + '\'' +
                ", authorName='" + authorName + '\'' +
                ", itemId=" + itemId +
                '}';
    }
}
