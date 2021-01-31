package entity;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Table
@Entity(name = "is_item")
@NotThreadSafe
public final class Item {

    @Id
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

    public Item() {
    }

    public Item(Long id, String name, int amount,
                BigDecimal priceForOne, Currency currency,
                String imageUrl, String itemDescription,
                Date putUpForSale) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.priceForOne = priceForOne;
        this.currency = currency;
        this.imageUrl = imageUrl;
        this.itemDescription = itemDescription;
        this.putUpForSale = putUpForSale;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return amount == item.amount &&
                id.equals(item.id) &&
                name.equals(item.name) &&
                priceForOne.equals(item.priceForOne) &&
                currency.equals(item.currency) &&
                imageUrl.equals(item.imageUrl) &&
                Objects.equals(itemDescription, item.itemDescription) &&
                putUpForSale.equals(item.putUpForSale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, priceForOne, currency, imageUrl, itemDescription, putUpForSale);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", priceForOne=" + priceForOne +
                ", currency=" + currency +
                ", imageUrl='" + imageUrl + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", putUpForSale=" + putUpForSale +
                '}';
    }
}
