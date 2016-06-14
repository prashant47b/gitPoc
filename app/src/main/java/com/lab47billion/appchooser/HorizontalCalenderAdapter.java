package com.lab47billion.appchooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
public class HorizontalCalenderAdapter extends RecyclerView.Adapter<HorizontalCalenderAdapter.ViewHolder> {

    private Context context;
    public int screenWidth;
    private static final String tag = "HorizontalCalenderAdapter";
    private List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[]{"", "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

    public HorizontalCalenderAdapter(Context context, int screenWidth) {
        this.context = context;
        this.screenWidth = screenWidth;
        this.list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        initList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.performance_custom_layout, null);
        int width = screenWidth / 5;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get a reference to the Day gridcell

        // ACCOUNT FOR SPACING
        String[] day_color = list.get(position).split("-");
        String thedate = day_color[0];
        int weekDay = Integer.parseInt(day_color[2]);
        int month = Integer.parseInt(day_color[3]);

        // Set the Day GridCell
        holder.dateOfmonth.setText(thedate);
        holder.month.setText(months[month]);
        holder.dayOfmonth.setText(weekdays[weekDay]);
        if (day_color[1].equals("GREY")) {
            holder.itemView.setBackgroundColor(context.getResources()
                    .getColor(android.R.color.darker_gray));
        } else {
            holder.itemView.setBackgroundColor(context.getResources()
                    .getColor(android.R.color.white));

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateOfmonth;
        TextView dayOfmonth;
        TextView month;

        public ViewHolder(View itemView) {
            super(itemView);
            dateOfmonth = (TextView) itemView.findViewById(R.id.dateOfmonth);
            dayOfmonth = (TextView) itemView.findViewById(R.id.dayOfmonth);
            month = (TextView) itemView.findViewById(R.id.month);
        }
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    /**
     * Initialize date list
     */
    private void initList() {
        int trailingSpaces = 4;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;
        Calendar _calendar = Calendar.getInstance(Locale.getDefault());
        int mm = _calendar.get(Calendar.MONTH) + 1;
        int yy = _calendar.get(Calendar.YEAR);

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
//        Log.e("Week",""+weekdays[cal.get(Calendar.DAY_OF_WEEK)]);

//        if (currentMonth == 11) {
//            prevMonth = currentMonth - 1;
//            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
//            nextMonth = 0;
//            prevYear = yy;
//            nextYear = yy + 1;
//        } else if (currentMonth == 0) {
//            prevMonth = 11;
//            prevYear = yy - 1;
//            nextYear = yy;
//            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
//            nextMonth = 1;
//        } else {
//            prevMonth = currentMonth - 1;
//            nextMonth = currentMonth + 1;
//            nextYear = yy;
//            prevYear = yy;
//            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
//        }
//
//        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        trailingSpaces = currentWeekDay;
//
//        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
//            if (mm == 2)
//                ++daysInMonth;
//            else if (mm == 3)
//                ++daysInPrevMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            _calendar.add(Calendar.DATE, -1);
            list.add(_calendar.get(Calendar.DATE)
                    + "-GREY"
                    + "-"
                    +_calendar.get(Calendar.DAY_OF_WEEK)
                    + "-"
                    + _calendar.get(Calendar.MONTH));
        }
        Collections.reverse(list);

        _calendar.setTime(Calendar.getInstance().getTime());
        list.add(_calendar.get(Calendar.DATE)
                + "-BLUE"
                + "-"
                + _calendar.get(Calendar.DAY_OF_WEEK)
                + "-"
                + _calendar.get(Calendar.MONTH));

        // Current Month Days
        for (int i = 1; i <= 40; i++) {
            _calendar.add(Calendar.DATE, 1);


            list.add(_calendar.get(Calendar.DATE)
                    + "-BLACK"
                    + "-"
                    + _calendar.get(Calendar.DAY_OF_WEEK)
                    + "-"
                    + _calendar.get(Calendar.MONTH));
        }

//        // Leading Month days
//        for (int i = 0; i < list.size() % 7; i++) {
////            Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
//            list.add(String.valueOf(i + 1) + "-GREY" + "-"
//                    + getMonthAsString(nextMonth) + "-" + nextYear);
//        }
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    private void getWeekDay() {
        Calendar calendar = Calendar.getInstance();
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    public Calendar getDate(int position) {
        Calendar calendar = Calendar.getInstance();
        try {
            String[] date = list.get(position).split("-");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[3]);
            calendar.set(calendar.get(Calendar.YEAR), month, day);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return calendar;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
}
