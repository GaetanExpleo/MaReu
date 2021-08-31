package gaetan.renault.mareu;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import gaetan.renault.mareu.ui.meetings.MeetingsActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static gaetan.renault.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest{

    private ActivityScenario<MeetingsActivity> activityScenario;

    @Before
    public void setUp(){
        activityScenario = ActivityScenario.launch(MeetingsActivity.class);
        assertThat(activityScenario, notNullValue());
    }

    @Test
    public void myMeetingList_addMeeting_shouldAddItem(){
        onView(allOf(isDisplayed(),withId(R.id.list_meeting_recyclerview))).check(withItemCount(0));
        onView(allOf(isDisplayed(),withId(R.id.list_meeting_fab))).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.meeting_topic_tiet))).perform(replaceText("Réunion 1"));
        onView(allOf(isDisplayed(),withId(R.id.meeting_participants_tiet))).perform(replaceText("participant@lamzone.com"));
        onView(allOf(isDisplayed(),withId(R.id.date_tiet))).perform(replaceText("30/08/2021"));
        onView(allOf(isDisplayed(),withId(R.id.time_tiet))).perform(replaceText("10:00"));
        onView(allOf(isDisplayed(),withId(R.id.duration_tiet))).perform(replaceText("1h"));
        onView(allOf(isDisplayed(),withId(R.id.create_button))).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.list_meeting_recyclerview))).check(withItemCount(1));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}