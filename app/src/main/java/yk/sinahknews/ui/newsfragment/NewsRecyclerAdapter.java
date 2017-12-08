package yk.sinahknews.ui.newsfragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import yk.sinahknews.R;
import yk.sinahknews.app.GlideApp;

/**
 * Created by YK on 2017/11/14.
 */

public class NewsRecyclerAdapter extends BaseQuickAdapter<NewsItem, BaseViewHolder> {
  Context context;
  public NewsRecyclerAdapter(@Nullable List<NewsItem> data) {
    super(R.layout.item_recycler_news, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, NewsItem item) {
    helper.setText(R.id.tv_title, item.title);
    helper.setText(R.id.tv_author, item.auther);
    helper.setText(R.id.tv_time, item.time);
    helper.setText(R.id.tv_tags, item.tag);
    String[] imgs = item.img;
    ViewGroup imgroot = helper.getView(R.id.ll_img_root);
    if (imgs==null){
       imgroot.setVisibility(View.GONE);
    }
    if (imgs != null && imgs.length != 0) {
      for (int i = 0; i<3&&i< imgs.length; i++) {
        GlideApp.with(helper.itemView).load(imgs[i]).
            placeholder(mContext.getResources().getDrawable(R.drawable.loading))
            .error(R.drawable.img_load_error).into((ImageView) imgroot.getChildAt(i));
      }
    }
  }
}
