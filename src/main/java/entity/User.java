package entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Table
@Entity(name = "is_user")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String name;

    @Column(name = "user_password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "phone_number_id", nullable = false, unique = true)
    private PhoneNumber phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "is_user_to_item",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "item_id", nullable = false)
    )
    private Collection<Item> items = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "is_user_to_comment",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    )
    private Collection<Comment> comments = new HashSet<>();

    public User() {
    }

    public User(String name, String password, PhoneNumber phoneNumber, String email, Collection<Item> items) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.items = items;
    }

    public User(Long id, String name, String password, PhoneNumber phoneNumber, String email, Collection<Item> items) {
        this(name, password, phoneNumber, email, items);
        this.id = id;
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(items, user.items) &&
                Objects.equals(comments, user.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, phoneNumber, email, items, comments);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", items=" + items +
                ", comments=" + comments +
                '}';
    }
}
