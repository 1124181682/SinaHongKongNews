package yk.sinahknews.util;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by YK on 2017/11/21.
 */
public class AnimatorUtil {
  private static Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR= new AccelerateDecelerateInterpolator();
  public static ViewPropertyAnimatorListener defalutScaleHideAnimatorListener=new ViewPropertyAnimatorListener() {
    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationEnd(View view) {
      view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationCancel(View view) {

    }
  };
  public static void scaleHide(View view){
    ViewCompat.animate(view)
        .scaleX(0.0f)
        .scaleY(0.0f)
        .alpha(0.0f)
        .setDuration(800)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setListener(defalutScaleHideAnimatorListener)
        .start();
  }
  // 显示view
  public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
    view.setVisibility(View.VISIBLE);
    ViewCompat.animate(view)
        .scaleX(1.0f)
        .scaleY(1.0f)
        .alpha(1.0f)
        .setDuration(800)
        .setListener(viewPropertyAnimatorListener)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .start();
  }
  // 隐藏view
  public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
    ViewCompat.animate(view)
        .scaleX(0.0f)
        .scaleY(0.0f)
        .alpha(0.0f)
        .setDuration(800)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setListener(viewPropertyAnimatorListener)
        .start();
  }
}
