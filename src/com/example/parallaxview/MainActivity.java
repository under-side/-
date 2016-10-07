package com.example.parallaxview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.parallaxview.ui.MyListView;
import com.example.parallaxview.utils.Cheeses;


public class MainActivity extends Activity {

    private MyListView mLv;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();
    }

	private void initListView() {
		mLv = (MyListView) findViewById(R.id.lv);
		mLv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        final View headerView = View.inflate
        		(this, R.layout.view_header, null);
        final ImageView imageView=(ImageView) headerView.findViewById(R.id.iv);
        //调用该方法确保ImageView已经加载到layout中去，则可以测量了
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				   //去设置图片的宽高
		        mLv.setParallaxImageView(imageView);
			}
		});
		mLv.addHeaderView(headerView);
        
        mLv.setAdapter(new ArrayAdapter<String>
          (this, android.R.layout.simple_expandable_list_item_1,Cheeses.NAMES));
	}
}
