package am.server.android.com.testview;

import android.os.Bundle;
import android.view.View;

/**
 * 创建时间: 2019-12-20 14:45
 * 类描述: 渲染器探索
 *
 * @author paul
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void linear(View view) {
       startNextActivity(LinearGradientActivity.class);
    }

    public void radial(View view) {
        startNextActivity(RadialGradientActivity.class);
    }

    public void sweep(View view) {
        startNextActivity(SweepGradientActivity.class);
    }

    public void bitmap(View view) {
        startNextActivity(BitmapGradientActivity.class);
    }

    public void compose(View view) {
        startNextActivity(ComposeGradientActivity.class);
    }
}
