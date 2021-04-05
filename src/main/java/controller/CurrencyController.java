package controller;

import dao.currency.CurrencyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import validation.CurrencyFormValidator;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyFormValidator currencyFormValidator;
    private final CurrencyDao currencyDao;

    @Autowired
    public CurrencyController(CurrencyFormValidator currencyFormValidator, CurrencyDao currencyDao) {
        this.currencyFormValidator = currencyFormValidator;
        this.currencyDao = currencyDao;
    }


}
