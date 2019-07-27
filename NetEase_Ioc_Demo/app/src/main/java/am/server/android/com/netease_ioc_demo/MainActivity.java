package am.server.android.com.netease_ioc_demo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import am.server.android.com.library1.animation.ContentView;
import am.server.android.com.library1.animation.InjectView;
import am.server.android.com.library1.animation.onClick;
import am.server.android.com.netease_ioc_demo.base.BaseActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tv)
    private TextView textView;
    @Override
    protected void onResume() {
        super.onResume();
        //TextView textView = this.findViewById(R.id.tv);
        Toast.makeText(this,textView.getText().toString(),Toast.LENGTH_SHORT).show();

    }

    @onClick(R.id.tv)
    public void onc(View view){
        Toast.makeText(this,"点击陈宫",Toast.LENGTH_SHORT).show();
    }
}
