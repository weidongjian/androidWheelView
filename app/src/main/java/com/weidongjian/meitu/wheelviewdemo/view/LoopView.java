package com.weidongjian.meitu.wheelviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Weidongjian on 2015/8/18.
 */
public class LoopView extends View {

    Timer mTimer;
    int totalScrollY;
    Handler handler;
    LoopListener loopListener;
    private GestureDetector gestureDetector;
    private int F;
    private android.view.GestureDetector.SimpleOnGestureListener simpleOnGestureListener;
    Context context;
    Paint paintA;
    Paint paintB;
    Paint paintC;
    ArrayList arrayList;
    int textSize;
    int g;
    int h;
    int colorGray;
    int colorBlack;
    int colorGrayLight;
    float l;
    boolean isLoop;
    int n;
    int o;
    int p;
    int positon;
    int r;
    int s;
    int t;
    int u;
    int v;
    int w;
    float x;
    float y;
    float z;

    public LoopView(Context context) {
        super(context);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset, int i1) {
        super(context, attributeset, i1);
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        textSize = 0;
        colorGray = 0xffafafaf;
        colorBlack = 0xff313131;
        colorGrayLight = 0xffc5c5c5;
        l = 2.0F;
        isLoop = true;
        positon = -1;
        r = 9;
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        totalScrollY = 0;
        simpleOnGestureListener = new LoopViewGestureListener(this);
        handler = new MessageHandler(this);
        this.context = context;
        setTextSize(16F);
    }

    static int a(LoopView loopview) {
        return loopview.F;
    }

    static void b(LoopView loopview) {
        loopview.f();
    }

    private void d() {
        if (arrayList == null) {
            return;
        }
        paintA = new Paint();
        paintA.setColor(colorGray);
        paintA.setAntiAlias(true);
        paintA.setTypeface(Typeface.MONOSPACE);
        paintA.setTextSize(textSize);
        paintB = new Paint();
        paintB.setColor(colorBlack);
        paintB.setAntiAlias(true);
        paintB.setTextScaleX(1.05F);
        paintB.setTypeface(Typeface.MONOSPACE);
        paintB.setTextSize(textSize);
        paintC = new Paint();
        paintC.setColor(colorGrayLight);
        paintC.setAntiAlias(true);
        paintC.setTypeface(Typeface.MONOSPACE);
        paintC.setTextSize(textSize);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
        gestureDetector.setIsLongpressEnabled(false);
        e();
        t = (int) ((float) h * l * (float) (r - 1));
        s = (int) ((double) (t * 2) / Math.PI);
        u = (int) ((double) t / Math.PI);
        v = g + textSize;
        n = (int) (((float) s - l * (float) h) / 2.0F);
        o = (int) (((float) s + l * (float) h) / 2.0F);
        if (positon == -1) {
            if (isLoop) {
                positon = (arrayList.size() + 1) / 2;
            } else {
                positon = 0;
            }
        }
        p = positon;
    }

    private void e() {
        Rect rect = new Rect();
        for (int i1 = 0; i1 < arrayList.size(); i1++) {
            String s1 = (String) arrayList.get(i1);
            paintB.getTextBounds(s1, 0, s1.length(), rect);
            int j1 = rect.width();
            if (j1 > g) {
                g = j1;
            }
            paintB.getTextBounds("\u661F\u671F", 0, 2, rect);
            j1 = rect.height();
            if (j1 > h) {
                h = j1;
            }
        }

    }

    private void f() {
        int i1 = (int) ((float) totalScrollY % (l * (float) h));
        Timer timer = new Timer();
        mTimer = timer;
        timer.schedule(new MTimer(this, i1, timer), 0L, 10L);
    }

