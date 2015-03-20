package com.fredde.savingsgoallist.ui.customlayouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquaredLayout extends RelativeLayout {
    // Desired width-to-height ratio - 1.0 for square
    private final double mScale = 1.0;

    public SquaredLayout(Context context) {
        super(context);
    }

    public SquaredLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (width > (int) ((mScale * height) + 0.5)) {
            width = (int) ((mScale * height) + 0.5);
        } else {
            height = (int) ((width / mScale) + 0.5);
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
    }
}