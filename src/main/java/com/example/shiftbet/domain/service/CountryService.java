package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.Country;
import com.example.shiftbet.domain.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void add(Country country)
    {
        countryRepository.save(country);
    }
    public Country get(long  id)
    {
        return countryRepository.findById(id).orElse(null);
    }
    public void delete(long id)
    {
        countryRepository.delete(get(id));
    }
    public List<Country> getAll()
    {
        return countryRepository.findAll();
    }
}
