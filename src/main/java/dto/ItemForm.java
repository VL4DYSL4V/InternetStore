package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ItemForm {

    private String name;

    private int amount;

    private BigDecimal price;

    private Integer currencyId;

    private String imageUrl;

    private String itemDescription;

    private LocalDateTime putUpForSale;

    private Long userId;

    private Integer countryId;

    public ItemForm() {
    }

    public ItemForm(String name, int amount, BigDecimal price, Integer currencyId, String imageUrl, String itemDescription, LocalDateTime putUpForSale, Long userId, Integer countryId) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.currencyId = currencyId;
        this.imageUrl = imageUrl;
        this.itemDescription = itemDescription;
        this.putUpForSale = putUpForSale;
        this.userId = userId;
        this.countryId = countryId;
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

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
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

    public LocalDateTime getPutUpForSale() {
        return putUpForSale;
    }

    public void setPutUpForSale(LocalDateTime putUpForSale) {
        this.putUpForSale = putUpForSale;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemForm itemForm = (ItemForm) o;
        return amount == itemForm.amount &&
                Objects.equals(name, itemForm.name) &&
                Objects.equals(price, itemForm.price) &&
                Objects.equals(currencyId, itemForm.currencyId) &&
                Objects.equals(imageUrl, itemForm.imageUrl) &&
                Objects.equals(itemDescription, itemForm.itemDescription) &&
                Objects.equals(putUpForSale, itemForm.putUpForSale) &&
                Objects.equals(userId, itemForm.userId) &&
                Objects.equals(countryId, itemForm.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, price, currencyId, imageUrl, itemDescription, putUpForSale, userId, countryId);
    }

    @Override
    public String toString() {
        return "ItemForm{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", currencyId=" + currencyId +
                ", imageUrl='" + imageUrl + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", putUpForSale=" + putUpForSale +
                ", userId=" + userId +
                ", countryId=" + countryId +
                '}';
    }
}
