package com.esp1617.albertomoretto.foodify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//Activity che viene lanciata quando si sceglie un cibo dal menù di FoodActivity
// https://www.youtube.com/watch?v=fn5OlqQuOCk

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Il layout che viene caricato varia a seconda del tipo di cibo che si sceglie
        //Creare diversi layout per diversi cibi
        setContentView(R.layout.activity_pop_up);
    }
}
