package com.menny.android.anysoftkeyboard.Dictionary;

import java.io.IOException;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;

public class SQLiteSimpleHeDictionary extends SQLiteUserDictionaryBase {

	protected SQLiteSimpleHeDictionary(AnyKeyboardContextProvider anyContext) {
		super(anyContext);
	}
	
	@Override
	protected DictionarySQLiteConnection createStorage() {
		try {
			return new AssertsSQLiteConnection(mContext, "he", "he");
		} catch (IOException e) {
			e.printStackTrace();
			return new DictionarySQLiteConnection(mContext, "he", "he", "Word", "Frequency");
		}
	}
	
	
	@Override
	public synchronized void addWord(String word, int frequency) {
		//does nothing
	}

}
