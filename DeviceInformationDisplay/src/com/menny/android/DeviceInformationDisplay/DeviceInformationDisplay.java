package com.menny.android.DeviceInformationDisplay;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceInformationDisplay extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        PackageInfo info;
		try {
			info = super.getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
			
			setTextOfLabel(R.id.welcome, "Welcome to Device Information v"+info.versionName);
			setTextOfLabel(R.id.locale, "Locale: "+getResources().getConfiguration().locale.toString());
			setTextOfLabel(R.id.board, "Board: "+android.os.Build.BOARD);
			setTextOfLabel(R.id.brand, "Brand: "+android.os.Build.BRAND);
			setTextOfLabel(R.id.device, "Device: "+android.os.Build.DEVICE);
			setTextOfLabel(R.id.model, "Model: "+android.os.Build.MODEL);
			setTextOfLabel(R.id.product, "Product: "+android.os.Build.PRODUCT);
			setTextOfLabel(R.id.tags, "TAGS: "+android.os.Build.TAGS);
			
			setTextOfLabel(R.id.build, "Build release "+android.os.Build.VERSION.RELEASE + ", Inc: "+android.os.Build.VERSION.INCREMENTAL);
			setTextOfLabel(R.id.display_build, "Display build: "+android.os.Build.DISPLAY);
			setTextOfLabel(R.id.fingerprint, "Finger print: "+android.os.Build.FINGERPRINT);
			setTextOfLabel(R.id.build_id, "Build ID: "+android.os.Build.ID);
			setTextOfLabel(R.id.time, "Time: "+android.os.Build.TIME);
			setTextOfLabel(R.id.type, "Type: "+android.os.Build.TYPE);
			setTextOfLabel(R.id.user, "User: "+android.os.Build.USER);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setTextOfLabel(R.id.welcome, "Exception: "+e.toString());
		}
    }
    
    private void setTextOfLabel(int resId, String text)
    {
    	TextView label = (TextView)super.findViewById(resId);
		label.setText(text);
    }
}