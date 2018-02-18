package uk.co.ribot.androidboilerplate.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.heatmap.HeatMapActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onButtonMap(View view){
        Intent intent = new Intent(getApplicationContext(), HeatMapActivity.class);
        startActivity(intent);
    }

}
