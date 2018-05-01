package com.example.compiler;

import com.example.annotation.WXPayAnnotation;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WXProcessor extends AbstractProcessor {

    private String packageName;

    private Class superClazz;

    /**
     * @param set              the annotation types requested to be processed
     * @param roundEnvironment environment for information about the current and prior round
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set != null && !set.isEmpty()) {
            System.out.println("invoke process");
            //System.out.println("param set>>>" + set);

            WXEntryAnnotationVisitor visitor = new WXEntryAnnotationVisitor(processingEnv.getFiler());

            for (TypeElement typeElement : set) {
                //System.out.println("params:typeElement >>>>" + typeElement);//com.example.annotation.WXPayAnnotation

                //使用该注解的元素集合
                Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(typeElement);

                //System.out.println(elementsAnnotatedWith);//[com.example.DelegatePayActivity]

                //在这里我们已经明确的知道WXEntryAnnotation只能用于type类型，因此可以使用ElementFilter.typesIn将其转化为具体的TypeElement类型的集合。
                Set<TypeElement> typeElements = ElementFilter.typesIn(elementsAnnotatedWith);

                //System.out.println(typeElements);//[com.example.DelegatePayActivity]

                //遍历使用该注解的类，方法，属性
                for (TypeElement element : typeElements) {
//                    System.out.println(element);//com.example.DelegatePayActivity
                    //获取包裹该元素的元素
                    //System.out.println(element.getEnclosingElement());//com.example
                    //获取内部包裹的元素
                    //List<? extends Element> enclosedElements = element.getEnclosedElements();
                    //System.out.println(enclosedElements);//DelegatePayActivity(),onCreate(android.os.Bundle)

//                    for (Element enclosedElement : enclosedElements) {
//
//                        if (enclosedElement instanceof TypeElement) {
//                            System.out.println(enclosedElement + ">>" + ((TypeElement) enclosedElement).getNestingKind());
//                        } else {
//                            System.out.println(enclosedElement + "<<<" + enclosedElement.getClass());
//                        }
//                    }

                    //对于TypeElement表示这个类的全限名称：com.example.DelegatePayActivity
                    //System.out.println(element.getQualifiedName());

                    //返回一个父类的TypeMirror对象 android.support.v7.app.AppCompatActivity
//                    System.out.println(element.getSuperclass());

                    //返回该类的泛型类型的集合 List<? extends TypeParameterElement>
                    //System.out.println(element.getTypeParameters());

                    //返回实现的接口的集合 List<? extends TypeMirror>
                    //System.out.println(element.getInterfaces());


                    //DelegatePayActivity
//                    System.out.println(element.getSimpleName());


                    //System.out.println(element.getKind().);//CLASS

                    //@com.example.annotation.WXPayAnnotation(packageName="com.example", superClass=com.example.DelegatePayActivity.class)
                    //System.out.println(element.getAnnotationMirrors());

                    List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
                    for (AnnotationMirror annotationMirror : annotationMirrors) {

                        //获取注解类型 com.example.annotation.WXPayAnnotation
                        //System.out.println(annotationMirror.getAnnotationType());

                        //判断当前处理的注解就是扫描出来的注解
                        if (annotationMirror.getAnnotationType().asElement().getSimpleName().toString().equals(typeElement.getSimpleName().toString())) {
                            //获取注解的值
                            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                            //System.out.println(elementValues);
                            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                                ExecutableElement key = entry.getKey();
                                AnnotationValue value = entry.getValue();
                                //System.out.println(key + ">>" + value);//packageName()>>"com.example"  superClass()>>com.example.DelegatePayActivity.class

                                //System.out.println(value.getValue());//com.example  com.example.DelegatePayActivity
                                //System.out.println(key.getSimpleName());

//                            String methodName = key.getSimpleName().toString();
//                            if ("packageName".equals(methodName)) {
//                                this.packageName = value.getValue().toString();
//                            } else if ("superClass".equals(methodName)) {
//                                this.superClazz =value.
//                            }

                                value.accept(visitor, null);

                            }
                            // System.out.println(this.packageName + ">>" + this.superClazz);

                        }
                    }


                }
            }

            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> supportAnnotationTypes = new HashSet<>();

        final Set<Class<? extends Annotation>> supportAnnotations = getSupportAnnotations();

        for (Class<? extends Annotation> supportAnnotion : supportAnnotations) {
            supportAnnotationTypes.add(supportAnnotion.getCanonicalName());
        }

        return supportAnnotationTypes;
    }

    /**
     * 设置需要扫描的注解
     *
     * @return
     */
    private final Set<Class<? extends Annotation>> getSupportAnnotations() {
        Set<Class<? extends Annotation>> supportAnnotations = new HashSet<>();
//        supportAnnotations.add(WXEntryAnnotation.class);
        supportAnnotations.add(WXPayAnnotation.class);
        return supportAnnotations;
    }
}
