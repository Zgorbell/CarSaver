package com.example.aprosoft.ui.filter;

import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aprosoft.App;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;
import com.example.aprosoft.data.state.FilterStateHelper;
import com.example.aprosoft.data.state.SortStateHelper;
import com.example.aprosoft.ui.base.Repository;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FilterPresenter extends MvpPresenter<FilterMvpView> implements FilterMvpPresenter {
    private static final String TAG = FilterPresenter.class.getSimpleName();
    @Inject
    Repository repository;
    @Inject
    FilterStateHelper filterStateHelper;
    @Inject
    SortStateHelper sortStateHelper;

    FilterPresenter() {
        App.getApplicationComponent().injectPresenter(this);
        repository.setFilterPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadSortState();
        loadFilterState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repository.setFilterPresenter(null);
    }

    @Override
    public void onRadioNoneChecked() {
        getViewState().setSpinnerManufacturerVisible(View.GONE);
        getViewState().setSpinnerModelVisible(View.GONE);
    }

    @Override
    public void onRadioManufacturerChecked() {
        repository.downloadManufacturers();
        repository.downloadModels();
        getViewState().setSpinnerManufacturerVisible(View.VISIBLE);
        getViewState().setSpinnerModelVisible(View.GONE);
    }

    @Override
    public void onRadioModelChecked() {
        repository.downloadManufacturers();
        repository.downloadModels();
        getViewState().setSpinnerManufacturerVisible(View.VISIBLE);
        getViewState().setSpinnerModelVisible(View.VISIBLE);
    }

    @Override
    public void onManufacturerSpinnerItemSelected(int manufacturerId) {
        repository.downloadModels(manufacturerId);
    }

    @Override
    public void onSaveClicked(boolean noneRadio,
                              int manufacturerId, int manufacturerPosition, boolean manufacturerRadio,
                              int modelId, int modelPosition, boolean modelRadio,
                              boolean noSort, boolean sortAsc, boolean sortDesc) {
        Log.e(TAG, "set result");
        if (noneRadio)
            filterStateHelper.setState(FilterStateHelper.FILTER_BY_NONE);
        else if (modelRadio)
            filterStateHelper.setState(FilterStateHelper.FILTER_BY_MODEL)
                    .setId(modelId)
                    .setSpinnerModelPosition(modelPosition)
                    .setSpinnerManufacturerPosition(manufacturerPosition);
        else if (manufacturerRadio)
            filterStateHelper.setState(FilterStateHelper.FILTER_BY_MANUFACTURER)
                    .setId(manufacturerId)
                    .setSpinnerManufacturerPosition(manufacturerPosition);
        saveSortState(noSort, sortAsc, sortDesc);

    }

    public void setManufacturerSpinner(List<Manufacturer> manufacturers) {
        getViewState().setSpinnerManufacturer(manufacturers, filterStateHelper.getSpinnerManufacturerPosition());
    }

    public void setModelSpinner(List<Model> models) {
        getViewState().setSpinnerModel(models, filterStateHelper.getSpinnerModelPosition());
    }

    private void saveSortState(boolean noSort, boolean sortAsc, boolean sortDesc){
        if (noSort) sortStateHelper.setNoSort();
        else if (sortAsc) sortStateHelper.setSortPriceAsc();
        else if (sortDesc) sortStateHelper.setSortPriceDesc();
        getViewState().setResultActivity();
    }

    private void loadSortState() {
        if (sortStateHelper.isNoSort()) getViewState().setRadioNoSortChecked(true);
        else if (sortStateHelper.isSortPriceAsc()) getViewState().setRadioSortAscChecked(true);
        else if (sortStateHelper.isSortPriceDesc()) getViewState().setRadioSortDescChecked(true);
    }

    private void loadFilterState() {
        if (filterStateHelper.isFilterByNone()) {
            prepareNoneFilter();
        } else if (filterStateHelper.isFilterByManufacturer()) {
            prepareNoneFilter();
            prepareManufacturerFilter();
        } else if (filterStateHelper.isFilterByModel()) {
            prepareNoneFilter();
            prepareModelFilter();
        }
    }

    private void prepareNoneFilter(){
        repository.downloadManufacturers();
        repository.downloadModels();
        getViewState().setRadioNoneChecked(true);
    }

    private void prepareManufacturerFilter(){
        getViewState().setRadioManufacturerChecked(true);
    }

    private void prepareModelFilter(){
        getViewState().setRadioModelChecked(true);
    }
}
