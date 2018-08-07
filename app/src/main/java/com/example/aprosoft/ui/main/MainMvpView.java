package com.example.aprosoft.ui.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface MainMvpView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void startNewCarActivity();

    @StateStrategyType(SkipStrategy.class)
    void startCarActivity(int id);

    @StateStrategyType(SkipStrategy.class)
    void startFilterActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void invalidateDataSource();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void scrollRecyclerViewPosition(int position);
}
