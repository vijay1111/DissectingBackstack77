package com.example.vijay.dissectingbackstack7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textview);
        fragmentManager = getSupportFragmentManager();
        textView.setText("fragment count in backstack =>" + fragmentManager.getBackStackEntryCount());

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                textView.setText("Fragment count in back stack: " + fragmentManager.getBackStackEntryCount());
                StringBuilder backstackEntryMessage = new StringBuilder("Current status of fragment transaction back stack: " + fragmentManager.getBackStackEntryCount() + "\n");
                for (int index = (fragmentManager.getBackStackEntryCount() - 1); index >= 0; index--) {
                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(index);
                    backstackEntryMessage.append(entry.getName() + "\n");
                }
                Log.e("check ", backstackEntryMessage.toString());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });
    }

    private void addFragment() {
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment instanceof BlankFragment1) {
            fragment = new BlankFragment1();
        } else if (fragment instanceof BlankFragment2) {
            fragment = new BlankFragment2();
        } else if (fragment instanceof BlankFragment3) {
            fragment = new BlankFragment3();
        } else {
            fragment = new BlankFragment1();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, "demo fragment");
        fragmentTransaction.addToBackStack("Add " + fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment!=null){
            fragmentTransaction.remove(fragment);
            fragmentTransaction.addToBackStack("Remove "+fragment.toString());
            fragmentTransaction.commit();
        }else{
            super.onBackPressed();
        }
    }
}
