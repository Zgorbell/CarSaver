package com.example.aprosoft.ui.main;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprosoft.App;
import com.example.aprosoft.R;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.ui.car.BitmapWorker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarAdapter extends PagedListAdapter<CarTotalInfo, CarAdapter.CarViewHolder> {
    private OnCardItemClickListener listener;

    CarAdapter(@NonNull DiffUtil.ItemCallback<CarTotalInfo> diffUtilCallback,
               OnCardItemClickListener listener) {
        super(diffUtilCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder h, int pos) {
        CarTotalInfo carTotalInfo = getItem(pos);
        if (carTotalInfo != null) {
            String photoPath = carTotalInfo.getPhotoPath();
            if (photoPath != null) {
                Picasso.get().load(new File(photoPath)).fit().centerCrop().into(h.carImageView);
//                try {
//                    BitmapFactory.Options opts = new BitmapFactory.Options();
//                    opts.inSampleSize = 16;
//                    Bitmap bitmap = BitmapFactory.decodeFile(photoPath, opts);
//                    bitmap = BitmapWorker.rotateImage(bitmap, photoPath);
//                    h.carImageView.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }else Picasso.get().load(R.drawable.no_image).fit().centerCrop().into(h.carImageView);

            Context context = App.getContext();
            String text = context.getString(R.string.manufacturer) + " " + carTotalInfo.getManufacturerName();
            h.manufacturer.setText(text);

            text = context.getString(R.string.model) + " " + carTotalInfo.getModelName();
            h.model.setText(text);

            text = context.getString(R.string.price) + " " + String.valueOf(carTotalInfo.getPrice());
            h.price.setText(text);

            text = context.getString(R.string.engine) + " " + carTotalInfo.getEngine();
            h.engine.setText(text);

            text = context.getString(R.string.engine_capacity) + " " + String.valueOf(carTotalInfo.getEngineCapacity());
            h.engineCapacity.setText(text);

            text = context.getString(R.string.body) + " " + carTotalInfo.getBody();
            h.body.setText(text);

            text = context.getString(R.string.transmission) + " " + carTotalInfo.getTransmission();
            h.transmission.setText(text);

            h.id = carTotalInfo.getId();
            h.modelId = carTotalInfo.getModelId();
            h.manufacturerId = carTotalInfo.getManufacturerId();

            h.itemView.setOnClickListener(v -> listener.onCarItemClicked(v, getItem(pos)));
        }
    }

    public interface OnCardItemClickListener {
        void onCarItemClicked(View view, CarTotalInfo carTotalInfo);
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageCar)
        ImageView carImageView;
        @BindView(R.id.textViewManufacturerCar)
        TextView manufacturer;
        @BindView(R.id.textViewModelCar)
        TextView model;
        @BindView(R.id.textViewEngine)
        TextView engine;
        @BindView(R.id.textViewEngineCapacity)
        TextView engineCapacity;
        @BindView(R.id.textViewPrice)
        TextView price;
        @BindView(R.id.textViewTransmission)
        TextView transmission;
        @BindView(R.id.textViewBody)
        TextView body;
        int id;
        int modelId;
        int manufacturerId;

        CarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
