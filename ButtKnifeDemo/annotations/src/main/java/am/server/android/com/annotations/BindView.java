package am.server.android.com.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间: 2019-07-29 21:39
 * 类描述:
 *
 * @author 香瓜
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)//定义注解的生命周期
public @interface BindView {
    int value();
}
