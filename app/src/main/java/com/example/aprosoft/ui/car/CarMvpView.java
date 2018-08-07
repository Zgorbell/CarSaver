package com.example.aprosoft.ui.car;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;

import java.io.File;
import java.util.List;

interface CarMvpView extends MvpView  {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void changeMode(int mode);

    @StateStrategyType(SkipStrategy.class)
    void setCarData(CarTotalInfo carData);

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerManufacturer(List<Manufacturer> manufacturers, int idManufacturer);

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerModel(List<Model> models, int idModel);

    @StateStrategyType(SkipStrategy.class)
    void startCameraActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setImageView(File photofile);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void deleteTemporaryPhotoFile();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void setResult();
}
