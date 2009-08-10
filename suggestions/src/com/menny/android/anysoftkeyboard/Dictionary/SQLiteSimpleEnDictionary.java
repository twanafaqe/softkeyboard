package com.menny.android.anysoftkeyboard.Dictionary;

import java.io.IOException;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;

public class SQLiteSimpleEnDictionary extends SQLiteUserDictionaryBase {

	protected SQLiteSimpleEnDictionary(AnyKeyboardContextProvider anyContext) {
		super(anyContext);
	}
	
	@Override
	protected DictionarySQLiteConnection createStorage() {
		try {
			return new AssertsSQLiteConnection(mContext, "en_simple", "en");
		} catch (IOException e) {
			e.printStackTrace();
			return new DictionarySQLiteConnection(mContext, "en_simple", "en", "Word", "Frequency");
		}
	}
	
	
	@Override
	public synchronized void addWord(String word, int frequency) {
		//does nothing
	}

}
