package am.server.android.com.skintest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;

/**
 * 创建时间: 2019-08-14 22:06
 * 类描述: 换肤管理类
 *
 * @author 香瓜
 */
public class SkinManager {

    private static SkinManager skinManager = new SkinManager();

    private Resources resources;

    private Context context;

    private String packName;

    public static SkinManager getInstance(){
        return skinManager;
    }

    public SkinManager() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 根据皮肤apk路劲加载资源
     * @param path
     */
    public void loadSkinAPK(String path){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        packName = packageInfo.packageName;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetManager = assetManager.getClass().getMethod("addAssetPaht",String.class);
            addAssetManager.invoke(addAssetManager, path);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  根据id资源查找 皮肤apk中是否存在，存在返回
     * @param id
     * @return
     */
    public int getColor(int id){
        if (resources == null){
            return id;
        }
        //获取属性值的名字 colorPrimary
        String resourceEntryName = context.getResources().getResourceEntryName(id);
        //获取属性值的类型 color
        String typeName = context.getResources().getResourceTypeName(id);
       //根据名字和类型判断是否匹配
        int identifier = resources.getIdentifier(resourceEntryName, typeName, packName);
        if (identifier == 0){
            return id;
        }
        return resources.getColor(identifier);

    }

    public Drawable getDrawable(int id){
        if (resources == null){
            return ContextCompat.getDrawable(context, id);
        }
        //获取属性值的名字 colorPrimary
        String resourceEntryName = context.getResources().getResourceEntryName(id);
        //获取属性值的类型 color
        String typeName = context.getResources().getResourceTypeName(id);
        //根据名字和类型判断是否匹配
        int identifier = resources.getIdentifier(resourceEntryName, typeName, packName);
        if (identifier == 0){
            return ContextCompat.getDrawable(context, id);
        }
        return resources.getDrawable(identifier);
    }
}
