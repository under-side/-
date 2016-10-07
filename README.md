# ParallaxView
利用ListView的HeaderView添加视差特效

### 功能实现
1. 重写ListView组件，在其中获取实现视差特效的view；
2. 利用overScrollBy方法去处理当滚动超出view的内容时的手势操作；
3. 从overScrollBy方法中获取动态数据，动态的改变ImageView在HeaderView中的显示布局；

### 项目要点
1. 项目是由ListView添加HeaderView，而添加的HeaderView的布局文件中含有ImageView子view，则通过在ListView中获取ImageView实例对象，再通过ImageView的LayoutParams设置该子view的布局属性，调用requestLayout去向父layout申请重新布置布局，达到动态改变HeaderView在ListView中的显示；
2. 使用overScrollBy方法动态的改变ImageView的LayoutParams属性值；
3. 运用ValueAnimator或者自定义Animation去实现从一个位置到另一个位置的平滑移动view的操作。

### 总结
自定义控件，要根据功能需求去设计，采用与之匹配的方法去实现。

#### 如果有任何建议，可以点击<a href=" https://github.com/under-side/ParallaxView/issues">这里<a/>告诉我。
