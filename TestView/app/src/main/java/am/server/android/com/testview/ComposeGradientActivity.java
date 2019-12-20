package am.server.android.com.testview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import am.server.android.com.testview.view.ComposeGradientView;

/**
 * 创建时间: 2019-12-20 16:04
 * 类描述: 组合渲染
 *
 * @author paul
 */
public class ComposeGradientActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ComposeGradientView(this));
    }
}
