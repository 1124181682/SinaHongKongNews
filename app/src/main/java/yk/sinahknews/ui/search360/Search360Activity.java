package yk.sinahknews.ui.search360;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yk.sinahknews.R;
import yk.sinahknews.component.SearchNotificationService;
import yk.sinahknews.ui.base.BaseMVPActivity;
import yk.sinahknews.ui.newsdetail.NewsDetailActivity;
import yk.sinahknews.ui.newsfragment.NewsItem;
import yk.sinahknews.util.ShareSettingUtil;

public class Search360Activity extends BaseMVPActivity<Search360Presenter>
    implements Search360Contract.ISearch360View {
  public final static String INTENT_VALUE_SEARCH_WORD = "searchWord";
  String mWord;
  @BindView(R.id.searchview_)
  SearchView mSearchView;

  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerView;
  SearchRecyclerAdapter mAdapter;
  @BindView(R.id.appbar_layout)
  AppBarLayout mAppBarLayout;

  @BindView(R.id.rl_toolbar)
  RelativeLayout mRLtoolbar;

  @OnClick({R.id.iv_back})
  void onClick(View v) {
    if (v.getId() == R.id.iv_back) {
      finish();
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  @Override
  public void initViews() {
    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        showLog("verticaloffset:" + verticalOffset);
      }
    });
    mSearchView.clearFocus();
    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        //避免重复搜索同一个内容
        if (mWord != null && mWord.equals(query)) {
          return true;
        } else {
          mPresenter.search(query);
          mWord = query;
          return true;
        }
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mAdapter = new SearchRecyclerAdapter(null);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mPresenter.onItemClick(Search360Activity.this, position);
      }
    });

    //设置通知栏常驻通知
    setNotification();

    //判断是否是从搜索跳转过来的
    String searchWord = getIntent().getStringExtra(INTENT_VALUE_SEARCH_WORD);
    if (searchWord != null) {
      mSearchView.setQuery(searchWord,true);
    }
  }

  private void setNotification() {
    boolean isShowNotification = ShareSettingUtil.getBool(null, "notifications_open_message");
    if (isShowNotification)
      startService(new Intent(getApplicationContext(), SearchNotificationService.class));
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_search360;
  }

  @Override
  public void searchShow(List<NewsItem> newsItems, boolean ishasNext) {
    mAdapter.setNewData(newsItems);
    if (ishasNext) {
      mAdapter.setEnableLoadMore(true);
      mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
          mPresenter.loadMore();
        }
      }, mRecyclerView);
    } else {
      mAdapter.setEnableLoadMore(false);
    }

  }

  @Override
  public void searchNewsFail() {
    mRecyclerView.setVisibility(View.INVISIBLE);
    final View refreshImg = findViewById(R.id.iv_refresh);
    refreshImg.setVisibility(View.VISIBLE);
    refreshImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mRecyclerView.setVisibility(View.VISIBLE);
        refreshImg.setVisibility(View.INVISIBLE);
        mPresenter.search(mWord);
      }
    });
  }

  @Override
  public void showSearchMore(List<NewsItem> newsItems, boolean hasNext) {
    mAdapter.loadMoreComplete();
    mAdapter.addData(newsItems);
    if (hasNext) {
    } else {
      mAdapter.setEnableLoadMore(false);
      mAdapter.setOnLoadMoreListener(null, mRecyclerView);
      mAdapter.loadMoreEnd(true);
    }
  }

  @Override
  public void searchMoreFail() {
    mAdapter.loadMoreFail();
  }


}
