package com.menny.android.anysoftkeyboard.keyboards;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;
import com.menny.android.anysoftkeyboard.keyboards.AnyKeyboard.HardKeyboardTranslator;

public class LatinKeyboard extends AnyKeyboard implements HardKeyboardTranslator
{
	protected LatinKeyboard(AnyKeyboardContextProvider context, int keyboardLayoutId, int keyboardNameId) 
	{
		super(context, keyboardLayoutId, true, keyboardNameId, true);
	}
		
	public char translatePhysicalCharacter(int keyCode, int metaKeys) 
	{
		//I'll return 0, so the caller will use defaults.
		return 0;
	}
	
	/*
	 * there are some keys which we'll like to expand, e.g.,
	 * lowercase:
		a: àâáäãæå
		e: éèêë
		u: ùûüú
		i: îïíì
		o: ôöòóõœø
		c: ç
		n: ñ
		y: ÿý
		s: ß§
		
		upper case:
		E: ÈÉÊË
		Y: ÝŸ
		U: ÛÙÛÜ
		I: ÎÌÏÍ
		O: ÒÓÔÖÕŒØ
		A: ÀÁÂÄÃÅÆ
		S: §
		C: Ç
		N: Ñ
	 */
	@Override
	protected void setKeyPopup(Key aKey, boolean shiftState) 
	{
		super.setKeyPopup(aKey, shiftState);
	
		if ((aKey.codes != null) && (aKey.codes.length > 0))
        {
			switch((char)aKey.codes[0])
			{
				case 'a':
					if (shiftState)
						aKey.popupCharacters = "ÀÁÂÄÃÅÆ";
					else
						aKey.popupCharacters = "àâáäãæå";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'c':
					if (shiftState)
						aKey.popupCharacters = "ÇČĆ";
					else
						aKey.popupCharacters = "çčć";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'd':
					if (shiftState)
						aKey.popupCharacters = "Đ";
					else
						aKey.popupCharacters = "đ";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'e':
					if (shiftState)
						aKey.popupCharacters = "ÈÉÊË€";
					else
						aKey.popupCharacters = "éèêë€";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'i':
					if (shiftState)
						aKey.popupCharacters = "ÎÌÏÍ";
					else
						aKey.popupCharacters = "îïíì";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'o':
					if (shiftState)
						aKey.popupCharacters = "ÒÓÔÖÕŒØ";
					else
						aKey.popupCharacters = "ôöòóõœø";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 's':
					if (shiftState)
						aKey.popupCharacters = "§š";
					else
						aKey.popupCharacters = "ß§Š";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'u':
					if (shiftState)
						aKey.popupCharacters = "ÛÙÛÜ";
					else
						aKey.popupCharacters = "ùûüú";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'n':
					if (shiftState)
						aKey.popupCharacters = "Ñ";
					else
						aKey.popupCharacters = "ñ";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'y':
					if (shiftState)
						aKey.popupCharacters = "ÝŸ";
					else
						aKey.popupCharacters = "ÿý";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'z':
					if (shiftState)
						aKey.popupCharacters = "Ž";
					else
						aKey.popupCharacters = "ž";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
			}
        }
	}
}