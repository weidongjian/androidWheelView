// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.qingchifan.view:
//            LoopView

final class MTimer extends TimerTask {

    int a;
    int b;
    final int c;
    final Timer timer;
    final LoopView loopView;

    MTimer(LoopView loopview, int i, Timer timer) {
        super();
        loopView = loopview;
        c = i;
        this.timer = timer;
        a = 0x7fffffff;
        b = 0;
    }

    public final void run() {
        if (a == 0x7fffffff) {
            if (c < 0) {
                if ((float) (-c) > (loopView.l * (float) loopView.h) / 2.0F) {
                    a = (int) (-loopView.l * (float) loopView.h - (float) c);
                } else {
                    a = -c;
                }
            } else if ((float) c > (loopView.l * (float) loopView.h) / 2.0F) {
                a = (int) (loopView.l * (float) loopView.h - (float) c);
            } else {
                a = -c;
            }
        }
        b = (int) ((float) a * 0.1F);
        if (b == 0) {
            if (a < 0) {
                b = -1;
            } else {
                b = 1;
            }
        }
        if (Math.abs(a) <= 0) {
            timer.cancel();
            loopView.C.sendEmptyMessage(3000);
            return;
        } else {
            LoopView loopview = loopView;
            loopview.B = loopview.B + b;
            loopView.C.sendEmptyMessage(1000);
            a = a - b;
            return;
        }
    }
}
