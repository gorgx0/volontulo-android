package com.stxnext.volontulo;

import android.support.annotation.DrawableRes;

public interface CollapsibleImage {

    void wantCollapse(@DrawableRes int imageResource);

    void wantCollapse(String imagePath);
}
