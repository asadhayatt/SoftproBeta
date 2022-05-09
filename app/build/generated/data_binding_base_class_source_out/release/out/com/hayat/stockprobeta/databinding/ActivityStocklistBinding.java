// Generated by view binder compiler. Do not edit!
package com.hayat.stockprobeta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.hayat.stockprobeta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStocklistBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final ScrollView container;

  @NonNull
  public final LinearLayout containerlay;

  @NonNull
  public final LinearLayout top;

  private ActivityStocklistBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull ScrollView container, @NonNull LinearLayout containerlay,
      @NonNull LinearLayout top) {
    this.rootView = rootView;
    this.container = container;
    this.containerlay = containerlay;
    this.top = top;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStocklistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStocklistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_stocklist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStocklistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.container;
      ScrollView container = ViewBindings.findChildViewById(rootView, id);
      if (container == null) {
        break missingId;
      }

      id = R.id.containerlay;
      LinearLayout containerlay = ViewBindings.findChildViewById(rootView, id);
      if (containerlay == null) {
        break missingId;
      }

      id = R.id.top;
      LinearLayout top = ViewBindings.findChildViewById(rootView, id);
      if (top == null) {
        break missingId;
      }

      return new ActivityStocklistBinding((LinearLayoutCompat) rootView, container, containerlay,
          top);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
