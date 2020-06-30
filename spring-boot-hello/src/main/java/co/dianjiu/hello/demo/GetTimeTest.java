package co.dianjiu.hello.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetTimeTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH24:MI:SS");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        System.out.println(sdf.format(c.getTime()));
    }
}