    public final void setNotLoop() {
        isLoop = false;
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
        }
    }

    public final void setPosition(int position) {
        this.positon = position;
    }

    public final void setListener(LoopListener LoopListener) {
        loopListener = LoopListener;
    }

    public final void setArrayList(ArrayList arraylist) {
        arrayList = arraylist;
        d();
        invalidate();
    }

    public final int b() {
        return F;
    }

    protected final void b(float f1) {
        Timer timer = new Timer();
        mTimer = timer;
        timer.schedule(new LoopTimerTask(this, f1, timer), 0L, 20L);
    }

    protected final void b(int i1) {
        Timer timer = new Timer();
        mTimer = timer;
        timer.schedule(new MyTimerTask(this, i1, timer), 0L, 20L);
    }

    protected final void c() {
        if (loopListener != null) {
            (new Handler()).postDelayed(new LoopRunnable(this), 200L);
        }
    }

    protected void onDraw(Canvas canvas) {
        String as[];
        if (arrayList == null) {
            super.onDraw(canvas);
            return;
        }
        as = new String[r];
        w = (int) ((float) totalScrollY / (l * (float) h));
        p = positon + w % arrayList.size();
        Log.i("test", (new StringBuilder("scrollY1=")).append(totalScrollY).toString());
        Log.i("test", (new StringBuilder("change=")).append(w).toString());
        Log.i("test", (new StringBuilder("lineSpacingMultiplier * maxTextHeight=")).append(l * (float) h).toString());
        Log.i("test", (new StringBuilder("preCurrentIndex=")).append(p).toString());
        int i1;
        if (!isLoop) {
            if (p < 0) {
                p = 0;
            }
            if (p > arrayList.size() - 1) {
                p = arrayList.size() - 1;
            }
            // break;
        } else {
            if (p < 0) {
                p = arrayList.size() + p;
            }
            if (p > arrayList.size() - 1) {
                p = p - arrayList.size();
            }
            // continue;
        }
        do {
            int j2 = (int) ((float) totalScrollY % (l * (float) h));
            int k1 = 0;
            while (k1 < r) {
                int l1 = p - (4 - k1);
                if (isLoop) {
                    i1 = l1;
                    if (l1 < 0) {
                        i1 = l1 + arrayList.size();
                    }
                    l1 = i1;
                    if (i1 > arrayList.size() - 1) {
                        l1 = i1 - arrayList.size();
                    }
                    as[k1] = (String) arrayList.get(l1);
                } else if (l1 < 0) {
                    as[k1] = "";
                } else if (l1 > arrayList.size() - 1) {
                    as[k1] = "";
                } else {
                    as[k1] = (String) arrayList.get(l1);
                }
                k1++;
            }
            k1 = (v - g) / 2;
            canvas.drawLine(0.0F, n, v, n, paintC);
            canvas.drawLine(0.0F, o, v, o, paintC);
            int j1 = 0;
            while (j1 < r) {
                canvas.save();
                double d1 = ((double) ((float) (h * j1) * l - (float) j2) * 3.1415926535897931D) / (double) t;
                float f1 = (float) (90D - (d1 / 3.1415926535897931D) * 180D);
                if (f1 >= 90F || f1 <= -90F) {
                    canvas.restore();
                } else {
                    int i2 = (int) ((double) u - Math.cos(d1) * (double) u - (Math.sin(d1) * (double) h) / 2D);
                    canvas.translate(0.0F, i2);
                    canvas.scale(1.0F, (float) Math.sin(d1));
                    if (i2 <= n && h + i2 >= n) {
                        canvas.save();
                        canvas.clipRect(0, 0, v, n - i2);
                        canvas.drawText(as[j1], k1, h, paintA);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, n - i2, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, paintB);
                        canvas.restore();
                    } else if (i2 <= o && h + i2 >= o) {
                        canvas.save();
                        canvas.clipRect(0, 0, v, o - i2);
                        canvas.drawText(as[j1], k1, h, paintB);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, o - i2, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, paintA);
                        canvas.restore();
                    } else if (i2 >= n && h + i2 <= o) {
                        canvas.clipRect(0, 0, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, paintB);
                        F = arrayList.indexOf(as[j1]);
                    } else {
                        canvas.clipRect(0, 0, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, paintA);
                    }
                    canvas.restore();
                }
                j1++;
            }
            super.onDraw(canvas);
            return;
        } while (true);
    }

    protected void onMeasure(int i1, int j1) {
        d();
        setMeasuredDimension(v, s);
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = motionevent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                y = motionevent.getRawY();
                z = x - y;
                x = y;
                totalScrollY = (int) ((float) totalScrollY + z);
                if (!isLoop) {
                    if (totalScrollY > (int) ((float) (-positon) * (l * (float) h))) {
                        break; /* Loop/switch isn't completed */
                    }
                    totalScrollY = (int) ((float) (-positon) * (l * (float) h));
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
                    f();
                }
                return true;
        }

        if (totalScrollY < (int)((float)(arrayList.size() - 1 - positon) * (l * (float)h))) {
            invalidate();
        }else {
            totalScrollY = (int)((float)(arrayList.size() - 1 - positon) * (l * (float)h));
            invalidate();
        }

        if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
            f();
        }
        return true;
    }
}
