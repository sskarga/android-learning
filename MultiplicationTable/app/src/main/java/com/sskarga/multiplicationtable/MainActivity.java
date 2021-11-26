package com.sskarga.multiplicationtable;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sskarga.multiplicationtable.databinding.ActivityMainBinding;
import com.sskarga.multiplicationtable.dto.OptionResult;
import com.sskarga.multiplicationtable.fragments.AboutFragment;
import com.sskarga.multiplicationtable.fragments.MenuFragment;
import com.sskarga.multiplicationtable.fragments.ResultFragment;
import com.sskarga.multiplicationtable.fragments.TestingFragment;
import com.sskarga.multiplicationtable.interfaces.HasCustomTitle;
import com.sskarga.multiplicationtable.interfaces.Navigator;

public class MainActivity extends AppCompatActivity implements Navigator {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);

        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, new MenuFragment())
                    .commit();
        }

        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks());
    }

    @NonNull
    private FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks() {
        return new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                updateUi();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        updateUi();
        return result;
    }

    private void updateUi() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        // Custom title
        if (fragment instanceof HasCustomTitle) {
            binding.toolbar.setTitle(((HasCustomTitle) fragment).getTitleRes());
        } else {
            binding.toolbar.setTitle(R.string.titleDefault);
        }

        //
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            } else {
                actionBar.setDefaultDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }
    }

    private void launchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                )
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void showTestScreen() {
        launchFragment(new TestingFragment());
    }

    @Override
    public void showAboutScreen() {
        launchFragment(new AboutFragment());
    }

    @Override
    public void showResultScreen(OptionResult options) {
        launchFragment(ResultFragment.newInstance(options));
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void goToMenu() {
        getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void close() {
        finish();
    }
}