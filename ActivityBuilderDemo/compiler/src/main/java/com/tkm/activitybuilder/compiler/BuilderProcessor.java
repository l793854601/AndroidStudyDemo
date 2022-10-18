package com.tkm.activitybuilder.compiler;

import com.tkm.activitybuilder.annotations.Builder;
import com.tkm.activitybuilder.annotations.Args;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class BuilderProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private Types typeUtils;

    private Messager messager;

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();

        messager.printMessage(Diagnostic.Kind.NOTE, "----> init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            return false;
        }
        Map<TypeElement, List<VariableElement>> elementListMap = new HashMap<>();
        Set<? extends Element> builders = roundEnvironment.getElementsAnnotatedWith(Builder.class);
        for (Element builder : builders) {
            TypeElement typeElement = (TypeElement) builder;
            List<VariableElement> variableElements = new ArrayList<>();
            elementListMap.put(typeElement, variableElements);
            List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
            for (Element enclosedElement : enclosedElements) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    variableElements.add(variableElement);
                }
            }

            processElementListMap(elementListMap);
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Builder.class.getCanonicalName());
        set.add(Args.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    private void processElementListMap(Map<TypeElement, List<VariableElement>> elementListMap) {
        messager.printMessage(Diagnostic.Kind.NOTE, "--> process result: " + elementListMap);

        for (TypeElement typeElement : elementListMap.keySet()) {
            //  获取包名
            String packageName = elementUtils.getPackageOf(typeElement).toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "--> packageName: " + packageName);

            //  获取类名
            String className = typeElement.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "--> className: " + className);

            //  拼接类名
            String finalClassName = generateFinalClassName(className);
            messager.printMessage(Diagnostic.Kind.NOTE, "--> finalClassName: " + finalClassName);

            try {
                JavaFileObject fileObject = filer.createSourceFile(packageName + "." + finalClassName);
                Writer writer = fileObject.openWriter();
                writer.write("package " + packageName + ";\n");
                writer.write("import android.os.Bundle;\n");
                writer.write("public class " + finalClassName + " {\n");

                List<VariableElement> variableElements = elementListMap.get(typeElement);
                for (VariableElement variableElement : variableElements) {
                    //  过滤掉未标记Args注解的field
                    if (variableElement.getAnnotation(Args.class) == null) {
                        continue;
                    }

                    String fieldTypeName = getTypeMirrorName(variableElement.asType());
                    messager.printMessage(Diagnostic.Kind.NOTE, "fieldTypeName: " + fieldTypeName);
                    if (fieldTypeName.length() > 0) {
                        String fieldName = variableElement.getSimpleName().toString();
                        //  生成field
                        writer.write("\tprivate " + fieldTypeName + " " + fieldName + ";\n");

                        //  生成setter方法
                        /*
                            public UserArgsBuilder setUser(String user) {
                                this.user = user;
                                return this;
                            }
                         */
                        writer.write("\tpublic " + finalClassName + " set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + "(" + fieldTypeName + " " + fieldName + ") {\n");
                        writer.write("\t\tthis." + fieldName + "=" + fieldName + ";\n");
                        writer.write("\t\treturn this;\n");
                        writer.write("\t}\n");
                    }
                }

                //  生成转为bundle方法
                writer.write("\tpublic Bundle toBundle() {\n");
                writer.write("\t\tBundle bundle = new Bundle();\n");
                for (VariableElement variableElement : variableElements) {
                    //  过滤掉未标记Args注解的field
                    if (variableElement.getAnnotation(Args.class) == null) {
                        continue;
                    }
                    //  生成toBundle()方法
                    /*
                        	public Bundle toBundle() {
                        		Bundle bundle = new Bundle();
                        		bundle.putString("NAME", this.name);
                        		bundle.putString("OWNER", this.owner);
                        		bundle.putString("URL", this.url);
                        		bundle.putLong("CREATEAT", this.createAt);
                        		return bundle;
                        }
                     */
                    String fieldName = variableElement.getSimpleName().toString();
                    String paramTypeName = getTypeMirrorParameterName(variableElement.asType());
                    writer.write("\t\tbundle.put" + paramTypeName + "(" + "\"" + fieldName.toUpperCase(Locale.ROOT) + "\"" + ", " + "this." + fieldName + ");\n");
                }
                writer.write("\t\treturn bundle;\n");
                writer.write("\t}\n");

                writer.write("}\n");
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generateFinalClassName(String className) {
        String result = className;
        try {
            int from = result.indexOf("Activity");
            result = result.substring(0, from);
            result = result + "ArgsBuilder";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getTypeMirrorName(TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case BOOLEAN:
                return "boolean";
            case BYTE:
                return "byte";
            case INT:
                return "int";
            case LONG:
                return "long";
            case FLOAT:
                return "float";
            case DOUBLE:
                return "double";
            case DECLARED:
                if (Objects.equals(typeMirror.toString(), "java.lang.String")) {
                    return "String";
                }
            default:
                return "";
        }
    }

    private String getTypeMirrorParameterName(TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
            case BOOLEAN:
                return "Boolean";
            case BYTE:
                return "Byte";
            case INT:
                return "Int";
            case LONG:
                return "Long";
            case FLOAT:
                return "Float";
            case DOUBLE:
                return "Double";
            case DECLARED:
                if (Objects.equals(typeMirror.toString(), "java.lang.String")) {
                    return "String";
                }
            default:
                return "";
        }
    }

    private String getFullClassName(String name) {
        String result = name;
        try {
            Class<?> aClass = Class.forName(name);
            result = aClass.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}