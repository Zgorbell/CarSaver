package com.example.aprosoft.ui.car;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.aprosoft.R;
import com.example.aprosoft.data.db.model.BaseModel;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;
import com.example.aprosoft.ui.car.delete.DeleteDialogFragment;
import com.example.aprosoft.ui.filter.CarSpinnerAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarActivity extends MvpAppCompatActivity implements CarMvpView,
        AdapterView.OnItemSelectedListener, DeleteDialogFragment.DialogListener {
    private static final String TAG = CarActivity.class.getSimpleName();
    private static final String KEY_TEMPORARY_PHOTO = "KEY_TEMPORARY_PHOTO";
    private static final String KEY_CAR_TOTAL_INFO = "KEY_CAR_TOTAL_INFO";
    private static final String KEY_MODE = "KEY_MODE";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 0;
    public static final int MODE_VIEW = 0;
    public static final int MODE_EDIT = 1;
    public static final int MODE_NEW = -1;
    //    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";
    public static final String CAR_ID = "CAR_ID";

    @InjectPresenter
    CarPresenter carPresenter;
    @BindView(R.id.imageViewCar)
    ImageView imageViewCar;
    @BindView(R.id.spinnerManufacturer)
    Spinner spinnerManufacturer;
    @BindView(R.id.spinnerModel)
    Spinner spinnerModel;
    @BindView(R.id.editTextPrice)
    EditText editTextPrice;
    @BindView(R.id.editTextEngine)
    EditText editTextEngine;
    @BindView(R.id.editTextEngineCapacity)
    EditText editTextEngineCapacity;
    @BindView(R.id.editTextBody)
    EditText editTextBody;
    @BindView(R.id.editTextTransmission)
    EditText editTextTransmission;
    private MenuItem menuItemSave;
    private MenuItem menuItemEdit;
    private MenuItem menuItemAddPhoto;
    private MenuItem menuItemDelete;
    private int mode;
    private CarTotalInfo carTotalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        spinnerManufacturer.setOnItemSelectedListener(this);
        if (savedInstanceState == null) initial();
    }

    @Override
    public void onBackPressed() {
        if (mode == MODE_EDIT) initial();
        else super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        Log.e(TAG, "on save instance state");
        if (temporaryPhotoFile != null) {
            outState.putString(KEY_TEMPORARY_PHOTO, temporaryPhotoFile.getAbsolutePath());
            outState.putParcelable(KEY_CAR_TOTAL_INFO, createCarTotalInfo());
        }else if(carTotalInfo != null && carTotalInfo.getPhotoPath() != null)
            outState.putParcelable(KEY_CAR_TOTAL_INFO, createCarTotalInfo(carTotalInfo.getPhotoPath()));
        else outState.putParcelable(KEY_CAR_TOTAL_INFO, createCarTotalInfo());
        outState.putInt(KEY_MODE, mode);

        carPresenter.onSavedInstanceState();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.e(TAG,"on restore instance state");
        String path = savedInstanceState.getString(KEY_TEMPORARY_PHOTO);
        if (path != null) {
            temporaryPhotoFile = new File(path);
            setImageView(temporaryPhotoFile);
        }
        carPresenter.onRestoreInstanceState(savedInstanceState.getInt(KEY_MODE),
                savedInstanceState.getParcelable(KEY_CAR_TOTAL_INFO));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "on destroy");
        carPresenter.onDestroyed(temporaryPhotoFile);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.e(TAG, "on create options menu ");
        getMenuInflater().inflate(R.menu.edit_car_menu, menu);
        menuItemSave = menu.findItem(R.id.menuItemSave);
        menuItemEdit = menu.findItem(R.id.menuItemEdit);
        menuItemAddPhoto = menu.findItem(R.id.menuItemAddPhoto);
        menuItemDelete = menu.findItem(R.id.menuItemDelete);
        changeMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemEdit:
                carPresenter.onItemEditSelected();
                return true;
            case R.id.menuItemSave:
                carPresenter.onItemSaveSelected(mode, createCarTotalInfo(
                        savePhotoFileToStorage(getIntent().getIntExtra(CAR_ID, 0))));
                return true;
            case R.id.menuItemAddPhoto:
                carPresenter.onAddPhotoClicked();
                return true;
            case R.id.menuItemDelete:
                showDeleteDialogFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE_CAPTURE:
                    carPresenter.onPhotoActivityResult(true, temporaryPhotoFile);
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE_CAPTURE:
                    carPresenter.onPhotoActivityResult(false, temporaryPhotoFile);
                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int manufacturerId = ((Manufacturer) spinnerManufacturer.getSelectedItem()).getId();
        carPresenter.onManufacturerSpinnerItemSelected(manufacturerId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void setCarData(CarTotalInfo carData) {
        carTotalInfo = carData;
        if (carData.getPhotoPath() != null) {
            Log.e(" d" , carData.getPhotoPath());
            setImageView(new File(carData.getPhotoPath()));
        }
        editTextBody.setText(carData.getBody());
        editTextEngine.setText(carData.getEngine());
        editTextTransmission.setText(carData.getTransmission());
        editTextEngineCapacity.setText(String.valueOf(carData.getEngineCapacity()));
        editTextPrice.setText(String.valueOf(carData.getPrice()));
        carPresenter.onSetDataComplete(carData.getManufacturerId(), carData.getModelId());
    }

    @Override
    public void setSpinnerManufacturer(List<Manufacturer> manufacturers, int idManufacturer) {
//        Log.e(TAG, "set manufacturer spinner " + manufacturers.size() + " " + idManufacturer);
        CarSpinnerAdapter<Manufacturer> adapter = new CarSpinnerAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                manufacturers);
        spinnerManufacturer.setAdapter(adapter);
        spinnerManufacturer.setSelection(adapter.getPosition(idManufacturer));
    }

    @Override
    public void setSpinnerModel(List<Model> models, int idModel) {
//        Log.e(TAG, "set model spinner " + models.size() + " " + idModel);
        CarSpinnerAdapter<Model> adapter = new CarSpinnerAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                models);
        spinnerModel.setAdapter(adapter);
        spinnerModel.setSelection(adapter.getPosition(idModel));
    }

    @Override
    public void changeMode(int mode) {
        this.mode = mode;
//        Log.e(TAG, "change mode to " + mode);
        if (mode == MODE_VIEW) enableCarData(false);
        else if (mode == MODE_EDIT) enableCarData(true);
    }

    @Override
    public void showMessage(String message) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Snackbar.make(imageViewCar, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setResult() {
//        Log.e(TAG, "set result");
        setResult(RESULT_OK, new Intent());
        finish();
    }

    private void initial() {
        Intent intent = getIntent();
        if (getIntent() != null) carPresenter.onCreate(intent.getIntExtra(CAR_ID, 0));
    }

    private void changeMenu() {
        if (mode == MODE_VIEW) {
            menuItemEdit.setVisible(true);
            menuItemDelete.setVisible(true);
            menuItemSave.setVisible(false);
            menuItemAddPhoto.setVisible(false);
        } else if (mode == MODE_EDIT || mode == MODE_NEW) {
            menuItemEdit.setVisible(false);
            menuItemDelete.setVisible(false);
            menuItemSave.setVisible(true);
            menuItemAddPhoto.setVisible(true);
        }
    }

    private void enableCarData(boolean status) {
        editTextBody.setEnabled(status);
        editTextEngine.setEnabled(status);
        editTextTransmission.setEnabled(status);
        editTextEngineCapacity.setEnabled(status);
        editTextPrice.setEnabled(status);
        spinnerManufacturer.setEnabled(status);
        spinnerModel.setEnabled(status);
        invalidateOptionsMenu();
    }

    private CarTotalInfo createCarTotalInfo(String filePath) {
        if(filePath != null) Log.e(TAG, filePath);
        return new CarTotalInfo(getIntent().getIntExtra(CAR_ID, 0),
                getDouble(editTextPrice),
                filePath,
                getString(editTextTransmission), getString(editTextBody), getString(editTextEngine),
                getDouble(editTextEngineCapacity),
                ((BaseModel) spinnerModel.getSelectedItem()).getName(),
                ((BaseModel) spinnerModel.getSelectedItem()).getId(),
                ((BaseModel) spinnerManufacturer.getSelectedItem()).getName(),
                ((BaseModel) spinnerManufacturer.getSelectedItem()).getId());
    }

    private CarTotalInfo createCarTotalInfo(){
        return createCarTotalInfo(null);
    }

    @Nullable
    private String savePhotoFileToStorage(long id) {
        if( id == -1) id = System.currentTimeMillis() + new Random().nextInt();
        if (temporaryPhotoFile != null)
            return BitmapWorker.saveImageToInternalStorage(
                    temporaryPhotoFile, id + "photo").getAbsolutePath();
        else if(carTotalInfo!= null && carTotalInfo.getPhotoPath() != null){
            return carTotalInfo.getPhotoPath();
        } else return null;
    }

    private String getString(TextView textView) {
        return textView.getText().toString();
    }

    private Double getDouble(TextView textView) {
        String string = textView.getText().toString();
        if (string != null && !string.equals("")) return Double.valueOf(string);
        else return 0d;
    }

    @Override
    public void startCameraActivity() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                if (createTemporaryFile() != null)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(temporaryPhotoFile));
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
    }

    @Override
    public void setImageView(File photoFile) {
        Log.e(TAG, "set image view");
        if (photoFile != null) {
            Picasso.get().load(photoFile).fit().centerCrop().into(imageViewCar);
        }
    }

    @Override
    public void deleteTemporaryPhotoFile() {
        if (temporaryPhotoFile != null)
            Log.e(TAG, "temporary photo file was deleted : " + temporaryPhotoFile.delete());
    }

    private File temporaryPhotoFile;

    private File createTemporaryFile() {
        String imageFileName = "JPEG_TEMP_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            if (temporaryPhotoFile != null) temporaryPhotoFile.delete();
            temporaryPhotoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
//            Log.e(TAG, "can not create temp file " + e.getMessage());
        }
        return temporaryPhotoFile;
    }

    @Override
    public void buttonPressed(int buttonId) {
        if (buttonId == DeleteDialogFragment.BUTTON_OK)
            carPresenter.onDeleteItemSelected(createCarTotalInfo());
    }

    private void showDeleteDialogFragment() {
        new DeleteDialogFragment().show(getSupportFragmentManager(), DeleteDialogFragment.TAG);
    }
}
