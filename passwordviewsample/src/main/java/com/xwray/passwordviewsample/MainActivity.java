package com.xwray.passwordviewsample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.xwray.passwordview.PasswordView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PasswordView passwordView = (PasswordView) findViewById(R.id.password);
        TextInputLayout passwordViewTIL = (TextInputLayout) findViewById(R.id.password_til);

        PasswordView passwordViewWithStrikeThrough = (PasswordView) findViewById(R.id.password_strike);
        TextInputLayout passwordViewWithStrikeThroughTIL = (TextInputLayout) findViewById(R.id.password_strike_til);

        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        passwordView.setTypeface(roboto);
        passwordViewTIL.setTypeface(roboto);
        passwordViewWithStrikeThrough.setTypeface(roboto);
        passwordViewWithStrikeThroughTIL.setTypeface(roboto);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
