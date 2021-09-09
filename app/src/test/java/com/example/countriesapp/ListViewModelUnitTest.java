package com.example.countriesapp;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelUnitTest
{
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setupRxSchedulers()
    {
        Scheduler immediate = new Scheduler()
        {
            @NonNull
            @Override
            public Worker createWorker()
            {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }
}
