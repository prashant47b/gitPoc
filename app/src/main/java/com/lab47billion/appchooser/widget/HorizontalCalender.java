package com.lab47billion.appchooser.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lab47billion.appchooser.HorizontalCalenderAdapter;

/**
 * Created by prashant on 6/11/2016.
 */
public class HorizontalCalender extends RecyclerView {

    public static final String TAG = "HorizontalCalender";
    private CenterLockListener centerLockListener;
    private HorizontalCalenderAdapter horizontalCalenderAdapter;
    private boolean mAutoSet = true;
    //The pivot to be snapped to
    private int mCenterPivot;
    private int mSideItems = 1;



    public HorizontalCalender(Context context) {
        super(context);
    }

    public HorizontalCalender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalCalender(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.horizontalCalenderAdapter = (HorizontalCalenderAdapter) adapter;
    }

    public void setCenterLockListener(CenterLockListener centerLockListener) {
        this.centerLockListener = centerLockListener;
        addScroll();
    }

    public void setSideItems(int sideItems) {
        if (mSideItems < 0) {
            throw new IllegalArgumentException("Number of items on each side must be grater or equal to 0.");
        } else if (mSideItems != sideItems) {
            mSideItems = sideItems;
        }
    }

    private void addScroll() {
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (mCenterPivot == 0) {

                    // Default pivot , Its a bit inaccurate .
                    // Better pass the center pivot as your Center Indicator view's
                    // calculated center on it OnGlobalLayoutListener event
                    mCenterPivot = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (recyclerView.getLeft() + recyclerView.getRight()) : (recyclerView.getTop() + recyclerView.getBottom());
                }
                if (!mAutoSet) {

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //ScrollStopped
                        View view = findCenterView(lm);//get the view nearest to center
                        int left = view.getLeft();
                        int right = view.getRight();
                        int viewCenter = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
                        //compute scroll from center
                        int scrollNeeded = (viewCenter - mCenterPivot) + ((horizontalCalenderAdapter.getScreenWidth() / 5) / 2); // Add or subtract any offsets you need here
                        recyclerView.smoothScrollBy(scrollNeeded, 0);
                        mAutoSet = true;
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    mAutoSet = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private View findCenterView(LinearLayoutManager lm) {

        int minDistance = 0;
        View view = null;
        View returnView = null;
        boolean notFound = true;

        for (int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && notFound; i++) {

            view = lm.findViewByPosition(i);

            int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
            int leastDifference = Math.abs(mCenterPivot - center);

            if (leastDifference <= minDistance || i == lm.findFirstVisibleItemPosition()) {
                minDistance = leastDifference;
                returnView = view;
            } else {
                notFound = false;

            }
        }
        int position = lm.getPosition(returnView) - 2;
        centerLockListener.onCenterLock(position, horizontalCalenderAdapter.getDate(position));
        return returnView;
    }

}
