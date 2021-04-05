package controller;

import dao.country.CountryDao;
import dto.CountryForm;
import entity.Country;
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
import validation.CountryFormValidator;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/countries")
@SessionAttributes("countryForm")
public final class CountryController {

    private final CountryDao countryDao;
    private final CountryFormValidator countryFormValidator;

    @Autowired
    public CountryController(CountryDao countryDao, CountryFormValidator countryFormValidator) {
        this.countryDao = countryDao;
        this.countryFormValidator = countryFormValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(countryFormValidator);
    }

    @GetMapping
    public String allCountries(Model model) {
        Collection<Country> countries = new ArrayList<>();
        try {
            countries = countryDao.allEntities();
        } catch (FetchException e) {
            e.printStackTrace();
        }
        model.addAttribute("countries", countries);
        return "country/allCountries";
    }

    @GetMapping("/new")
    public String createCountry(Model model) {
        model.addAttribute("creationCountryForm", new CountryForm());
        return "country/createCountry";
    }

    @PostMapping("/new")
    public String postNewCountry(
            @Validated @ModelAttribute("creationCountryForm") CountryForm countryForm,
            BindingResult bindingResult,
            SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            return "country/createCountry";
        }
        Country country = new Country(countryForm.getCountryName());
        try {
            countryDao.save(country);
            sessionStatus.setComplete();
        } catch (StoreException e) {
            e.printStackTrace();
        }
        return "redirect:/countries";
    }

    @GetMapping("/{id}")
    public String getCountry(@PathVariable("id") Integer id, Model model) {
        try {
            Country country = countryDao.getById(id);
            CountryForm countryForm = new CountryForm(country.getId(), country.getCountryName());
            model.addAttribute("countryForm", countryForm);
        } catch (FetchException e) {
            e.printStackTrace();
        }
        return "country/country";
    }

    @PostMapping("/{id}/delete")
    public String deleteCountry(@PathVariable("id") Integer id, SessionStatus sessionStatus) {
        try {
            countryDao.delete(id);
            sessionStatus.setComplete();
        } catch (DeleteException e) {
            e.printStackTrace();
        }
        return "redirect:/countries";
    }

    @GetMapping("/{id}/edit")
    public String editCountry(@PathVariable("id") Integer id, Model model) {
        try {
            Country country = countryDao.getById(id);
            CountryForm countryForm = new CountryForm(country.getId(), country.getCountryName());
            model.addAttribute("countryForm", countryForm);
        } catch (FetchException e) {
            e.printStackTrace();
        }
        return "country/editCountry";
    }

    @PostMapping("/{id}/edit")
    public String updateCountry(
            @ModelAttribute("countryForm") @Validated CountryForm countryForm,
            BindingResult bindingResult,
            SessionStatus sessionStatus,
            @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "country/editCountry";
        }
        Country country = new Country(id, countryForm.getCountryName());
        try {
            countryDao.update(id, country);
            sessionStatus.setComplete();
        } catch (UpdateException e) {
            e.printStackTrace();
        }
        return "redirect:/countries";
    }

}
