package yk.sinahknews.ui.base.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;

import yk.sinahknews.util.AnimatorUtil;

/**
 * Created by YK on 2017/11/22.
 */

public class GoTopFloatingButtonBehavior extends FloatingActionButton.Behavior {
  private boolean isAnimatingOut = false;

  ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

    @Override
    public void onAnimationStart(View view) {
      isAnimatingOut = true;
    }

    @Override
    public void onAnimationEnd(View view) {
      isAnimatingOut = false;
      view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationCancel(View arg0) {
      isAnimatingOut = false;
    }
  };

  public GoTopFloatingButtonBehavior() {
    super();
  }

  public GoTopFloatingButtonBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                     View directTargetChild, View target, int nestedScrollAxes) {
    return nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override
  public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                             View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0))
        && child.getVisibility() != View.VISIBLE) {// 显示
      AnimatorUtil.scaleShow(child, null);
    }
    if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0))
        && child.getVisibility() != View.INVISIBLE && !isAnimatingOut) {
      AnimatorUtil.scaleHide(child, viewPropertyAnimatorListener);
    }
  }
}
