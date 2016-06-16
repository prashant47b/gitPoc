package com.lab47billion.appchooser.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by prashant on 6/11/2016.
 */
public class CenterLockRecyclerView extends RecyclerView {

    public static final String TAG = "HorizontalCalender";
    private CenterLockListener centerLockListener;
    private CenterLockBaseAdapter centerLockBaseAdapter;
    //The pivot to be snapped to
    private int mCenterPivot;
    private GestureDetector gestureDetector;
    private int centerItemPosition = 0;
    private boolean isTapped=false;
    private boolean isDragged=false;
    private boolean isSmoothScrolled =false;


    public CenterLockRecyclerView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public CenterLockRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public CenterLockRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public void setAdapter(CenterLockBaseAdapter adapter) {
        super.setAdapter(adapter);
        this.centerLockBaseAdapter = (CenterLockBaseAdapter) adapter;
        addScroll();
        addItemTouchListener();
    }

    public void setCenterLockListener(CenterLockListener centerLockListener) {
        this.centerLockListener = centerLockListener;
        callCenterListener(centerLockBaseAdapter.getSideItems());
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {

        velocityY *= 0.7;
        // velocityX *= 0.7; for Horizontal recycler view. comment velocityY line not require for Horizontal Mode.

        return super.fling(velocityX, velocityY);
    }

    private void addItemTouchListener() {
        this.addOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && gestureDetector.onTouchEvent(e)) {
                    setScrollToPosition(rv.getChildAdapterPosition(childView));
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
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

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //ScrollStopped
                    if (isDragged) {
                        View view = findCenterView(lm);//get the view nearest to center
                        if (view != null) {
                            int viewCenter = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
                            //compute scroll from center
                            int viewWidth = centerLockBaseAdapter.getScreenViewWidth();
                            int scrollNeeded;
                            scrollNeeded = (viewCenter - mCenterPivot) + (viewWidth / 2); // Add subtract any offsets you need here
                            if (scrollNeeded > (viewWidth / 2)) {
                                scrollNeeded = (viewCenter - mCenterPivot) - (viewWidth / 2); //  subtract any offsets you need here
                            }
                            isSmoothScrolled=true;
                            isDragged=false;
                            recyclerView.smoothScrollBy(scrollNeeded, 0);
                        }
                    }
                    if (isSmoothScrolled){
                        if (lm.findLastCompletelyVisibleItemPosition() == centerLockBaseAdapter.list.size() - 1) {
                            callCenterListener(lm.findLastCompletelyVisibleItemPosition() - centerLockBaseAdapter.getSideItems());
                        }
                        if (lm.findFirstVisibleItemPosition() == 0) {
                            callCenterListener(centerLockBaseAdapter.getSideItems());
                        }
                        int position = lm.findLastCompletelyVisibleItemPosition() - centerLockBaseAdapter.getSideItems();
                        callCenterListener(position);
                        isSmoothScrolled=false;
                    }
                    if (isTapped) {
                        int position = lm.findLastCompletelyVisibleItemPosition() - centerLockBaseAdapter.getSideItems();
                        callCenterListener(position);
                        isTapped=false;
                    }
                }
            if(newState==RecyclerView.SCROLL_STATE_DRAGGING ){
                isDragged = true;
            }
            if(newState==RecyclerView.SCROLL_STATE_SETTLING ){
                isDragged = true;
            }
        }

        @Override
        public void onScrolled (RecyclerView recyclerView,int dx, int dy){
            super.onScrolled(recyclerView, dx, dy);
        }
    }
    );
    }

    private void setScrollToPosition(int position) {
        if (position >= centerLockBaseAdapter.getSideItems()) {
            isTapped=true;
            if (position < centerItemPosition) {
                this.smoothScrollToPosition(position - centerLockBaseAdapter.getSideItems());
            } else if (position > centerItemPosition) {
                this.smoothScrollToPosition(position + centerLockBaseAdapter.getSideItems());
            }
        }
    }

    private void callCenterListener(int position) {
        if (position != centerItemPosition) {
            centerItemPosition = position;
            centerLockBaseAdapter.setCenterPosition(position);
            centerLockBaseAdapter.notifyDataSetChanged();
            if (centerLockListener != null) {
                centerLockListener.onCenterLock(position);
            }
        }
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
        return returnView;
    }

}
