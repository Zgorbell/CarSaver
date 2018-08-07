package com.example.aprosoft.ui.filter;

public interface FilterMvpPresenter {

    void onRadioNoneChecked();

    void onRadioManufacturerChecked();

    void onRadioModelChecked();

    void onSaveClicked(boolean noneRadio,
                       int manufacturerId, int manufacturerPosition, boolean manufacturerRadio,
                       int modelId, int modelPosition, boolean modelRadio,
                       boolean noSort, boolean sortAsc, boolean sortDesc);

    void onManufacturerSpinnerItemSelected(int manufacturerId);
}
