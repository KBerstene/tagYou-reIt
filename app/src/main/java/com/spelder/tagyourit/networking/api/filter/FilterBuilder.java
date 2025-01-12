package com.spelder.tagyourit.networking.api.filter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.spelder.tagyourit.R;

public class FilterBuilder {
  private static final String TAG = FilterBuilder.class.getName();

  private final String SHEET_MUSIC_KEY;

  private final String LEARNING_TRACK_KEY;

  private final String RATING_KEY;

  private final String TYPE_KEY;

  private final String KEY_KEY;

  private final String PARTS_NUMBER_KEY;

  private final SharedPreferences preferences;

  public FilterBuilder(Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SHEET_MUSIC_KEY = context.getResources().getString(R.string.filter_sheet_music_key);
    LEARNING_TRACK_KEY = context.getResources().getString(R.string.filter_learning_track_key);
    RATING_KEY = context.getResources().getString(R.string.filter_rating_key);
    TYPE_KEY = context.getResources().getString(R.string.filter_type_key);
    KEY_KEY = context.getResources().getString(R.string.filter_key_key);
    PARTS_NUMBER_KEY = context.getResources().getString(R.string.filter_part_key);
  }

  public boolean isFilterApplied() {
    boolean sheetMusic = !preferences.getBoolean(SHEET_MUSIC_KEY, false);
    boolean learningTrack = !preferences.getBoolean(LEARNING_TRACK_KEY, false);
    boolean ratingString = preferences.getString(RATING_KEY, "any").equals("any");
    boolean partString = preferences.getString(PARTS_NUMBER_KEY, "any").equals("any");
    boolean typeString = preferences.getString(TYPE_KEY, "any").equals("any");
    boolean keyString = preferences.getString(KEY_KEY, "key_any").equals("key_any");

    Log.d(TAG, "sheetMusic: " + sheetMusic);
    Log.d(TAG, "learningTrack: " + learningTrack);
    Log.d(TAG, "ratingString: " + ratingString);
    Log.d(TAG, "partString: " + partString);
    Log.d(TAG, "typeString: " + typeString);
    Log.d(TAG, "keyString: " + preferences.getString(KEY_KEY, "any"));

    return !(ratingString && partString && typeString && keyString && sheetMusic && learningTrack);
  }

  public FilterBy build() {
    FilterBy filter = new FilterBy();
    boolean sheetMusic = preferences.getBoolean(SHEET_MUSIC_KEY, false);
    boolean learningTrack = preferences.getBoolean(LEARNING_TRACK_KEY, false);
    String ratingString = preferences.getString(RATING_KEY, "any");
    String partString = preferences.getString(PARTS_NUMBER_KEY, "any");
    String typeString = preferences.getString(TYPE_KEY, "any");
    String keyString = preferences.getString(KEY_KEY, "key_any");

    filter.setHasSheetMusic(sheetMusic);
    filter.setHasLearningTrack(learningTrack);
    filter.setMinimumRating(convertRating(ratingString));
    filter.setNumberOfParts(convertPart(partString));
    filter.setType(convertType(typeString));
    filter.setKey(convertKey(keyString));

    Log.d(TAG, "Filter: " + filter);

    return filter;
  }

  private Double convertRating(String rating) {
    try {
      Log.d(TAG, rating);
      return Double.parseDouble(rating);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private Integer convertPart(String part) {
    try {
      return Integer.parseInt(part);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private Type convertType(String type) {
    try {
      return Type.valueOf(type);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private Key convertKey(String key) {
    try {
      Log.d(TAG, key);
      return Key.valueOf(key);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
