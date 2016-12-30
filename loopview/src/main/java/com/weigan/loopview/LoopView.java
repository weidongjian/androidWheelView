package com.weigan.loopview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Weidongjian on 2015/8/18.
 */
public class LoopView extends View {

    private float scaleX = 1.05F;

    private static final int DEFAULT_TEXT_SIZE = (int) (Resources.getSystem().getDisplayMetrics().density * 15);

    private static final float DEFAULT_LINE_SPACE = 2f;

    private static final int DEFAULT_VISIBIE_ITEMS = 9;

    public enum ACTION {
        // 点击，滑翔(滑到尽头)，拖拽事件
        CLICK, FLING, DAGGLE
    }

    private Context context;

    Handler handler;
    private GestureDetector flingGestureDetector;
    OnItemSelectedListener onItemSelectedListener;

    // Timer mTimer;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    private Paint paintOuterText;
    private Paint paintCenterText;
    private Paint paintIndicator;

    List<String> items;

    int textSize;
    int maxTextHeight;

    int outerTextColor;

    int centerTextColor;
    int dividerColor;

    // 条目间距倍数
    float lineSpacingMultiplier;
    //是否循环滚轮
    boolean isLoop;

    // 第一条线Y坐标值
    int firstLineY;
    int secondLineY;

    int totalScrollY;
    int initPosition;
    private int selectedItem;
    int preCurrentIndex;
    int change;

    // 显示几个条目
    int itemsVisibleCount;

    String[] drawingStrings;

    int measuredHeight;
    int measuredWidth;

    // 半圆周长
    int halfCircumference;
    // 半径
    int radius;

    private int mOffset = 0;
    private float previousY;
    long startTime = 0;

    private Rect tempRect = new Rect();

    private int paddingLeft, paddingRight;

