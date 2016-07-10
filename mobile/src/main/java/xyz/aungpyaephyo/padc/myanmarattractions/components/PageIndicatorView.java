package xyz.aungpyaephyo.padc.myanmarattractions.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import xyz.aungpyaephyo.padc.myanmarattractions.utils.ScreenUtils;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Page Indicator View of the View Pager Screen Slider.
 */
public class PageIndicatorView extends View {
    private final String VIEW_TAG = "PageIndicatorView";

    private final int WHITE_COLOR = 0xFFFFFFFF;
    private final int GRAY_COLOR = 0xFFFFFFFF;
    private final Paint mPaintNormalFill = new Paint(ANTI_ALIAS_FLAG);
    private final Paint mPaintSelectFill = new Paint(ANTI_ALIAS_FLAG);

    private float mPadding;
    private float mRadius;

    private int currentPage;
    private int numPage;

    public PageIndicatorView(Context context) {
        super(context, null);

        this.init();
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        //this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
        super(context, attrs);

        this.init();
    }

    private void init() {
        this.mPaintNormalFill.setStyle(Paint.Style.STROKE);
        this.mPaintNormalFill.setColor(GRAY_COLOR);
        this.mPaintNormalFill.setStrokeWidth(1.5f);

        this.mPaintSelectFill.setStyle(Paint.Style.FILL);
        this.mPaintSelectFill.setColor(WHITE_COLOR);

        this.numPage = 0;
        this.currentPage = 0;

        this.mRadius = ScreenUtils.getObjInstance().getPixelFromDPI(5);
        this.mPadding = ScreenUtils.getObjInstance().getPixelFromDPI(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.numPage == 0)
            return;

        if (this.currentPage >= this.numPage) {
            this.setCurrentPage(this.numPage - 1);
            return;
        }

        //only redraw if valid condition
        int width = this.getWidth();
        float realWidth = this.calculateWith();

        //center circle object
        float dx = (float)(width / 2.0 - realWidth / 2.0);
        float dy = 0;
        //Log.d(VIEW_TAG, "onDraw>>>" + realWidth + "/ " + width + " / height " + this.getHeight());
        for (int iLoop = 0; iLoop < this.numPage; iLoop++) {

            if (iLoop == this.currentPage) {
                canvas.drawCircle(dx + mRadius, dy + mRadius, mRadius, mPaintSelectFill);
            } else {
                canvas.drawCircle(dx + mRadius, dy + mRadius, mRadius, mPaintNormalFill);
            }

            dx += mRadius * 2 + mPadding;
        }
    }

    private float calculateWith() {
        return (this.numPage - 1) * mPadding + this.numPage * this.mRadius * 2 + 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newWidth = this.measureLong(widthMeasureSpec);
        int newHeight = this.measureShort(heightMeasureSpec);
        //Log.d(VIEW_TAG, "onMeasure>>>>>>>> " + newWidth + "/ " + newHeight);
        setMeasuredDimension(newWidth, newHeight);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            result = (int)(getPaddingLeft() + getPaddingRight()
                    + this.calculateWith());
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = (int)(2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    //public method
    public void setNumPage(int num) {
        this.numPage = num;
        this.invalidate();
    }

    public void setCurrentPage(int currentNum) {
        this.currentPage = currentNum;
        this.invalidate();
    }

}
