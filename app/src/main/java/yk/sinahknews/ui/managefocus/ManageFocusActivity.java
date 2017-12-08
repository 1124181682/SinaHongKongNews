package yk.sinahknews.ui.managefocus;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import yk.sinahknews.R;
import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.base.BaseActivity;
import yk.sinahknews.ui.main.MainActivity;
import yk.sinahknews.util.ShareSettingUtil;

public class ManageFocusActivity extends BaseActivity {
  @BindView(R.id.tagflowlayout)
  TagFlowLayout tagFlowLayout;
  List<String> tags = new ArrayList<>();
  private MyTagAdapter mAdapter;
  public static final String SHARED_SETTING_FILE_NAME="focus";
  @Override
  public void initViews() {
    super.initViews();
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar supportActionBar = getSupportActionBar();
    supportActionBar.setDisplayHomeAsUpEnabled(true);
    supportActionBar.setTitle("设置关注");
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //确定选择
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Set<Integer> selectedList = tagFlowLayout.getSelectedList();
        Set<String> setFocus=new HashSet<>();
        for (int i:selectedList){
          setFocus.add(tags.get(i));
        }
        ShareSettingUtil.setStringSet(SHARED_SETTING_FILE_NAME,"focus",setFocus);
        finish();
        MainActivity.reloadActivity();
      }
    });
    mAdapter = new MyTagAdapter(tags,this);
    getTags();
    tagFlowLayout.setAdapter(mAdapter);
    setFocused();
  }

  private void setFocused() {
    Set<String> focusTags = getFocusTags();
    if (focusTags==null) {
      return;
    }
    Set<Integer> focusPosition=new HashSet<>();
    for (int i = 0; i < tags.size(); i++) {
      String tag = tags.get(i);
      if (focusTags.contains(tag)){
        focusPosition.add(i);
        focusTags.remove(tag);
      }
    }
    mAdapter.setSelectedList(focusPosition);
  }

  private void getTags() {
    for (NewsType.NewsTypeModel tag : NewsType.typeArray) {
      tags.add(tag.name);
    }
  }
  private Set<String> getFocusTags(){
     return ShareSettingUtil.getStringSet(SHARED_SETTING_FILE_NAME, "focus");
  }
  @Override
  protected int getLayoutId() {
    return R.layout.activity_manage_focus;
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  static class MyTagAdapter extends TagAdapter<String> {
    int defaultTextColor = -1;
    Context context;
    List<ViewGroup> viewGroups=new ArrayList<>();
    public MyTagAdapter(List<String> datas, Context context) {
      super(datas);
      this.context=context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String o) {
      ViewGroup childAt = (ViewGroup) parent.getChildAt(position);
      ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_flow_tags,
          (ViewGroup) parent.getChildAt(position),false);
      TextView textView = (TextView) root.findViewById(R.id.tv_);
      if (defaultTextColor == -1)
        defaultTextColor = textView.getCurrentTextColor();
      textView.setText(o);
      viewGroups.add(root);
      return root;
    }

    @Override
    public void onSelected(int position, View view) {
      if (view==null) view=viewGroups.get(position);
      TextView tv = (TextView) view.findViewById(R.id.tv_);
      tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void unSelected(int position, View view) {
      TextView tv = (TextView) view.findViewById(R.id.tv_);
      tv.setTextColor(defaultTextColor);
    }
  }
}
