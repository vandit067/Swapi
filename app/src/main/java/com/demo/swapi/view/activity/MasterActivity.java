package com.demo.swapi.view.activity;

import android.os.Bundle;

import com.demo.swapi.R;
import com.demo.swapi.view.fragment.MasterFragment;

public class MasterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
    }

    @Override
    protected void setUp() {
        getSupportFragmentManager().beginTransaction().add(R.id.activity_master_container,
                MasterFragment.newInstance()).commit();
    }
}
