package com.anysoftkeyboard.ng.keyboards;

import com.anysoftkeyboard.ng.AnyKeyboardContextProvider;
import com.anysoftkeyboard.ng.AnySoftKeyboardConfiguration;
import com.anysoftkeyboard.ng.keyboards.KeyboardBuildersFactory.KeyboardBuilder;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class KeyboardFactory 
{
	private static final String TAG = "ASK_KF";
    public static KeyboardBuilder[] createAlphaBetKeyboards(AnyKeyboardContextProvider contextProvider)
    {
    	final ArrayList<KeyboardBuilder> keyboardCreators = KeyboardBuildersFactory.getAllBuilders(contextProvider.getApplicationContext());
        Log.i(TAG, "Creating keyboards. I have "+ keyboardCreators.size()+" creators");
        //Thread.dumpStack();

        //getting shared prefs to determine which to create.
        final SharedPreferences sharedPreferences = contextProvider.getSharedPreferences();
        
        final ArrayList<KeyboardBuilder> keyboards = new ArrayList<KeyboardBuilder>();
        for(int keyboardIndex=0; keyboardIndex<keyboardCreators.size(); keyboardIndex++)
        {
            final KeyboardBuilder creator = keyboardCreators.get(keyboardIndex);
            //the first keyboard is defaulted to true
            final boolean keyboardIsEnabled = sharedPreferences.getBoolean(creator.getId(),
            		creator.getKeyboardDefaultEnabled());

            if (keyboardIsEnabled)
            {
                keyboards.add(creator);
            }
        }

        // Fix: issue 219
        // Check if there is any keyboards created if not, lets create a default english keyboard
        if( keyboards.size() == 0 ) {
            final SharedPreferences.Editor editor = sharedPreferences.edit( );
            final KeyboardBuilder creator = keyboardCreators.get( 0 );
            editor.putBoolean( creator.getId( ) , true );
            editor.commit( );
            keyboards.add( creator );
        }

        if (AnySoftKeyboardConfiguration.DEBUG)
        {
	        for(final KeyboardBuilder aKeyboard : keyboards) {
	            Log.d(TAG, "Factory provided creator: "+aKeyboard.getId());
	        }
        }

        keyboards.trimToSize();
        final KeyboardBuilder[] keyboardsArray = new KeyboardBuilder[keyboards.size()];
        return keyboards.toArray(keyboardsArray);
    }

    
}
