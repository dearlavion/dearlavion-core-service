package com.dearlavion.coreservice.datasource.location.city;

public record CityOption(
        String name,
        String countryName,
        String countryCode,
        long population,
        Double latitude,
        Double longitude
) {
    public static CityOption from(City c) {
        return new CityOption(
                c.getName(),
                c.getCountryName(),
                c.getCountryCode(),
                c.getPopulation(),
                c.getLatitude(),
                c.getLongitude()
        );
    }
}
