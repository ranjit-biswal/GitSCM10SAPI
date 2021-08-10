package utils;

import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * This class contains all the date utilities for date manipulation.
 *
 * @author Ranjit.Biswal
 * @since
 */

/**
 * @author Ranjit.Biswal
 *
 */
public class DateUtil {
    static String dateFormatConfigCspQuery = SqlQuery.sDateFormatConfigSettingsCspQuery;
    static String defaultLanguageQuery = SqlQuery.sDefaultLanguageQuery;
    static ResultSet resultSet;
	public static Date _date;

    /**
     * This method is used to get the current year.
     *
     * @return
     */
    public static String getCurrentYear() {
        String year = null;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * This method is used to get the Next year.
     *
     * @return
     */
    public static String getNextYear(int yr) {
        String year = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, yr);
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * This method is used to get the future date of given month difference.
     *
     * @param month
     * @return
     */
    public static String getFutureDate(int month) {
        Calendar now = Calendar.getInstance();
        // Add days to current date using Calendar.add method
        now.add(Calendar.MONTH, month);
        return ((now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DATE)
				+ "/" + now.get(Calendar.YEAR));
    }

    public static String getCurrentSystemDate(String sFormat) {
        DateFormat dateFormat = new SimpleDateFormat(sFormat);
        return (dateFormat.format(new Date()));
    }

    /**
     * This method is used to get the current system date in format mm/dd/yy.
     *
     * @return
     */
    public static String getCurrentSystemDateMDY() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        return (dateFormat.format(new Date()));
    }

    /**
     * This method is used to get past date of given days before and in given
     * format.
     *
     * @param daysBefore
     * @return
     */
    public static String getPastDate(int daysBefore) {
        LocalDate now = LocalDate.now();
        LocalDate pastDate = now.minusDays(daysBefore);
        return pastDate.toString();
    }

    /**
     * This method is used to get the number of days between two given dates.
     *
     * @param dateBeforeString
     * @param dateAfterString
     * @return
     */
    public static long getNoOfDaysBetween(String dateBeforeString, String dateAfterString) {
        LocalDate dateBefore = LocalDate.parse(dateBeforeString);
        LocalDate dateAfter = LocalDate.parse(dateAfterString);
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        return noOfDaysBetween;
    }

    /**
     * This method is used to get the count of days for the given month of given
     * year.
     *
     * @param strYear
     * @param strMonthName
     * @return
     */
    public static int daysCountForMonth(String strYear, String strMonthName) {
        int intYear = Integer.parseInt(strYear); // To hold the year
        int intNumDays = 0;
        if (strMonthName.contains("Feb")) {
            if ((intYear % 4 == 0) && (intYear % 400 == 0) && !(intYear % 100 == 0)) {
                intNumDays = 29;
            } else {
                intNumDays = 28;
            }
        } else if ((strMonthName.contains("Jan")) || (strMonthName.contains("Mar")) || (strMonthName.contains("May"))
                || (strMonthName.contains("Jul")) || (strMonthName.contains("Aug")) || (strMonthName.contains("Oct"))
                || (strMonthName.contains("Dec"))) {
            intNumDays = 31;
        } else {
            intNumDays = 30;
        }
        return intNumDays;
    }

    /**
     * This method is used to get the month value corresponding to the given
     * month name.
     *
     * @param strMonthName
     * @return
     */
    public static int getMonthValue(String strMonthName) {
        int intMonthValue = 0;
        if (strMonthName.contains("January")) {
            intMonthValue = 1;
        } else if (strMonthName.contains("February")) {
            intMonthValue = 2;
        } else if (strMonthName.contains("March")) {
            intMonthValue = 3;
        } else if (strMonthName.contains("April")) {
            intMonthValue = 4;
        } else if (strMonthName.contains("May")) {
            intMonthValue = 5;
        } else if (strMonthName.contains("June")) {
            intMonthValue = 6;
        } else if (strMonthName.contains("July")) {
            intMonthValue = 7;
        } else if (strMonthName.contains("August")) {
            intMonthValue = 8;
        } else if (strMonthName.contains("September")) {
            intMonthValue = 9;
        } else if (strMonthName.contains("October")) {
            intMonthValue = 10;
        } else if (strMonthName.contains("November")) {
            intMonthValue = 11;
        } else if (strMonthName.contains("December")) {
            intMonthValue = 12;
        }
        return intMonthValue;
    }

