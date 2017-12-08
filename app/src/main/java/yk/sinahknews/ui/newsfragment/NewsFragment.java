package yk.sinahknews.ui.newsfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yk.sinahknews.R;
import yk.sinahknews.app.NewsType;
import yk.sinahknews.util.AnimatorUtil;

/**
 * Created by YK on 2017/11/13.
 */

public class NewsFragment extends Fragment implements NewsContract.INewsView {
  @BindView(R.id.recyclerview)
  RecyclerView recyclerView;
  NewsType.NewsTypeModel newsType;
  Unbinder bkBind;
  NewsPresenter newsPresenter;
  NewsRecyclerAdapter newsRecyclerAdapter;
  Handler handler = new Handler();
  List<NewsItem> data = new ArrayList<>();
  @BindView(R.id.fab_)
  FloatingActionButton mFAB;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle arguments = getArguments();
    newsType = arguments.getParcelable("NewsType");
    newsPresenter = new NewsPresenter();
    newsPresenter.setView(this);
    newsPresenter.start();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_viewpageritem_news, null);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    bkBind = ButterKnife.bind(this, view);
    //设置recyclerview
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    newsRecyclerAdapter = new NewsRecyclerAdapter(null);
    newsRecyclerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    recyclerView.setAdapter(newsRecyclerAdapter);
    newsPresenter.startGetNews(newsType);
    newsRecyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        newsPresenter.onItemClick(getContext(), position);
      }
    });
    mFAB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        recyclerView.scrollToPosition(0);
      }
    });
    AnimatorUtil.scaleHide(mFAB);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (bkBind != null) bkBind.unbind();
    if (newsPresenter != null) newsPresenter.detachView();
  }

  @Override
  public void showFirstContent(List<NewsItem> news) {
    newsRecyclerAdapter.addData(news);
    newsRecyclerAdapter.setEnableLoadMore(true);
    newsRecyclerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
      @Override
      public void onLoadMoreRequested() {
        newsPresenter.loadMore();
      }
    }, recyclerView);
  }

  @Override
  public void getStartNewsFail() {
    recyclerView.setVisibility(View.INVISIBLE);
    final View refreshImg = getView().findViewById(R.id.iv_refresh);
    refreshImg.setVisibility(View.VISIBLE);
    refreshImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        recyclerView.setVisibility(View.VISIBLE);
        refreshImg.setVisibility(View.INVISIBLE);
        newsPresenter.startGetNews(newsType);
      }
    });
  }

  @Override
  public void showMoreContent(List<NewsItem> news, boolean hasNext) {
    newsRecyclerAdapter.loadMoreComplete();
    newsRecyclerAdapter.addData(news);
    if (hasNext) {
    } else {
      newsRecyclerAdapter.setEnableLoadMore(false);
      newsRecyclerAdapter.setOnLoadMoreListener(null, recyclerView);
      newsRecyclerAdapter.loadMoreEnd(true);
    }
  }

  @Override
  public void getMoreNewsFail() {
    newsRecyclerAdapter.loadMoreFail();
  }
}
