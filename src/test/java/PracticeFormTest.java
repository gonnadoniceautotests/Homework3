import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTest {

    TestData creds = new TestData();

    @BeforeAll
    public static void suiteSetup() {
        Configuration.baseUrl = "https://demoqa.com";
    }

    @Test
    void fillFormAssertModal () {
        open(creds.formPath);
        // Для маленьких разрешений нужно спрятать футер, иначе не кликнуть Submit
        // Хорошо бы перенести это в before вместе с "open", но пока слабо разбираюсь в static/public etc.
        executeJavaScript("$('footer').hide()");
        executeJavaScript("$('#close-fixedban').hide()");
        $("#firstName").setValue(creds.firstName);
        $("#lastName").setValue(creds.lastName);
        $("#userEmail").setValue(creds.userEmail);
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue(creds.phoneNumber);
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL + "a");
        $("#dateOfBirthInput").sendKeys(creds.bDay);
        // Для больших разрешений нужно кликнуть по пустому месту, т.к. календарь откроется вниз и заблокирует Subject
        $("#dateOfBirth-label").click();
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $(".form-control-file").uploadFile(new File("src/test/resources/userpic.png"));
        $("#currentAddress").setValue(creds.address);
        $$("div#state").first().$("input").setValue(creds.state).pressEnter();
        $$("div#city").first().$("input").setValue(creds.city).pressEnter();
        $("#submit").click();
        $(".modal-title").shouldHave(text("Thanks for submitting the form"));
        $(".modal-body").shouldHave(text(creds.firstName), text(creds.lastName),
                text(creds.userEmail), text(creds.phoneNumber), text(creds.bDay),
                text("Maths"), text("Sports"), text("Reading"), text("Music"),
                text("userpic.png"), text(creds.address), text(creds.state), text(creds.city));
    }


}
