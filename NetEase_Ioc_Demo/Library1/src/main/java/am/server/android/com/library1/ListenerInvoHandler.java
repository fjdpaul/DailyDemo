package am.server.android.com.library1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间: 2019-07-27 22:01
 * 类描述:
 *
 * @author 香瓜
 */
public class ListenerInvoHandler implements InvocationHandler {

    //需要拦截MainActivity中的某些方法
    private Object target;

    private Map<String, Method> methodMap = new HashMap<>();

    public ListenerInvoHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null){
            String methodName = method.getName();
            method = methodMap.get(methodName);
            if (method != null){
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     *
     * @param methodName 需要拦截的方法
     * @param method 自定义的方法
     */
    public void addMethod(String methodName, Method method){
        methodMap.put(methodName, method);
    }

}
