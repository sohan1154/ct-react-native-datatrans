package com.reactnativedatatrans;

import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatatransPackage implements ReactPackage {
  private Context mContext;
  private DatatransModule mModuleInstance;

//  public DatatransPackage(Context activityContext) {
//    mContext = activityContext;
//  }


  @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new DatatransModule(reactContext));
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

//  public boolean handleActivityResult(final int requestCode, final int resultCode, final Intent data) {
//    if (mModuleInstance == null) {
//      return false;
//    }
//
//    return mModuleInstance.handleActivityResult(requestCode, resultCode, data);
//  }

}
