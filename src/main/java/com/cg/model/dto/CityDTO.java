package com.cg.model.dto;

import com.cg.model.City;
import com.cg.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CityDTO {

    private String id;

    @NotBlank(message = "Name should not be empty")
    private String cityName;

    @NotBlank(message = "Area should not be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Area only digit")
    private String area;

    @NotBlank(message = "Population should not be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Population only digit")
    private String population;

    @NotBlank(message = "GDP should not be empty")
    @Pattern(regexp = "^[0-9]+$", message = "GDP only digit")
    private String gdp;

    @NotBlank(message = "Description should not be empty")
    private String description;

    private CountryDTO country;

    public CityDTO(Long id, String cityName, Long area, Long population, Long gdp, String description, Country country) {
        this.id = id.toString();
        this.cityName = cityName;
        this.area = area.toString();
        this.population = population.toString();
        this.gdp = gdp.toString();
        this.description = description;
        this.country = country.toCountryDTO();
    }

    public City toCity() {
        return new City()
                .setId(Long.parseLong(id))
                .setCityName(cityName)
                .setArea(Long.parseLong(area))
                .setPopulation(Long.parseLong(population))
                .setGdp(Long.parseLong(gdp))
                .setDescription(description)
                .setCountry(country.toCountry());
    }

}
