package yk.sinahknews.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by YK on 2017/11/14.
 */

public interface ApiService {
  public static final String URL_BASE = "http://47.90.63.143/";
  @GET("/news/sinahk?apikey=OVrXCLBqf6PP5d2LcvP2PIutWTSBYQsaehjuSqpfxzAg0qejVnNtQ7JACbsuKNpI")
  Observable<NewsJson> getNews(@Query("catid") int catid,@Query("pageToken") int pageNum);
}
