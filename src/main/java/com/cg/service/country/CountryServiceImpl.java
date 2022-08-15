package com.cg.service.country;

import com.cg.model.Country;
import com.cg.model.dto.CountryDTO;
import com.cg.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return null;
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country getById(Long id) {
        return null;
    }

    @Override
    public Country save(Country country) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<CountryDTO> getAllCountryDTO() {
        return countryRepository.getAllCountryDTO();
    }

    @Override
    public Optional<CountryDTO> getCountryDTOById(Long countryId) {
        return countryRepository.getCountryDTOById(countryId);

    }
}
