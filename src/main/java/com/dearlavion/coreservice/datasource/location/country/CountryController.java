package com.dearlavion.coreservice.datasource.location.country;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/country")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    // 🌍 Country dropdown
    @GetMapping
    public List<CountryOption> getAllCountries() {
        return service.getCountries();
    }
}

