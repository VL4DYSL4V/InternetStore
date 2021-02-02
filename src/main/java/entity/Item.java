package entity;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Table
@Entity(name = "is_item")
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

    @Column(name = "price_for_one", nullable = false)
    private BigDecimal priceForOne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "img_url", nullable = false)
    private String imageUrl;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "put_up_for_sale", nullable = false)
    private Date putUpForSale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_number_id", referencedColumnName = "phone_number_id", nullable = false)
    private PhoneNumber phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", unique = false, nullable = true)
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "is_item_to_comment",
        joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    )
    private Collection<Comment> comments = new HashSet<>();

    public Item() {
    }

    public Item(String name, int amount,
                BigDecimal priceForOne, Currency currency,
                String imageUrl, String itemDescription,
                Date putUpForSale, PhoneNumber phoneNumber, Country country) {
        this.name = name;
        this.amount = amount;
        this.priceForOne = priceForOne;
        this.currency = currency;
        this.imageUrl = imageUrl;
        this.itemDescription = itemDescription;
        this.putUpForSale = putUpForSale;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public Item(Long id, String name, int amount,
                BigDecimal priceForOne, Currency currency,
                String imageUrl, String itemDescription,
                Date putUpForSale, PhoneNumber phoneNumber, Country country) {
        this(name, amount, priceForOne, currency,
                imageUrl, itemDescription, putUpForSale, phoneNumber, country);
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPriceForOne() {
        return priceForOne;
    }

    public void setPriceForOne(BigDecimal priceForOne) {
        this.priceForOne = priceForOne;
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

    @Nullable
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        Item item = (Item) o;
        return amount == item.amount &&
                Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(priceForOne, item.priceForOne) &&
                Objects.equals(currency, item.currency) &&
                Objects.equals(imageUrl, item.imageUrl) &&
                Objects.equals(itemDescription, item.itemDescription) &&
                Objects.equals(putUpForSale, item.putUpForSale) &&
                Objects.equals(phoneNumber, item.phoneNumber) &&
                Objects.equals(country, item.country) &&
                Objects.equals(comments, item.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, priceForOne, currency, imageUrl, itemDescription, putUpForSale, phoneNumber, country, comments);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("Item{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", amount=").append(amount)
                .append(", priceForOne=").append(priceForOne)
                .append(",\n currency=").append(currency)
                .append(", imageUrl='").append(imageUrl).append('\'')
                .append(",\n itemDescription='").append(itemDescription).append('\'')
                .append(", putUpForSale=").append(putUpForSale)
                .append(", phoneNumber=").append(phoneNumber)
                .append(", country=").append(country)
                .append(",").append("\n\tcomments={");
        comments.forEach(comment -> sb.append(comment).append("\n"));
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }
}
