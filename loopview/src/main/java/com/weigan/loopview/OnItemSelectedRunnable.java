// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.weigan.loopview;

// Referenced classes of package com.qingchifan.view:
//            LoopView, OnItemSelectedListener

final class OnItemSelectedRunnable implements Runnable {
    final LoopView loopView;

    OnItemSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}
