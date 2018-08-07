package com.example.aprosoft.data.state;

public class SortStateHelper {
    public static final int NO_SORT = 0;
    public static final int SORT_PRICE_ASC = 1;
    public static final int SORT_PRICE_DESC = 2;
    private int state;

    public SortStateHelper() {
        state = NO_SORT;
    }

    public boolean isNoSort() {
        return state == NO_SORT;
    }

    public boolean isSortPriceAsc() {
        return state == SORT_PRICE_ASC;
    }

    public boolean isSortPriceDesc() {
        return state == SORT_PRICE_DESC;
    }

    public void setNoSort() {
        state = NO_SORT;
    }

    public void setSortPriceAsc() {
        state = SORT_PRICE_ASC;
    }

    public void setSortPriceDesc() {
        state = SORT_PRICE_DESC;
    }
}
