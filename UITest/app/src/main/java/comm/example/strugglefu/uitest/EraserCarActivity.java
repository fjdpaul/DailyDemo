package comm.example.strugglefu.uitest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import comm.example.strugglefu.uitest.view.EraserCarView;

/**
 * 创建时间: 2019-12-23 11:34
 * 类描述:
 *
 * @author paul
 */
public class EraserCarActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EraserCarView(this));
    }
}
