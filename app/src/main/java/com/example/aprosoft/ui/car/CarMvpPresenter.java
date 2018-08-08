package com.example.aprosoft.ui.car;

import com.example.aprosoft.data.db.model.CarTotalInfo;

import java.io.File;

interface CarMvpPresenter  {
    void onCreate(int carTotalInfoId);

    void onItemEditSelected();

    void onItemSaveSelected(int mode, CarTotalInfo carTotalInfo);

    void onDeleteItemSelected(CarTotalInfo carTotalInfo);

    void onSetDataComplete(int manufacturerId, int modelId);

    void onManufacturerSpinnerItemSelected(int manufacturerId);

    void onAddPhotoClicked();

    void onPhotoActivityResult(boolean status, File photoFile);

    void onSavedInstanceState();

    void onDestroyed(File file);

    void onRestoreInstanceState(int mode, CarTotalInfo carTotalInfo);
}
