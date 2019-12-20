package am.server.android.com.testview;

import android.app.Activity;
import android.content.Intent;

/**
 * 创建时间: 2019-12-20 16:08
 * 类描述:
 *
 * @author paul
 */
public class BaseActivity extends Activity {

    protected void startNextActivity(Class mClass){
        startActivity(new Intent(this, mClass));
    }
}
