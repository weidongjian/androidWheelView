// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weidongjian.meitu.wheelviewdemo.view;

// Referenced classes of package com.qingchifan.view:
//            LoopView, ae

final class LoopRunnable implements Runnable {

    final LoopView loopView;

    LoopRunnable(LoopView loopview) {
        super();
        loopView = loopview;

    }

    public final void run() {
        ae ae1 = loopView.D;
        int i = LoopView.a(loopView);
        loopView.e.get(LoopView.a(loopView));
        ae1.a(i);
    }
}
