// Generated code from Butter Knife. Do not modify!
package iamalexmoss.com.stormy.ui;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492948, "field 'mTimeLabel'");
    target.mTimeLabel = finder.castView(view, 2131492948, "field 'mTimeLabel'");
    view = finder.findRequiredView(source, 2131492946, "field 'mTemperatureLabel'");
    target.mTemperatureLabel = finder.castView(view, 2131492946, "field 'mTemperatureLabel'");
    view = finder.findRequiredView(source, 2131492952, "field 'mHumidityValue'");
    target.mHumidityValue = finder.castView(view, 2131492952, "field 'mHumidityValue'");
    view = finder.findRequiredView(source, 2131492954, "field 'mPrecipValue'");
    target.mPrecipValue = finder.castView(view, 2131492954, "field 'mPrecipValue'");
    view = finder.findRequiredView(source, 2131492955, "field 'mSummaryLabel'");
    target.mSummaryLabel = finder.castView(view, 2131492955, "field 'mSummaryLabel'");
    view = finder.findRequiredView(source, 2131492949, "field 'mIconImageView'");
    target.mIconImageView = finder.castView(view, 2131492949, "field 'mIconImageView'");
    view = finder.findRequiredView(source, 2131492957, "field 'mRefreshImageView'");
    target.mRefreshImageView = finder.castView(view, 2131492957, "field 'mRefreshImageView'");
    view = finder.findRequiredView(source, 2131492958, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131492958, "field 'mProgressBar'");
    view = finder.findRequiredView(source, 2131492960, "method 'startDailyActivity'");
    unbinder.view2131492960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startDailyActivity(p0);
      }
    });
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends MainActivity> implements Unbinder {
    private T target;

    View view2131492960;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      target.mTimeLabel = null;
      target.mTemperatureLabel = null;
      target.mHumidityValue = null;
      target.mPrecipValue = null;
      target.mSummaryLabel = null;
      target.mIconImageView = null;
      target.mRefreshImageView = null;
      target.mProgressBar = null;
      view2131492960.setOnClickListener(null);
    }
  }
}
