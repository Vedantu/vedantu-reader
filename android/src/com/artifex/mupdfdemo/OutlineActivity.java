package com.artifex.mupdfdemo;

import com.vedantu.android.reader.customize.Constants;
import com.vedantu.android.reader.utils.ga.GoogleAnalyticsUtils;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class OutlineActivity extends ListActivity {
	OutlineItem mItems[];

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalyticsUtils.setScreenName(Constants.GA_SCREEN_NAME_PDF_OUTLINE_ACTIVITY);
		mItems = OutlineActivityData.get().items;
		setListAdapter(new OutlineAdapter(getLayoutInflater(),mItems));
		// Restore the position within the list from last viewing
		getListView().setSelection(OutlineActivityData.get().position);
		getListView().setDividerHeight(0);
		setResult(-1);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		OutlineActivityData.get().position = getListView().getFirstVisiblePosition();
		setResult(mItems[position].page);
		finish();
	}
}
