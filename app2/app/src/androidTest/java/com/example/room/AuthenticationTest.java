package com.example.room;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.room.view.activity.AuthenticationActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AuthenticationTest {

    private final String login = "login";
    private final String pwd = "pwd";

    @Test
    public void customerRegistration_successfully() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.sign_up_authentication_button))
                .perform(click());

        onView(withId(R.id.sign_up_editName)).perform(clearText(), typeText("testName"));
        onView(withId(R.id.sign_up_editPhone)).perform(clearText(), typeText("+79217778844"));
        onView(withId(R.id.sign_up_editLogin)).perform(clearText(), typeText(login));
        onView(withId(R.id.sign_up_editPassword)).perform(clearText(), typeText(pwd));

        onView(withId(R.id.sign_up_button))
                .perform(click());

        onView(withText(R.string.signedUp))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void customerAuthorization_successfully() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.authentication_editLogin)).perform(clearText(), typeText(login));
        onView(withId(R.id.authentication_editPassword)).perform(clearText(), typeText(pwd));

        onView(withId(R.id.sign_in_button))
                .perform(click());

        onView(withId(R.id.text_home))
                .check(matches(isDisplayed()));
    }

    @Test
    public void customerAuthorization_notSuccessfully() {
        ActivityScenario.launch(AuthenticationActivity.class);

        onView(withId(R.id.authentication_editLogin)).perform(clearText(), typeText("testLogin"));
        onView(withId(R.id.authentication_editPassword)).perform(clearText(), typeText("testPwd"));

        onView(withId(R.id.sign_in_button))
                .perform(click());

        onView(withId(R.id.text_home))
                .check(doesNotExist());

        onView(withText(R.string.userNotFound))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
