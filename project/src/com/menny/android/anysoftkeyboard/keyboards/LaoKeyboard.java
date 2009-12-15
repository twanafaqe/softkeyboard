package com.menny.android.anysoftkeyboard.keyboards;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;
import com.menny.android.anysoftkeyboard.R;
import com.menny.android.anysoftkeyboard.Dictionary.Dictionary;

public class LaoKeyboard extends AnyKeyboard /*implements HardKeyboardTranslator*/
{
	public LaoKeyboard(AnyKeyboardContextProvider context) 
	{
		super(context, KeyboardFactory.LAO_KEYBOARD, R.xml.lao_qwerty, R.string.lao_keyboard, true, Dictionary.Language.None, R.drawable.lao);
	}
}
