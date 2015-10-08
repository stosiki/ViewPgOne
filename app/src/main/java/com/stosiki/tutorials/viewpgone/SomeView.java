package com.stosiki.tutorials.viewpgone;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by mike on 10/7/2015.
 */
public class SomeView extends View {

    private String letter;

    private Paint paintLetter;
    private Paint paintAux;
    private RectF mainRect;

    private RectF selectRect;
    private Paint paintBackground;
    private Rect textBounds;

    private int mainColorResId;
    private int overlayColorResId;
    private int highlightColorResId;

    private int selectedBorderWidthPx;
    private int textSizePx;


    public SomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mainColorResId = getSolidColor();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SomeView, 0, 0);

        try {
            mainColorResId = a.getResourceId(R.styleable.SomeView_main_color, R.color.black);
            overlayColorResId = a.getResourceId(R.styleable.SomeView_overlay_color, R.color.black);
            highlightColorResId = a.getResourceId(R.styleable.SomeView_highlight_color, R.color.black);

            Resources r = context.getResources();
            float selectedBorderWidth = a.getDimension(R.styleable.SomeView_selected_border_width, 10);
            selectedBorderWidthPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    selectedBorderWidth,
                    r.getDisplayMetrics()
            );
            float textSize = a.getDimensionPixelSize(R.styleable.SomeView_text_size, 20);
            textSizePx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    textSize,
                    r.getDisplayMetrics()
            );



            letter = a.getString(R.styleable.SomeView_letter);
        } finally {
            a.recycle();
        }

        setOnClickListener(new OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   setSelected(!isSelected());
                                   invalidate();
                               }
                           }

        );

        textBounds = new Rect();
    }



    private void initRects() {
        if(mainRect == null) {
            selectRect = new RectF(0, 0, getWidth(), getHeight());
            mainRect = new RectF(selectedBorderWidthPx, selectedBorderWidthPx,
                    getWidth() - selectedBorderWidthPx,
                    getHeight() - selectedBorderWidthPx);
        }
    }

    private void initPaints() {
        if(paintBackground == null) {
            paintBackground = new Paint();
            paintBackground.setColor(getResources().getColor(mainColorResId));
            paintAux = new Paint();
            paintAux.setColor(getResources().getColor(overlayColorResId));
            paintLetter = new Paint();
            paintLetter.setColor(getResources().getColor(highlightColorResId));

            paintLetter.setTextSize(textSizePx);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initRects();
        initPaints();

        float letterWidth = paintLetter.measureText(letter, 0, letter.length());

        if(isSelected()) {
            canvas.drawOval(selectRect, paintAux);
        }
        canvas.drawOval(mainRect, paintBackground);

        paintLetter.getTextBounds(letter, 0, 1, textBounds);
        float left = (selectRect.width() - letterWidth) / 2;
        float top = (selectRect.height() - textBounds.height()) / 2;

        canvas.drawText(letter, left, top + textBounds.height(), paintLetter);
    }
}
