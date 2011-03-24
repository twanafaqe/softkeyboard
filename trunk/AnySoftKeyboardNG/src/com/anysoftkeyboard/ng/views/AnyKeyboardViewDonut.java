package com.anysoftkeyboard.ng.views;

import com.anysoftkeyboard.base.Keyboard;

import android.content.Context;
import android.util.AttributeSet;

public class AnyKeyboardViewDonut extends AnyKeyboardView
{
	public AnyKeyboardViewDonut(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void requestSpecialKeysRedraw() {
		invalidateAllKeys();
	}
	
	@Override
	public void requestShiftKeyRedraw() {
		if (canInteractWithUi())
		{
			//Log.d("FSGSDFGS", "canInteractWithUi");
			Keyboard keyboard = getKeyboard();
			if (keyboard != null)
			{
				final int shiftKeyIndex = keyboard.getShiftKeyIndex();
				if (shiftKeyIndex >= 0)
					invalidateKey(shiftKeyIndex);
			}
		}
	}
}