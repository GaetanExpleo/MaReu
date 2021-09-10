package gaetan.renault.mareu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.ui.meetings.MeetingsActivity;
import gaetan.renault.mareu.utils.DatePickerFragment;
import gaetan.renault.mareu.utils.DeleteViewAction;
import gaetan.renault.mareu.utils.TimePickerFragment;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static gaetan.renault.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private ActivityScenario<MeetingsActivity> activityScenario;

    private MeetingsActivity mActivity;

    //First meeting
    private final String FIRST_TOPIC = "Réunion 1";
    private final String FIRST_PARTICIPANT = "gaetan@lamzone.com";
    private final String FIRST_DATE = "06/09/2021";
    private final String FIRST_HOUR = "10:00";
    private final String FIRST_DURATION = "1h";
    private final int ID_FIRST_ROOM = 0;

    //Second meeting
    private final String SECOND_TOPIC = "Réunion 2";
    private final String SECOND_PARTICIPANT = "gaetan@lamzone.com";
    private final String SECOND_DATE = "06/09/2021";
    private final String SECOND_HOUR = "11:00";
    private final String SECOND_DURATION = "1h";
    private final int ID_SECOND_ROOM = 1;

    //Third meeting
    private final String THIRD_TOPIC = "Réunion 3";
    private final String THIRD_PARTICIPANT = "gaetan@lamzone.com";
    private final String THIRD_DATE = "06/09/2021";
    private final String THIRD_HOUR = "10:00";
    private final String THIRD_DURATION = "1h";
    private final int ID_THIRD_ROOM = 0;

    @Before
    public void setUp() {
        activityScenario = ActivityScenario.launch(MeetingsActivity.class);
        activityScenario.onActivity(activity -> mActivity = activity);
        assertThat(activityScenario, notNullValue());
    }

    @After
    public void tearDown() {
        mActivity = null;
    }

    @Test
    public void myMeetingList_addMeeting_shouldAddItem() throws InterruptedException {
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(0));
        createMeeting(FIRST_TOPIC, FIRST_PARTICIPANT, FIRST_DATE, FIRST_HOUR, FIRST_DURATION, ID_FIRST_ROOM);
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(1));
        Thread.sleep(500);
        createMeeting(SECOND_TOPIC, SECOND_PARTICIPANT, SECOND_DATE, SECOND_HOUR, SECOND_DURATION, ID_SECOND_ROOM);
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(2));
    }

    @Test
    public void myMeetingList_deleteMeeting_shouldDeleteMeeting() throws InterruptedException {
        createMeeting(FIRST_TOPIC, FIRST_PARTICIPANT, FIRST_DATE, FIRST_HOUR, FIRST_DURATION, ID_FIRST_ROOM);
        Thread.sleep(500);
        createMeeting(SECOND_TOPIC, SECOND_PARTICIPANT, SECOND_DATE, SECOND_HOUR, SECOND_DURATION, ID_SECOND_ROOM);
        Thread.sleep(500);
        createMeeting(THIRD_TOPIC, THIRD_PARTICIPANT, THIRD_DATE, THIRD_HOUR, THIRD_DURATION, ID_THIRD_ROOM);
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(3));
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(2));
    }

    @Test
    public void myMeetingList_RoomFiltered_shouldDisplayMeetingWithRoomSelected() throws InterruptedException {
        createMeeting(FIRST_TOPIC, FIRST_PARTICIPANT, FIRST_DATE, FIRST_HOUR, FIRST_DURATION, ID_FIRST_ROOM);
        Thread.sleep(500);
        createMeeting(SECOND_TOPIC, SECOND_PARTICIPANT, SECOND_DATE, SECOND_HOUR, SECOND_DURATION, ID_SECOND_ROOM);
        Thread.sleep(500);
        createMeeting(THIRD_TOPIC, THIRD_PARTICIPANT, THIRD_DATE, THIRD_HOUR, THIRD_DURATION, ID_THIRD_ROOM);
        Thread.sleep(500);
        onView(allOf(isDisplayed(), withId(R.id.menu_filter_room))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_room_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(2));
    }

    @Test
    public void myMeetingList_HourFiltered_shouldDisplayMeetingWithHourSelected() throws InterruptedException {
        createMeeting(FIRST_TOPIC, FIRST_PARTICIPANT, FIRST_DATE, FIRST_HOUR, FIRST_DURATION, ID_FIRST_ROOM);
        Thread.sleep(500);
        createMeeting(SECOND_TOPIC, SECOND_PARTICIPANT, SECOND_DATE, SECOND_HOUR, SECOND_DURATION, ID_SECOND_ROOM);
        Thread.sleep(500);
        createMeeting(THIRD_TOPIC, THIRD_PARTICIPANT, THIRD_DATE, THIRD_HOUR, THIRD_DURATION, ID_THIRD_ROOM);
        Thread.sleep(500);
        onView(allOf(isDisplayed(), withId(R.id.menu_filter_hour))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_hour_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_recyclerview))).check(withItemCount(2));

    }

    public void createMeeting(
            @NonNull final String topic,
            @NonNull final String participants,
            @NonNull final String date,
            @NonNull final String hour,
            @NonNull final String duration,
            @NonNull final int idRoom
    ) throws InterruptedException {
        onView(allOf(isDisplayed(), withId(R.id.list_meeting_fab))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.meeting_topic_tiet))).perform(replaceText(topic));
        onView(allOf(isDisplayed(), withId(R.id.meeting_participants_tiet))).perform(replaceText(participants));
        onView(allOf(isDisplayed(), withId(R.id.meeting_participants_tiet))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.meeting_topic_tiet))).perform(click(), closeSoftKeyboard());
//        onView(allOf(isDisplayed(), withId(R.id.date_tiet))).perform(replaceText(date));
//        onView(allOf(isDisplayed(),withId(R.id.date_tiet))).perform(click());
        onData(withClassName(Matchers.equalTo(DatePickerFragment.class.getName()))).perform(PickerActions.setDate(2021,9,7));
//        onData(withId(android.R.id.button1)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePickerFragment.class.getName()))).perform(PickerActions.setTime(10,0));
//        onView(allOf(isDisplayed(), withId(R.id.time_tiet))).perform(replaceText(hour));
        onView(allOf(isDisplayed(), withId(R.id.duration_tiet))).perform(replaceText(duration));
        onData(allOf())
                .inAdapterView(withId(R.id.room_spinner))
                .atPosition(idRoom)
                .perform(doubleClick());
        Thread.sleep(500);
        onView(allOf(isDisplayed(), withId(R.id.create_button))).perform(click());
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