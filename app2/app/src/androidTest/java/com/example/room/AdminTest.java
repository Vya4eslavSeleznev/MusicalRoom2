package com.example.room;

//import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.room.view.activity.AuthenticationActivity;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

//import android.support.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AdminTest {

    //@Rule
    //public final ActivityTestRule<RoomActivity> roomActivityRule = new ActivityTestRule<>(RoomActivity.class);

    //private int getRVcount(){
        //ActivityTestRule<RoomActivity> roomActivityRule = new ActivityTestRule<>(RoomActivity.class);
        //RecyclerView recyclerView = roomActivityRule.getActivity().findViewById(R.id.room_recyclerview);
        //return recyclerView.getAdapter().getItemCount();
    //}





    @Test
    public void bookingConfirmation_successfully() {
        adminAuthentication();

        onView(withId(R.id.confirmation_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on delete button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View button = view.findViewById(R.id.confirmation_button);
                        button.performClick();
                    }
                }));
    }

    @Test
    public void addRoom_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_room))
                .perform(click());

        onView(withId(R.id.room_name_edit)).perform(clearText(), typeText("RoomName"));
        onView(withId(R.id.room_description_edit)).perform(clearText(), typeText("RoomDescription"));
        onView(withId(R.id.room_price_edit)).perform(clearText(), typeText("1234"));

        onView(withId(R.id.add_room_button))
                .perform(click());

        onView(withText(R.string.addedSuccessfully))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void deleteRoom_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_room))
                .perform(click());

        onView(withId(R.id.view_rooms_button))
                .perform(click());

        ///if (getRVcount() > 0) {
            onView(withId(R.id.room_recyclerview))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                        @Override
                        public Matcher<View> getConstraints() {
                            return null;
                        }

                        @Override
                        public String getDescription() {
                            return "Click on delete button";
                        }

                        @Override
                        public void perform(UiController uiController, View view) {
                            View button = view.findViewById(R.id.admin_room_delete_button);
                            button.performClick();
                        }
                    }));
        //}
    }

    @Test
    public void addInstrument_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_instrument))
                .perform(click());

        onView(withId(R.id.instrument_name_edit)).perform(clearText(), typeText("InstrumentName"));
        onView(withId(R.id.instrument_description_edit)).perform(clearText(), typeText("InstrumentDescription"));

        onView(withId(R.id.add_instrument_button))
                .perform(click());

        onView(withText(R.string.addedSuccessfully))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void deleteInstrument_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_instrument))
                .perform(click());

        onView(withId(R.id.view_instruments_button))
                .perform(click());

        onView(withId(R.id.instrument_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on delete button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View button = view.findViewById(R.id.delete_admin_button);
                        button.performClick();
                    }
                }));
    }

    @Test
    public void addRoomsInstrument_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_rooms_instrument))
                .perform(click());

        onView(withId(R.id.instrument_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("instrument_name1"))).perform(click());
        onView(withId(R.id.instrument_spinner)).check(matches(withSpinnerText(containsString("instrument_name1"))));

        onView(withId(R.id.room_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("room2"))).perform(click());
        onView(withId(R.id.room_spinner)).check(matches(withSpinnerText(containsString("room2"))));

        onView(withId(R.id.add_equipment_button))
                .perform(click());

        onView(withText(R.string.successful))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void deleteRoomsInstrument_successfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_rooms_instrument))
                .perform(click());

        onView(withId(R.id.view_equipment_button))
                .perform(click());

        onView(withId(R.id.rooms_instrument_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on delete button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View button = view.findViewById(R.id.rooms_instrument_delete_button);
                        button.performClick();
                    }
                }));
    }

    private void adminAuthentication() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.authentication_editLogin)).perform(clearText(), typeText("admin"));
        onView(withId(R.id.authentication_editPassword)).perform(clearText(), typeText("password"));

        onView(withId(R.id.sign_in_button))
                .perform(click());
    }
}
