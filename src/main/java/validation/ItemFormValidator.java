package validation;

import dto.ItemForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

public class ItemFormValidator implements Validator {

    private static final Pattern ITEM_NAME_PATTERN = Pattern.compile("^[a-zA-Z]([a-zA-Z0-9]|[ ]|[-#@№%&*().,'\"]){2,}");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 255;
    private static final int MAX_PRICE_INTEGER_PART_LENGTH = 11;
    private static final int PRICE_FRACTIONAL_PART_LENGTH = 2;
    private static final int MAX_IMG_URL_LENGTH = 255;

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, ItemForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof ItemForm) {
            ItemForm itemForm = (ItemForm) o;
            checkName(itemForm.getName(), errors);
            checkAmount(itemForm.getAmount(), errors);
            checkCurrencyId(itemForm.getCurrencyId(), errors);
            checkDescription(itemForm.getItemDescription(), errors);
            checkImageUrl(itemForm.getImageUrl(), errors);
            checkPrice(itemForm.getPrice(), errors);
            checkPutUpForSale(itemForm.getPutUpForSale(), errors);
            checkUserId(itemForm.getUserId(), errors);
        }
    }

    private void checkName(String name, Errors errors) {
        if (name == null) {
            errors.rejectValue("name", "Name must not be null");
        } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            errors.rejectValue("name",
                    String.format("Name size must be between %d and %d", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        } else if (!ITEM_NAME_PATTERN.matcher(name).matches()) {
            errors.rejectValue("name", "Invalid name");
        }
    }

    private void checkAmount(int amount, Errors errors) {
        if (amount <= 0) {
            errors.rejectValue("amount", "Amount must be positive");
        }
    }

    private void checkPrice(BigDecimal price, Errors errors) {
        if (price == null) {
            errors.rejectValue("price", "Price must not be null");
            return;
        }else if (price.compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("price", "Price must not be negative");
            return;
        }
        String[] priceParts = price.toString().split("[.]");
        String integerPart = priceParts[0];
        String fractionalPart = priceParts[1];
        if(integerPart.length() > MAX_PRICE_INTEGER_PART_LENGTH){
            errors.rejectValue("price", String.format("Price must be less than %d digits", MAX_PRICE_INTEGER_PART_LENGTH));
        }else if(fractionalPart.length() != PRICE_FRACTIONAL_PART_LENGTH){
            errors.rejectValue("price", String.format("Fractional part of price must be exactly %d digits", PRICE_FRACTIONAL_PART_LENGTH));
        }
    }

    private void checkCurrencyId(Integer currencyId, Errors errors){
        if(currencyId == null){
            errors.rejectValue("currencyId", "Currency id must not be null");
        }
    }

    private void checkImageUrl(String imageUrl, Errors errors){
        if(imageUrl == null){
            errors.rejectValue("imageUrl", "Image url must not be null");
        }else if(imageUrl.length() > MAX_IMG_URL_LENGTH){
            errors.rejectValue("imageUrl", String.format("Image url length must be less than %d", MAX_IMG_URL_LENGTH));
        }else {
            try{
                URL url = new URL(imageUrl);
            } catch (MalformedURLException e) {
                errors.rejectValue("imageUrl", "Invalid url");
            }
        }
    }

    private void checkDescription(String description, Errors errors){

    }

    private void checkPutUpForSale(LocalDateTime putUpForSale, Errors errors){
        if(putUpForSale == null){
            errors.rejectValue("putUpForSale", "Time of putting up for sale must not be null");
        }
    }

    private void checkUserId(Long userId, Errors errors){
        if(userId == null){
            errors.rejectValue("userId", "User id must not be null");
        }
    }

}
