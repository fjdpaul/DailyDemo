package comm.example.strugglefu.uitest;

import android.app.Activity;
import android.content.Intent;

/**
 * 创建时间: 2019-12-23 10:58
 * 类描述:
 *
 * @author paul
 */
public class BaseActivity extends Activity {

    protected void startNextActivity(Class nextClass){
        startActivity(new Intent(this, nextClass));
    }
}
