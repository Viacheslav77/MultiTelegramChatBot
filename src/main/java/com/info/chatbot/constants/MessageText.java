package com.info.chatbot.constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MessageText {

    public static final String MAIN_MENU = "Головне меню";
    public static final String COURT_CASES = "Судові справи ВС, судова практика і статистика";
    public static final String SEARCH_COURT_CASES = "Пошук судових справ";
    public static final String COURT_PRACTICE = "Судова практика та статистика";
    public static final String RETURN_TO_COURT_PRACTICE = "Повернутися до судової практики";

    public static final String HELP = "Довідкова інформація";
    public static final String ELECTRONIC_COURT = "Електронний суд";
    public static final String RECEPTION_OF_CITIZENS = "Прийом громадян";
    public static final String SAMPLE_DOCUMENTS = "Зразки документів";
    public static final String HOW_TO_BEHAVE_IN_COURT = "Як поводитися в суді";
    public static final String RETURN_TO_MAIN_MENU = "В головне меню";

    // Меню Пошук судових справ
    public static final String SEARCH = "ПОШУК";

    public static final String SEARCHING_CASES = "Шукакаємо судові справи";
    public static final String CLEAN_CASES = "Видаляємо критерії пошуку";
    public static final String NEXT_CASES = "Показуємо судові справи";
    public static final String NEW_SEARCHING_CASES = "Створюємо нові критерії пошуку";
    public static final String ACTION_SUBSCRIBE = "Підписатися";
    public static final String KEYWORDS_IN_JUDGMENT = "Ключові слова у судовому рішенні";
    public static final String PERSON_NAME = "П.І.Б. фізичної особи";
    public static final String COMPANY_NAME = "Назва підприємства";
    public static final String CASE_NUMBER = "Номер справи";
    public static final String JUDGE = "ПІБ судді";
    public static final String COURT_FORM_LABEL = "Форма судочинства";
    public static final String PERIOD_MAKING_FROM = "Період ухвалення з дд.мм.гггг";
    public static final String PERIOD_MAKING_TO  = "Період ухвалення по дд.мм.гггг";
    public static final String ACTION_UNSUBSCRIBE = "Відписатися";

    // Константи для періоду ухвалення
    public static final String DECISION_PERIOD = "Період ухвалення";
    public static final String ALL_DECISIONS = "Всі рішення";

    public static final String SIGNED_UP_TEXT = "Ви підписалися на судову справу №";

    public static final String TEXT_MAIN_MENU =
            "Для швидкого доступу до інформації, будь ласка : \n\n" +
            "- клікніть потрібну кнопку нижче \uD83D\uDC47\n" +
            "- \uD83D\uDC47 або виберіть потрібний пункт меню \n" ;

    public static final String TEXT_SEARCH_COURT_CASES =
            "Задайте ключові слова по справі просто набрав текст \n\n" +
                    "Aбо клікніть потрібну кнопку нижче і введіть текст\uD83D\uDC47\n" +
                    "щоб задати один або кілька критеріїв пошуку, \n\n" +
                    "Коли введете всі потрібні крітерії - нажміть кнопку \"Пошук\" , яка з'явиться в критеріях \n";

    public static final String TEXT_COURT_PRACTICE =
            "Клікніть потрібну кнопку нижче \uD83D\uDC47\n" +
                    "щоб вибрати потрібний пункт меню  \n\n";

    public static final String TEXT_NEXT_SEARCH_COURT_CASES =
            "Можливо змінити ключові слова по справі просто набрав текст \n\n" +
               "Aбо можете клікнути потрібну кнопку нижче і ввести \uD83D\uDC47\n" +
               "один або кілька критеріїв пошуку \n\n" +
               "Коли введете всі потрібні крітерії - нажміть кнопку \"Пошук\" \n";


    public static final String ERROR_TEXT_INPUTS_SEARCH_COURT_CASES =
            "Ви ввели помилково дані \n" +
                    "клікніть потрібну кнопку нижче \uD83D\uDC47\n\n" +
                    " - задате один, чи декілько критеріїв пошуку, \n" +
                    "і введіть значення \n\n" +
                    "Коли введете всі потрібні крітерії - нажміть кнопку \"Пошук\" \n";


    public static final String TEXT_TYPE = "Будь ласка, наберіть текст";

    public static final String TEXT_COURT_FORM_LABEL =
            "Введіть крітерій форми судочинства \n\n" +
             "клікнувши потрібну кнопку нижче \uD83D\uDC47\n";

    public static final String TEXT_KEYWORDS_IN_JUDGMENT =
            "Ви ввели запит в полі Ключові слова у судовому рішенні \n\n" +
                    "- можете задати чи змінити інші поля \n\n" +
                    "Для цього клікніть потрібну кнопку нижче \uD83D\uDC47\n" +
                    "і введіть значення потрібне значення \n\n" +
                    "Коли введете всі значення - нажміть кнопку \"Пошук\"";

    public static String returnToMainMenu = "Return to the main menu";

    // Дайджести судової практики Великої Палати Верховного Суду
    public static final String GRAND_CHAMBER_SUPREME_COURT_DIGESTS = "Дайджести судової практики ВС";

    public static final String PERIODIC_REVIEWS_GRAND_CHAMBER = "Періодичні огляди дайджестів";

    public static final String RETURN_TO_PERIODIC_REVIEWS_GRAND_CHAMBER = "Повернутися до Періодичні оглядів";

    public static final String THEMATIC_REVIEWS_GRAND_CHAMBER = "Тематичні огляди дайджестів";

    public static final String REVIEWS_CASSATION_COURTS = "Огляди судової практики касаційних судів";

    // Періодичні огляди судової практики касаційних судів
    public static final String PERIODIC_REVIEWS_CASSATION_COURTS = "Періодичні огляди судової практики касаційних судів";

    // Касаційний адміністративний суд
    public static final String CASSATION_ADMINISTRATIVE_COURT = "Касаційний адміністративний суд";
    public static final String CASSATION_ADMINISTRATIVE = "Огляди судової практики Касаційного адміністративного суду у складі Верховного Суду (актуальна практика)";

    // Касаційний цивільний суд
    public static final String CASSATION_CIVIL_COURT = "Касаційний цивільний суд";
    public static final String CASSATION_CIVIL = "Огляди судової практики Касаційного цівільного суду у складі Верховного Суду (актуальна практика)";

    // Касаційний господарський суд
    public static final String CASSATION_COMMERCIAL_COURT = "Касаційний господарський суд";
    public static final String CASSATION_COMMERCIAL = "Огляди судової практики Касаційного господарського суду у складі Верховного Суду (актуальна практика)";

    // Касаційний кримінальний суд
    public static final String CASSATION_CRIMINAL_COURT = "Касаційний кримінальний суд";
    public static final String CASSATION_CRIMINAL = "Огляди судової практики Касаційного крімінального    суду у складі Верховного Суду (актуальна практика)";

    // Тематичні огляди судової практики касаційних судів
    public static final String THEMATIC_REVIEWS_CASSATION_COURTS = "Тематичні огляди судової практики касаційних судів";

    public static final String RETURN_TP_THEMATIC_REVIEWS_CASSATION_COURTS = "Повернутися до тематичних оглядів";

    // Тематичні огляди касаційного адміністративного суду
    public static final String THEMATIC_ADMINISTRATIVE_COURT = "Тематичні огляди касаційного адміністративного суду";

    // Тематичні огляди касаційного цивільного суду
    public static final String THEMATIC_CIVIL_COURT = "Тематичні огляди касаційного цивільного суду";

    // Тематичні огляди касаційного господарського суду
    public static final String THEMATIC_COMMERCIAL_COURT = "Тематичні огляди касаційного господарського суду";

    // Тематичні огляди касаційного кримінального суду
    public static final String THEMATIC_CRIMINAL_COURT = "Тематичні огляди касаційного кримінального суду";

    // Огляд рішень ЄСПЛ
    public static final String ECHR_DECISIONS_REVIEWS = "Огляди рішень ЄСПЛ";
    public static final String PERIODIC_REVIEWS_ECHR = "Періодичні огляди рішень ЄСПЛ";
    public static final String PERIODIC_REVIEWS =  "Огляд рішень Європейського суду з прав людини";


    public static final String RETURN_TO_PERIODIC_REVIEWS_ECHR = "Повернутися до рішень ЄСПЛ";


    public static final String THEMATIC_REVIEWS_ECHR = "Тематичні огляди рішень ЄСПЛ";

    // Огляд практики Суду ЄС
    public static final String EU_COURT_PRACTICE_REVIEWS = "Огляди практики Суду ЄС";
    public static final String EU_COURT_PRACTICE = "Огляд практики Суду справедливості Європейського Союзу" +
            "Огляд рішень Європейського суду з прав людини";



}
