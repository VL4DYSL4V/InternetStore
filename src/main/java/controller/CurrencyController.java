package controller;

import dao.currency.CurrencyDao;
import dto.CurrencyForm;
import entity.Currency;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import validation.CurrencyFormValidator;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/currencies")
@SessionAttributes("currencyForm")
public final class CurrencyController {

    private final CurrencyFormValidator currencyFormValidator;
    private final CurrencyDao currencyDao;

    @Autowired
    public CurrencyController(CurrencyFormValidator currencyFormValidator, CurrencyDao currencyDao) {
        this.currencyFormValidator = currencyFormValidator;
        this.currencyDao = currencyDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(currencyFormValidator);
    }

    @GetMapping
    public String allCurrencies(Model model){
        Collection<Currency> currencies = new ArrayList<>();
        try{
            currencies = currencyDao.allEntities();
        }catch (FetchException e){
            e.printStackTrace();
        }
        model.addAttribute("currencies", currencies);
        return "currency/allCurrencies";
    }

    @GetMapping("/new")
    public String createCurrency(Model model){
        model.addAttribute("creationCurrencyForm", new CurrencyForm());
        return "currency/createCurrency";
    }

    @PostMapping("/new")
    public String postNewCurrency(
            @Validated @ModelAttribute("creationCurrencyForm") CurrencyForm currencyForm,
            BindingResult bindingResult,
            SessionStatus sessionStatus){
        if(bindingResult.hasErrors()){
            return "currency/createCurrency";
        }
        Currency currency = new Currency(currencyForm.getCurrencyName());
        try{
            currencyDao.save(currency);
            sessionStatus.setComplete();
        } catch (StoreException e) {
            e.printStackTrace();
        }
        return "redirect:/currencies";
    }

    @GetMapping("/{id}")
    public String getCurrency(@PathVariable("id") Integer id, Model model){
        try{
            Currency currency = currencyDao.getById(id);
            CurrencyForm currencyForm = new CurrencyForm(currency.getId(), currency.getCurrencyName());
            model.addAttribute("currencyForm", currencyForm);
        }catch (FetchException e){
            e.printStackTrace();
        }
        return "currency/currency";
    }

    @PostMapping("/{id}/delete")
    public String deleteCurrency(@PathVariable("id") Integer id, SessionStatus sessionStatus){
        try{
            currencyDao.delete(id);
            sessionStatus.setComplete();
        }catch (DeleteException e){
            e.printStackTrace();
        }
        return "redirect:/currencies";
    }

    @GetMapping("/{id}/edit")
    public String editCurrency(@PathVariable("id") Integer id, Model model){
        try{
            Currency currency = currencyDao.getById(id);
            CurrencyForm currencyForm = new CurrencyForm(currency.getId(), currency.getCurrencyName());
            model.addAttribute("currencyForm", currencyForm);
        }catch (FetchException e){
            e.printStackTrace();
        }
        return "currency/editCurrency";
    }

    @PostMapping("{id}/edit")
    public String updateCountry(
            @ModelAttribute("currencyForm") @Validated CurrencyForm currencyForm,
            BindingResult bindingResult,
            SessionStatus sessionStatus,
            @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "/currency/editCurrency";
        }
        try{
            Currency currency = new Currency(id, currencyForm.getCurrencyName());
            currencyDao.update(id, currency);
            sessionStatus.setComplete();
        }catch (UpdateException e){
            e.printStackTrace();
        }
        return "redirect:/currencies";
    }
}
