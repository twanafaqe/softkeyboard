package com.menny.android.anysoftkeyboard.Dictionary;

import java.util.HashMap;

import android.util.Log;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;

public class DictionaryFactory 
{
	private static UserDictionaryBase msUserDictionary = null;
	private static final HashMap<Dictionary.Language, Dictionary> msDictionaries;
	
	static
	{
		msDictionaries = new HashMap<Dictionary.Language, Dictionary>();
	}
	
	public synchronized static UserDictionaryBase createUserDictionary(AnyKeyboardContextProvider context)
	{
		if (msUserDictionary == null)
		{
			try
	        {
				msUserDictionary = new AndroidUserDictionary(context);
	        }
	        catch(Exception ex)
	        {
	        	Log.w("AnySoftKeyboard", "Failed to load 'AndroidUserDictionary' (could be that the platform does not support it). Will use fall-back dictionary. Error:"+ex.getMessage());
	        	msUserDictionary = new FallbackUserDictionary(context);
	        }
		}
        return msUserDictionary;
	}
	
	
	public synchronized static Dictionary getDictionary(Dictionary.Language language, AnyKeyboardContextProvider context)
	{
		if (msDictionaries.containsKey(language))
			return msDictionaries.get(language);
		
		Dictionary dict = null;
		switch(language)
		{
		case English:
			dict = new SQLiteSimpleEnDictionary(context);
			break;
		case Hebrew:
			dict = new SQLiteSimpleHeDictionary(context);
			break;
		default:
			return null;
		}
		msDictionaries.put(language, dict);
		
		return dict;
//		PackageManager packageManager = context.getApplicationContext().getPackageManager();
//		
//		String[] allDictionaryPackages = packageManager.getPackagesForUid(android.os.Process.myUid());
//		if ((allDictionaryPackages == null) || (allDictionaryPackages.length == 0))
//		{
//			Log.i("AnySoftKeyboard", "*** No dictionary packages were found installed on system!");
//			return null;
//		}
//		for(String packageName : allDictionaryPackages)
//		{
//			Log.d("AnySoftKeyboard", "Found package: "+packageName);
//			try {
//				Intent intent = packageManager.getLaunchIntentForPackage(packageName);
//				if (intent != null)
//				{
//					String intentName = intent.getClass().getName();
//					String classToInstantize = intent.getComponent().getClassName();
//					Log.d("AnySoftKeyboard", "Found intent '"+intentName+"': component:"+classToInstantize);
//					Object o = intent.getClass().getClassLoader().loadClass(classToInstantize).newInstance();
//					Log.d("AnySoftKeyboard", "I've created an instance of "+o.getClass().getName());
//				}
//				else
//				{
//					Log.d("AnySoftKeyboard", "No intent found!");
//				}
//			} catch (NameNotFoundException e) {
//				Log.e("AnySoftKeyboard", "This should not happen! This package '"+packageName+" was not found!");
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return null;
	}
}
