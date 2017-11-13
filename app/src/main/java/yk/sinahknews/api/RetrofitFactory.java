package yk.sinahknews.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yk.sinahknews.util.CacheInterceptor;
import yk.sinahknews.util.HttpCache;
import yk.sinahknews.util.TrustManager;

/**
 * @author tufei
 * @date 2017/9/11
 */

public class RetrofitFactory {
  public static final String URL_BASE = "https://47.90.63.143/";
  public static final int HTTP_CONNECT_TIMEOUT=10;
  public static final int HTTP_READ_TIMEOUT=10;
  private static final int HTTP_WRITE_TIMEOUT = 10;
  private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
  private static final HttpLoggingInterceptor interceptor =
      new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
  private static ApiService sApiService;
    private static Retrofit createRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createGsonConverterFactory())
                .build();
        return retrofit;
    }

    private static OkHttpClient createOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //打印日志
            .addInterceptor(interceptor)
            //设置Cache
            .addNetworkInterceptor(cacheInterceptor)//缓存方面需要加入这个拦截器
            .addInterceptor(cacheInterceptor)
            .cache(HttpCache.getCache())
            //time out
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
                .build();
        return client;
    }

    private static GsonConverterFactory createGsonConverterFactory(){
        return GsonConverterFactory.create(new Gson());
    }
    public static synchronized ApiService getApiService(){
      if (sApiService==null){
        sApiService=createRetrofit().create(ApiService.class);
      }
      return sApiService;
    }
}
