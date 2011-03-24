package com.anysoftkeyboard.ng.keyboards;

import com.anysoftkeyboard.ng.AnyKeyboardContextProvider;

public class GenericKeyboard extends ExternalAnyKeyboard 
{
	public GenericKeyboard(AnyKeyboardContextProvider context, int xmlLayoutResId, int nameResId, String prefKeyId, int mode) 
	{
		super(context, context.getApplicationContext(), xmlLayoutResId, xmlLayoutResId, prefKeyId, nameResId, -1, -1, null, null, mode);
	}
	
	public GenericKeyboard(AnyKeyboardContextProvider context, int xmlLayoutResId, int xmlLandscapeLayoutResId,  int nameResId, String prefKeyId, int mode) 
	{
		super(context, context.getApplicationContext(), xmlLayoutResId, xmlLandscapeLayoutResId, prefKeyId, nameResId, -1, -1, null, null, mode);
	}
}