package date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 一个简单的日期计算实现(添加工作日)
 * @author Lv9
 *
 */
public class CalculateWorkDays {
	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		Date weekend = format.parse("2011-01-21");
		Date workday = format.parse("2011-01-13");
		System.err.println(format.format(addWorkingDays(weekend, 1)));
		System.err.println(format.format(addWorkingDays(workday, 9)));
	}

	/**
	 * 增加工作日(休息日周五周六...别扭..)
	 * 
	 * @param days
	 * @return
	 */
	public static Date addWorkingDays(Date startDate, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if (calendar.get(Calendar.DAY_OF_WEEK) > 5) {
			calendar.add(Calendar.DAY_OF_WEEK, Calendar.DAY_OF_WEEK - calendar.get(Calendar.DAY_OF_WEEK));
		}
		if (days > 5 - calendar.get(Calendar.DAY_OF_WEEK)) {
			int remainingDays = 5 - calendar.get(Calendar.DAY_OF_WEEK);
			days -= remainingDays;
			calendar.add(Calendar.DAY_OF_WEEK, remainingDays);

			int weeks = days / 5;
			if (weeks % 5 != 0) {
				weeks++;
			}
			calendar.add(Calendar.DAY_OF_WEEK, days + weeks * 2);
		} else {
			calendar.add(Calendar.DAY_OF_WEEK, days);
		}

		return calendar.getTime();
	}
}
