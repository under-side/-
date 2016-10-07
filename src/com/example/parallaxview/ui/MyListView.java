package com.example.parallaxview.ui;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.parallaxview.utils.Utils;

public class MyListView extends ListView {

	//获取HeaderView中的ImageView组件实体对象
	private ImageView mImage;
	//照片本质高度
	private int mOriginalHeight;
	//ImageView在layout中的显示高度
	private int mDrawableHeight;
	
	public MyListView(Context context) {
		this(context,null);
	}

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	//得到传入的headerView中的ImageView，获取其原始的宽高
    public void setParallaxImageView(ImageView view)
    {
    	mImage = view;
    	//该高度是图片在layout中的原始显示高度
    	mOriginalHeight = mImage.getHeight();
    	//该高度为图片本身的高度
    	mDrawableHeight=mImage.getDrawable().getIntrinsicHeight();
    }
    /*
     * 通过overScrollBy方法去处理当ListView的HeaderView到达超过滑动范围后的手势处理(non-Javadoc)
     * 当滚动超过了view的内容后会调用onOverScrolled方法，该方法会再去调用overScrollBy方法处理超出滚动
     * 内容的手势操作。
     * @see android.view.View#overScrollBy(int, int, int, int, int, int, int, int, boolean)
     */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		/*
		 * deltaY : 竖直方向的瞬时偏移量 / 变化量 dx,其值会根据手指滑动力度不同得到数值大小不同，当手势很用力时很大
		 * 顶部到头下拉为-, 底部到头上拉为+
		 * scrollY : 竖直方向的偏移量 / 变化量 
		 * scrollRangeY : 竖直方向滑动的范围 
		 * maxOverScrollY:竖直方向最大滑动范围 
		 * isTouchEvent : 是否是手指触摸滑动, true为手指, false为惯性
		 */
		Utils.showToast(getContext(), "scrollY"+scrollY+"  deltaY"+deltaY);
		//判断是由是向下滑动且是手势滑动时才生效
		if(deltaY<0&&isTouchEvent)
		{
			//当高度超过了ImageView的本来大小了则不再去改变其布局属性
			if(mImage.getHeight()<=mDrawableHeight)
			{
				int newHeight=(int) (mImage.getHeight()+Math.abs(deltaY/3.0f));
				mImage.getLayoutParams().height=newHeight;
				mImage.requestLayout();
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP)
		{
			int startHeight=mImage.getHeight();
			int endHeight=mOriginalHeight;
			//方法一：使用valueAnimator来进行平移动画
			//setValueAnimator(startHeight,endHeight);
			//方法二：使用自定义动画实现，借用Animation中的applyTransformation方法实现渐变的过程
			SmoothAnimation animation=new SmoothAnimation(startHeight, endHeight, mImage);
			mImage.startAnimation(animation);
		}
		return super.onTouchEvent(ev);
	}
	
	//设置平滑变化的操作
	private void setValueAnimator(final int startHeight, final int endHeight)
	{
		ValueAnimator valueAnimator=ValueAnimator.ofInt(1);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//这里fraction为0.0-->1.0的渐变过程
				 float fraction = animation.getAnimatedFraction();
				 //使用类型估值器来获取两点之间的渐变过程的值的变化
				 int currentHeight=evaluate(fraction, startHeight, endHeight);
				 mImage.getLayoutParams().height=currentHeight;
				 mImage.requestLayout();
			}
		});
		valueAnimator.setInterpolator(new OvershootInterpolator());
		valueAnimator.setDuration(500);
		valueAnimator.start();
	}
	//使用的是int类型的估值器
	  public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
	        int startInt = startValue;
	        return (int)(startInt + fraction * (endValue - startInt));
	    }
}
