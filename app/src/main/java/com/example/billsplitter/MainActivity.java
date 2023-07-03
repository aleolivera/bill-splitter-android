package com.example.billsplitter;

import android.os.Bundle;
import android.view.Menu;

import com.example.billsplitter.dialogs.AddExpenceDialog;
import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.ui.home.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.billsplitter.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddExpenceDialog.NewAddExpenceDialogListener {

    private SharedViewModel model;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(SharedViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_expences, R.id.navigation_splitted_bill)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        model.getFriends().observe(this, friends -> updateUI());
    }

    private void updateUI(){
        BottomNavigationView nav = findViewById(R.id.nav_view);
        Menu menu = nav.getMenu();

        if(Objects.requireNonNull(model.getFriends().getValue()).size() < 1) {
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        }
        else {
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
        }
    }

    @Override
    public void onNewDialogPositiveClick(Expence expence, int friendIndex) {
        if(expence == null)
            Snackbar.make(findViewById(R.id.container),"Must complete all fields",Snackbar.LENGTH_SHORT).show();
        else
            model.AddExpence(friendIndex,expence);
    }
}