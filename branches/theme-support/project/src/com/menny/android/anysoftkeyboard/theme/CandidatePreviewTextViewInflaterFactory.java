package com.menny.android.anysoftkeyboard.theme;

import com.menny.android.anysoftkeyboard.CandidateViewContainer;
import com.menny.android.anysoftkeyboard.R;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.LayoutInflater.Factory;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CandidatePreviewTextViewInflaterFactory implements Factory {

	private ThemeResources mResources;
	private static final String CANDIDATE_PREVIEW_TEXTVIEW_TAG = "com.menny.android.anysoftkeyboard.theme.CandidatePreviewTextView";
	private static final String TEXT_SIZE_ATTRIBUTE_NAME = "textSize";
	private static final String TEXT_COLOR_ATTRIBUTE_NAME = "textColor";

	public CandidatePreviewTextViewInflaterFactory(ThemeResources resources) {
		mResources = resources;

	}

	public View onCreateView(String name, Context context, AttributeSet attrs) {
		if(CANDIDATE_PREVIEW_TEXTVIEW_TAG.equals(name)){
			TextView textView = new TextView(context, attrs);
			if(textView.getBackground() == null){
				textView.setBackgroundDrawable(mResources.getDrawable(R.id.theme_drawableCandidateFeedbackBackground));
			}
			if(!Utils.isAttributeDefined(attrs, TEXT_SIZE_ATTRIBUTE_NAME)){
				Float dimen = mResources.getDimension(R.id.theme_dimensionCandidatePreviewTextSize);
				if(dimen == null){
					textView.setTextSize(18);
				} else {
					textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen);
				}
			}
			if(!Utils.isAttributeDefined(attrs, TEXT_COLOR_ATTRIBUTE_NAME)){
				textView.setTextColor(mResources.getColor(R.id.theme_colorCandidatePreviewTextColor, color.primary_text_light));
			}

			return textView;
		}
		return null;
	}

}