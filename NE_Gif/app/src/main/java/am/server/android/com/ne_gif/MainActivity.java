package am.server.android.com.ne_gif;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private GifNDKDecoder gifNDKDecoder;

    Bitmap bitmap;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        imageView = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        RxPermissions  rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                } else {
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void ndkLoadGif(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "demo.gif");
        gifNDKDecoder = GifNDKDecoder.load(file.getAbsolutePath());
        int width = GifNDKDecoder.getWidth(gifNDKDecoder.getGifPoint());
        int height = GifNDKDecoder.getHeight(gifNDKDecoder.getGifPoint());
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int nextTime = gifNDKDecoder.updateFrame(bitmap, gifNDKDecoder.getGifPoint());
        handler.sendEmptyMessageDelayed(1, nextTime);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int nextTime = gifNDKDecoder.updateFrame(bitmap, gifNDKDecoder.getGifPoint());
            handler.sendEmptyMessageDelayed(1, nextTime);
            imageView.setImageBitmap(bitmap);
        }
    };
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
