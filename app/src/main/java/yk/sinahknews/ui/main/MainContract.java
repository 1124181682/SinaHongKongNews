package yk.sinahknews.ui.main;

import yk.sinahknews.app.NewsType;
import yk.sinahknews.ui.base.BasePresenter;
import yk.sinahknews.ui.base.IBaseView;

/**
 * Created by YK on 2017/11/13.
 */

public interface MainContract {
  interface IMainView extends IBaseView{

  }
  abstract class MainPresenter extends BasePresenter<IMainView>{
    public abstract NewsType.NewsTypeModel[] getViewPagerNewsTypes();
  }

}
