# androidWheelView
仿照iOS的滚轮控件，从请吃饭apk反编译出来的

具体的请查看这个博客：http://www.jianshu.com/p/fa7adfa90c68

由于Jcenter库停止维护，现迁移到jitpack，依赖如下
### 在根目录的build.gradle增加jitpack
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### 添加依赖
```gradle
dependencies {
    implementation 'com.github.weidongjian:androidWheelView:1.0.0'
}
```



效果图如下：

![DemoView](/photo/1625735942774356.gif)



### 常见问题

1. 设置初始位置：调用`setInitPosition()`，不是`setCurrentPosition()`。

### 更新历史
**版本号：1.0.0 更新时间：2021.7.08**

增加对无3D效果的配置：app:awv_isCurve="false"

**版本号：0.9.1 更新时间：2021.7.06**

修复setCurrentPosition后，对应的位置不会回调的异常

**版本号：0.2.2 更新时间：2019.10.30**

修复设置当前位置为0无效的异常

**版本号：0.2.1 更新时间：2019.6.25**

修复一波bug

**版本号：0.1.2   更新时间：2017.3.25**

1. 适配`setCurrentPosition(0)`的场景
2. 适配嵌套在`ScrollerView`滑动冲突
3. 适配在`dialog`中显示



## 2016-12-30：各种性能效果做了很大的更新

同时，提供各种参数接口，包括文本大小，显示数量，控件颜色等各种参数
#### Description of Attributes

|      Attributes       | Format  |  Default   |    Description     |
| :-------------------: | :-----: | :--------: | :----------------: |
|     awv_textsize      | integer |     15     |      textsize      |
|     awv_lineSpace     |  float  |    2.0f    |     line space     |
|  awv_centerTextColor  | integer | oxff313131 | center text color  |
|  awv_outerTextColor   | integer | 0xffafafaf |  outer text color  |
| awv_dividerTextColor  | integer | oxff313131 | center text color  |
| awv_itemsVisibleCount | integer |     9      | visible item count |
|      awv_isLoop       | boolean |    true    |    is loop mode    |

![LoopView](/photo/circle.jpg)

## 滚动效果类似一个圆柱 ##
### 绘制 ###

在onMeasure方法中计算控件宽和高以及初始化画笔

itemHeight = lineSpacingMultiplier * maxTextHeight

圆柱半圆周为itemHeight*(itemCount - 1)

计算出圆柱直径即为控件高度

在onTouchEvent方法中计算圆柱滚动距离totalScrollY

### 在onDraw方法中绘制控件 ###

计算滚动了多少个条目change = (int) ((float) totalScrollY / itemHeight);

计算当前条目位置preCurrentIndex（处理超出边界的情况）
计算滚动超出条目的位移：
int j2 = (int) ((float) totalScrollY % itemHeight);

绘制各个条目：

计算条目弧度radian

计算图纸canvas偏移量

图中的弧度标错了，应该标它的补角，那样感觉不好理解，大家自行脑补一下

h2也标错了，画图的时候忘记考虑空白区域了，h2应该是文字高度的sin值

double h1 = Math.cos(radian) * (double) radius;

double h2 = (Math.sin(radian) * (double) maxTextHeight) / 2D)

int translateY = (int) ((double) radius h1 h2;

图纸延Y方向缩放，值为弧度radian的sin值（这样出来的效果就感觉是个圆柱）

### 最后分不同情况绘制各个条目 ###
1. 偏移量translateY y值小于第一条线firstLineY y值的并且偏移量translateY+maxTextHeight大于第一条线y值小于第一条线firstLineY y值的（即第一条线穿过该条目文字）
2. 条目文字穿过第二条线的情况
3. 条目刚好在两条线中间的
4. 其他情况

onTouchEvent方法，当手离开控件时开始平滑滚动控件

LoopViewGestureListener处理手势，当按下时取消所有的滚动，当滑行时，平滑滚动

平滑滚动用定时器ScheduledExecutorService来处理

哪里写的不对的多多指正。
