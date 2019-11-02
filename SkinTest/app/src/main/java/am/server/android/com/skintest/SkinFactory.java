package am.server.android.com.skintest;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2019-08-14 22:51
 * 类描述:
 *
 * @author 香瓜
 */
public class SkinFactory implements LayoutInflater.Factory2 {

    //换肤容器
    List<SkinView> list = new ArrayList<>();

    private static final String[] prxfixList = {
        "android.widget",
        "android.view",
        "android.webkit"
    };
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 过滤出自己需要的view
        View view = null;
        if (name.contains(".")){
            //自定义view
            view = onCreateView(name, context, attrs);
        } else {
            for (String s : prxfixList) {
                view = onCreateView(s +name, context, attrs);
                if (view != null){
                    break;
                }
            }
        }
        //收集所有需要换肤的控件
        if (view != null){
            parseView(view, name, attrs);
        }

        return view;
    }

    public void apply(){
        for (SkinView skinView : list) {
            skinView.apply();
        }
    }

    private void parseView(View view, String name, AttributeSet attrs) {
        List<SkinItem> itemList = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++){
             //属性的名字
            String attributeName = attrs.getAttributeName(i);
            //获取到属性id
            String attributeValue = attrs.getAttributeValue(i);
            //判断每条属性是否包含 background textColor src color

            if (attributeName.contains("background") || attributeName.contains("textColor") ||
                    attributeName.contains("src") || attributeName.contains("color") ){

                //获取资源id 去掉@
                int resId = Integer.parseInt(attributeValue.substring(1));
                //获取属性值的类型
                String resourceTypeName = view.getResources().getResourceTypeName(resId);
                //获取属性的值的名字
                String entryName = view.getResources().getResourceEntryName(resId);
                SkinItem skinItem = new SkinItem(attributeName, resId, entryName, resourceTypeName);
                itemList.add(skinItem);
            }
        }
        if (itemList.size() > 0){
            SkinView skinView = new SkinView(view, itemList);
            list.add(skinView);
            skinView.apply();
        }
    }

    /**
     *  将控件实例化的方法
     * @param name
     * @param context
     * @param attrs
     * @return
     */

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            Class loadClass = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = loadClass.getConstructor(new Class[]{Context.class, AttributeSet.class});
            view = constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 属性封装对象
     */
    class SkinItem{
        //属性名字
        String name;
        //属性id
        int redId;
        //属性的值的名字 colorPrimary
        String entryName;
        //属性值的类型 color mipmap drawble
        String typeName;

        public SkinItem(String attributeName, int resId, String entryName, String resourceTypeName) {
            this.name = attributeName;
            this.redId = resId;
            this.entryName = entryName;
            this.typeName = resourceTypeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRedId() {
            return redId;
        }

        public void setRedId(int redId) {
            this.redId = redId;
        }

        public String getEntryName() {
            return entryName;
        }

        public void setEntryName(String entryName) {
            this.entryName = entryName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

    class SkinView{
        View view;
        List<SkinItem> skinItems;

        public SkinView(View view, List<SkinItem> skinItems) {
            this.view = view;
            this.skinItems = skinItems;
        }

        public View getView() {
            return view;
        }

        public List<SkinItem> getSkinItems() {
            return skinItems;
        }

        /**
         * 给单个控件进行换肤
         */
        public void apply(){
            for (SkinItem item : skinItems) {
                 if (item.getName().equals("background")){
                     if (item.getTypeName().equals("color")){
                         view.setBackgroundColor(SkinManager.getInstance().getColor(item.getRedId()));
                     } else if (item.getTypeName().equals("drawable") || item.getTypeName().equals("mipmap")){
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                             view.setBackground(SkinManager.getInstance().getDrawable(item.getRedId()));
                         } else {
                             view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(item.getRedId()));
                         }
                     }
                 } else if (item.getName().equals("textColor")){
                     if (view instanceof TextView){
                         ((TextView) view).setTextColor(SkinManager.getInstance().getColor(item.getRedId()));
                     } else if (view instanceof Button){
                         ((Button)view).setTextColor(SkinManager.getInstance().getColor(item.getRedId())) ;
                     }
                 }
            }
        }
    }


}
