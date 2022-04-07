package com.example.room;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

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

@RunWith(AndroidJUnit4ClassRunner.class)
public class AdminTest {

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
        onData(anything()).atPosition(0).perform(click());

        onView(withId(R.id.room_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());

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

    @Test
    public void addRoomWithEmptyFields_notSuccessfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_room))
                .perform(click());

        onView(withId(R.id.add_room_button))
                .perform(click());

        onView(withText(R.string.emptyField))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void addInstrumentWithEmptyField_notSuccessfully() {
        adminAuthentication();

        onView(withId(R.id.navigation_instrument))
                .perform(click());

        onView(withId(R.id.add_instrument_button))
                .perform(click());

        onView(withText(R.string.emptyField))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    private void adminAuthentication() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.authentication_editLogin)).perform(clearText(), typeText("admin"));
        onView(withId(R.id.authentication_editPassword)).perform(clearText(), typeText("password"));

        onView(withId(R.id.sign_in_button))
                .perform(click());
    }
}
