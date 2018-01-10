package com.innfotech.imagelibrary.widget;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import android.support.annotation.Nullable;


/**
 * 图片组件基础类管理器
 */
public class ReactImageManager extends SimpleViewManager<IFImageView> {
    /**
     * 设置js引用名
     * @return String
     */
    @Override
    public String getName() {
        return "RCTIFImageView";
    }

    /**
     * 创建UI组件实例
     * @param reactContext
     * @return ReactImageView
     */
    @Override
    protected IFImageView createViewInstance(final ThemedReactContext reactContext) {
        return new IFImageView(reactContext);
    }
    @ReactProp(name = "src")
    public void setSrc(final IFImageView imageView, @Nullable ReadableMap src) {
        imageView.setInitValue(
                (float) src.getDouble("borderRadius"),
                (float) src.getDouble("borderWidth"),
                src.getString("borderColor"),
                src.getBoolean("isCircle"),
                src.getString("scaleType"),
                src.getString("uri"),
				src.getBoolean("autoPlay"),
				(float) src.getDouble("width"),
				(float) src.getDouble("height"));
    }
    @ReactProp(name="scale")
    public void setScale(IFImageView imageView, float scale){
        imageView.setScale(scale);
    }
	@ReactProp(name="focusAnim")
	public void setFocusAnim(IFImageView imageView,boolean focusAnim){
		imageView.setFocusAnim(focusAnim);
	}
	@ReactProp(name="canFocused")
	public void setCanFocused(IFImageView imageView,boolean canFocused){
		imageView.setCanFocused(canFocused);
	}
	@ReactProp(name="interceptDirection")
	public void setInterceptDirection(IFImageView imageView,String interceptDirection){
		imageView.setInterceptDirection(interceptDirection);
	}

	@ReactProp(name = "focusS")
    public void setFocusStyle(final IFImageView imageView, @Nullable ReadableMap focusS) {
        imageView.setFocusStyle(
                focusS.getString("borderColorF"),
                (float) focusS.getDouble("borderWidthF"),
                (float) focusS.getDouble("borderRadiusF"));
    }
	@ReactProp(name = "selectedS")
    public void setSelectedStyle(final IFImageView imageView, @Nullable ReadableMap selectedS) {
        imageView.setSelectedStyle(
                selectedS.getString("borderColorS"),
                (float) selectedS.getDouble("borderWidthS"),
                (float) selectedS.getDouble("borderRadiusS"));
    }


	//更改该控件焦点情况
	@ReactProp(name = "changeFocused")
	public void changeFocused(IFImageView imageView,boolean changeFocused){
		imageView.changeFocused(changeFocused);
	}

	//设置该控件为选中状态，
	@ReactProp(name = "focusBorder")
	public void setFocusBorder(IFImageView imageView,boolean focusBorder){
		imageView.setFocusBorder(focusBorder);
	}
	//设置为选中状态
	@ReactProp(name = "selectedBorder")
	public void setSelectedBorder(IFImageView imageView,boolean selectedBorder){
		imageView.setSelectedBorder(selectedBorder);
	}
}