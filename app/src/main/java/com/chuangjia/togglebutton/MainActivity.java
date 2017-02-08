package com.chuangjia.togglebutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton= (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setBackground(R.drawable.toggle_off);
        toggleButton.setMoveBackground(R.drawable.toggle);
        toggleButton.setState(ToggleButton.ToggleState.Close);
        toggleButton.setOnToggleChangeListener(new ToggleButton.OnToggleChangeListener() {
            @Override
            public void onToggleChangeListener(ToggleButton.ToggleState state) {
                Toast.makeText(MainActivity.this, state== ToggleButton.ToggleState.Open?"开启":"关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
