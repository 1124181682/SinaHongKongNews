package yk.sinahknews.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YK on 2017/11/13.
 */

public class NewsType {
  public static final String URL_BASE = "https://47.90.63.143/news/sinahk" +
      "?catid=11&apikey=OVrXCLBqf6PP5d2LcvP2PIutWTSBYQsaehjuSqpfxzAg0qejVnNtQ7JACbsuKNpI";
  public static final NewsTypeModel[] typeArray = {
      new NewsTypeModel(11, "两岸新闻"),
      new NewsTypeModel(32,"外娱"),
      new NewsTypeModel(70,"即时财经"),
      new NewsTypeModel(73,"信报财经"),
      new NewsTypeModel(35,"羽毛球"),
      new NewsTypeModel(93,"国际股市"),
      new NewsTypeModel(63,"健康"),
      new NewsTypeModel(71,"财星股评"),
      new NewsTypeModel(109,"数码游戏汇总"),
      new NewsTypeModel(42,"手机"),
      new NewsTypeModel(8,"焦点推荐"),
      new NewsTypeModel(39,"单车"),
      new NewsTypeModel(98,"地产新闻"),
      new NewsTypeModel(69,"财经今日重点"),
  };

  public static class NewsTypeModel implements Parcelable{
    public String name;
    public int id;

    public NewsTypeModel(int id, String name) {
      this.name = name;
      this.id = id;
    }

    protected NewsTypeModel(Parcel in) {
      name = in.readString();
      id = in.readInt();
    }

    public static final Creator<NewsTypeModel> CREATOR = new Creator<NewsTypeModel>() {
      @Override
      public NewsTypeModel createFromParcel(Parcel in) {
        return new NewsTypeModel(in);
      }

      @Override
      public NewsTypeModel[] newArray(int size) {
        return new NewsTypeModel[size];
      }
    };

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(name);
      dest.writeInt(id);
    }
  }
}
