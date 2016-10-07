package com.example.parallaxview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class SmoothAnimation extends Animation {

	int startHeight;
	int endHeight;
    ImageView view;
	
	public SmoothAnimation(int startHeight, int endHeight,ImageView view) {
		super();
		this.startHeight = startHeight;
		this.endHeight = endHeight;
		this.view=view;
		setDuration(500);
		setInterpolator(new OvershootInterpolator());
	}
	public SmoothAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//在动画执行期间，都会调用该方法
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		//这里的interpolateTime是从0.0--》1.0的
		int currentHeight=evaluate(interpolatedTime, startHeight, endHeight);
		view.getLayoutParams().height=currentHeight;
		view.requestLayout();
		super.applyTransformation(interpolatedTime, t);
	}
	//使用的是int类型的估值器
	  public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
	        int startInt = startValue;
	        return (int)(startInt + fraction * (endValue - startInt));
	    }
}
