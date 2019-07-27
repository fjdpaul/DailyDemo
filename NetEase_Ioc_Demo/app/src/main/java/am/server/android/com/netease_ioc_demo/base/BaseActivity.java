package am.server.android.com.netease_ioc_demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import am.server.android.com.library1.InjectManager;

/**
 * 创建时间: 2019-07-27 20:53
 * 类描述:
 *
 * @author 香瓜
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }
}
