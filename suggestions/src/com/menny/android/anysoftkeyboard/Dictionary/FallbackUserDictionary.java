package com.menny.android.anysoftkeyboard.Dictionary;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;


public class FallbackUserDictionary extends UserDictionaryBase {

	private class FallBackSQLite extends SQLiteOpenHelper
	{
		private static final String DB_NAME = "fallback.db";
		private static final String TABLE_NAME = "FALL_BACK_USER_DICTIONARY";
		private static final String WORD_COL = "Word";
		private static final String FREQ_COL = "Freq";
		
		public FallBackSQLite(Context context) {
			super(context, DB_NAME, null, 2);
			// TODO Auto-generated constructor stub
		}

		@Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + "Id INTEGER PRIMARY KEY,"
                    + WORD_COL+" TEXT,"
                    + FREQ_COL+" INTEGER"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
		
        public void addWord(String word, int freq)
        {
        	SQLiteDatabase db = super.getWritableDatabase();
        	
        	ContentValues values = new ContentValues();
        	values.put("Id", word.hashCode());//ensuring that any word is inserted once
        	values.put(WORD_COL, word);
        	values.put(FREQ_COL, freq);
			long res = db.insert(TABLE_NAME, null, values);
			if (res < 0)
			{
				Log.e("AnySoftKeyboard", "Unable to insert '"+word+"' to the fall-back dictionary! Result:"+res);
			}
			else
			{
				Log.d("AnySoftKeyboard", "Inserted '"+word+"' to the fall-back dictionary. Id:"+res);
			}
        }
        
        public List<String> getAllWords()
        {
        	List<String> words = new ArrayList<String>();
        	SQLiteDatabase db = super.getReadableDatabase();
        	Cursor c = db.query(TABLE_NAME, new String[]{WORD_COL}, null, null, null, null, null);
        	
        	if (c != null)
        	{
	        	if (c.moveToFirst()) {
	                while (!c.isAfterLast()) {
	                    String word = c.getString(0);
	                    if (word.length() < MAX_WORD_LENGTH) {
	                    	words.add(word);
	                    }
	                    c.moveToNext();
	                }
	            }
	        	c.close();
        	}
            
        	return words;
        }
	}
	
	private FallBackSQLite mStorage;
	
	public FallbackUserDictionary(AnyKeyboardContextProvider context) {
		super(context);
	}
	
	@Override
	protected void loadAllWords() 
	{
		if (mStorage == null)
			mStorage = new FallBackSQLite(mContext);
		
		List<String> words = mStorage.getAllWords();
		for(String word : words)
		{
			Log.d("AnySoftKeyboard", "Fall-back dictionary loaded: "+word);
			addWordFromStorage(word, 128);
		}		
	}

	@Override
	protected void AddWordToStorage(String word, int frequency) {
		mStorage.addWord(word, frequency);
	}

	@Override
	protected void closeAllResources() {
		mStorage.close();
	}
}