    /**
     * This method is used to get the month value corresponding to the given
     * month name.
     *
     * @param strMonthName
     * @return
     */
    public static String getMonthValues(String strMonthName) {
        String sMonthValue = null;
        if (strMonthName.contains("January")) {
            sMonthValue = "01";
        } else if (strMonthName.contains("February")) {
            sMonthValue = "02";
        } else if (strMonthName.contains("March")) {
            sMonthValue = "03";
        } else if (strMonthName.contains("April")) {
            sMonthValue = "04";
        } else if (strMonthName.contains("May")) {
            sMonthValue = "05";
        } else if (strMonthName.contains("June")) {
            sMonthValue = "06";
        } else if (strMonthName.contains("July")) {
            sMonthValue = "07";
        } else if (strMonthName.contains("August")) {
            sMonthValue = "08";
        } else if (strMonthName.contains("September")) {
            sMonthValue = "09";
        } else if (strMonthName.contains("October")) {
            sMonthValue = "10";
        } else if (strMonthName.contains("November")) {
            sMonthValue = "11";
        } else if (strMonthName.contains("December")) {
            sMonthValue = "12";
        }
        return sMonthValue;
    }

    /**
     * This method is used to format a string date into given format.
     *
     * @param sDate
     * @param sFormat
     * @return
     */
    public static String changeStringToDateInFormat(String sDate, String sFormat) {
        String sConvertedDate = null;
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat(sFormat);
        try {
            Date date = sourceFormat.parse(sDate);
            sConvertedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sConvertedDate;
    }

    public static String changeSrcToDestDateOnPaymentSuccess(String sDate, String sFormat) {
        String sConvertedDate = null;
        sDate = sDate.split(" ")[0];
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat(sFormat);
        try {
            Date date = sourceFormat.parse(sDate);
            sConvertedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sConvertedDate;
    }

    public static String changeDateFormatFromDateCSP(String sDate, String sFormat) {
        String sConvertedDate = null;
        sDate = sDate.split(" - ")[0];
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MMMM dd,yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat(sFormat);
        try {
            Date date = sourceFormat.parse(sDate);
            sConvertedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sConvertedDate;
    }

    public static String changeDateFormatToDateCSP(String inputDate, String sFormat) {
        String parsedDate = null;
        inputDate = inputDate.split(" - ")[1];
        SimpleDateFormat sourceFormat = new SimpleDateFormat("MMMM dd,yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat(sFormat);
        try {
            Date date = sourceFormat.parse(inputDate);
            parsedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static String changeStringToDateInFormat(String inputDate, String inputFormat, String targetFormat) {
        String parsedDate = null;
        SimpleDateFormat sourceDate = new SimpleDateFormat(inputFormat);
        SimpleDateFormat targetDate = new SimpleDateFormat(targetFormat);
        try {
            Date date = sourceDate.parse(inputDate);
            parsedDate = targetDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    /**
     * This method is used to get the month value of corresponding given month
     * value.
     *
     * @param monthValue
     * @return
     */
    public static String getMonthName(int monthValue) {
        String monthName = null;
        if (monthValue == 1) {
            monthName = "Jan";
        } else if (monthValue == 2) {
            monthName = "Feb";
        } else if (monthValue == 3) {
            monthName = "Mar";
        } else if (monthValue == 4) {
            monthName = "Apr";
        } else if (monthValue == 5) {
            monthName = "May";
        } else if (monthValue == 6) {
            monthName = "Jun";
        } else if (monthValue == 7) {
            monthName = "Jul";
        } else if (monthValue == 8) {
            monthName = "Aug";
        } else if (monthValue == 9) {
            monthName = "Sep";
        } else if (monthValue == 10) {
            monthName = "Oct";
        } else if (monthValue == 11) {
            monthName = "Nov";
        } else if (monthValue == 12) {
            monthName = "Dec";
        }
        return monthName;
    }

    public static String getCurrentMonth() {
        String month = null;
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Calendar cal = Calendar.getInstance();
        month = monthName[cal.get(Calendar.MONTH)];
        return month;
    }

    // Get Previous Month Year value as Jan 2018
    public static String getPreviousMonthYear() {
        String prevMonthYear = null;
        Calendar now = Calendar.getInstance();
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        now = Calendar.getInstance();
        now.add(Calendar.MONTH, -1); // Decrement Month by 1
        if ((now.get(Calendar.MONTH) + 1) == 0) { // Checks if Month is January
            // and Decrements Year by 1
            // and increments Month by
            // 11
            now.add(Calendar.MONTH, 11);
            now.add(Calendar.YEAR, -1);
        }
        prevMonthYear = monthName[(now.get(Calendar.MONTH))] + " " + now.get(Calendar.YEAR); // Drecemented
        // Date
        return prevMonthYear;
    }

    /**
     * Get All Previous Month Year If Year selected as 2020 and Month as
     * September, This method will bring a collection septermer, october,November
     * and December This method is used in One time payment and Add Credit Card
     * etc
     *
     * @return
     */
    public static List<String> collectAllPreviousMonthForYear() {
        ArrayList<String> expectedMonths = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        int iMonth = cal.get(Calendar.MONTH);
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        if (iMonth == 1) {
            expectedMonths.addAll(getAllMonthsForYear());
        } else {
            for (int i = iMonth; i < 12; i++)
                expectedMonths.add(monthName[i]);
        }
        return expectedMonths;
    }

    /**
     * This method is used to get the current complete month.
     * @return
     */
    public static String getCurrentCompleteMonth() {
        String month = null;
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        Calendar cal = Calendar.getInstance();
        month = monthName[cal.get(Calendar.MONTH)];
        return month;
    }

    public static String getCurrentMonthInMMMFormat() {
        String month = null;
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec"};
        Calendar cal = Calendar.getInstance();
        month = monthName[cal.get(Calendar.MONTH)];
        return month;
    }

    public static String getNextMonthInMMMFormat(int nextMonthInNumber) {
        String month = null;
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec"};
        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.MONTH) + nextMonthInNumber;
        if (num > 12) {
            num = num - 12;
        }
        month = monthName[num];
        return month;
    }

    /**
     * This method is used to get the next complete month.
     *
     * @return
     */
    public static String getNextCompleteMonth(int nextMonthInNumber) {
        String month = null;
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        Calendar cal = Calendar.getInstance();
        int num = cal.get(Calendar.MONTH) + nextMonthInNumber;
        if (num > 12) {
            num = num - 12;
        }
        month = monthName[num - 1];
        return month;
    }

    // This method is used for usage page
    public static String convertDateToMonthYear(String actualDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) sdf.parse(actualDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        String month_name = month_date.format(date);
        return month_name;
    }

    // This method is used to check if the date is a valid format as per the
    // config in CSP
    public static boolean isValidDateFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);
        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * This method is used for Converting the hour format getting from the
     * Database to the 12/24 hour format as per the CSP config. Used in Usage
     * Hourly validations
     *
     * @param actualDateTime
     *            - the date and time received from Database.
     * @param expFormat-
     *            The CSP format either 12 Hours/24 Hours that we get by the SQL
     *            Query getDateFormatMetricSettings
     * @return
     */
    public static String convertIntoHourlyAmPmFormat(String actualDateTime, String expFormat) {
        String result = "";
        String actualDateformat = "yyyy-MM-dd HH:mm:ss.sss";
        String expectedHourFormat = null;

        if (expFormat.equals("24 Hour")) {
            expectedHourFormat = "HH:mm a";
        } else {
            expectedHourFormat = "hh:mm a";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(actualDateformat, Locale.US); // PST`
            Date startDTE = null;
            startDTE = formatter.parse(actualDateTime);
            SimpleDateFormat requiredformat = new SimpleDateFormat(expectedHourFormat, Locale.US); // PST`
            result = requiredformat.format(startDTE);
        } catch (Exception e) {
            e.printStackTrace();
            result = actualDateTime;
        }
        return result;
    }

    public static String convertIntoSpecificDateFormat(String givenDate, String givenDateFormat, String resultFormat) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(givenDateFormat, Locale.US); // PST`
            Date startDTE = null;
            startDTE = formatter.parse(givenDate);
            SimpleDateFormat requiredFormat = new SimpleDateFormat(resultFormat, Locale.US); // PST`
            result = requiredFormat.format(startDTE);
        } catch (Exception e) {
            e.printStackTrace();
            result = givenDate;
        }
        return result;
    }

    // This method is used to add Days/Months/Years in given date
    public static HashMap<String, Integer> getAddDaysMonthsAndYears(String dateTime, int days, int months, int year)
            throws ParseException {
        String sDate1 = dateTime;
        Date date1 = new SimpleDateFormat("MMMM dd,yyyy").parse(sDate1);
        System.out.println(sDate1);
        Calendar now = Calendar.getInstance();
        now.setTime(date1);
        now.add(Calendar.DATE, days);
        now.add(Calendar.MONTH, months);
        now.add(Calendar.YEAR, year);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("date", now.get(Calendar.DATE));
        map.put("month", now.get(Calendar.MONTH));
        map.put("year", now.get(Calendar.YEAR));
        _date = now.getTime();
        return map;
    }

    public static String getFullMonthName(String date) throws ParseException {
        String sDate1 = date;
        Date date1 = new SimpleDateFormat("MMMM dd,yyyy").parse(sDate1);
        Calendar now = Calendar.getInstance();
        now.setTime(date1);
        Month month = Month.of(now.get(Calendar.MONTH));
        Locale locale = Locale.getDefault();
        String monthValue = month.getDisplayName(TextStyle.FULL, locale);
        return monthValue;
    }

    public static String getFullMonthNameBilling(String date) throws ParseException {
        String sDate1 = date;
        Date date1 = new SimpleDateFormat("MMMM dd,yyyy").parse(sDate1);
        Calendar now = Calendar.getInstance();
        now.setTime(date1);
        int month_int = now.get(Calendar.MONTH);
        //Locale locale = Locale.getDefault();
        //String monthValue = month.getDisplayName(TextStyle.FULL, locale);
        //return monthValue;
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (month_int >= 0 && month_int <= 11) {
            month = months[month_int];
        }
        return month;


    }

    public static List<String> getAllMonthsForYear() {
        List<String> monthsList = new ArrayList<String>();
        monthsList.clear();
        monthsList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December");

        return monthsList;
    }

    public static String getCurrentDateInFormat(String sFormat) {
        String s = null;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(sFormat);
        s = dateFormat.format(date);
        return s;
    }

    public static String getCurrentDateTimeInOutageUIFormat() {
        String sExpDateTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        sExpDateTime = dtf.format(now);
        System.out.println(dtf.format(now));
        return sExpDateTime;
    }

    public static String getFutureDateTimeInOutageUIFormat(int futureDay, int futureMonth) {
        String sExpDateTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        now = now.plusDays(futureDay);
        now = now.plusMonths(futureMonth);
        sExpDateTime = dtf.format(now);
        System.out.println(dtf.format(now));
        return sExpDateTime;
    }

    public static String getCurrentDateTimeInOutageAPIFormat() {
        String sExpDateTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        sExpDateTime = dtf.format(now);
        System.out.println(dtf.format(now));
        return sExpDateTime;
    }

    public static String getFutureDateTimeInOutageAPIFormat(int futureDay, int futureMonth) {
        String sExpDateTime = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        now = now.plusDays(futureDay);
        now = now.plusMonths(futureMonth);
        sExpDateTime = dtf.format(now);
        System.out.println(dtf.format(now));
        return sExpDateTime;
    }

    public static String getGivenDateInGivenFormat(String date, String actFormat, String expFormat) {
        String expDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expFormat);
        Date actDate = null;
        try {
            actDate = new SimpleDateFormat(actFormat).parse(date);
            expDate = simpleDateFormat.format(actDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return expDate;
    }

    public static void main(String[] args) {
		/*ArrayList<String> expectedMonths = new ArrayList<String>();
		expectedMonths.addAll(collectAllPreviousMonthForYear());
		System.out.println(collectAllPreviousMonthForYear());*/
        int i = DateUtil.getNoOfDaysInCurrentMonth();
        int j = DateUtil.getNoOfDaysInParticularYearOfMonth(2020, 9);
    }

    public static int getNoOfDaysInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return monthMaxDays;
    }

    public static int getNoOfDaysInParticularYearOfMonth(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
        return daysInMonth;
    }

    public static String getCurrentDateForExportToExcel() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static double getCurrentTimeInHourFormate() {
        DateFormat dateFormat = new SimpleDateFormat("hh.mm");
        String dateString = dateFormat.format(new Date()).toString();
        System.out.println("Current time in AM/PM: " + dateString);
        double time = Double.parseDouble(dateString);
        return time;
    }
}