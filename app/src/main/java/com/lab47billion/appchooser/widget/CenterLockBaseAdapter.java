package com.lab47billion.appchooser.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by prashant on 6/10/2016.
 */
public abstract class CenterLockBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public int screenWidth;
    private static final String tag = "HorizontalCalenderAdapter";
    private int mSideItems = 2;
    private static final int DATE_VIEW = 0;
    private static final int FOOTER_VIEW = 1;
    private static final int HEADER_VIEW=2;
    private int centerPosition;
    public List list;

    /**
     * Default constructor to get the required parameters
     * @param context Activity to get the device width
     * @param sideItems no of side items to be appeared on both sides
     */

    public CenterLockBaseAdapter(Activity context, int sideItems) {
        this.context = context;
        int mWidth = context.getWindowManager().getDefaultDisplay().getWidth();
        this.screenWidth = mWidth;
        if (sideItems < 0) {
            throw new IllegalArgumentException("Number of items on each side must be grater or equal to 0.");
        } else if (mSideItems != sideItems) {
            mSideItems = sideItems;
        }
    }
    // get the custom layout resource id to be shown.
    protected abstract int getCustomLayoutResource();

    // get the viewHolder for the custom layout passed
    protected abstract RecyclerView.ViewHolder getViewHolder(View view);

    /**
     * Update the list to add footer elements in it
     * @param list pass the item list in it (Avoid adding null on it)
     */
    public void setList(List list) {
        this.list = list;
        for (int i = 0; i < mSideItems; i++) {
            if (list != null) {
                list.add(null);
            }
        }
        notifyDataSetChanged();
    }
    // set the footer View to be added if null added the default footer.
    protected abstract View getFooterView();

    // set the Header View to be added if null added the default header.
    protected abstract View getHeaderView();

    // set the footer ViewHolder to be added if null added the default footer view holder.
    protected abstract RecyclerView.ViewHolder getFooterViewHolder(View view);

    // set the header ViewHolder to be added if null added the default header view holder.
    protected abstract RecyclerView.ViewHolder getHeaderViewHolder(View view);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int width = screenWidth / (mSideItems * 2 + 1);
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == DATE_VIEW) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            view = LayoutInflater.from(context).inflate(getCustomLayoutResource(), null);
            view.setLayoutParams(layoutParams);
            viewHolder = getViewHolder(view);
        } else if (viewType == FOOTER_VIEW) {
            if (getFooterView() != null) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getFooterView().getLayoutParams());
                layoutParams.width=width;
                view = getFooterView();
                view.setLayoutParams(layoutParams);
            } else {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, 15);
                view = new View(context);
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                view.setLayoutParams(layoutParams);
            }
            if (getFooterViewHolder(view) != null) {
                viewHolder = getFooterViewHolder(view);
            } else {
                viewHolder = new FooterViewHolder(view);
            }
        }else if (viewType==HEADER_VIEW){
            if (getHeaderView() != null) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getHeaderView().getLayoutParams());
                layoutParams.width=width;
                view = getHeaderView();
                view.setLayoutParams(layoutParams);
            } else {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, 15);
                view = new View(context);
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                view.setLayoutParams(layoutParams);
            }
            if (getHeaderViewHolder(view) != null) {
                viewHolder = getHeaderViewHolder(view);
            } else {
                viewHolder = new HeaderViewHolder(view);
            }
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) != null) {
            return DATE_VIEW;
        } else {
            return FOOTER_VIEW;
        }
    }

    /**
     *
     * @return single item width
     */
    public int getScreenViewWidth() {
        return screenWidth / (mSideItems * 2 + 1);
    }

    /**
     *
     * @return number of side items
     */
    public int getSideItems() {
        return mSideItems;
    }

    /**
     *
     * @return the center item position
     */
    public int getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(int centerPosition) {
        this.centerPosition = centerPosition;
    }

    // Default footer view holder
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    // Default header view holder
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
