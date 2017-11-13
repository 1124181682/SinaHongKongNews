package yk.sinahknews.ui.base;

import android.support.annotation.CallSuper;

/**
 * Created by YK on 2017/11/13.
 */

public abstract class BasePresenter<V extends yk.sinahknews.ui.base.IBaseView> {
  protected V mView;
  public void setView(V view){
    mView=view;
  }
  public void start(){};
  @CallSuper
  public void detachView(){
    mView=null;
  }
}
