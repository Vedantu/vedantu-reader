package com.vedantu.android.reader.customize;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.view.View;

import com.vedantu.android.reader.R;

public class CustomizeViewerLayout {

    static Map<String, Integer> keyToIdMap = new HashMap<String, Integer>();
    static {
        keyToIdMap.put(Constants.VEDANTU_READER_LINK_BUTTON, R.id.linkButton);
        keyToIdMap.put(Constants.VEDANTU_READER_REFLOW_BUTTON, R.id.reflowButton);
        keyToIdMap.put(Constants.VEDANTU_READER_OUTLINE_BUTTON, R.id.outlineButton);
        keyToIdMap.put(Constants.VEDANTU_READER_SEARCH_BUTTON, R.id.searchButton);
        keyToIdMap.put(Constants.VEDANTU_READER_MORE_BUTTON, R.id.moreButton);
        keyToIdMap.put(Constants.VEDANTU_READER_HIGHLIGHT_BUTTON, R.id.highlightButton);
        keyToIdMap.put(Constants.VEDANTU_READER_UNDERLINE_BUTTON, R.id.underlineButton);
        keyToIdMap.put(Constants.VEDANTU_READER_STRIKE_OUT_BUTTON, R.id.strikeOutButton);
        keyToIdMap.put(Constants.VEDANTU_READER_INK_BUTTON, R.id.inkButton);
        keyToIdMap.put(Constants.VEDANTU_READER_PRINT_BUTTON, R.id.printButton);
        keyToIdMap.put(Constants.VEDANTU_READER_COPY_TEXT_BUTTON, R.id.copyTextButton);
        keyToIdMap.put(Constants.VEDANTU_READER_EDIT_ANNOTATION_BUTTON, R.id.editAnnotButton);
    }

    public static void customizeButtonViews(Intent intent, View buttonLayoutContainer) {

        if (intent == null || buttonLayoutContainer == null) {
            return;
        }

        String[] hideLayouts = intent.getStringArrayExtra(Constants.VEDANTU_READER_HIDE_BUTTONS);

        if (hideLayouts == null) {
            return;
        }

        for (String layoutKey : hideLayouts) {
            Integer id = keyToIdMap.get(layoutKey.trim());
            View buttonView = null;
            if (id != null
                    && (buttonView = buttonLayoutContainer.findViewById(id.intValue())) != null) {
                buttonView.setVisibility(View.INVISIBLE);
            }
        }
        intent.removeExtra(Constants.VEDANTU_READER_HIDE_BUTTONS);
    }
}
