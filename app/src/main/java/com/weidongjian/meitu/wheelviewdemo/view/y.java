// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.qingchifan.view:
//            LoopView

final class y extends TimerTask {

    float a;
    float b;
    final int c;
    final Timer d;
    final LoopView e;

    y(LoopView loopview, int i, Timer timer) {
        super();
        e = loopview;
        c = i;
        d = timer;

        a = 2.147484E+09F;
        b = 0.0F;
    }

    public final void run() {
        if (a == 2.147484E+09F) {
            a = (float) (c - LoopView.a(e)) * e.l * (float) e.h;
            if (c > LoopView.a(e)) {
                b = -1000F;
            } else {
                b = 1000F;
            }
        }
        if (Math.abs(a) < 1.0F) {
            d.cancel();
            e.C.sendEmptyMessage(2000);
            return;
        }
        int j = (int) ((b * 10F) / 1000F);
        int i = j;
        if (Math.abs(a) < (float) Math.abs(j)) {
            i = (int) (-a);
        }
        LoopView loopview = e;
        loopview.B = loopview.B - i;
        float f = a;
        a = (float) i + f;
        e.C.sendEmptyMessage(1000);
    }
}
