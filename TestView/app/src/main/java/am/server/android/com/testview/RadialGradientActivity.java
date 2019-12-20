package am.server.android.com.testview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import am.server.android.com.testview.view.RadialGradientView;

/**
 * 创建时间: 2019-12-20 16:04
 * 类描述: 环形渲染
 *
 * @author paul
 */
public class RadialGradientActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RadialGradientView(this));
    }
}
