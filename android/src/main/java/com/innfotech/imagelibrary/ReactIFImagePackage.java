package com.innfotech.imagelibrary;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.innfotech.imagelibrary.widget.ReactImageManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自定义组件模块注册类
 */
public class ReactIFImagePackage implements ReactPackage {

    /**
     * 创建原生模块
     * @param reactContext
     * @return
     */
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
    //@Override
    //public List<Class<? extends JavaScriptModule>> createJSModules() {
    //    return Collections.emptyList();
    //}

    /**
     * 创建原生UI组件控制器
     * @param reactContext
     * @return
     */
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
            new ReactImageManager()
        );
    }
}