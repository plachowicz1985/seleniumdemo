package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.LoggedUserPage;
import pl.seleniumdemo.pages.SignUpPage;

import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() throws InterruptedException {
        String name = "Piotrek";
        int randomNumber = (int) (Math.random() * 1000);

        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstNameInput(name)
                .setLastNameInput("Pio")
                .setPhone("987654321")
                .setEmail(name + randomNumber + "@pio.pl")
                .setPassword("123456")
                .setConfirmPassword("123456")
                .signUp();

        Assert.assertTrue(loggedUserPage.getHeadingtext().contains(name));
        Assert.assertEquals(loggedUserPage.getHeadingtext(), "Hi, Piotrek Pio");
    }

    @Test
    public void SignUpEmptyFormTest() {

        SignUpPage signUpPage = new HotelSearchPage(driver).openSignUpForm();
        signUpPage.signUp();

        List<String> errors = signUpPage.getErrors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void signUpWithoutCorrectEmailTest() {
        String name = "Piotrek";

        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstNameInput(name)
                .setLastNameInput("Pio")
                .setPhone("987654321")
                .setEmail(name)
                .setPassword("123456")
                .setConfirmPassword("123456");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}