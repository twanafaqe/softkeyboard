/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.menny.android.anysoftkeyboard;

import android.inputmethodservice.Keyboard;

import java.util.ArrayList;
import java.util.List;

import com.menny.android.anysoftkeyboard.keyboards.AnyKeyboard;
import com.menny.android.anysoftkeyboard.keyboards.GenericKeyboard;
import com.menny.android.anysoftkeyboard.keyboards.KeyboardFactory;

public class KeyboardSwitcher 
{

    public static final int MODE_TEXT = 1;
    public static final int MODE_SYMBOLS = 2;
    public static final int MODE_PHONE = 3;
    public static final int MODE_URL = 4;
    public static final int MODE_EMAIL = 5;
    public static final int MODE_IM = 6;
//    
//    public static final int MODE_TEXT_QWERTY = 0;
//    public static final int MODE_TEXT_ALPHA = 1;
//    public static final int MODE_TEXT_COUNT = 2;
    
    AnyKeyboardView mInputView;
    AnySoftKeyboard mContext;
    
//    private LatinKeyboard mPhoneKeyboard;
//    private LatinKeyboard mPhoneSymbolsKeyboard;
//    private LatinKeyboard mSymbolsKeyboard;
//    private LatinKeyboard mSymbolsShiftedKeyboard;
//    private LatinKeyboard mQwertyKeyboard;
//    private LatinKeyboard mAlphaKeyboard;
//    private LatinKeyboard mUrlKeyboard;
//    private LatinKeyboard mEmailKeyboard;
//    private LatinKeyboard mIMKeyboard;
    
    private static final int PHONE_KEYBOARD_INDEX = 2;
    private int mLastSelectedSymbolsKeyboard = 0;
    private AnyKeyboard[] mSymbolsKeyboards;
    //my working keyboards
    private ArrayList<AnyKeyboard> mKeyboards = null;
    private int mLastSelectedKeyboard = 0;
    
    //private int mMode;
    private int mImeOptions;
    //private int mTextMode = MODE_TEXT_QWERTY;

    private int mLastDisplayWidth;

    KeyboardSwitcher(AnySoftKeyboard context) {
        mContext = context;
    }

    void setInputView(AnyKeyboardView inputView) {
        mInputView = inputView;
    }
    
