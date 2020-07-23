package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * Servlet implementation class MyCalendar
 */
@WebServlet("/MyCalendar")
public class MyCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int year;
	private int month;
	private int day;
	
	private int [] calendarDay;
	private int count;
	private int weekCount;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 今月のカレンダー情報を取得
		getCalendar();

		String todayStr =
				String.valueOf(year) + "年" +
				String.valueOf(month + 1) + "月" +
				String.valueOf(day) + "日";
		
		String dateTimeStr = 
				String.valueOf(year) + "-" +
				String.valueOf(month) + "-" +
				String.valueOf(day);
		
		// 表示データを出力
		out.println("<!doctype html>");
		out.println("<html lang=\"ja\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<link rel=\"stylesheet\" href=\"calendar.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>" + year + "年" + (month + 1) + "月</h1>") ;
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>日</th>");
		out.println("<th>月</th>");
		out.println("<th>火</th>");
		out.println("<th>水</th>");
		out.println("<th>木</th>");
		out.println("<th>金</th>");
		out.println("<th>土</th>");
		out.println("</tr>");
		out.println("<tr>");
		
		for (int i = 0; i < weekCount; i++) {
			for (int j = i * 7; j < i * 7 + 7; j++) {
				if (j % 7 == 0) {
					out.println("<td class=\"sun\">" + calendarDay[j] + "</td>");
				} else if (j % 7 == 6) {
					out.println("<td class=\"sat\">" + calendarDay[j] + "</td>");
				} else {
					out.println("<td>" + calendarDay[j] + "</td>");
				}
			}
			out.println("</tr><tr>");
		}
		
		out.println("</tr>");
		out.println("</table>");
		out.println("<p>今日は <time datetime=" + dateTimeStr + ">" + todayStr + "</time></p>");
		out.println("</body>");
		out.println("</html>");
		
		// out.close();  // これは今は不必要？
}
	
	private void getCalendar ( ) {
		// getInstance() -- デフォルトのタイムゾーンおよびロケールを使用して
		// カレンダを取得する
		Calendar calendar = Calendar.getInstance();
		
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
		
		// 今月が何曜日から開始されているか？
		// 今月の1日をセット
		calendar.set( year, month, 1 );
		// 1日の曜日を取得 -- Sunday:1 ... Saturday:7 
		int startWeek = calendar.get( Calendar.DAY_OF_WEEK);
		
		// 先月が何日までだったか？
		// 今月の1日の前日をセット
		calendar.set( year, month, 0 );
		// 先月の最終の日付を取得
		int beforeMonthLastDay = calendar.get( Calendar.DATE );
		
		// 今月が何日までか？
		// 来月の1日の前日をセット
		calendar.set( year, month + 1, 0 );
		// 今月の最終の日付を取得
		int thisMonthLastDay = calendar.get( Calendar.DATE );
		
		// カレンダーの各欄を配列で用意
		calendarDay = new int[42];
		// カレンダーの各欄の通し番号
		count = 0;
		
		// 先月分の日付をセット
		// startWeekが月曜日つまり 2 ならば、i=0 となる。
		// calendarDay[0] には先月の最終日の日付がセットされる。
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count] = beforeMonthLastDay - i;
			count++;
		}
		
		// 今月分の日付をセット
		for (int i = 1; i <= thisMonthLastDay; i++) {
			calendarDay[count] = i;
			count++;
		}
		
		// 翌月分の日付をセット
		int nextMonthDay = 1;
		// カレンダーの欄に余裕がある限り
		while (count % 7 != 0) {
			calendarDay[count] = nextMonthDay++;
			count++;
		}
		
		// カレンダーに何週分あるか
		weekCount = count / 7;
	}

}

// 修正時刻： Wed Jul 15 18:39:45 2020
