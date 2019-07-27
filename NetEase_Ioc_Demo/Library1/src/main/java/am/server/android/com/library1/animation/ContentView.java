package am.server.android.com.library1.animation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间: 2019-07-27 20:57
 * 类描述:
 *
 * @author 香瓜
 */
@Target(ElementType.TYPE)//注解作用在类之上
@Retention(RetentionPolicy.RUNTIME)//jvm运行时通过反射获取该注解的值
public @interface ContentView {
    int value();
}
