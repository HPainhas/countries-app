package com.example.countriesapp.di;

import com.example.countriesapp.model.CountriesService;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent
{
    void inject(CountriesService service);
}
