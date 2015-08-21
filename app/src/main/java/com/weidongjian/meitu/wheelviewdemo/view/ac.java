// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.qingchifan.view:
//            LoopView

final class ac extends TimerTask {

    float a;
    final float b;
    final Timer c;
    final LoopView d;

    ac(LoopView loopview, float f, Timer timer) {
        super();
        d = loopview;
        b = f;
        c = timer;

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
            c.cancel();
            d.C.sendEmptyMessage(2000);
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        LoopView loopview = d;
        loopview.B = loopview.B - i;
        if (!d.m) {
            if (d.B <= (int) ((float) (-d.q) * (d.l * (float) d.h))) {
                a = 40F;
                d.B = (int) ((float) (-d.q) * (d.l * (float) d.h));
            } else if (d.B >= (int) ((float) (d.e.size() - 1 - d.q) * (d.l * (float) d.h))) {
                d.B = (int) ((float) (d.e.size() - 1 - d.q) * (d.l * (float) d.h));
                a = -40F;
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        d.C.sendEmptyMessage(1000);
    }
}
