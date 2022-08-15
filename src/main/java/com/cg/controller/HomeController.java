package com.cg.controller;

import com.cg.model.City;
import com.cg.model.Country;
import com.cg.model.dto.CityDTO;
import com.cg.model.dto.CountryDTO;
import com.cg.service.city.CityService;
import com.cg.service.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    CountryService countryService;

    @Autowired
    CityService cityService;

    @GetMapping("/list")
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<CityDTO> cities = cityService.getAllCityDTO();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/layout/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/layout/create");
        List<CountryDTO> countries = countryService.getAllCountryDTO();
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", new CityDTO());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdatePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/layout/edit");
        List<CountryDTO> countries = countryService.getAllCountryDTO();
        modelAndView.addObject("countries", countries);

        Optional<CityDTO> cityDTOS = cityService.getCityDTOById(id);
        modelAndView.addObject("city", cityDTOS.get());
        return modelAndView;
    }

    @GetMapping("/info/{id}")
    public ModelAndView showInfoPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/layout/info");

        List<CountryDTO> countries = countryService.getAllCountryDTO();
        modelAndView.addObject("countries", countries);

        Optional<CityDTO> cityDTOS = cityService.getCityDTOById(id);
        modelAndView.addObject("city", cityDTOS.get());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable Long id) {
        cityService.deleteById(id);
        return "redirect:/home/list";
    }

    @PostMapping("/create")
    public ModelAndView doCreate(@Valid @ModelAttribute("city") CityDTO cityDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/layout/create");
        cityDTO.setId(String.valueOf(0L));

        List<CountryDTO> countries = countryService.getAllCountryDTO();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("hasError", true);

            List<ObjectError> errors = bindingResult.getAllErrors();

            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", new CityDTO());
            modelAndView.addObject("errors", errors);

            return modelAndView;
        }

        Optional<Country> country = countryService.findById(Long.valueOf(cityDTO.getCountry().getId()));

        if (!country.isPresent()) {
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", new CityDTO());
            modelAndView.addObject("error", "Country is not define");
            return modelAndView;
        }

        City newCity = new City();
        newCity.setId(Long.valueOf(cityDTO.getId()));
        newCity.setCityName(cityDTO.getCityName());
        newCity.setPopulation(Long.parseLong(cityDTO.getPopulation()));
        newCity.setGdp(Long.parseLong(cityDTO.getGdp()));
        newCity.setArea(Long.parseLong(cityDTO.getArea()));
        newCity.setDescription(cityDTO.getDescription());
        newCity.setCountry(country.get());

        cityService.save(newCity);

        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", new CityDTO());
        modelAndView.addObject("success", "Created successful");

        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView doUpdate(@PathVariable Long id, @Valid @ModelAttribute("city") CityDTO cityDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/layout/edit");
        cityDTO.setId(String.valueOf(0L));

        List<CountryDTO> countries = countryService.getAllCountryDTO();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("hasError", true);

            List<ObjectError> errors = bindingResult.getAllErrors();

            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", cityDTO);
            modelAndView.addObject("errors", errors);

            return modelAndView;
        }

        Optional<Country> country = countryService.findById(Long.valueOf(cityDTO.getCountry().getId()));

        if (!country.isPresent()) {
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", cityDTO);
            modelAndView.addObject("error", "Country is not define");
            return modelAndView;
        }

        Optional<CityDTO> currentCity = cityService.getCityDTOById(id);

        if (!currentCity.isPresent()) {
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", cityDTO);
            modelAndView.addObject("error", "City is not define");
            return modelAndView;
        }

        City updateCity = new City();

        updateCity.setId(id);
        updateCity.setCityName(cityDTO.getCityName());
        updateCity.setPopulation(Long.parseLong(cityDTO.getPopulation()));
        updateCity.setGdp(Long.parseLong(cityDTO.getGdp()));
        updateCity.setArea(Long.parseLong(cityDTO.getArea()));
        updateCity.setDescription(cityDTO.getDescription());
        updateCity.setCountry(country.get());

        cityService.save(updateCity);

        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", updateCity);
        modelAndView.addObject("success", "Updated successful");

        return modelAndView;
    }

}
