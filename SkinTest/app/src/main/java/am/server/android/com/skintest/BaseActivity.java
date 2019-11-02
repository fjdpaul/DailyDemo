package am.server.android.com.skintest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * 创建时间: 2019-08-14 22:50
 * 类描述:
 *
 * @author 香瓜
 */
public class BaseActivity extends AppCompatActivity {

    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().setContext(this);
        skinFactory = new SkinFactory();
        //监听xml生成过程
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), skinFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        skinFactory.apply();
    }
}
