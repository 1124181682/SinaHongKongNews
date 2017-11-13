package yk.sinahknews.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YK on 2017/11/15.
 */

public class TimeUtil {
  public static String StampToDate(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
  }
}
