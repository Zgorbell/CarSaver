package com.example.aprosoft.ui.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aprosoft.R;
import com.example.aprosoft.data.db.model.BaseModel;

import java.util.List;

public class CarSpinnerAdapter<T extends BaseModel> extends ArrayAdapter<T> {

    public CarSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return setDefaultView(position, parent, android.R.layout.simple_spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return setDefaultView(position, parent, android.R.layout.simple_spinner_dropdown_item);
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }


    private View setDefaultView(int position, @NonNull ViewGroup parent, int layoutId) {
        T baseModel = getItem(position);
        View convertView = LayoutInflater
                .from(getContext()).inflate(layoutId, parent, false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextColor(parent.getResources().getColor(R.color.text_color));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        if (baseModel != null) textView.setText(baseModel.getName());
        return convertView;
    }

    public int getPosition(int id) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i) != null && getItem(i).getId() == id) return i;
        }
        return 0;
    }
}
