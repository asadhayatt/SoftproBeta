// Generated by view binder compiler. Do not edit!
package com.hayat.stockprobeta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.hayat.stockprobeta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDashboardBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final TextView nameTV;

  @NonNull
  public final TextView orgTV;

  @NonNull
  public final Button prbtn;

  @NonNull
  public final LinearLayout productpan;

  @NonNull
  public final TextView replacelist;

  @NonNull
  public final TextView stkTV;

  @NonNull
  public final Button stkbtn;

  @NonNull
  public final LinearLayout stockpan;

  @NonNull
  public final LinearLayout top;

  @NonNull
  public final LinearLayout welcomenote;

  private ActivityDashboardBinding(@NonNull LinearLayoutCompat rootView, @NonNull TextView nameTV,
      @NonNull TextView orgTV, @NonNull Button prbtn, @NonNull LinearLayout productpan,
      @NonNull TextView replacelist, @NonNull TextView stkTV, @NonNull Button stkbtn,
      @NonNull LinearLayout stockpan, @NonNull LinearLayout top,
      @NonNull LinearLayout welcomenote) {
    this.rootView = rootView;
    this.nameTV = nameTV;
    this.orgTV = orgTV;
    this.prbtn = prbtn;
    this.productpan = productpan;
    this.replacelist = replacelist;
    this.stkTV = stkTV;
    this.stkbtn = stkbtn;
    this.stockpan = stockpan;
    this.top = top;
    this.welcomenote = welcomenote;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDashboardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDashboardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dashboard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDashboardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.nameTV;
      TextView nameTV = ViewBindings.findChildViewById(rootView, id);
      if (nameTV == null) {
        break missingId;
      }

      id = R.id.orgTV;
      TextView orgTV = ViewBindings.findChildViewById(rootView, id);
      if (orgTV == null) {
        break missingId;
      }

      id = R.id.prbtn;
      Button prbtn = ViewBindings.findChildViewById(rootView, id);
      if (prbtn == null) {
        break missingId;
      }

      id = R.id.productpan;
      LinearLayout productpan = ViewBindings.findChildViewById(rootView, id);
      if (productpan == null) {
        break missingId;
      }

      id = R.id.replacelist;
      TextView replacelist = ViewBindings.findChildViewById(rootView, id);
      if (replacelist == null) {
        break missingId;
      }

      id = R.id.stkTV;
      TextView stkTV = ViewBindings.findChildViewById(rootView, id);
      if (stkTV == null) {
        break missingId;
      }

      id = R.id.stkbtn;
      Button stkbtn = ViewBindings.findChildViewById(rootView, id);
      if (stkbtn == null) {
        break missingId;
      }

      id = R.id.stockpan;
      LinearLayout stockpan = ViewBindings.findChildViewById(rootView, id);
      if (stockpan == null) {
        break missingId;
      }

      id = R.id.top;
      LinearLayout top = ViewBindings.findChildViewById(rootView, id);
      if (top == null) {
        break missingId;
      }

      id = R.id.welcomenote;
      LinearLayout welcomenote = ViewBindings.findChildViewById(rootView, id);
      if (welcomenote == null) {
        break missingId;
      }

      return new ActivityDashboardBinding((LinearLayoutCompat) rootView, nameTV, orgTV, prbtn,
          productpan, replacelist, stkTV, stkbtn, stockpan, top, welcomenote);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
