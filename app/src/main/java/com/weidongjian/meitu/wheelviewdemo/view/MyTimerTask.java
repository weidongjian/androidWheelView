// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.qingchifan.view:
//            LoopView

final class MyTimerTask extends TimerTask {

    float a;
    float b;
    final int c;
    final Timer timer;
    final LoopView loopView;

    MyTimerTask(LoopView loopview, int i, Timer timer) {
        super();
        this.loopView = loopview;
        c = i;
        this.timer = timer;

        a = 2.147484E+09F;
        b = 0.0F;
    }

    public final void run() {
        if (a == 2.147484E+09F) {
            a = (float) (c - LoopView.a(loopView)) * loopView.l * (float) loopView.h;
            if (c > LoopView.a(loopView)) {
                b = -1000F;
            } else {
                b = 1000F;
            }
        }
        if (Math.abs(a) < 1.0F) {
            timer.cancel();
            loopView.handler.sendEmptyMessage(2000);
            return;
        }
        int j = (int) ((b * 10F) / 1000F);
        int i = j;
        if (Math.abs(a) < (float) Math.abs(j)) {
            i = (int) (-a);
        }
        LoopView loopview = loopView;
        loopview.totalScrollY = loopview.totalScrollY - i;
        float f = a;
        a = (float) i + f;
        loopView.handler.sendEmptyMessage(1000);
    }
}
