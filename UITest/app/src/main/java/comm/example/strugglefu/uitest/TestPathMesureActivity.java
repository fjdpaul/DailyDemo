package comm.example.strugglefu.uitest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import comm.example.strugglefu.uitest.view.PathMeasureView;

/**
 * 创建时间: 2019-12-23 14:59
 * 类描述: 仿某知名app刷新控件
 *
 * @author paul
 */
public class TestPathMesureActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathMeasureView(this));
    }
}
