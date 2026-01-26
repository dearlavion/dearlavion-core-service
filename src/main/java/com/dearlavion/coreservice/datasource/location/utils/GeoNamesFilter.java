package com.dearlavion.coreservice.datasource.location.utils;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.HashMap;
import java.util.Map;

public class GeoNamesFilter {

    public static void main(String[] args) {
        // Download files in: https://www.geonames.org/export/?utm_source=chatgpt.com
        // After files are generated: import it in mongo:
        // mongoimport \
        //  --uri "mongodb+srv://admin:admin@dearlavioncluster.xnanadi.mongodb.net/core-service" \
        //  --collection countries \
        //  --file "/Users/alysson/Documents/DearLavionAnimation_Assets/cities_10000.json" \
        //  --drop

        // Paths to GeoNames data
        String inputFile = "/Users/alysson/Documents/DearLavionAnimation_Assets/allCountries.txt";
        String countryFile = "/Users/alysson/Documents/DearLavionAnimation_Assets/countryInfo.txt";
        String outputFile = "/Users/alysson/Documents/DearLavionAnimation_Assets/cities_10000.json";

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty JSON

        // Load country code → country name map
        Map<String, String> countryMap = loadCountryMap(countryFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");

                // Only process populated places (feature class "P")
                if (!fields[6].equals("P")) continue;

                long population = 0;
                try {
                    population = Long.parseLong(fields[14]);
                } catch (NumberFormatException e) {
                    continue; // skip invalid population
                }

                if (population >= 10000) {
                    String countryCode = fields[8];
                    String countryName = countryMap.getOrDefault(countryCode, "Unknown");

                    // Create JSON object
                    Map<String, Object> city = new HashMap<>();
                    city.put("geonameid", Long.parseLong(fields[0]));
                    city.put("name", fields[1]);
                    city.put("asciiName", fields[2]);
                    city.put("latitude", Double.parseDouble(fields[4]));
                    city.put("longitude", Double.parseDouble(fields[5]));
                    city.put("countryCode", countryCode);
                    city.put("countryName", countryName);
                    city.put("population", population);

                    // Write as JSON line
                    writer.write(mapper.writeValueAsString(city));
                    writer.newLine();
                    count++;
                }
            }

            System.out.println("Finished! Total cities with population > 10,000: " + count);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to load country code → country name
    private static Map<String, String> loadCountryMap(String filePath) {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue; // skip comment lines
                String[] parts = line.split("\t");
                if (parts.length >= 5) {
                    String code = parts[0];   // ISO country code
                    String name = parts[4];   // country name
                    map.put(code, name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
