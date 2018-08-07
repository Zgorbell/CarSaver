package com.example.aprosoft.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.aprosoft.R;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.ui.car.CarActivity;
import com.example.aprosoft.ui.filter.FilterActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainMvpView, CarAdapter.OnCardItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CAR_ACTIVITY = 1;
    private static final int REQUEST_FILTER_ACTIVITY = 2;
    @InjectPresenter
    MainPresenter mainPresenter;
    @BindView(R.id.toolbarMain)
    Toolbar toolbarMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerViewCars)
    RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private int positionCurrentRecycler;

    public MainActivity() {
        this.carAdapter = new CarAdapter(new CarDiffUtilItemCallback(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(carAdapter);
        fab.setOnClickListener(v -> mainPresenter.onFabClicked());
        prepareAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemFilter:
                mainPresenter.onFilterClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startNewCarActivity() {
//        Log.e(TAG, "start new car activity");
        Intent intent = new Intent(this, CarActivity.class);
        intent.putExtra(CarActivity.CAR_ID, CarActivity.MODE_NEW);
        startActivityForResult(intent, REQUEST_CAR_ACTIVITY);
    }

    @Override
    public void startCarActivity(int id) {
//        Log.e(TAG, "start car activity");
        Intent intent = new Intent(this, CarActivity.class);
        intent.putExtra(CarActivity.CAR_ID, id);
        startActivityForResult(intent, REQUEST_CAR_ACTIVITY);
    }

    @Override
    public void startFilterActivity() {
//        Log.e(TAG, "start filter activity");
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, REQUEST_FILTER_ACTIVITY);
    }

    @Override
    public void onCarItemClicked(View view, CarTotalInfo carTotalInfo) {
//        Log.e(TAG, "on car item clicked ");
        mainPresenter.onItemClicked(carTotalInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_FILTER_ACTIVITY:
                    mainPresenter.onFilterReturn(true);
                    break;
                case REQUEST_CAR_ACTIVITY:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_FILTER_ACTIVITY:
//                    Log.e(TAG, "filter activity return canceled");
                    break;
            }
        }
    }

    @Override
    public void invalidateDataSource() {
//        Log.e(TAG, "invalidate");
        PagedList pagedList = carAdapter.getCurrentList();
        if (pagedList != null) pagedList.getDataSource().invalidate();
    }

    @Override
    public void showMessage(String message) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
        else Toast.makeText(this, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void scrollRecyclerViewPosition(int position) {
        positionCurrentRecycler = position;
        recyclerView.scrollToPosition(position);
    }

    private void prepareAdapter() {
        getPagedLiveDataList().observe(this, carTotalInfos -> {
//            Log.e(TAG, "submit cars in car adapter");
            carAdapter.submitList(carTotalInfos);
        });
    }

    private PagedList.Config getPagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setPrefetchDistance(3)
                .build();
    }

    private LiveData<PagedList<CarTotalInfo>> getPagedLiveDataList() {
        PagedList.Config config = getPagedListConfig();
        return new LivePagedListBuilder<>(new CarDataSourceFactory(), config)
                .build();
    }

    class CarPositionalDataSource extends PositionalDataSource<CarTotalInfo> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<CarTotalInfo> callback) {
            mainPresenter.onLoadInitial(0, params.pageSize, callback);

        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<CarTotalInfo> callback) {
            mainPresenter.onLoadRange(params.startPosition, params.loadSize, callback);
        }
    }

    public class CarDataSourceFactory extends DataSource.Factory<Integer, CarTotalInfo> {
        @Override
        public CarPositionalDataSource create() {
            return new CarPositionalDataSource();
        }
    }

}
