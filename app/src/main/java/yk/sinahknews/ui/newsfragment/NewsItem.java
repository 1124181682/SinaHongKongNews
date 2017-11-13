package yk.sinahknews.ui.newsfragment;

/**
 * Created by YK on 2017/11/14.
 */

public class NewsItem {
  public String title;
  public String[] img;
  public String time;
  public String auther;
  public String tag;

  public NewsItem(String title, String[] img, String time, String auther, String tag) {
    this.title = title;
    this.img = img;
    this.time = time;
    this.auther = auther;
    this.tag = tag;
  }
}
