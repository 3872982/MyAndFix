package com.learning.andfix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class DexManager {

    private Context mContext;
    private String mAbsolutePath;

    public DexManager(Context context) {
        mContext = context;
    }

    public void Loadpath(File file){
        try {
            mAbsolutePath = file.getAbsolutePath();
            DexFile dexFile =  DexFile.loadDex(mAbsolutePath,new File(mContext.getCacheDir(),"opt").getAbsolutePath(),Context.MODE_PRIVATE);

            //当前Dex中所有class类名的集合
            Enumeration<String> entries = dexFile.entries();
            while(entries.hasMoreElements()){
                String clzName = entries.nextElement();

                Class realClazz= dexFile.loadClass(clzName, mContext.getClassLoader());
                if (realClazz != null) {
                    fixClazz(realClazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void fixClazz(Class realClazz) {
        Method[] methods = realClazz.getMethods();

        for (Method rightMethod : methods) {
            Replace replaceAnnotation = rightMethod.getAnnotation(Replace.class);

            if(replaceAnnotation == null){
                continue;
            }

            String errorClazz = replaceAnnotation.clazz();
            String errorMethod = replaceAnnotation.method();

            Class<?> errClass = null;
            try {
                errClass = Class.forName(errorClazz);
                Method errMethod = errClass.getDeclaredMethod(errorMethod, rightMethod.getParameterTypes());
                replaceMethod(rightMethod,errMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private native void replaceMethod(Method rightMethod, Method errMethod);
}
