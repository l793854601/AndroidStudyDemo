package com.tkm.hotfixdemo.hotfix;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import dalvik.system.PathClassLoader;

/**
 *  ClassLoader继承关系
 *  ClassLoader
 *  BootClassLoader        BaseDexClassLoader
 *                         PathClassLoader     DexClassLoader
 */
public class HotFixLite {

    private HotFixLite() {

    }

    public static void installDex(Context context, String path) {
        if (context == null || TextUtils.isEmpty(path)) {
            return;
        }

        try {
            File dexFile = new File(path);
            if (!dexFile.exists()) {
                throw new FileNotFoundException("File: " + dexFile.getAbsolutePath() + " not exists");
            }

            //  获取PathClassLoader
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            //  反射获取BaseDexClassLoader中的private final DexPathList pathList;
            Field pathListField = pathClassLoader.getClass().getSuperclass().getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListValue = pathListField.get(pathClassLoader);

            //  反射获取DexPathList中的private Element[] dexElements;
            Field dexElementsField = pathListValue.getClass().getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElementsValue = dexElementsField.get(pathListValue);

            //  反射获取dexElements的类型，也就是Element[]
            Class<?> componentType = dexElementsValue.getClass().getComponentType();

            //  加载dexFile，得到Elements[]
            Object hotFixDexElementsValue = getDexElements(pathClassLoader, dexFile);

            //  将两个Elements[]合并
            int dexElementsValueLength = Array.getLength(dexElementsValue);
            int hotFixDexElementsValueLength = Array.getLength(hotFixDexElementsValue);
            int totalLength = dexElementsValueLength + hotFixDexElementsValueLength;
            Object elementsValue = Array.newInstance(componentType, totalLength);
            for (int i = 0; i < hotFixDexElementsValueLength; i++) {
                Array.set(elementsValue, i, Array.get(hotFixDexElementsValue, i));
            }
            for (int i = 0; i < dexElementsValueLength; i++) {
                Array.set(elementsValue, i + hotFixDexElementsValueLength, Array.get(dexElementsValue, i));
            }

            //  反射赋值给pathListField
            dexElementsField.set(pathListValue, elementsValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getDexElements(ClassLoader classLoader, File dexFile) {
        Object result = new Object();
        try {
            //  反射获取DexPathList的构造器
            Class<?> pathListClazz = Class.forName("dalvik.system.DexPathList");
            Constructor<?> pathListClazzConstructor = pathListClazz.getConstructor(ClassLoader.class, String.class, String.class, File.class);
            //  反射创建DexPathList实例
            //  librarySearchPath：native library相关，可以为null
            //  optimizedDirectory：为null，则使用默认
            Object pathList = pathListClazzConstructor.newInstance(classLoader, dexFile.getAbsolutePath(), null, null);
            //  反射获取DexPathList实例的Elements[]
            Field elementsField = pathList.getClass().getDeclaredField("dexElements");
            elementsField.setAccessible(true);
            result = elementsField.get(pathList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