    /**
     * set text line space, must more than 1
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        if (lineSpacingMultiplier > 1.0f) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
        }
    }


    /**
     * set outer text color
     * @param centerTextColor
     */
    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        paintCenterText.setColor(centerTextColor);
    }

    /**
     * set center text color
     * @param outerTextColor
     */
    public void setOuterTextColor(int outerTextColor) {
        this.outerTextColor = outerTextColor;
        paintOuterText.setColor(outerTextColor);
    }

    /**
     * set divider color
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        paintIndicator.setColor(dividerColor);
    }



    public LoopView(Context context) {
        super(context);
        initLoopView(context, null);
    }

    public LoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initLoopView(context, attributeset);
    }

    public LoopView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        initLoopView(context, attributeset);
    }

    private void initLoopView(Context context, AttributeSet attributeset) {
        this.context = context;
        handler = new MessageHandler(this);
        flingGestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        flingGestureDetector.setIsLongpressEnabled(false);

        TypedArray typedArray = context.obtainStyledAttributes(attributeset, R.styleable.androidWheelView);
        textSize = typedArray.getInteger(R.styleable.androidWheelView_awv_textsize, DEFAULT_TEXT_SIZE);
        textSize = (int) (Resources.getSystem().getDisplayMetrics().density * textSize);
        lineSpacingMultiplier = typedArray.getFloat(R.styleable.androidWheelView_awv_lineSpace, DEFAULT_LINE_SPACE);
        centerTextColor = typedArray.getInteger(R.styleable.androidWheelView_awv_centerTextColor, 0xff313131);
        outerTextColor = typedArray.getInteger(R.styleable.androidWheelView_awv_outerTextColor, 0xffafafaf);
        dividerColor = typedArray.getInteger(R.styleable.androidWheelView_awv_dividerTextColor, 0xffc5c5c5);
        itemsVisibleCount = typedArray.getInteger(R.styleable.androidWheelView_awv_itemsVisibleCount, DEFAULT_VISIBIE_ITEMS);
        if (itemsVisibleCount % 2 == 0) {
            itemsVisibleCount = DEFAULT_VISIBIE_ITEMS;
        }
        isLoop = typedArray.getBoolean(R.styleable.androidWheelView_awv_isLoop, true);
        typedArray.recycle();

        drawingStrings = new String[itemsVisibleCount];

        totalScrollY = 0;
        initPosition = -1;

        initPaints();
    }


    /**
     * 设置可见的items数量，必须是奇数
     *
     * @param visibleNumber
     */
    public void setItemsVisibleCount(int visibleNumber) {
        if (visibleNumber % 2 == 0) {
            return;
        }
        if (visibleNumber != itemsVisibleCount) {
            itemsVisibleCount = visibleNumber;
            drawingStrings = new String[itemsVisibleCount];
        }
    }


    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(outerTextColor);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(Typeface.MONOSPACE);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(centerTextColor);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(scaleX);
        paintCenterText.setTypeface(Typeface.MONOSPACE);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

    }

    private void remeasure() {
        if (items == null) {
            return;
        }

        measuredWidth = getMeasuredWidth();

        measuredHeight = getMeasuredHeight();

        if (measuredWidth == 0 || measuredHeight == 0) {
            return;
        }

        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();

        measuredWidth = measuredWidth - paddingRight;

        paintCenterText.getTextBounds("\u661F\u671F", 0, 2, tempRect); // 星期
        maxTextHeight = tempRect.height();
        halfCircumference = (int) (measuredHeight * Math.PI / 2);

        maxTextHeight = (int) (halfCircumference / (lineSpacingMultiplier * (itemsVisibleCount - 1)));

        radius = measuredHeight / 2;
        firstLineY = (int) ((measuredHeight - lineSpacingMultiplier * maxTextHeight) / 2.0F);
        secondLineY = (int) ((measuredHeight + lineSpacingMultiplier * maxTextHeight) / 2.0F);
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (items.size() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }

        preCurrentIndex = initPosition;
    }

    void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            float itemHeight = lineSpacingMultiplier * maxTextHeight;
            mOffset = (int) ((totalScrollY % itemHeight + itemHeight) % itemHeight);
            if ((float) mOffset > itemHeight / 2.0F) {
                mOffset = (int) (itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    protected final void scrollBy(float velocityY) {
        cancelFuture();
        // 修改这个值可以改变滑行速度
        int velocityFling = 10;
        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, velocityFling, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     * 设置不要循环模块，默认是循环模式
     */
    public void setNotLoop() {
        isLoop = false;
    }

    /**
     * set text size in dp
     * @param size
     */
    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
            paintOuterText.setTextSize(textSize);
            paintCenterText.setTextSize(textSize);
        }
    }

    public final void setInitPosition(int initPosition) {
        if (initPosition < 0) {
            this.initPosition = 0;
        } else {
            if (items != null && items.size() > initPosition) {
                this.initPosition = initPosition;
            }
        }
    }

    public final void setListener(OnItemSelectedListener OnItemSelectedListener) {
        onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setItems(List<String> items) {
        this.items = items;
        remeasure();
        invalidate();
    }

    public final int getSelectedItem() {
        return selectedItem;
    }
//
//    protected final void scrollBy(float velocityY) {
//        Timer timer = new Timer();
//        mTimer = timer;
//        timer.schedule(new InertiaTimerTask(this, velocityY, timer), 0L, 20L);
//    }

    protected final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }


    /**
     * 设置中间文字的scaleX的值，如果为1.0，则没有错位效果,
     * link https://github.com/weidongjian/androidWheelView/issues/10
     *
     * @param scaleX
     */
    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (items == null) {
            return;
        }


        change = (int) (totalScrollY / (lineSpacingMultiplier * maxTextHeight));
        preCurrentIndex = initPosition + change % items.size();

        if (!isLoop) {
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > items.size() - 1) {
                preCurrentIndex = items.size() - 1;
            }
        } else {
            if (preCurrentIndex < 0) {
                preCurrentIndex = items.size() + preCurrentIndex;
            }
            if (preCurrentIndex > items.size() - 1) {
                preCurrentIndex = preCurrentIndex - items.size();
            }
        }

        int j2 = (int) (totalScrollY % (lineSpacingMultiplier * maxTextHeight));
        // 设置as数组中每个元素的值
        int k1 = 0;
        while (k1 < itemsVisibleCount) {
            int l1 = preCurrentIndex - (itemsVisibleCount / 2 - k1);
            if (isLoop) {
                while (l1 < 0) {
                    l1 = l1 + items.size();
                }
                while (l1 > items.size() - 1) {
                    l1 = l1 - items.size();
                }
                drawingStrings[k1] = items.get(l1);
            } else if (l1 < 0) {
                drawingStrings[k1] = "";
            } else if (l1 > items.size() - 1) {
                drawingStrings[k1] = "";
            } else {
                drawingStrings[k1] = items.get(l1);
            }
            k1++;
        }
        canvas.drawLine(paddingLeft, firstLineY, measuredWidth, firstLineY, paintIndicator);
        canvas.drawLine(paddingLeft, secondLineY, measuredWidth, secondLineY, paintIndicator);

        int i = 0;
        while (i < itemsVisibleCount) {
            canvas.save();
            // L(弧长)=α（弧度）* r(半径) （弧度制）
            // 求弧度--> (L * π ) / (π * r)   (弧长X派/半圆周长)
            float itemHeight = maxTextHeight * lineSpacingMultiplier;
            double radian = ((itemHeight * i - j2) * Math.PI) / halfCircumference;
            // 弧度转换成角度(把半圆以Y轴为轴心向右转90度，使其处于第一象限及第四象限
            if (radian >= Math.PI || radian <= 0) {
                canvas.restore();
            } else {
                int translateY = (int) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 条目经过第一条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintOuterText, tempRect), maxTextHeight, paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintCenterText, tempRect), maxTextHeight, paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 条目经过第二条线
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintCenterText, tempRect), maxTextHeight, paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintOuterText, tempRect), maxTextHeight, paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 中间条目
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintCenterText, tempRect), maxTextHeight, paintCenterText);
                    selectedItem = items.indexOf(drawingStrings[i]);
                } else {
                    // 其他条目
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.drawText(drawingStrings[i], getTextX(drawingStrings[i], paintOuterText, tempRect), maxTextHeight, paintOuterText);
                }
                canvas.restore();
            }
            i++;
        }
    }

    // 绘制文字起始位置
    private int getTextX(String a, Paint paint, Rect rect) {
        paint.getTextBounds(a, 0, a.length(), rect);
        // 获取到的是实际文字宽度
        int textWidth = rect.width();
        // 转换成绘制文字宽度
        textWidth *= scaleX;
        return (measuredWidth - paddingLeft - textWidth) / 2 + paddingLeft;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        remeasure();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = flingGestureDetector.onTouchEvent(event);
        float itemHeight = lineSpacingMultiplier * maxTextHeight;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                previousY = event.getRawY();

                totalScrollY = (int) (totalScrollY + dy);

                // 边界处理。
                if (!isLoop) {
                    float top = -initPosition * itemHeight;
                    float bottom = (items.size() - 1 - initPosition) * itemHeight;

                    if (totalScrollY < top) {
                        totalScrollY = (int) top;
                    } else if (totalScrollY > bottom) {
                        totalScrollY = (int) bottom;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            default:
                if (!eventConsumed) {
                    float y = event.getY();
                    double l = Math.acos((radius - y) / radius) * radius;
                    int circlePosition = (int) ((l + itemHeight / 2) / itemHeight);

                    float extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight;
                    mOffset = (int) ((circlePosition - itemsVisibleCount / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 处理拖拽事件
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 处理条目点击事件
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
        }

        invalidate();
        return true;
    }
}
