package com.example.aprosoft.ui.main;

import android.arch.paging.PositionalDataSource;
//import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aprosoft.App;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.data.db.room.CarDao;
import com.example.aprosoft.data.state.FilterStateHelper;
import com.example.aprosoft.data.state.SortStateHelper;
import com.example.aprosoft.ui.base.Repository;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainMvpView> implements MainMvpPresenter {
//    private static final String TAG = MainPresenter.class.getSimpleName();
    @Inject
    Repository repository;
    @Inject
    FilterStateHelper filterStateHelper;
    @Inject
    SortStateHelper sortStateHelper;

    MainPresenter() {
        super();
        App.getApplicationComponent().injectPresenter(this);
        repository.setMainPresenter(this);
    }

    @Override
    public void onFilterClicked() {
        getViewState().startFilterActivity();
    }

    @Override
    public void onFilterReturn(boolean status) {
//        Log.e(TAG, "on filter return " + status);
        if(status) getViewState().invalidateDataSource();
    }

    @Override
    public void onItemClicked(CarTotalInfo carTotalInfo) {
        getViewState().startCarActivity(carTotalInfo.getId());
    }

    @Override
    public void onFabClicked() {
        getViewState().startNewCarActivity();
    }

    @Override
    public void onLoadInitial(int start, int limit,
                              PositionalDataSource.LoadInitialCallback<CarTotalInfo> callback) {
        List<CarTotalInfo> cars = downloadCarTotalInfo(start, limit);
//        Log.e(TAG,"load initial " + cars.size());
        callback.onResult(cars, start);
    }

    @Override
    public void onLoadRange(int start, int limit,
                            PositionalDataSource.LoadRangeCallback<CarTotalInfo> callback) {
        List<CarTotalInfo> cars = downloadCarTotalInfo(start, limit);
//        Log.e(TAG,"load range " + cars.size());
        callback.onResult(cars);
    }

    private List<CarTotalInfo> downloadCarTotalInfo(int start, int limit){
        return repository.downloadCarTotalInfoPageFromDb(start, limit,
                getSortType(),
                getFilterType(), filterStateHelper.getId());
    }

    private String getSortType(){
        if(sortStateHelper.isSortPriceAsc()) return CarDao.SORT_PRICE_ASC;
        else if(sortStateHelper.isSortPriceDesc()) return CarDao.SORT_PRICE_DESC;
        else return CarDao.NO_SORT;
    }

    private String getFilterType(){
        if(filterStateHelper.isFilterByManufacturer()) return CarDao.QUERY_FILTER_BY_MANUFACTURER;
        else if(filterStateHelper.isFilterByModel()) return CarDao.QUERY_FILTER_BY_MODEL;
        else return CarDao.QUERY_NO_FILTER;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repository.setMainPresenter(null);
    }

    public void setUpdateCarResult(int count){
        if(count == 1) {
            getViewState().invalidateDataSource();
            getViewState().showMessage("Saved successfully");
        }
    }

    public void setInsertCarResult(int count){
        if(count > 0) {
            getViewState().invalidateDataSource();
            getViewState().showMessage("Insert successfully");
            getViewState().scrollRecyclerViewPosition(0);
        }
    }

    public void setDeleteCarResult(int count){
        if(count == 1){
            getViewState().invalidateDataSource();
            getViewState().showMessage("Delete successfully");
        }
    }
}
