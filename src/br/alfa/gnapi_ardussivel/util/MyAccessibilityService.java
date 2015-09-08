package br.alfa.gnapi_ardussivel.util;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
	static final String TAG = "accessibility";
	static final long timeOut = 500;
	long lastCommand = 0;
	private static MyAccessibilityService thisService;

	public static MyAccessibilityService getInstance() {
		return thisService;
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		thisService = this;
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
		
	}

	@Override
	public void onInterrupt() {
		
	}

}
