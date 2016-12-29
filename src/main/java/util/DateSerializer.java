package util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateSerializer implements JsonDeserializer<Object> {
	public Object deserialize(final JsonElement json, final Type arg1, final JsonDeserializationContext arg2)
			throws JsonParseException {
		try {
			Date date = null;
			final String datejson = json.getAsJsonPrimitive().getAsString();

			if (!datejson.isEmpty()) {
				if (datejson.contains("T")) {
					final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					date = sdf.parse(json.getAsJsonPrimitive().getAsString());

				} else {
					final String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
					final Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
					final Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
					final String result = matcher.replaceAll("$2");
					final String result2 = result.split("-")[0];
					date = new Date(new Long(result2));

				}
			}

			return new java.sql.Date(date.getTime());
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static java.util.Date parseToDate(final java.util.Date date) {

		final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		final String utcTime = sdf.format(date);

		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
		try {
			return dateFormat.parse(utcTime);
		} catch (final ParseException e) {
			return getToday();
		}
	}

	public static java.util.Date parseToDateWithoutTimezone(final java.util.Date date) throws Exception {
		final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
		final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		final String utcTime = sdf.format(date);
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
		return dateFormat.parse(utcTime);
	}

	private static String getTowDigits(final int value) {
		if (value < 10) {
			return "0" + value;
		}
		return value + "";
	}

	/**
	 * Retona uma string de data no formato yyyy-mm-dd hh:MM:ss
	 *
	 * @param date
	 * @return
	 */
	public static String parseToString(final java.util.Date date) {
		if (date != null) {
			final DateFormat format = DateFormat.getDateInstance();
			final Calendar cal = format.getCalendar();
			cal.setTime(date);
			final StringBuilder builder = new StringBuilder();
			builder.append(cal.get(Calendar.YEAR)).append("-").append(getTowDigits(cal.get(Calendar.MONTH) + 1))
					.append("-").append(getTowDigits(cal.get(Calendar.DAY_OF_MONTH))).append(" ")
					.append(getTowDigits(cal.get(Calendar.HOUR_OF_DAY))).append(":")
					.append(getTowDigits(cal.get(Calendar.MINUTE))).append(":")
					.append(getTowDigits(cal.get(Calendar.SECOND)));
			return builder.toString();
		}
		return null;
	}

	/**
	 * Retona uma string de data no formato dd/mm/yyy às HH:mm para ser usado na
	 * exibição das datas da mensagem
	 *
	 * @param date
	 * @return
	 */
	public static String parseToMesageDateString(final java.util.Date date) {
		final DateFormat format = DateFormat.getDateInstance();
		final Calendar cal = format.getCalendar();
		cal.setTime(date);
		final StringBuilder builder = new StringBuilder();
		builder.append(getTowDigits(cal.get(Calendar.DAY_OF_MONTH))).append("/")
				.append(getTowDigits(cal.get(Calendar.MONTH) + 1)).append("/").append(cal.get(Calendar.YEAR))
				.append("\n").append(getTowDigits(cal.get(Calendar.HOUR_OF_DAY))).append(":")
				.append(getTowDigits(cal.get(Calendar.MINUTE)));
		return builder.toString();
	}

	/**
	 * Retorna uma string de data no formato dd/mm/yy
	 *
	 * @param date
	 */
	public static String parseDateToString(final java.util.Date date) {
		final DateFormat format = DateFormat.getDateInstance();
		final Calendar cal = format.getCalendar();
		cal.setTime(date);
		final StringBuilder builder = new StringBuilder();
		builder.append(getTowDigits(cal.get(Calendar.DAY_OF_MONTH))).append("/")
				.append(getTowDigits(cal.get(Calendar.MONTH) + 1)).append("/")
				.append(getTowDigits(cal.get(Calendar.YEAR)));
		return builder.toString();
	}

	public static String parseDateToString(final java.util.Date date, final String format) {
		final DateFormat df = new SimpleDateFormat(format);
		return df.format(date.getTime());
	}

	public static String parseDateToStringWithTrace(final java.util.Date date) {
		final StringBuilder builder = new StringBuilder();
		builder.append(date.getYear()).append("-").append(getTowDigits(date.getMonth())).append("-")
				.append(getTowDigits(date.getDate()));
		return builder.toString();
	}

	public static String getFormatedDateTime(final java.util.Date date) {
		final StringBuilder builder = new StringBuilder();
		builder.append(getTowDigits(date.getDate())).append("/").append(getTowDigits(date.getMonth())).append("/")
				.append(getTowDigits(date.getYear())).append(" ").append(getTowDigits(date.getHours())).append(":")
				.append(getTowDigits(date.getMinutes())).append(":").append(getTowDigits(date.getSeconds()));
		return builder.toString();
	}

	public static String getFormatedTime(final java.util.Date date) {
		final StringBuilder builder = new StringBuilder();
		builder.append(getTowDigits(date.getHours())).append(":").append(getTowDigits(date.getMinutes())).append(":")
				.append(getTowDigits(date.getSeconds()));
		return builder.toString();
	}

	public static java.util.Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static java.util.Date getTodayZeroHours() {
		final java.util.Date now = new java.util.Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}

	public static final boolean isToday(final Date date) {
		try {
			final Date now = getToday();
			return now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
					&& now.getYear() == date.getYear();

		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * Converts the given <code>date</code> from the <code>fromTimeZone</code>
	 * to the <code>toTimeZone</code>. Since java.util.Date has does not really
	 * store time zome information, this actually converts the date to the date
	 * that it would be in the other time zone.
	 *
	 * @param date
	 * @param fromTimeZone
	 * @param toTimeZone
	 * @return
	 */
	public static Date convertTimeZone(Date date, TimeZone fromTimeZone, TimeZone toTimeZone) {
		long fromTimeZoneOffset = getTimeZoneUTCAndDSTOffset(date, fromTimeZone);
		long toTimeZoneOffset = getTimeZoneUTCAndDSTOffset(date, toTimeZone);

		return new Date(date.getTime() + (toTimeZoneOffset - fromTimeZoneOffset));
	}

	/**
	 * Calculates the offset of the <code>timeZone</code> from UTC, factoring in
	 * any additional offset due to the time zone being in daylight savings time
	 * as of the given <code>date</code>.
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	private static long getTimeZoneUTCAndDSTOffset(Date date, TimeZone timeZone) {
		long timeZoneDSTOffset = 0;
		if (timeZone.inDaylightTime(date)) {
			timeZoneDSTOffset = timeZone.getDSTSavings();
		}

		return timeZone.getRawOffset() + timeZoneDSTOffset;
	}
}