package co.dianjiu.hello.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetAgeTest {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
        Date date = sdf.parse( "19830307" );
        String str = sdf.format(new Date());
        int age = getAge(date);
        System.out.println(age>35);
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth){
                    age--;
                }
            }else{
                age--;
            }
        }
        return age;
    }
}
