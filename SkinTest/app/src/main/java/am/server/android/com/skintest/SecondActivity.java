package am.server.android.com.skintest;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 创建时间: 2019-08-15 16:42
 * 类描述:
 *
 * @author 香瓜
 */
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.findViewById(R.id.btn_skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
    }

    private void apply() {
        SkinManager.getInstance().loadSkinAPK(Environment.getExternalStorageDirectory() + "/skin.apk");
        apply();
    }
}
