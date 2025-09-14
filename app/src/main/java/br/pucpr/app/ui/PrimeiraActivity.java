package br.pucpr.app.ui;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import br.pucpr.app.R;


public class PrimeiraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira);
        setTitle("PrimeiraActivity");
    }
}