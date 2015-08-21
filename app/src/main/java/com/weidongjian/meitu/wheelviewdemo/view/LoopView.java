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

    Timer A;
    int B;
    Handler C;
    ae D;
    private GestureDetector E;
    private int F;
    private android.view.GestureDetector.SimpleOnGestureListener G;
    Context a;
    Paint b;
    Paint c;
    Paint d;
    ArrayList e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    float l;
    boolean m;
    int n;
    int o;
    int p;
    int q;
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
        f = 0;
        i = 0xffafafaf;
        j = 0xff313131;
        k = 0xffc5c5c5;
        l = 2.0F;
        m = true;
        q = -1;
        r = 9;
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        B = 0;
        G = new aa(this);
        C = new ab(this);
        a = context;
        a(16F);
    }

    public LoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        f = 0;
        i = 0xffafafaf;
        j = 0xff313131;
        k = 0xffc5c5c5;
        l = 2.0F;
        m = true;
        q = -1;
        r = 9;
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        B = 0;
        G = new aa(this);
        C = new ab(this);
        a = context;
        a(16F);
    }

    public LoopView(Context context, AttributeSet attributeset, int i1) {
        super(context, attributeset, i1);
        f = 0;
        i = 0xffafafaf;
        j = 0xff313131;
        k = 0xffc5c5c5;
        l = 2.0F;
        m = true;
        q = -1;
        r = 9;
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        B = 0;
        G = new aa(this);
        C = new ab(this);
        a = context;
        a(16F);
    }

    static int a(LoopView loopview) {
        return loopview.F;
    }

    static void b(LoopView loopview) {
        loopview.f();
    }

    private void d() {
        if (e == null) {
            return;
        }
        b = new Paint();
        b.setColor(i);
        b.setAntiAlias(true);
        b.setTypeface(Typeface.MONOSPACE);
        b.setTextSize(f);
        c = new Paint();
        c.setColor(j);
        c.setAntiAlias(true);
        c.setTextScaleX(1.05F);
        c.setTypeface(Typeface.MONOSPACE);
        c.setTextSize(f);
        d = new Paint();
        d.setColor(k);
        d.setAntiAlias(true);
        d.setTypeface(Typeface.MONOSPACE);
        d.setTextSize(f);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        E = new GestureDetector(a, G);
        E.setIsLongpressEnabled(false);
        e();
        t = (int) ((float) h * l * (float) (r - 1));
        s = (int) ((double) (t * 2) / 3.1415926535897931D);
        u = (int) ((double) t / 3.1415926535897931D);
        v = g + f;
        n = (int) (((float) s - l * (float) h) / 2.0F);
        o = (int) (((float) s + l * (float) h) / 2.0F);
        if (q == -1) {
            if (m) {
                q = (e.size() + 1) / 2;
            } else {
                q = 0;
            }
        }
        p = q;
    }

    private void e() {
        Rect rect = new Rect();
        for (int i1 = 0; i1 < e.size(); i1++) {
            String s1 = (String) e.get(i1);
            c.getTextBounds(s1, 0, s1.length(), rect);
            int j1 = rect.width();
            if (j1 > g) {
                g = j1;
            }
            c.getTextBounds("\u661F\u671F", 0, 2, rect);
            j1 = rect.height();
            if (j1 > h) {
                h = j1;
            }
        }

    }

    private void f() {
        int i1 = (int) ((float) B % (l * (float) h));
        Timer timer = new Timer();
        A = timer;
        timer.schedule(new z(this, i1, timer), 0L, 10L);
    }

    public final void a() {
        m = false;
    }

    public final void a(float f1) {
        if (f1 > 0.0F) {
            f = (int) (a.getResources().getDisplayMetrics().density * f1);
        }
    }

    public final void a(int i1) {
        q = i1;
    }

    public final void a(ae ae) {
        D = ae;
    }

    public final void a(ArrayList arraylist) {
        e = arraylist;
        d();
        invalidate();
    }

    public final int b() {
        return F;
    }

    protected final void b(float f1) {
        Timer timer = new Timer();
        A = timer;
        timer.schedule(new ac(this, f1, timer), 0L, 20L);
    }

    protected final void b(int i1) {
        Timer timer = new Timer();
        A = timer;
        timer.schedule(new y(this, i1, timer), 0L, 20L);
    }

    protected final void c() {
        if (D != null) {
            (new Handler()).postDelayed(new ad(this), 200L);
        }
    }

    protected void onDraw(Canvas canvas) {
        String as[];
        if (e == null) {
            super.onDraw(canvas);
            return;
        }
        as = new String[r];
        w = (int) ((float) B / (l * (float) h));
        p = q + w % e.size();
        Log.i("test", (new StringBuilder("scrollY1=")).append(B).toString());
        Log.i("test", (new StringBuilder("change=")).append(w).toString());
        Log.i("test", (new StringBuilder("lineSpacingMultiplier * maxTextHeight=")).append(l * (float) h).toString());
        Log.i("test", (new StringBuilder("preCurrentIndex=")).append(p).toString());
        int i1;
        if (!m) {
            if (p < 0) {
                p = 0;
            }
            if (p > e.size() - 1) {
                p = e.size() - 1;
            }
            // break;
        } else {
            if (p < 0) {
                p = e.size() + p;
            }
            if (p > e.size() - 1) {
                p = p - e.size();
            }
            // continue;
        }
        do {
            int j2 = (int) ((float) B % (l * (float) h));
            int k1 = 0;
            while (k1 < r) {
                int l1 = p - (4 - k1);
                if (m) {
                    i1 = l1;
                    if (l1 < 0) {
                        i1 = l1 + e.size();
                    }
                    l1 = i1;
                    if (i1 > e.size() - 1) {
                        l1 = i1 - e.size();
                    }
                    as[k1] = (String) e.get(l1);
                } else if (l1 < 0) {
                    as[k1] = "";
                } else if (l1 > e.size() - 1) {
                    as[k1] = "";
                } else {
                    as[k1] = (String) e.get(l1);
                }
                k1++;
            }
            k1 = (v - g) / 2;
            canvas.drawLine(0.0F, n, v, n, d);
            canvas.drawLine(0.0F, o, v, o, d);
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
                        canvas.drawText(as[j1], k1, h, b);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, n - i2, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, c);
                        canvas.restore();
                    } else if (i2 <= o && h + i2 >= o) {
                        canvas.save();
                        canvas.clipRect(0, 0, v, o - i2);
                        canvas.drawText(as[j1], k1, h, c);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0, o - i2, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, b);
                        canvas.restore();
                    } else if (i2 >= n && h + i2 <= o) {
                        canvas.clipRect(0, 0, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, c);
                        F = e.indexOf(as[j1]);
                    } else {
                        canvas.clipRect(0, 0, v, (int) ((float) h * l));
                        canvas.drawText(as[j1], k1, h, b);
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
                B = (int) ((float) B + z);
                if (!m) {
                    if (B > (int) ((float) (-q) * (l * (float) h))) {
                        break; /* Loop/switch isn't completed */
                    }
                    B = (int) ((float) (-q) * (l * (float) h));
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!E.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
                    f();
                }
                return true;
        }

        if (B < (int)((float)(e.size() - 1 - q) * (l * (float)h))) {
            invalidate();
        }else {
            B = (int)((float)(e.size() - 1 - q) * (l * (float)h));
            invalidate();
        }

        if (!E.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
            f();
        }
        return true;
    }
}
