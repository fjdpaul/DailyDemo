package am.server.android.com.library1;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import am.server.android.com.library1.animation.ContentView;
import am.server.android.com.library1.animation.EventBase;
import am.server.android.com.library1.animation.InjectView;

/**
 * 创建时间: 2019-07-27 20:52
 * 类描述:
 *
 * @author 香瓜
 */
public class InjectManager {
    public static void inject(Activity activity) {

        //布局注入
        injectLayout(activity);

        //控件注入
        injectView(activity);

        //事件注入
        injectEvent(activity);


    }

    private static void injectEvent(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){
            //获取每个方法的注解
            Annotation[] animations = method.getAnnotations();
            for (Annotation annotation : animations){
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //事件的三个重要成员
                    String listener = eventBase.listener();
                    Class<?> listenerType = eventBase.listenerType();
                    String callListener = eventBase.callBackListener();

                    try {
                        Method valueMethod = annotationType.getMethod("value");
                        int[] ids = (int[]) valueMethod.invoke(annotation);
                        ListenerInvoHandler invoHandler = new ListenerInvoHandler(activity);
                        invoHandler.addMethod(callListener,method);
                        Object li = Proxy.newProxyInstance(listenerType.getClassLoader(),  new Class[]{listenerType}, invoHandler);
                        for (int id : ids) {
                            View view = activity.findViewById(id);
                            Method setter = view.getClass().getMethod(listener,listenerType);
                            setter.invoke(view, li);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectView(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null){
                int id = injectView.value();
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, id);
                    //第一种
                    //属性的值赋给控件，在当前的activity
                    field.setAccessible(true);//设置当前属性可以访问，哪怕是private
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void injectLayout(Activity activity) {

        //获取类
        Class<? extends Activity> clazz = activity.getClass();

        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null){
            int layoutId = contentView.value();
            try {
               Method method =  clazz.getMethod("setContentView", int.class);
               method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
