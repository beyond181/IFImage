package com.innfotech.imagelibrary.widget;

import android.animation.ObjectAnimator;
import android.view.View;
import android.net.Uri;
import android.graphics.Color;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.react.bridge.Arguments;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.innfotech.imagelibrary.util.DensityUtil;
/**
 * Created by rou on 2017/10/10.
 */

public class IFImageView extends SimpleDraweeView {
    public float scale = 1.1f;
    private static final long DURATION_TIME = 200;
    private boolean focusAnim= false;//焦点变换是否触发动画,默认为不触发
    ObjectAnimator objectAnimatorX;
    ObjectAnimator objectAnimatorY;
	ThemedReactContext reactContext;
	float borderWidth;//默认边框的宽度
	int borderColor;//默认边框的颜色
    float borderRadius;//默认圆角
	int focusColor;//获得焦点时边框的颜色
    float focusWidth;//获得焦点时边框的宽度
    float focusRadius;//焦点时圆角
    float selectedWidth;//选中边框的宽度
	int selectedColor;//选中的颜色（可能和获得焦点时的颜色不一样）
    float selectRadius;//选中时的圆角

	//要屏蔽的方向键
	String direction="none";
	String[] directions;//要屏蔽的多个方向
    private final static String UP_DIRECTION = "up";
    private final static String DOWM_DIRECTION = "down";
    private final static String LEFT_DIRECTION = "left";
    private final static String RIGHT_DIRECTION = "right";
    public IFImageView(ThemedReactContext reactContext){
        super(reactContext);
        initView(reactContext);
    }
    private void initView(final ThemedReactContext reactContext){
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources()).build();
        this.setHierarchy(hierarchy);
        objectAnimatorX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, scale)
                .setDuration(DURATION_TIME);
        objectAnimatorY= ObjectAnimator.ofFloat(this, "scaleY", 1.0f, scale)
                .setDuration(DURATION_TIME);
        this.setFocusable(true);
		this.reactContext = reactContext;
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                WritableMap nativeEvent= Arguments.createMap();
                nativeEvent.putBoolean("hasFocus", hasFocus);
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        v.getId(), "topChange",nativeEvent);
				setFocusBorder(hasFocus);
                if(focusAnim){
                    if(hasFocus){
                        objectAnimatorX.setFloatValues(1.0f, scale);
                        objectAnimatorY.setFloatValues(1.0f, scale);
                    }else{
                        objectAnimatorX.setFloatValues(scale, 1.0f);
                        objectAnimatorY.setFloatValues(scale, 1.0f);
                    }
                    objectAnimatorX.start();
                    objectAnimatorY.start();
                }
            }
        });
		this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN) {//监听按下的事件
					int id = v.getId();
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return dealDirection(UP_DIRECTION, id);
                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return dealDirection(DOWM_DIRECTION, id);
                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        return dealDirection(LEFT_DIRECTION, id);
                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        return dealDirection(RIGHT_DIRECTION, id);
                    }
                }
                return false;
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WritableMap nativeEvent= Arguments.createMap();
				nativeEvent.putString("click", ""+v.getId());
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        v.getId(), "topChange", nativeEvent);
            }
        });

    }
	private boolean dealDirection(String dpad, int id){
		WritableMap nativeEvent= Arguments.createMap();
		nativeEvent.putString("direction", dpad);
		reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
				id, "topChange", nativeEvent);
		if(directions==null)
			return false;
        for(String dir : directions) {
            if (dir.equalsIgnoreCase(dpad)) {
                return true;
            }
        }
        return false;
    }
    //初始化样式
    public void setInitValue(float cornersRadius, float borderWidth, String borderColor, boolean isCircle,
			String scaleType, String uri, boolean autoPlay, float width, float height){
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources()).build();
        RoundingParams roundingParams = new RoundingParams();
		this.borderColor = Color.parseColor(borderColor);
        if(borderWidth>0){
			this.borderWidth = DensityUtil.dip2px(getContext(), (float)borderWidth/2);
            roundingParams.setBorder(this.borderColor, this.borderWidth);
        }else{
			this.borderWidth = 0;
		}
        if(cornersRadius>0){
            this.borderRadius = DensityUtil.dip2px(getContext(), cornersRadius + borderWidth/2);
        } else{
            this.borderRadius = 0;
        }
        roundingParams.setCornersRadius(this.borderRadius);
        roundingParams.setRoundAsCircle(isCircle);
        hierarchy.setRoundingParams(roundingParams);
        ScalingUtils.ScaleType scaleTypeU = ScalingUtils.ScaleType.FIT_CENTER;
        if(scaleType.equals("centerCrop")) {
            scaleTypeU = ScalingUtils.ScaleType.CENTER_CROP;
        }else if(scaleType.equals("center")){
            scaleTypeU = ScalingUtils.ScaleType.CENTER;
        }else if(scaleType.equals("centerInside")){
            scaleTypeU = ScalingUtils.ScaleType.CENTER_INSIDE;
        }else if(scaleType.equals("fitEnd")){
            scaleTypeU = ScalingUtils.ScaleType.FIT_END;
        }else if(scaleType.equals("fitXY")){
            scaleTypeU = ScalingUtils.ScaleType.FIT_XY;
        }else if(scaleType.equals("fitStart")){
            scaleTypeU = ScalingUtils.ScaleType.FIT_START;
        }
        hierarchy.setActualImageScaleType(scaleTypeU);
        this.setHierarchy(hierarchy);

		ImageRequest request = null;
        if(width>0&&height>0){
            request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                    .setResizeOptions(new ResizeOptions((int) DensityUtil.dip2px(getContext(),width),
                            (int) DensityUtil.dip2px(getContext(),height)))
                    .build();
        }
		PipelineDraweeControllerBuilder  builder = Fresco.newDraweeControllerBuilder();
		builder.setOldController(this.getController());
		builder.setAutoPlayAnimations(true);
		if(request!=null)
			builder.setImageRequest(request);
		else
			builder.setUri(uri);
		PipelineDraweeController controller = (PipelineDraweeController) builder.build();
		this.setController(controller);
    }
    //设置缩放倍数，需要大于1
    public void setScale(float scale){
        if(scale>0){
            this.scale = scale;
		}
    }
	//设置是否有缩放动画
	public void setFocusAnim(boolean focusAnim){
		this.focusAnim = focusAnim;
	}
	//设置该控件是否可获得焦点
    public void setCanFocused(boolean canFocused){
		this.setFocusable(canFocused);
	}
	//设置需要屏蔽的焦点方向，可以多个方向,用逗号分隔
    public void setInterceptDirection(String direction){
        if(!TextUtils.isEmpty(direction)) {
            directions = direction.split(",");
        }else{
			directions = new String[]{};
		}
    }


	//设置选中时边框的颜色
	public void setSelectedStyle(String selectedColor, float selectedWidth, float radius){
		this.selectedColor = Color.parseColor(selectedColor);
		if(selectedWidth>0){
			this.selectedWidth = DensityUtil.dip2px(getContext(), (float) selectedWidth/2);
		}else{
			this.selectedWidth = 0;
		}
		if(radius>0){
            this.selectRadius = DensityUtil.dip2px(getContext(), radius + selectedWidth/2);
        } else{
            this.selectRadius = 0;
        }
	}
	//设置获得焦点时边框的颜色
	public void setFocusStyle(String focusColor, float focusWidth, float radius){
		this.focusColor = Color.parseColor(focusColor);
		if(focusWidth>0){
			this.focusWidth = DensityUtil.dip2px(getContext(),(float)focusWidth/2);
		}else{
			this.focusWidth = 0;
		}
		if(radius>0){
		    this.focusRadius = DensityUtil.dip2px(getContext(), radius + focusWidth/2);
        } else{
            this.focusRadius = 0;
        }

	}





	//更改该控件焦点情况(new)
	public void changeFocused(boolean focused){
		if(focused){
			this.requestFocus();
		}else{
			this.clearFocus();
		}
	}

	//设置该控件为选中状态，
	public void setFocusBorder(boolean flag){
		if(flag){
		    RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
            roundingParams.setBorder(focusColor, focusWidth);
            roundingParams.setCornersRadius(focusRadius);
            this.getHierarchy().setRoundingParams(roundingParams);
		}else{
			setNormalBorder();
		}
	}
	//设置为选中状态
	public void setSelectedBorder(boolean flag){
		if(flag){
			RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
			roundingParams.setBorder(selectedColor, selectedWidth);
			roundingParams.setCornersRadius(selectRadius);
			this.getHierarchy().setRoundingParams(roundingParams);
		}else{
			setNormalBorder();
		}
	}
	//设置为正常状态
	public void setNormalBorder(){
		RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
		roundingParams.setBorder(borderColor, borderWidth);
		roundingParams.setCornersRadius(borderRadius);
		this.getHierarchy().setRoundingParams(roundingParams);
	}

}