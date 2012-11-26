package date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarTester {
    public static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Calendar compareCalendar = getCalendarInstance();
        Calendar calendar = getCalendarInstance();
        setTime(calendar, new java.util.Date());
        add(calendar, Calendar.DAY_OF_MONTH, 1);
        get(calendar, Calendar.MONTH);
        getMaximum(calendar, Calendar.MONTH);
        getMinimum(calendar, Calendar.MONTH);
        after(calendar, compareCalendar);
        before(calendar, compareCalendar);
        clear(calendar, Calendar.MONTH);
        clear(calendar);
        getDisplayName(calendar, Calendar.MONTH, Calendar.LONG, Locale.CHINESE);
        getTimeInMillis(calendar);
        getTimeZone(calendar);
        isSet(calendar, Calendar.MONTH);
        roll(calendar, Calendar.YEAR, true);
        roll(calendar, Calendar.YEAR, -5);
        set(calendar, Calendar.YEAR, 1991);
    }

    /**
     * 将给定的日历字段设置为给定值。
     * 
     * @param calendar
     * @param field
     * @param value
     */
    public static void set(Calendar calendar, int field, int value) {
        calendar.set(field, value);
        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 向指定日历字段添加指定（有符号的）时间量，不更改更大的字段。
     * 
     * @param calendar
     * @param field
     * @param amount
     */
    public static void roll(Calendar calendar, int field, int amount) {
        calendar.roll(field, amount);
        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 在给定的时间字段上添加或减去（上/下）单个时间单元(比如小时的单个时间单元就是一小时)，不更改更大的字段。
     * 
     * @param calendar
     * @param field
     * @param up
     */
    public static void roll(Calendar calendar, int field, boolean up) {
        calendar.roll(field, up);
        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 确定给定日历字段是否已经设置了一个值，其中包括因为调用 get 方法触发内部字段计算而导致已经设置该值的情况。
     * 
     * @param calendar
     * @param field
     */
    public static void isSet(Calendar calendar, int field) {
        System.err.println(calendar.isSet(field));
    }

    /**
     * 获得时区。
     * 
     * @param calendar
     */
    public static void getTimeZone(Calendar calendar) {
        System.err.println(calendar.getTimeZone());
    }

    /**
     * 返回此 Calendar 的时间值，以毫秒为单位。
     * 
     * @param calendar
     */
    public static void getTimeInMillis(Calendar calendar) {
        System.err.println(calendar.getTimeInMillis());
    }

    /**
     * 获取一星期的第一天；例如，在美国，这一天是 SUNDAY，而在法国，这一天是 MONDAY。
     * 
     * @param calendar
     */
    public static void getFirstDayOfWeek(Calendar calendar) {
        System.err.println(calendar.getFirstDayOfWeek());
    }

    /**
     * 返回给定 style 和 locale 下的日历 field 值的字符串表示形式。
     * 
     * @param calendar
     * @param field
     * @param style
     * @param locale
     */
    public static void getDisplayName(Calendar calendar, int field, int style, Locale locale) {
        System.err.println(calendar.getDisplayName(field, style, locale));
    }

    /**
     * 返回此 Calendar 实例给定日历字段的最小值。最小值被定义为 get 方法为任何可能时间值返回的最小值。最小值取决于日历系统实例的特定参数。
     * 
     * @param calendar
     * @param field
     */
    public static void getMinimum(Calendar calendar, int field) {
        System.err.println(calendar.getMinimum(field));
    }

    /**
     * 返回此 Calendar 实例给定日历字段的最大值。最大值被定义为 get 方法为任何可能时间值返回的最大值。最大值取决于日历系统实例的特定参数。
     * 
     * @param calendar
     * @param field
     */
    public static void getMaximum(Calendar calendar, int field) {
        System.err.println(calendar.getMaximum(field));
    }

    /**
     * 返回给定日历字段的值
     * 
     * @param calendar
     * @param field
     */
    public static void get(Calendar calendar, int field) {
        System.err.println(calendar.get(field));
    }

    /**
     * 将此 Calendar 的给定日历字段值和时间值（从历元至现在的毫秒偏移量）设置成未定义。
     * 
     * @param calendar
     */
    public static void clear(Calendar calendar, int field) {
        calendar.clear(field);
        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 将此 Calendar 的所有日历字段值和时间值（从历元至现在的毫秒偏移量）设置成未定义。
     * 
     * @param calendar
     */
    public static void clear(Calendar calendar) {
        calendar.clear();
        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 判断此 calendar 表示的时间是否在指定 compareCalendar 表示的时间之之前，返回判断结果。
     * 
     * @param calendar
     * @param compareCalendar
     */
    public static void before(Calendar calendar, Calendar compareCalendar) {
        System.err.println(calendar.before(compareCalendar));
    }

    /**
     * 判断此 calendar 表示的时间是否在指定 compareCalendar 表示的时间之后，返回判断结果。
     * 
     * @param calendar
     * @param compareCalendar
     */
    public static void after(Calendar calendar, Calendar compareCalendar) {
        System.err.println(calendar.after(compareCalendar));
    }

    /**
     * 根据日历的规则为给定的日历字段添加或减去指定的时间量
     * 
     * @param calendar
     * @param field
     * @param amount
     */
    public static void add(Calendar calendar, int field, int amount) {
        calendar.add(field, amount);

        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 为日历指定具体时间
     * 
     * @param calendar
     * @param date
     */
    public static void setTime(Calendar calendar, java.util.Date date) {
        calendar.setTime(new java.util.Date());

        System.err.println(DF.format(calendar.getTime()));
    }

    /**
     * 获得一个由当前时间初始化的日历对象.实例类型为java.util.GregorianCalendar.
     * 
     * @return Calendar实例
     */
    public static Calendar getCalendarInstance() {
        return Calendar.getInstance();
    }

    /**
     * 根据不同的时区获得日历对象
     * 
     * @return Calendar实例
     */
    public static Calendar getCalendarInstance(TimeZone timeZone) {
        return Calendar.getInstance(timeZone);
    }
}
