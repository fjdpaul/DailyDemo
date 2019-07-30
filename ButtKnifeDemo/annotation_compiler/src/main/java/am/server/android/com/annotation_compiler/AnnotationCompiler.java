package am.server.android.com.annotation_compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import am.server.android.com.annotations.BindView;

/**
 * 创建时间: 2019-07-29 21:43
 * 类描述: 注解处理器 生成activity 对应的activity
 *
 * @author 香瓜
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    //生成对象用的
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明注解要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的java版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 实现我们要生成的文件
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //拿到整个模块中用到 BindView 注解的节点
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        Map<String, List<VariableElement>> map = new HashMap<>();

        for (Element element : elementsAnnotatedWith) {
            //获取成员变量的节点 也就是控件的节点
            VariableElement variableElement = (VariableElement) element;
            String name = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> variableElements = map.get(name);
            if (variableElements == null) {
                variableElements = new ArrayList<>();
                map.put(name, variableElements);
            }
            variableElements.add(variableElement);

        }
        if (map.size() > 0){
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String activityName = iterator.next();
                List<VariableElement> variableElements = map.get(activityName);
                //通过控件变量节点获取 到他的上一层节点 也就是类节点
                TypeElement element = (TypeElement) variableElements.get(0).getEnclosingElement();
                //通过成员变量获得包名
                String packName = processingEnv.getElementUtils().getPackageOf(element).toString();
                try {
                    JavaFileObject javaFileObject = filer.createSourceFile(packName + "." + activityName + "_ViewBinding");
                    writer = javaFileObject.openWriter();
                    writer.write("package " + packName + ";\n");
                    writer.write("import " +packName+ ".IBinder;\n");
                    writer.write("public class "+activityName+ "_ViewBinding implements IBinder<" +packName+"." +activityName+ ">{\n");
                    writer.write(" @Override\n" +
                            "  public void bind(" +packName+"." +activityName +" target){");
                    for (VariableElement variableElement : variableElements){
                        //获取控件名字
                        String variableName = variableElement.getSimpleName().toString();
                        //控件id
                        int id = variableElement.getAnnotation(BindView.class).value();
                        TypeMirror typeMirror = variableElement.asType();
                        writer.write("target." +variableName+ "=("+typeMirror+")target.findViewById("+id+");\n");
                    }
                    writer.write("}\n}\n ");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }
}
