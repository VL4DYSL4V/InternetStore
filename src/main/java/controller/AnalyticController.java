package controller;

import dao.analytic.AnalyticDAO;
import entity.Country;
import entity.Currency;
import exception.dao.FetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/analytic")
public final class AnalyticController {

    private final AnalyticDAO analyticDAO;
    private final Converter<String, LocalDate> stringLocalDateConverter;

    @Autowired
    public AnalyticController(AnalyticDAO analyticDAO, Converter<String, LocalDate> stringLocalDateConverter) {
        this.analyticDAO = analyticDAO;
        this.stringLocalDateConverter = stringLocalDateConverter;
    }

    @GetMapping("/country-to-item-names")
    public String countryToItemNames(Model model) {
        try {
            Map<Country, Collection<String>> countryToItemNames = analyticDAO.countryToItemNames();
            model.addAttribute("countryToItemNames", countryToItemNames);
        } catch (FetchException e) {
            e.printStackTrace();
        }
        return "jsonTemplate";
    }

    @GetMapping("/currency-to-item-amount")
    public String currencyToItemAmount(Model model) {
        try {
            Map<Currency, Integer> currencyToItemAmount = analyticDAO.currencyToItemAmount();
            model.addAttribute("currencyToItemAmount", currencyToItemAmount);
        } catch (FetchException e) {
            e.printStackTrace();
        }
        return "jsonTemplate";
    }

    @GetMapping("/users-who-got-reply-from-seller")
    public String usersWhoGotReplyFromSeller(Model model) {
        Collection<String> names = analyticDAO.usersWhoGotReplyFromSeller();
        model.addAttribute("usersWhoGotReplyFromSeller", names);
        return "jsonTemplate";
    }

    @GetMapping("/count-users-who-posted-items-between-dates")
    public String countUsersWhoPostedItemsBetweenDates(
            Model model,
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ) {
        LocalDate startDate = stringLocalDateConverter.convert(start);
        LocalDate endDate = stringLocalDateConverter.convert(end);
        if(startDate == null || endDate == null){
            throw new RuntimeException("Date must be null");
        }
        long count;
        try {
            if (startDate.isBefore(endDate)) {
                count = analyticDAO.countUsersWhoPostedItemsBetweenDates(startDate, endDate);
            } else {
                count = analyticDAO.countUsersWhoPostedItemsBetweenDates(endDate, startDate);
            }
        }catch (FetchException e){
            throw new RuntimeException(e);
        }
        model.addAttribute("countUsersWhoPostedItemsBetweenDates", count);
        return "jsonTemplate";
    }
}
