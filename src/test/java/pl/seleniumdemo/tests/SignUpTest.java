package pl.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.LoggedUserPage;
import pl.seleniumdemo.pages.SignUpPage;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {
        String name = "Piotrek";
        int randomNumber = (int) (Math.random() * 1000);
        String randomMail = name + randomNumber + "@pio.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstNameInput(name);
        signUpPage.setLastNameInput("Pio");
        signUpPage.setPhone("987654321");
        signUpPage.setEmail(randomMail);
        signUpPage.setPassword("123456");
        signUpPage.setConfirmPassword("123456");
        signUpPage.signUp();

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertTrue(loggedUserPage.getHeadingtext().contains(name));
        Assert.assertEquals(loggedUserPage.getHeadingtext(), "Hi, Piotrek Pio");
    }

    @Test
    public void signUpTest2() {
        int randomNumber = (int) (Math.random() * 1000);
        String randomMail = "name" + randomNumber + "@pio.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstNameInput("Marek");
        signUpPage.setLastNameInput("Marecki");
        signUpPage.setEmail(randomMail);
        signUpPage.setPhone("111111111");
        signUpPage.setPassword("qazwsx");
        signUpPage.setConfirmPassword("qazwsx");
        signUpPage.signUp();

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        SeleniumHelper.waitForElementToExist(driver, By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(loggedUserPage.getHeadingtext().contains("Marecki"));
        Assert.assertEquals(loggedUserPage.getHeadingtext(), "Hi, Marek Marecki");
    }

    @Test
    public void SignUpEmptyFormTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();
        SignUpPage signUpPage = new SignUpPage(driver);
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

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstNameInput(name);
        signUpPage.setLastNameInput("Pio");
        signUpPage.setPhone("987654321");
        signUpPage.setEmail("mail");
        signUpPage.setPassword("123456");
        signUpPage.setConfirmPassword("123456");
        signUpPage.signUp();

        SeleniumHelper.waitForNotEmptyList(driver, By.xpath("//div[@class='alert alert-danger']//p"));
        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}