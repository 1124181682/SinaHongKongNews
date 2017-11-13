package yk.sinahknews.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import yk.sinahknews.ui.base.BaseActivity;

/**
 * Created by YK on 2017/11/13.
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IBaseView{
  protected P mPresenter;

  @Override
  protected void setPresenter(){
   ParameterizedType superClass= (ParameterizedType) getClass().getGenericSuperclass();
    Class presenter = (Class) superClass.getActualTypeArguments()[0];
    try {
      mPresenter= (P) presenter.newInstance();
    } catch (Exception e){
      e.printStackTrace();
    }
    if (mPresenter==null){
      throw new IllegalStateException(getClass().getSimpleName()+"必须实现父类泛型参数");
    }
    mPresenter.setView(this);
  }

  @Override
  protected void startPresenter() {
    mPresenter.start();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.detachView();
  }
}
