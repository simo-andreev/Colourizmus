package com.colourizmus;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by simeon on 3/16/17.
 */

public class ProductPagerAdapter extends PagerAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
