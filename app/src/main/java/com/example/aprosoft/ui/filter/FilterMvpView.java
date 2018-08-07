package com.example.aprosoft.ui.filter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;

import java.util.List;

interface FilterMvpView extends MvpView{

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerManufacturerVisible(int status);

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerModelVisible(int status);

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerManufacturer(List<Manufacturer> manufacturers, int position);

    @StateStrategyType(SkipStrategy.class)
    void setSpinnerModel(List<Model> models, int position);

    @StateStrategyType(SkipStrategy.class)
    void setResultActivity();

    @StateStrategyType(SkipStrategy.class)
    void setRadioManufacturerChecked(boolean status);

    @StateStrategyType(SkipStrategy.class)
    void setRadioModelChecked(boolean status);

    @StateStrategyType(SkipStrategy.class)
    void setRadioNoneChecked(boolean status);

    @StateStrategyType(SkipStrategy.class)
    void setRadioNoSortChecked(boolean status);

    @StateStrategyType(SkipStrategy.class)
    void setRadioSortAscChecked(boolean status);

    @StateStrategyType(SkipStrategy.class)
    void setRadioSortDescChecked(boolean status);
}
