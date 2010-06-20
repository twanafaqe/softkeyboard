package com.menny.android.anysoftkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.AttributeSet;

public class AnyKeyboardViewDonut extends AnyKeyboardView
{
	public AnyKeyboardViewDonut(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void requestSpecialKeysRedraw() {
		invalidateAllKeys();
	}
	
	@Override
	public void requestShiftKeyRedraw() {
		Keyboard keyboard = getKeyboard();
		if (keyboard != null)
		{
			final int shiftKeyIndex = keyboard.getShiftKeyIndex();
			if (shiftKeyIndex >= 0)
				invalidateKey(shiftKeyIndex);
		}
	}
}