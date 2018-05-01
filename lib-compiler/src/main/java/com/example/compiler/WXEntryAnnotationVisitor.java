package com.example.compiler;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * @author zeal
 * @createTime 2018/1/3
 * @des 注解值访问器
 */

public class WXEntryAnnotationVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {

    private final Filer mFiler;
    private String packageName;
    private TypeMirror typeMirror;

    public WXEntryAnnotationVisitor(Filer filer) {
        this.mFiler = filer;
    }

//    @Override
//    public Void visitArray(List<? extends AnnotationValue> list, Void aVoid) {
//        if (list != null) {
//            for (AnnotationValue annotationValue : list) {
//                mPackageNames.add((String) annotationValue.getValue());
//            }
//        }
//        return super.visitArray(list, aVoid);
//    }


    @Override
    public Void visitString(String s, Void aVoid) {
        System.out.println("visitString>>" + s);
        this.packageName = s;
        if (typeMirror != null && packageName != null) {
            generateWXEntryCode(typeMirror);
        }
        return super.visitString(s, aVoid);
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void p) {
        System.out.println("visitType>>" + typeMirror);
        this.typeMirror = typeMirror;
        if (typeMirror != null && packageName != null) {
            generateWXEntryCode(typeMirror);
        }
        return p;
    }

    /**
     * WXEntryActivity代码生成
     *
     * @param typeMirror
     */
    private final void generateWXEntryCode(TypeMirror typeMirror) {
        System.out.println("generateWXEntryCode");

//        if (!mPackageNames.isEmpty()) {
//            for (String packageName : mPackageNames) {
                TypeSpec targetActivityTypeSpec =
                        TypeSpec.classBuilder("WXEntryActivity")
                                .addModifiers(Modifier.FINAL)
                                .addModifiers(Modifier.PUBLIC)
                                .superclass(TypeName.get(typeMirror))
                                .build();

                final JavaFile javaFile =
                        JavaFile.builder(packageName + ".wxapi", targetActivityTypeSpec)
                                .build();

                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//        }

    }
}
