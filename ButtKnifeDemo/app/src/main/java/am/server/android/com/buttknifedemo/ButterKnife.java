package am.server.android.com.buttknifedemo;

/**
 * 创建时间: 2019-07-30 10:59
 * 类描述:
 *  利用发射拿到 我们为MainActivity生成的文件，生成的文件实现接口IBinder
 *  然后调用IBinder.bind方法
 *
 * @author 香瓜
 */
public class ButterKnife{

    public static void bind(MainActivity activity){
        String activityName = activity.getClass().getName() + "_ViewBinding";

        try {
            Class<?> aClass = Class.forName(activityName);
            IBinder iBinder = (IBinder) aClass.newInstance();
            iBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
