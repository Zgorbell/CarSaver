package com.example.aprosoft.ui.main;

import android.support.v7.util.DiffUtil;

import com.example.aprosoft.data.db.model.CarTotalInfo;

public class CarDiffUtilItemCallback extends DiffUtil.ItemCallback<CarTotalInfo> {

    @Override
    public boolean areItemsTheSame(CarTotalInfo oldItem, CarTotalInfo newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(CarTotalInfo oldItem, CarTotalInfo newItem) {
//        return (oldItem.getBody().equals(newItem.getBody())
//                && oldItem.getEngine().equals(newItem.getEngine())
//                && oldItem.getTransmission().equals(newItem.getTransmission())
//                && oldItem.getEngineCapacity() == newItem.getEngineCapacity()
//                && oldItem.getManufacturerId() == newItem.getManufacturerId()
//                && oldItem.getModelId() == newItem.getModelId()
//                && oldItem.getPrice() == newItem.getPrice())
//                && (oldItem.getPhotoPath() == null && newItem.getPhotoPath() == null
//                || oldItem.getPhotoPath() != null && newItem.getPhotoPath() != null
//                && oldItem.getPhotoPath().equals(newItem.getPhotoPath()));
        return false;
    }
}
