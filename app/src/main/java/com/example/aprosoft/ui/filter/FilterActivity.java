package com.example.aprosoft.ui.filter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.aprosoft.R;
import com.example.aprosoft.data.db.model.BaseModel;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends MvpAppCompatActivity implements FilterMvpView,
        RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = FilterActivity.class.getSimpleName();

    @InjectPresenter
    FilterPresenter filterPresenter;
    @BindView(R.id.spinnerManufacturer)
    Spinner spinnerManufacturer;
    @BindView(R.id.spinnerModel)
    Spinner spinnerModel;
    @BindView(R.id.radioGroupFilter)
    RadioGroup radioGroup;
    @BindView(R.id.radioButtonNone)
    RadioButton radioButtonNone;
    @BindView(R.id.radioButtonManufacturer)
    RadioButton radioButtonManufacturer;
    @BindView(R.id.radioButtonModel)
    RadioButton radioButtonModel;
    @BindView(R.id.radioButtonNoSort)
    RadioButton radioButtonNoSort;
    @BindView(R.id.radioButtonSortAsc)
    RadioButton radioButtonSortAsc;
    @BindView(R.id.radioButtonSortDesc)
    RadioButton radioButtonSortDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        ButterKnife.bind(this);
        radioGroup.setOnCheckedChangeListener(this);
        spinnerManufacturer.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSave:
                setSaveInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonNone:
                Log.e(TAG, "radio button none checked");
                filterPresenter.onRadioNoneChecked();
                break;
            case R.id.radioButtonManufacturer:
                Log.e(TAG, "radio button manufacturer checked");
                filterPresenter.onRadioManufacturerChecked();
                break;
            case R.id.radioButtonModel:
                Log.e(TAG, "radio button model checked");
                filterPresenter.onRadioModelChecked();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int manufacturerId = ((Manufacturer) spinnerManufacturer.getSelectedItem()).getId();
        filterPresenter.onManufacturerSpinnerItemSelected(manufacturerId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void setRadioManufacturerChecked(boolean status) {
        radioButtonManufacturer.setChecked(status);
    }

    @Override
    public void setRadioModelChecked(boolean status) {
        radioButtonModel.setChecked(status);
    }

    @Override
    public void setRadioNoneChecked(boolean status) {
        radioButtonNone.setChecked(status);
    }

    @Override
    public void setRadioNoSortChecked(boolean status) {
        radioButtonNoSort.setChecked(status);
    }

    @Override
    public void setRadioSortAscChecked(boolean status) {
        radioButtonSortAsc.setChecked(status);
    }

    @Override
    public void setRadioSortDescChecked(boolean status) {
        radioButtonSortDesc.setChecked(status);
    }

    @Override
    public void setSpinnerManufacturerVisible(int status) {
        spinnerManufacturer.setVisibility(status);

    }

    @Override
    public void setSpinnerModelVisible(int status) {
        spinnerModel.setVisibility(status);

    }

    @Override
    public void setSpinnerManufacturer(List<Manufacturer> manufacturers, int position) {
        Log.e(TAG, "set manufacturer spinner ");
        CarSpinnerAdapter<Manufacturer> adapter = new CarSpinnerAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                manufacturers);
        spinnerManufacturer.setAdapter(adapter);
        spinnerManufacturer.setSelection(position);
    }

    @Override
    public void setSpinnerModel(List<Model> models, int position) {
        Log.e(TAG, "set model spinner ");
        CarSpinnerAdapter<Model> adapter = new CarSpinnerAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                models);
        spinnerModel.setAdapter(adapter);
        spinnerModel.setSelection(position);
    }

    @Override
    public void setResultActivity() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void setSaveInfo() {
        int manufacturerId = 0;
        int modelId = 0;
        int manufacturerPosition = 0;
        int modelPosition = 0;
        if (spinnerModel.getSelectedItem() != null) {
            modelId = ((BaseModel) spinnerModel.getSelectedItem()).getId();
            modelPosition = spinnerModel.getSelectedItemPosition();
        }
        if ((spinnerManufacturer.getSelectedItem()) != null) {
            manufacturerId = ((BaseModel) spinnerManufacturer.getSelectedItem()).getId();
            manufacturerPosition = spinnerManufacturer.getSelectedItemPosition();
        }

        filterPresenter.onSaveClicked(radioButtonNone.isChecked(),
                manufacturerId, manufacturerPosition, radioButtonManufacturer.isChecked(),
                modelId, modelPosition, radioButtonModel.isChecked(),
                radioButtonNoSort.isChecked(),
                radioButtonSortAsc.isChecked(),
                radioButtonSortDesc.isChecked());
    }
}
