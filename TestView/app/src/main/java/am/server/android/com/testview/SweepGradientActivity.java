package am.server.android.com.testview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import am.server.android.com.testview.view.SweepGradientView;

/**
 * 创建时间: 2019-12-20 16:04
 * 类描述: 扫描渲染
 *
 * @author paul
 */
public class SweepGradientActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SweepGradientView(this));
    }
}
