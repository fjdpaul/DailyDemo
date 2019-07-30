package am.server.android.com.buttknifedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import am.server.android.com.annotations.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setText("ButterKnife");
    }
}
