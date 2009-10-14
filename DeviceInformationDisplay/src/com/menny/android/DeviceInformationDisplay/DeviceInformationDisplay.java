package com.menny.android.DeviceInformationDisplay;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DeviceInformationDisplay extends Activity implements OnClickListener {
	private String mReport = "Empty";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mReport = "Device information report:\n";
        try {
        	PackageInfo info = super.getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
			
			setTextOfLabel(R.id.welcome, "Welcome to Device Information v"+info.versionName);
			setTextOfLabel(R.id.locale, "Locale: "+getResources().getConfiguration().locale.toString());
			setTextOfLabel(R.id.board, "Board: "+android.os.Build.BOARD);
			setTextOfLabel(R.id.brand, "Brand: "+android.os.Build.BRAND);
			setTextOfLabel(R.id.device, "Device: "+android.os.Build.DEVICE);
			setTextOfLabel(R.id.model, "Model: "+android.os.Build.MODEL);
			setTextOfLabel(R.id.product, "Product: "+android.os.Build.PRODUCT);
			setTextOfLabel(R.id.tags, "TAGS: "+android.os.Build.TAGS);
			
			setTextOfLabel(R.id.build, "Build release "+android.os.Build.VERSION.RELEASE + ", Inc: '"+android.os.Build.VERSION.INCREMENTAL+"'");
			setTextOfLabel(R.id.display_build, "Display build: "+android.os.Build.DISPLAY);
			setTextOfLabel(R.id.fingerprint, "Finger print: "+android.os.Build.FINGERPRINT);
			setTextOfLabel(R.id.build_id, "Build ID: "+android.os.Build.ID);
			setTextOfLabel(R.id.time, "Time: "+android.os.Build.TIME);
			setTextOfLabel(R.id.type, "Type: "+android.os.Build.TYPE);
			setTextOfLabel(R.id.user, "User: "+android.os.Build.USER);
			
			setTextOfLabel(R.id.ltr_workaround, "LTR workaround: "+ltrWorkaroundRequired());
			
			Button sendEmail = (Button)super.findViewById(R.id.send_email_button);
			sendEmail.setOnClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			setTextOfLabel(R.id.welcome, "Exception: "+e.toString());
		}
    }
    
    private void setTextOfLabel(int resId, String text)
    {
    	TextView label = (TextView)super.findViewById(resId);
		label.setText(text);
		mReport = mReport + "\n" + text;
    }

    private static String ltrWorkaroundRequired()
    {
    	String requiresRtlWorkaround = "defaulting to true";//all devices required this fix (in 1.6 it is still required)
		
		if (android.os.Build.MODEL.toLowerCase().contains("galaxy"))
		{
			try
			{
				final int buildInc = Integer.parseInt(android.os.Build.VERSION.INCREMENTAL);
				if (buildInc < 20090903)
				{
					requiresRtlWorkaround = "galaxy without the fix";
				}
				else
				{
					requiresRtlWorkaround = "galaxy WITH the fix";
				}
			}
			catch(Exception ex)
			{
				requiresRtlWorkaround = "Error: "+ex.getMessage();//if it is like that, then I do not know, and rather say WORKAROUND!
			}
		}
		
		return requiresRtlWorkaround;
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