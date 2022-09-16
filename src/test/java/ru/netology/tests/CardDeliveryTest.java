package ru.netology.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Visible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.DownloadActions.click;
import static org.openqa.selenium.devtools.v101.log.Log.clear;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
    }

    @Test
    void shouldTestCompletedForm() {

        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldTryToSpendEmptyForm() {
        $("[placeholder=\"Город\"]").setValue("");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $x("//*[contains(text(),\"Поле обязательно для заполнения\")]").hover();

    }

    //протестируем поле "Выберете ваш город"
    @Test
    void shouldTryToNotFillFieldCities() {
        $("[placeholder=\"Город\"]").setValue("");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $x("//*[contains(text(),\"Поле обязательно для заполнения\")]").hover();
    }

    @Test
    void shouldTryToPutIncorrectValuesFieldCities1() {
        $("[placeholder=\"Город\"]").setValue("Moscow");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $x("//span[@data-test-id=\"city\"]").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTryToPutIncorrectValuesFieldCities2() {
        $("[placeholder=\"Город\"]").setValue("Дзержинск");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $x("//span[@data-test-id=\"city\"]").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTryToPutIncorrectValuesFieldCities3() {
        $("[placeholder=\"Город\"]").setValue("7");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $x("//span[@data-test-id=\"city\"]").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    //Протестируем поле "Дата встречи"
    @Test
    void shouldTryToNotContainsFieldDate() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        //String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldTryToPutWrongDate1() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTryToPutWrongDate2() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTryToPutWrongDate3() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    //Протестируем поле "Фамилия и имя"
    @Test
    void shouldTryToPutWrongName() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Leia Organa-Solo");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTryToPutNameWithSpecialLetter() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Алёна Апина");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15));
    }


    //Протестируем поле "Мобильный телефон"
    @Test
    void shouldTryToPutWrongPhone1() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+780055535355");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"phone\"] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTryToPutWrongPhone2() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+7800555353");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"phone\"] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTryToPutWrongPhone3() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("88005553535");
        $x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"phone\"] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // игнорируем чекбокс
    @Test
    void shouldTryToNotClickOnTheCheckBox() {
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder=\"Дата встречи\"]").setValue(requireDate);
        $("[name=\"name\"]").setValue("Лея Органа-Соло");
        $x("//input[@name=\"phone\"]").setValue("+78005553535");
        //$x("//label[@data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id=\"agreement\"].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}

