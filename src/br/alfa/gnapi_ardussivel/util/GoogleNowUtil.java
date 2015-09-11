package br.alfa.gnapi_ardussivel.util;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.KeyEvent;

/**
 * Created by Ryan on 7/23/2014.
 */
public class GoogleNowUtil {
	private static final String GOOGLE_PKG = "com.google.android.googlequicksearchbox";

	public static void resetGoogleNowOnly(Context context) {
		Intent i;
		PackageManager manager = context.getPackageManager();
		try {
			i = manager.getLaunchIntentForPackage(GOOGLE_PKG);
			if (i == null)
				throw new PackageManager.NameNotFoundException();
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			context.startActivity(i);
		} catch (PackageManager.NameNotFoundException e) {

		}
	}

	public static void resetGoogleNow(final Context context) {
		resetGoogleNowOnly(context);
		if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("closegoogle", true)) {
			returnPreviousApp();
		}

	}

	public static void returnPreviousApp() {
		Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message message) {
				try {
					MyAccessibilityService.getInstance().performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				} catch (Exception e) {
				}
				return true;
			}
		});
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	public static void returnHome() {
		Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message message) {
				try {
					MyAccessibilityService.getInstance().performGlobalAction(KeyEvent.KEYCODE_HOME);
				} catch (Exception e) {
				}
				return true;
			}
		});
		handler.sendEmptyMessageDelayed(0, 1000);
	}

}
