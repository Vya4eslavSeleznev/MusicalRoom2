package com.example.room;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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

@RunWith(AndroidJUnit4ClassRunner.class)
public class UserTest {

    @Test
    public void addCustomerReservation_successfully() {
        final String room = "room1";
        userAuthentication();

        onView(withId(R.id.navigation_reserve))
                .perform(click());

        onView(withId(R.id.roomSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(room))).perform(click());
        onView(withId(R.id.roomSpinner)).check(matches(withSpinnerText(containsString(room))));

        onView(withId(R.id.dateButton))
                .perform(click());

        onView(withText(R.string.ok))
                .perform(click());

        onView(withId(R.id.reservationButton))
                .perform(click());

        onView(withText(R.string.successful))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void browseEquipmentByCustomer_successfully() {
        userAuthentication();

        onView(withId(R.id.navigation_equipment))
                .perform(click());

        onView(withId(R.id.user_rooms_instrument_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        pressBack();

        onView(withId(R.id.user_rooms_instrument_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        pressBack();

        onView(withId(R.id.user_rooms_instrument_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }

    @Test
    public void updateCustomerData_successfully() {
        userAuthentication();

        onView(withId(R.id.navigation_profile))
                .perform(click());

        onView(withId(R.id.editName)).perform(clearText(), typeText("NewName"));
        onView(withId(R.id.editPhone)).perform(clearText(), typeText("4444"));

        onView(withId(R.id.refresh_button))
                .perform(click());

        onView(withText(R.string.updatedSuccessfully))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void deleteCustomerReservation_successfully() {
        userAuthentication();

        onView(withId(R.id.navigation_profile))
                .perform(click());

        onView(withId(R.id.my_rooms_button))
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
                        View button = view.findViewById(R.id.reservation_delete_button);
                        button.performClick();
                    }
                }));
    }

    @Test
    public void deleteAllReservations_successfully() {
        userAuthentication();

        onView(withId(R.id.navigation_profile))
                .perform(click());

        onView(withId(R.id.my_rooms_button))
                .perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(R.string.deleteAll))
                .perform(click());

        onView(withText(R.string.yes))
                .perform(click());
    }

    private void userAuthentication() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.authentication_editLogin)).perform(clearText(), typeText("customer"));
        onView(withId(R.id.authentication_editPassword)).perform(clearText(), typeText("password"));

        onView(withId(R.id.sign_in_button))
                .perform(click());
    }
}
