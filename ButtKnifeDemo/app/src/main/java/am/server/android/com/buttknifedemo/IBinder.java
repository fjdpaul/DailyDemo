package am.server.android.com.buttknifedemo;

/**
 * 创建时间: 2019-07-29 22:01
 * 类描述:
 *
 * @author 香瓜
 */
public interface IBinder<T>{

    void bind(T target);
}
