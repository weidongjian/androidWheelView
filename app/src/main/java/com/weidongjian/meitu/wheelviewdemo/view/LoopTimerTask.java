// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.qingchifan.view:
//            LoopView

final class LoopTimerTask extends TimerTask {

    float a;
    final float b;
    final Timer timer;
    final LoopView loopView;

    LoopTimerTask(LoopView loopview, float f, Timer timer) {
        super();
        loopView = loopview;
        b = f;
        this.timer = timer;
        a = 2.147484E+09F;
    }

    public final void run() {
        if (a == 2.147484E+09F) {
            if (Math.abs(b) > 2000F) {
                if (b > 0.0F) {
                    a = 2000F;
                } else {
                    a = -2000F;
                }
            } else {
                a = b;
            }
        }
        if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
            timer.cancel();
            loopView.C.sendEmptyMessage(2000);
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        LoopView loopview = loopView;
        loopview.B = loopview.B - i;
        if (!loopView.m) {
            if (loopView.B <= (int) ((float) (-loopView.q) * (loopView.l * (float) loopView.h))) {
                a = 40F;
                loopView.B = (int) ((float) (-loopView.q) * (loopView.l * (float) loopView.h));
            } else if (loopView.B >= (int) ((float) (loopView.e.size() - 1 - loopView.q) * (loopView.l * (float) loopView.h))) {
                loopView.B = (int) ((float) (loopView.e.size() - 1 - loopView.q) * (loopView.l * (float) loopView.h));
                a = -40F;
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        loopView.C.sendEmptyMessage(1000);
    }
}
