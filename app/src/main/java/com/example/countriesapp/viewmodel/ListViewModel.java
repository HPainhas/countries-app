package com.example.countriesapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesapp.di.DaggerApiComponent;
import com.example.countriesapp.model.CountriesService;
import com.example.countriesapp.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel
{
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();

    @Inject
    public CountriesService countriesService;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public ListViewModel()
    {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void refresh()
    {
        fetchCountries();
    }

    private void fetchCountries()
    {
        loading.setValue(true);

        disposable.add(
                countriesService.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>()
                        {
                            @Override
                            public void onSuccess(@NonNull List<CountryModel> countryModels)
                            {
                                countries.setValue(countryModels);
                                countryLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@NonNull Throwable e)
                            {
                                countryLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        disposable.clear();
    }
}
