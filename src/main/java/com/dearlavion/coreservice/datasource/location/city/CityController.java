package com.dearlavion.coreservice.datasource.location.city;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core/city")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    // 🇵🇭 Country → cities
    @GetMapping("/{countryCode}")
    public List<CityOption> citiesByCountry(@PathVariable String countryCode) {
        return service.getCitiesByCountry(countryCode)
                .stream()
                .map(CityOption::from)
                .toList();
    }

    // 🏙️ City autocomplete
    @GetMapping("/search")
    public List<CityOption> searchCity(@RequestParam String q,
                                       @RequestParam(required = false) String country) {
        return service.searchCity(q, country)
                .stream()
                .map(CityOption::from)
                .toList();
    }
}
