package yk.sinahknews.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YK on 2017/11/14.
 */

public class NewsJson {
  private boolean hasNext;
  private String retcode;
  private String appCode;
  private String dataType;
  private String pageToken;
  private NewsDetailJson[] data;
  public boolean isHasNext() {
    return hasNext;
  }

  public void setHasNext(boolean hasNext) {
    this.hasNext = hasNext;
  }

  public String getRetcode() {
    return retcode;
  }

  public void setRetcode(String retcode) {
    this.retcode = retcode;
  }

  public String getAppCode() {
    return appCode;
  }

  public void setAppCode(String appCode) {
    this.appCode = appCode;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getPageToken() {
    return pageToken;
  }

  public void setPageToken(String pageToken) {
    this.pageToken = pageToken;
  }

  public NewsDetailJson[] getData() {
    return data;
  }

  public void setData(NewsDetailJson[] data) {
    this.data = data;
  }

  public static class NewsDetailJson implements Parcelable{
    private String posterId;
    private String[] tags;
    private long publishDate;
    private int likeCount;
    private int commentCount;
    private String[] imageUrls;
    private String id;
    private String posterScreenName;
    private String title;
    private String url;
    private String publishDateStr;
    private String content;
    private int shareCount;
    public String tagsString;
    protected NewsDetailJson(Parcel in) {
      tagsString=in.readString();
      posterId = in.readString();
      tags = in.createStringArray();
      publishDate = in.readLong();
      likeCount = in.readInt();
      commentCount = in.readInt();
      imageUrls = in.createStringArray();
      id = in.readString();
      posterScreenName = in.readString();
      title = in.readString();
      url = in.readString();
      publishDateStr = in.readString();
      content = in.readString();
      shareCount = in.readInt();
    }

    public static final Creator<NewsDetailJson> CREATOR = new Creator<NewsDetailJson>() {
      @Override
      public NewsDetailJson createFromParcel(Parcel in) {
        return new NewsDetailJson(in);
      }

      @Override
      public NewsDetailJson[] newArray(int size) {
        return new NewsDetailJson[size];
      }
    };

    public String getPosterId() {
      return posterId;
    }

    public void setPosterId(String posterId) {
      this.posterId = posterId;
    }

    public String[] getTags() {
      return tags;
    }

    public void setTags(String[] tags) {
      this.tags = tags;
    }

    public long getPublishDate() {
      return publishDate;
    }

    public void setPublishDate(long publishDate) {
      this.publishDate = publishDate;
    }

    public int getLikeCount() {
      return likeCount;
    }

    public void setLikeCount(int likeCount) {
      this.likeCount = likeCount;
    }

    public int getCommentCount() {
      return commentCount;
    }

    public void setCommentCount(int commentCount) {
      this.commentCount = commentCount;
    }

    public String[] getImageUrls() {
      return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
      this.imageUrls = imageUrls;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getPosterScreenName() {
      return posterScreenName;
    }

    public void setPosterScreenName(String posterScreenName) {
      this.posterScreenName = posterScreenName;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getPublishDateStr() {
      return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
      this.publishDateStr = publishDateStr;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public int getShareCount() {
      return shareCount;
    }

    public void setShareCount(int shareCount) {
      this.shareCount = shareCount;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(tagsString);
      dest.writeString(posterId);
      dest.writeStringArray(tags);
      dest.writeLong(publishDate);
      dest.writeInt(likeCount);
      dest.writeInt(commentCount);
      dest.writeStringArray(imageUrls);
      dest.writeString(id);
      dest.writeString(posterScreenName);
      dest.writeString(title);
      dest.writeString(url);
      dest.writeString(publishDateStr);
      dest.writeString(content);
      dest.writeInt(shareCount);
    }
  }
}