    void makeKeyboards() {
        // Configuration change is coming after the keyboard gets recreated. So don't rely on that.
        // If keyboards have already been made, check if we have a screen width change and 
        // create the keyboard layouts again at the correct orientation
        if ((mKeyboards != null) || (mSymbolsKeyboards != null)) 
        {
            int displayWidth = mContext.getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        // Delayed creation when keyboard mode is set.
        mSymbolsKeyboards = null;
        mKeyboards = null;
    }

    void setKeyboardMode(int mode, int imeOptions) {
        //mMode = mode;
        mImeOptions = imeOptions;
        AnyKeyboard keyboard = (AnyKeyboard) mInputView.getKeyboard();
        mInputView.setPreviewEnabled(true);
        //creating what needed.
        
        switch (mode) {
            case MODE_TEXT:
            case MODE_URL:
            case MODE_EMAIL:
            case MODE_IM:
            	if (mKeyboards == null)
            		mKeyboards = KeyboardFactory.createAlphaBetKeyboards(mContext);
            	break;
            case MODE_SYMBOLS:
            case MODE_PHONE:
                if (mSymbolsKeyboards == null)
                {
                	mSymbolsKeyboards = new AnyKeyboard[3];
                	mSymbolsKeyboards[0] = new GenericKeyboard(mContext, R.xml.symbols, false, -1); 
                	mSymbolsKeyboards[1] = new GenericKeyboard(mContext, R.xml.symbols_shift, false, -1);
                	mSymbolsKeyboards[2] = new GenericKeyboard(mContext, R.xml.simple_numbers, false, -1);
                }
                break;
        }
        mInputView.setKeyboard(keyboard);
        keyboard.setShifted(false);
        //keyboard.setShiftLocked(keyboard.isShiftLocked());
        keyboard.setImeOptions(mContext.getResources()/*, mMode*/, imeOptions);
    }

//    int getKeyboardMode() {
//        return mMode;
//    }
//    
//    boolean isTextMode() {
//        return mMode == MODE_TEXT;
//    }
//    
//    int getTextMode() {
//        return mTextMode;
//    }
//    
//    void setTextMode(int position) {
//        if (position < MODE_TEXT_COUNT && position >= 0) {
//            mTextMode = position;
//        }
//        if (isTextMode()) {
//            setKeyboardMode(MODE_TEXT, mImeOptions);
//        }
//    }
//
//    int getTextModeCount() {
//        return MODE_TEXT_COUNT;
//    }

    boolean isAlphabetMode() {
        Keyboard current = mInputView.getKeyboard();
        for(AnyKeyboard enabledKeyboard : mKeyboards)
        {
        	if (enabledKeyboard == current)
        		return true;
        }
//        if (current == mQwertyKeyboard
//                || current == mAlphaKeyboard
//                || current == mUrlKeyboard
//                || current == mIMKeyboard
//                || current == mEmailKeyboard) {
//            return true;
//        }
        return false;
    }

    void toggleShift() 
    {
        Keyboard currentKeyboard = mInputView.getKeyboard();
        
        if (currentKeyboard == mSymbolsKeyboards[0]) 
        {
        	mLastSelectedSymbolsKeyboard = 1;
        }
        else if (currentKeyboard == mSymbolsKeyboards[1]) 
        {
        	mLastSelectedSymbolsKeyboard = 0;
        }
        else return;
        
        AnyKeyboard nextKeyboard = mSymbolsKeyboards[mLastSelectedSymbolsKeyboard];
        boolean shiftStateToSet = currentKeyboard == mSymbolsKeyboards[0];
    	currentKeyboard.setShifted(shiftStateToSet);
        mInputView.setKeyboard(nextKeyboard);
        nextKeyboard.setShifted(shiftStateToSet);
        nextKeyboard.setImeOptions(mContext.getResources()/*, mMode*/, mImeOptions);
    }
    
    void nextKeyboard()
    {
    	AnyKeyboard current;
    	if (isAlphabetMode())
    	{
    		mLastSelectedKeyboard++;
    		if (mLastSelectedKeyboard >= mKeyboards.size())
    			mLastSelectedKeyboard = 0;
    		
    		current = mKeyboards.get(mLastSelectedKeyboard);
    	}
    	else
    	{
    		mLastSelectedSymbolsKeyboard++;
    		if (mLastSelectedSymbolsKeyboard >= mSymbolsKeyboards.length)
    			mLastSelectedSymbolsKeyboard = 0;
    		
    		current = mSymbolsKeyboards[mLastSelectedSymbolsKeyboard];
    	}
    	mInputView.setKeyboard(current);
    	//all keyboards start as un-shifted, except the second symbols
    	current.setShifted(current == mSymbolsKeyboards[1]);
    }

//    void toggleSymbols() {
//        Keyboard current = mInputView.getKeyboard();
//        if (mSymbolsKeyboard == null) {
//            mSymbolsKeyboard = new LatinKeyboard(mContext, R.xml.kbd_symbols);
//        }
//        if (mSymbolsShiftedKeyboard == null) {
//            mSymbolsShiftedKeyboard = new LatinKeyboard(mContext, R.xml.kbd_symbols_shift);
//        }
//        if (current == mSymbolsKeyboard || current == mSymbolsShiftedKeyboard) {
//            setKeyboardMode(mMode, mImeOptions); // Could be qwerty, alpha, url, email or im
//            return;
//        } else if (current == mPhoneKeyboard) {
//            current = mPhoneSymbolsKeyboard;
//            mPhoneSymbolsKeyboard.setImeOptions(mContext.getResources(), mMode, mImeOptions);
//        } else if (current == mPhoneSymbolsKeyboard) {
//            current = mPhoneKeyboard;
//            mPhoneKeyboard.setImeOptions(mContext.getResources(), mMode, mImeOptions);
//        } else {
//            current = mSymbolsKeyboard;
//            mSymbolsKeyboard.setImeOptions(mContext.getResources(), mMode, mImeOptions);
//        }
//        mInputView.setKeyboard(current);
//        if (current == mSymbolsKeyboard) {
//            current.setShifted(false);
//        }
//    }
}
