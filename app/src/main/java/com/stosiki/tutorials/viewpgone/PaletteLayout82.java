package com.stosiki.tutorials.viewpgone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

/**
 * Created by mike on 10/8/2015.
 */
public class PaletteLayout82 extends GridLayout {
    private int selectedIdx;

    public PaletteLayout82(Context context, AttributeSet attrs) {
        super(context, attrs);

        setColumnCount(6);
        selectedIdx = 0;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int widthSize = getDefaultSize(0, widthSpec);

        int blockWidth = widthSize / getColumnCount();
        int blockSpec = MeasureSpec.makeMeasureSpec(blockWidth, MeasureSpec.EXACTLY);
        measureChildren(blockSpec, blockSpec);

        setMeasuredDimension(widthSize, widthSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, left, top;
        for(int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            row = i / getColumnCount();
            col = i % getColumnCount();
            left = col * view.getMeasuredWidth();
            top = row * view.getMeasuredHeight();

            view.layout(
                    left,
                    top,
                    left + view.getMeasuredWidth(),
                    top + view.getMeasuredHeight()
            );
        }
    }
}
