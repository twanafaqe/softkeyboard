package com.menny.android.anysoftkeyboard.Dictionary;

import java.io.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

class AssertsSQLiteConnection extends DictionarySQLiteConnection {

	//The Android's default system path of your application database.
    private static final String DB_PATH = "/data/data/com.menny.android.anysoftkeyboard/databases/";

    private final String mDbName;
    private SQLiteDatabase mDataBase; 
    
	protected AssertsSQLiteConnection(Context conext, String dbName, String wordsTableName) throws IOException {
		super(conext, dbName, wordsTableName, "Word", "Frequency");
		mDbName = dbName;
		
		createDataBase();
	}
	
	/**
    * Creates a empty database on the system and rewrites it with your own database.
    * */
	private void createDataBase() throws IOException
	{
		boolean dbExist = checkDataBase();
		if(dbExist){
			Log.v("AnySoftKeyboard", "AssertsSQLiteConnection:createDataBase: Database exists.");
			//do nothing - database already exist
		}else{
			Log.d("AnySoftKeyboard", "AssertsSQLiteConnection:createDataBase: Database does not exist. Creating empty database file for "+mDbName);
			//By calling this method and empty database will be created into the default system path
		    //of your application so we are gonna be able to overwrite that database with our database.
		   	super.getReadableDatabase();
		
		   	try {		
				copyDataBase();		
			} catch (IOException e) {		
		   		throw new Error("Error copying database");		
		   	}
		}
	}
	
	@Override
	public synchronized void close() {
		if(mDataBase != null)
			mDataBase.close();
		
		mDataBase = null;
		super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
	}


   /**
    * Check if the database already exist to avoid re-copying the file each time you open the application.
    * @return true if it exists, false if it doesn't
    */
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		
		try{
			String myPath = DB_PATH + mDbName;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){		
			//database does't exist yet.
			return false;
		}
		
		if(checkDB != null){		
			checkDB.close();		
		}
		
		return checkDB != null ? true : false;
	}

   /**
    * Copies your database from your local assets-folder to the just created empty database in the
    * system folder, from where it can be accessed and handled.
    * This is done by transfering bytestream.
    * */
   private void copyDataBase() throws IOException{

   	//Open your local db as the input stream
   	InputStream myInput = super.mContext.getAssets().open(mDbName);

   	// Path to the just created empty db
   	String outFileName = DB_PATH + mDbName;

   	Log.d("AnySoftKeyboard", "AssertsSQLiteConnection: About to copy DB from assets to '"+outFileName+"'. Size: "+myInput.available());
   	//Open the empty db as the output stream
   	OutputStream myOutput = new FileOutputStream(outFileName);

   	//transfer bytes from the inputfile to the outputfile
   	byte[] buffer = new byte[1024];
   	int length;
   	while ((length = myInput.read(buffer))>0){
   		myOutput.write(buffer, 0, length);
   	}

   	//Close the streams
   	myOutput.flush();
   	myOutput.close();
   	myInput.close();
   	Log.d("AnySoftKeyboard", "AssertsSQLiteConnection: DB was copied!");
   }
   
   @Override
   public synchronized SQLiteDatabase getReadableDatabase() {
	   if (mDataBase != null)
		   return mDataBase;
	   
		String myPath = DB_PATH + mDbName;
		mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		return mDataBase;
   }
}
