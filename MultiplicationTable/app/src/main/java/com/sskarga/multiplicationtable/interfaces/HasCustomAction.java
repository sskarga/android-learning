package com.sskarga.multiplicationtable.interfaces;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public interface HasCustomAction {
    CustomAction getCustomAction();
}

class CustomAction {
    @DrawableRes
    Integer iconRes;

    @StringRes
    Integer textRes;

    Runnable onCustomAction;
}