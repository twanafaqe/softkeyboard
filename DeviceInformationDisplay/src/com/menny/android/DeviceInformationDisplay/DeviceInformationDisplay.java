package com.menny.android.DeviceInformationDisplay;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeviceInformationDisplay extends Activity implements OnClickListener {
	private String mReport = "Empty";
	private LinearLayout mLayout;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLayout = (LinearLayout)findViewById(R.id.layout);
        mReport = "Device information report:\n";
        try {
        	PackageInfo info = super.getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
			
			setTextOfLabel(true, "ASK Device Info v"+info.versionName+"("+info.versionCode+")");
			setTextOfLabel(false, getAskVersion());
			setTextOfLabel(false, "Locale: "+getResources().getConfiguration().locale.toString());
			setTextOfLabel(true, "** Device:");
			setTextOfLabel(false, "Board: "+android.os.Build.BOARD);
			setTextOfLabel(false, "Brand: "+android.os.Build.BRAND);
			setTextOfLabel(false, "Device: "+android.os.Build.DEVICE);
			setTextOfLabel(false, "Model: "+android.os.Build.MODEL);
			setTextOfLabel(false, "Product: "+android.os.Build.PRODUCT);
			setTextOfLabel(false, "TAGS: "+android.os.Build.TAGS);
			
			setTextOfLabel(true, "** OS:");
			setTextOfLabel(false, "Build release "+android.os.Build.VERSION.RELEASE + ", Inc: '"+android.os.Build.VERSION.INCREMENTAL+"'");
			setTextOfLabel(false, "Display build: "+android.os.Build.DISPLAY);
			setTextOfLabel(false, "Finger print: "+android.os.Build.FINGERPRINT);
			setTextOfLabel(false, "Build ID: "+android.os.Build.ID);
			setTextOfLabel(false, "Time: "+android.os.Build.TIME);
			setTextOfLabel(false, "Type: "+android.os.Build.TYPE);
			setTextOfLabel(false, "User: "+android.os.Build.USER);
			
			Button sendEmail = new Button(this);
			sendEmail.setText("Send report...");
			sendEmail.setOnClickListener(this);
			mLayout.addView(sendEmail);
		} catch (Exception e) {
			e.printStackTrace();
			setTextOfLabel(true, "Exception: "+e.toString());
		}
    }
    
    private String getAskVersion() {
		try {
			PackageInfo info = getApplication().getPackageManager().getPackageInfo("com.menny.android.anysoftkeyboard", PackageManager.GET_ACTIVITIES);
			return "ASK "+info.versionName+" ("+info.versionCode+")";
		} catch (NameNotFoundException e) {
			return "Failed to get ASK info: "+e.getMessage();
		}
	}

	private void setTextOfLabel(boolean bold, String text)
    {
    	TextView label = new TextView(this);
		label.setText(text);
		label.setTypeface(Typeface.DEFAULT, bold?Typeface.BOLD : Typeface.NORMAL);
		mLayout.addView(label);
		mReport = mReport + "\n" + text;
    }
    
	@Override
	public void onClick(View arg0) {
		/* Create the Intent */  
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
		  
		/* Fill it with Data */  
		emailIntent.setType("plain/text");  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"anysoftkeyboard@gmail.com"});  
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Device Information");  
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mReport);  
		  
		/* Send it off to the Activity-Chooser */  
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));		
	}
}