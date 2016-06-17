package com.lab47billion.appchooser.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab47billion.appchooser.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by prashant on 6/15/2016.
 */
public class CenterLockDateAdapter extends CenterLockBaseAdapter {

    private static final String TAG = "CenterLockDateAdapter";
    public List<String> list;
    public final static String[] weekdays = new String[]{"", "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    public final static String[] months = {"JAN", "FEB", "MAR",
            "APR", "MAY", "JUN", "JUL", "AUG", "SEPT",
            "OCT", "NOV", "DEC"};
    private Typeface aller_rg;
    private Typeface aller_lt;

    public CenterLockDateAdapter(Activity context, int sideItems) {
        super(context, sideItems);
        this.list = new ArrayList<>();
        aller_rg = Typeface.createFromAsset(context.getAssets(), "Aller_Rg.ttf");
        aller_lt = Typeface.createFromAsset(context.getAssets(), "Aller_Lt.ttf");
        initList();
        setList(list);
    }

    @Override
    protected int getCustomLayoutResource() {
        return R.layout.performance_custom_layout;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new DateViewHolder(view);
    }

    @Override
    public void setList(List list) {
        super.setList(list);
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    protected View getHeaderView() {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            DateViewHolder dateViewHolder = (DateViewHolder) holder;
            String[] day_color = list.get(position).split("-");
            String thedate = day_color[0];
            int weekDay = Integer.parseInt(day_color[2]);
            int month = Integer.parseInt(day_color[3]);
            //setup Fonts
            dateViewHolder.dayOfmonth.setTypeface(aller_rg);
            dateViewHolder.dateOfmonth.setTypeface(aller_rg);
            dateViewHolder.month.setTypeface(aller_lt);

            // Set the Day GridCell
            dateViewHolder.dateOfmonth.setText(thedate);
            dateViewHolder.month.setText(months[month]);
            dateViewHolder.dayOfmonth.setText(weekdays[weekDay]);
            if (day_color[1].equals("GREY")) {
                dateViewHolder.viewGroup.setBackgroundResource(R.drawable.item_disabled_backgroung);
                dateViewHolder.dateOfmonth.setTextColor(R.color.item_border_color);
                dateViewHolder.dayOfmonth.setTextColor(R.color.item_border_color);
                dateViewHolder.month.setTextColor(R.color.item_border_color);
            } else if (day_color[1].equals("WHITE")) {
                dateViewHolder.viewGroup.setBackgroundResource(R.drawable.performance_item_bg);
                dateViewHolder.dateOfmonth.setTextColor(Color.BLACK);
                dateViewHolder.dayOfmonth.setTextColor(Color.BLACK);
                dateViewHolder.month.setTextColor(Color.BLACK);
            } else if (day_color[1].equals("RED")) {
                dateViewHolder.viewGroup.setBackgroundResource(R.drawable.performance_item_bg);
                dateViewHolder.dateOfmonth.setTextColor(Color.RED);
                dateViewHolder.dayOfmonth.setTextColor(Color.BLACK);
                dateViewHolder.month.setTextColor(Color.BLACK);
            }
            dateViewHolder.viewGroup.setPadding(2, 10, 2, 20);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Initialize date list
     */
    private void initList() {
        Calendar _calendar = Calendar.getInstance(Locale.getDefault());

        // Trailing Month days for header view
        for (int i = 0; i < getSideItems(); i++) {
            _calendar.add(Calendar.DATE, 1);
            list.add(_calendar.get(Calendar.DATE)
                    + "-GREY"
                    + "-"
                    + _calendar.get(Calendar.DAY_OF_WEEK)
                    + "-"
                    + _calendar.get(Calendar.MONTH));
        }
        Collections.reverse(list);

        _calendar.setTime(Calendar.getInstance().getTime());
        list.add(_calendar.get(Calendar.DATE)
                + "-RED"
                + "-"
                + _calendar.get(Calendar.DAY_OF_WEEK)
                + "-"
                + _calendar.get(Calendar.MONTH));

        // Current Month Days
        for (int i = 1; i <= 40; i++) {
            _calendar.add(Calendar.DATE, -1);
            list.add(_calendar.get(Calendar.DATE)
                    + "-WHITE"
                    + "-"
                    + _calendar.get(Calendar.DAY_OF_WEEK)
                    + "-"
                    + _calendar.get(Calendar.MONTH));
        }
    }

    /**
     * Used to get the date for the position supplied
     * @param position
     * @return Calender object for the specified position
     */

    public Calendar getDate(int position) {
        Calendar calendar = Calendar.getInstance();
        try {
            String[] date = list.get(position).split("-");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[3]);
            calendar.set(calendar.get(Calendar.YEAR), month, day);
        } catch (Exception e) {
            return null;
        }
        return calendar;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateOfmonth;
        TextView dayOfmonth;
        TextView month;
        ViewGroup viewGroup;

        public DateViewHolder(View itemView) {
            super(itemView);
            dateOfmonth = (TextView) itemView.findViewById(R.id.dateOfmonth);
            dayOfmonth = (TextView) itemView.findViewById(R.id.dayOfmonth);
            month = (TextView) itemView.findViewById(R.id.month);
            viewGroup = (ViewGroup) itemView.findViewById(R.id.internal_layout);
        }
    }
}
