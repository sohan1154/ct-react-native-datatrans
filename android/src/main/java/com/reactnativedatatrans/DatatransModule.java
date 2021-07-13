package com.reactnativedatatrans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.datatrans.payment.ExternalProcessRelayActivity;
import ch.datatrans.payment.api.Transaction;
import ch.datatrans.payment.api.TransactionListener;
import ch.datatrans.payment.api.TransactionRegistry;
import ch.datatrans.payment.api.TransactionSuccess;
import ch.datatrans.payment.exception.TechnicalException;
import ch.datatrans.payment.exception.TransactionException;
import ch.datatrans.payment.paymentmethods.CardExpiryDate;
import ch.datatrans.payment.paymentmethods.CardToken;
import ch.datatrans.payment.paymentmethods.PaymentMethodToken;
import ch.datatrans.payment.paymentmethods.PaymentMethodType;

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
  private WritableMap mapObj;
  //private Callback mTokenCallback;
  private  Promise mPromise;

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
  public void transaction(String mobileToken, ReadableMap options, final Promise promise) {
    ReactApplicationContext context = getReactApplicationContext();
    Activity activity = getCurrentActivity();
    mPromise = promise;
    if (activity == null) {
      WritableMap map = Arguments.createMap();
      map.putString("message", "Activity doesn't exist");
      consumeCallback(E_ACTIVITY_DOES_NOT_EXIST, map);
      return;
    }

    //aliasPaymentMethods
    if (activity != null) {
      Intent intent = new Intent(context, ExternalProcessRelayActivity.class);
      activity.startActivity(intent);
      Transaction transaction;
      try {
        Collection paymentCollection= new ArrayList<>();

        ReadableArray aliasPaymentMethods;//=new ArrayList<>();
        aliasPaymentMethods=options.getArray("aliasPaymentMethods");
        //aliasPaymentMethods.get
        List<PaymentMethodToken> paymentMethodTokens = new ArrayList<>();
        //Log.d("myTag", aliasPaymentMethods.toString());
        for(int i = 0; i < aliasPaymentMethods.size(); i++)
        {
          ReadableMap apm;
          apm=aliasPaymentMethods.getMap(i);
        CardExpiryDate ced=new CardExpiryDate(apm.getInt("expiryMonth"),apm.getInt("expiryYear"));
         //  Log.d("Visssssss",PaymentMethodType.VISA.toString());
        //  Log.d("fromIdentifier", PaymentMethodType.fromIdentifier(apm.getString("paymentMethods")).toString());

        CardToken ct=new CardToken(PaymentMethodType.fromIdentifier(apm.getString("paymentMethods")),apm.getString("alias"),ced,apm.getString("ccNumber"),"");


        paymentMethodTokens.add(ct);
        }

      //modules.add(new DatatransModule(reactContext));

        if(paymentMethodTokens.size()>0) {
          transaction = new Transaction(mobileToken, paymentMethodTokens);
        }
        else{
          transaction = new Transaction(mobileToken);
        }

                // transaction = new Transaction(mobileToken);
      TransactionListener transactionListener= new TransactionListener() {
        @Override
        public void onTransactionCancel() {
          WritableMap map = Arguments.createMap();
          WritableMap data = Arguments.createMap();

          map.putMap("Data",data);
          map.putString("action","Cancel");

          Toast.makeText(getReactApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
          mapObj=map;
          consumeCallback(CALLBACK_TYPE_CANCEL, map);
        }

        @Override
        public void onTransactionError(@NotNull TransactionException e) {
          WritableMap map = Arguments.createMap();
          WritableMap data = Arguments.createMap();
          data.putString("transactionId", e.getTransactionId());
          data.putString("message", e.getMessage());
          data.putString("paymentMethodType", e.getPaymentMethodType().getIdentifier());
          map.putMap("Data",data);
          map.putString("action","Error");
          Toast.makeText(getReactApplicationContext(), "Error", Toast.LENGTH_LONG).show();
          mapObj=map;
          consumeCallback(CALLBACK_TYPE_ERROR, map);
        }

        @Override
        public void onTransactionSuccess(@NotNull TransactionSuccess transactionSuccess) {
          WritableMap map = Arguments.createMap();
          WritableMap data = Arguments.createMap();
          data.putString("transactionId", transactionSuccess.getTransactionId());
          data.putString("paymentMethodToken", transactionSuccess.getPaymentMethodToken().toString());
          data.putString("paymentMethodType", transactionSuccess.getPaymentMethodType().getIdentifier());

          map.putMap("Data",data);
          map.putString("action","Finish");

          Toast.makeText(getReactApplicationContext(), "Success", Toast.LENGTH_LONG).show();
          mapObj=map;
          consumeCallback(CALLBACK_TYPE_SUCCESS, map);
        }
      };
      transaction.setListener(transactionListener); // this refers to Android's Activity
        transaction.getOptions().setAppCallbackScheme(options.getString("appCallbackScheme"));
        transaction.getOptions().setTesting(options.getBoolean("isTesing") );
        transaction.getOptions().setUseCertificatePinning(options.getBoolean("isUseCertificatePinning"));
        TransactionRegistry.INSTANCE.startTransaction(activity, transaction);

      } catch (Exception e) {

        promise.reject(e);
      }
    }

  }

  private void consumeCallback(String type, WritableMap map) {
    if (mPromise != null) {
      map.putString("type", type);
      map.putString("provider", "ct-datatrans");

      mPromise.resolve(map);
      mPromise = null;
    }
  }


  @ReactMethod
  public void multiply(int a, int b, Promise promise) {
    promise.resolve(a * b);
  }

  public static native int nativeMultiply(int a, int b);


}
