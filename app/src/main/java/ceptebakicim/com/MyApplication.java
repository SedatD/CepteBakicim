package ceptebakicim.com;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import ceptebakicim.com.Activity.BakiciBanaOzelActivity;
import ceptebakicim.com.Activity.TeklifDetayActivity;
import io.fabric.sdk.android.Fabric;

/**
 * Created by SD on 8.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class MyApplication extends Application {
    private static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        singleton = this;

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        OSNotificationAction.ActionType actionType = result.action.type;
                        JSONObject data = result.notification.payload.additionalData;
                        int chatId = 0;
                        int interviewid = 0;
                        int serviceid = 0;
                        int caryId = 0;
                        boolean banaOzel = false;

                        Log.wtf("MyApplication", "data : " + data);

                        if (data != null) {
                            try {
                                chatId = data.optInt("chatId");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                caryId = data.optInt("caryId");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                interviewid = data.optInt("interviewid");
                                serviceid = data.optInt("serviceid");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                banaOzel = data.getBoolean("banaOzel");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        if (actionType == OSNotificationAction.ActionType.ActionTaken)
                            Log.wtf("MyApplication", "Button pressed with id: " + result.action.actionID);

                        if (chatId != 0) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("chatId", chatId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        if (caryId != 0) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("caryId", caryId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        // kullanılmıyor silebilirsin
                        if (interviewid != 0) {
                            Intent intent = new Intent(getApplicationContext(), TeklifDetayActivity.class);
                            intent.putExtra("interview", interviewid);
                            intent.putExtra("pos", serviceid);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        if (banaOzel) {
                            Intent intent = new Intent(getApplicationContext(), BakiciBanaOzelActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                })
                .init();

        //user id & token
        OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
        OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getPushToken();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }

}
