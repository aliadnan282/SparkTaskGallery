package com.spaktask;

import android.content.Context;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.spaktask.utils.StateLiveData;
import com.spaktask.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Adnan Ali on 9/19/2019.
 * Email: aliadnan282@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);


    @Test
    public void menuRefreshdButtonClick() {
        onView(withId(R.id.mi_refresh))
                .check(matches(isDisplayed()));

        onView(withId(R.id.mi_refresh))
                .perform(click());
    }

    @Test
    public void uploadButtonClick() {

        onView(withId(R.id.fb_upload))
                .perform(click());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.sparktask", appContext.getPackageName());
    }
}
