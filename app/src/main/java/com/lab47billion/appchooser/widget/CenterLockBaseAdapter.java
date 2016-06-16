package com.lab47billion.appchooser.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lab47billion.appchooser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

    public CenterLockBaseAdapter(Context context, int screenWidth, int sideItems) {
        this.context = context;
        this.screenWidth = screenWidth;
        if (sideItems < 0) {
            throw new IllegalArgumentException("Number of items on each side must be grater or equal to 0.");
        } else if (mSideItems != sideItems) {
            mSideItems = sideItems;
        }
    }

    protected abstract int getCustomLayoutResource();

    protected abstract RecyclerView.ViewHolder getViewHolder(View view);

    public void setList(List list) {
        this.list = list;
        for (int i = 0; i < mSideItems; i++) {
            if (list != null) {
                list.add(null);
            }
        }
        notifyDataSetChanged();
    }

    protected abstract View getFooterView();

    protected abstract View getHeaderView();

    protected abstract RecyclerView.ViewHolder getFooterViewHolder(View view);

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


    public int getScreenViewWidth() {
        return screenWidth / (mSideItems * 2 + 1);
    }

    public int getSideItems() {
        return mSideItems;
    }

    public int getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(int centerPosition) {
        this.centerPosition = centerPosition;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
