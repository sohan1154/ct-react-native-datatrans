package com.reactnativedatatrans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.datatrans.payment.ExternalProcessRelayActivity;
import ch.datatrans.payment.api.Transaction;
import ch.datatrans.payment.api.TransactionListener;
import ch.datatrans.payment.api.TransactionRegistry;
import ch.datatrans.payment.api.TransactionSuccess;
import ch.datatrans.payment.exception.TechnicalException;
import ch.datatrans.payment.exception.TransactionException;

@ReactModule(name = DatatransModule.NAME)
public class DatatransModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Datatrans";
  private Context mActivityContext;
  private final String CALLBACK_TYPE_SUCCESS = "success";
  private final String CALLBACK_TYPE_ERROR = "error";
  private final String CALLBACK_TYPE_CANCEL = "cancel";
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private JSONArray jsonarray;
  private JSONObject jsonobject;


  public DatatransModule(ReactApplicationContext reactContext) {
    super(reactContext);
    // mActivityContext = activityContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void transaction(@NonNull String mobileToken, String aliasPaymentMethods, Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    Activity activity = getCurrentActivity();
    if (activity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
      return;
    }

    if (activity != null) {
      Intent intent = new Intent(context, ExternalProcessRelayActivity.class);
      activity.startActivity(intent);

      try {


        Transaction transaction = new Transaction(mobileToken);
        transaction.setListener(new TransactionListener() {
          @Override
          public void onTransactionSuccess(TransactionSuccess transactionSuccess) {
            Toast.makeText(getReactApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            promise.resolve(transactionSuccess);
          }

          @Override
          public void onTransactionError(TransactionException e) {
            Toast.makeText(getReactApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            promise.resolve(e);
          }

          @Override
          public void onTransactionCancel() {
            Toast.makeText(getReactApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();

          }
        }); // this refers to Android's Activity
        transaction.getOptions().setAppCallbackScheme("your_scheme");
        transaction.getOptions().setTesting(true);
        transaction.getOptions().setUseCertificatePinning(true);
        TransactionRegistry.INSTANCE.startTransaction(activity, transaction);
        throw  new TechnicalException("Technical Error",null,null,null);
        //promise.resolve(transaction);
      } catch (TechnicalException e) {

        promise.reject(e);
      }
    }
  }

  @ReactMethod
  public void multiply(int a, int b, Promise promise) {
    promise.resolve(a * b);
  }

  public static native int nativeMultiply(int a, int b);


}
