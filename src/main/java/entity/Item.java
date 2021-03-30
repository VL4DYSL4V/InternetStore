package entity;

import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Table
@Entity(name = "item")
@NotThreadSafe
public final class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "img_url", nullable = false)
    private String imageUrl;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "put_up_for_sale", nullable = false)
    private Date putUpForSale;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemId")
    private Collection<Comment> comments = new HashSet<>();

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    public Item() {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Date getPutUpForSale() {
        return putUpForSale;
    }

    public void setPutUpForSale(Date putUpForSale) {
        this.putUpForSale = putUpForSale;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return amount == item.amount &&
                Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(currency, item.currency) &&
                Objects.equals(imageUrl, item.imageUrl) &&
                Objects.equals(itemDescription, item.itemDescription) &&
                Objects.equals(putUpForSale, item.putUpForSale) &&
                Objects.equals(comments, item.comments) &&
                Objects.equals(userId, item.userId) &&
                Objects.equals(country, item.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, price, currency, imageUrl, itemDescription, putUpForSale, comments, userId, country);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", currency=" + currency +
                ", imageUrl='" + imageUrl + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", putUpForSale=" + putUpForSale +
                ", comments=" + comments +
                ", userId=" + userId +
                ", country=" + country +
                '}';
    }
}
