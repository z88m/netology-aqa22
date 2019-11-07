package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;



public class Aqa221Test {

    @Test
    void inputValidDataExpectSuccess() {
        open("http://localhost:9999");

        //Блок создания переменной с датой на 3 дня впереди текущей
        LocalDate date = LocalDate.now().plusDays(3);
        String myDate = String.format("%02d.%02d.%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        //Конец блока создания даты на 5 дней впереди текущей

        //Ввод данных в форму
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(myDate);
        $("[data-test-id=name] input").setValue("Алибабаевич Василий");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        //Ожидание и проверка ответа
        SelenideElement notification = $("[data-test-id=notification]");
        $("[data-test-id=notification]").waitUntil(Condition.visible, 13003);
        $("[data-test-id=notification] .notification__title").should(Condition.exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").should(Condition.exactText("Встреча успешно забронирована на " + myDate));
    }
}
