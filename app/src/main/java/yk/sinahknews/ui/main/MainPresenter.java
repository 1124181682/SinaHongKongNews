package yk.sinahknews.ui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.managefocus.ManageFocusActivity;
import yk.sinahknews.util.ShareSettingUtil;

/**
 * Created by YK on 2017/11/13.
 */

public class MainPresenter extends MainContract.MainPresenter {
  public void start() {

  }

  @Override
  public NewsType.NewsTypeModel[] getViewPagerNewsTypes() {
    Set<String> focus = ShareSettingUtil.getStringSet(ManageFocusActivity.SHARED_SETTING_FILE_NAME, "focus");
    if (focus == null)
      return NewsType.typeArray;
    else {
      if (focus.size()==0) return new NewsType.NewsTypeModel[0];
      else {
        List<NewsType.NewsTypeModel> retList=new ArrayList();
        for (NewsType.NewsTypeModel newsTypeModel : NewsType.typeArray) {
          if (focus.contains(newsTypeModel.name)){
            retList.add(newsTypeModel);
          }
        }
        NewsType.NewsTypeModel[] retarray=new NewsType.NewsTypeModel[retList.size()];
        for (int i = 0; i < retarray.length; i++) {
          retarray[i]=retList.get(i);
        }
        return retarray;
      }
    }
  }
}
