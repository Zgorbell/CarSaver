package com.example.aprosoft.data.state;

public class FilterStateHelper {
    public static final String FILTER_BY_MANUFACTURER = "FILTER_BY_MANUFACTURER";
    public static final String FILTER_BY_MODEL = "FILTER_BY_MODEL";
    public static final String FILTER_BY_NONE = "FILTER_BY_NONE";
    public static final int FILTER_ID_DEFAULT = -1;
    private String state;
    private int id;
    private int spinnerManufacturerPosition;
    private int spinnerModelPosition;

    public FilterStateHelper() {
        state = FILTER_BY_NONE;
        id = FILTER_ID_DEFAULT;
        spinnerManufacturerPosition = FILTER_ID_DEFAULT;
        spinnerModelPosition = FILTER_ID_DEFAULT;
    }

    public FilterStateHelper setState(String state) {
        this.state = state;
        return this;
    }

    public FilterStateHelper setId(int id) {
        this.id = id;
        return this;
    }

    public int getSpinnerManufacturerPosition() {
        return spinnerManufacturerPosition;
    }

    public FilterStateHelper setSpinnerManufacturerPosition(int spinnerManufacturerPosition) {
        this.spinnerManufacturerPosition = spinnerManufacturerPosition;
        return this;
    }

    public int getSpinnerModelPosition() {
        return spinnerModelPosition;
    }

    public FilterStateHelper setSpinnerModelPosition(int spinnerModelPosition) {
        this.spinnerModelPosition = spinnerModelPosition;
        return this;
    }

    public int getId() {
        return id;
    }

    public boolean isFilterByManufacturer(){
        return FILTER_BY_MANUFACTURER.equals(state);
    }

    public boolean isFilterByModel(){
        return FILTER_BY_MODEL.equals(state);
    }

    public boolean isFilterByNone(){
        return FILTER_BY_NONE.equals(state);
    }
}
