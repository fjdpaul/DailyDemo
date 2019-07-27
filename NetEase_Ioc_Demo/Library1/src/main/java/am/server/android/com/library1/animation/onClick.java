package am.server.android.com.library1.animation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间: 2019-07-27 21:30
 * 类描述:
 *
 * @author 香瓜
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listener = "setOnClickListener",listenerType = View.OnClickListener.class, callBackListener = "onClick")
public @interface onClick {
    int[] value();
}
