package com.sskarga.multiplicationtable.interfaces;

import com.sskarga.multiplicationtable.dto.OptionResult;

public interface Navigator {
    void showTestScreen();
    void showAboutScreen();
    void showResultScreen(OptionResult options);
    void goBack();
    void goToMenu();
    void close();
}
