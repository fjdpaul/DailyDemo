package am.server.android.com.library1.animation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间: 2019-07-27 21:32
 * 类描述:
 *
 * @author 香瓜
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //方法名 setonxxListener
    String listener();

    //监听的对象 View.onxxlistener
    Class<?> listenerType();

    //回调onclick（View view）
    String callBackListener();
}
