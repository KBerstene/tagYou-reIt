package com.spelder.tagyourit.ui;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import com.spelder.tagyourit.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVideoPlayer {
  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule =
      new ActivityTestRule<>(MainActivity.class);

  @Test
  public void videoPlayerTest() {
    onView(ViewMatchers.withId(R.id.action_search)).perform(click());

    onView(withId(com.google.android.material.R.id.search_src_text))
        .perform(replaceText("like leaves"));

    onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

    onView(allOf(withId(R.id.action_menu), withContentDescription("Menu"))).perform(click());

    SystemClock.sleep(500);

    onData(anything()).inAdapterView(withId(R.id.list)).atPosition(7).perform(click());

    SystemClock.sleep(7000);

    onView(withId(R.id.panel)).perform(click());

    SystemClock.sleep(1000);

    onView(withId(R.id.panel)).perform(click());

    onView(allOf(withId(R.id.play_pause_button), withContentDescription("Play button")))
        .perform(click());

    onData(anything()).inAdapterView(withId(R.id.video_player_list)).atPosition(2).perform(click());

    onView(allOf(withContentDescription("Navigate up"), isDisplayed())).perform(click());

    onView(allOf(withId(R.id.action_menu), withContentDescription("Menu"))).perform(click());

    SystemClock.sleep(500);
    onView(withId(R.id.bottom_sheet)).perform(swipeUp());

    onData(anything()).inAdapterView(withId(R.id.list)).atPosition(11).perform(click());

    onView(withId(R.id.youtube_view)).check(matches(not(isDisplayed())));

    onView(
            allOf(
                withId(R.id.video_list_title),
                withText("Chanticleer Tag Time - Like Leaves Will Fall")))
        .check(matches(isDisplayed()));

    onView(allOf(withId(R.id.video_list_posted_by), withText("Chanticleer et al")))
        .check(matches(isDisplayed()));

    onView(allOf(withId(R.id.video_list_key), withText("B"))).check(matches(isDisplayed()));
  }
}
