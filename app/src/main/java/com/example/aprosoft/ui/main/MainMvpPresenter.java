package com.example.aprosoft.ui.main;

import android.arch.paging.PositionalDataSource;

import com.example.aprosoft.data.db.model.CarTotalInfo;

import java.util.Map;

interface MainMvpPresenter {

    void onFilterClicked();

    void onItemClicked(CarTotalInfo carTotalInfo);

    void onFabClicked();

    void onFilterReturn(boolean status);

    void onCarActivityReturn(boolean status, String message);

    void onLoadInitial(int start, int limit,
                       PositionalDataSource.LoadInitialCallback<CarTotalInfo> callback);

    void onLoadRange(int start, int limit,
                     PositionalDataSource.LoadRangeCallback<CarTotalInfo> callback);
}
