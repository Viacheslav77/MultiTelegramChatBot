package com.info.chatbot.constants;

public enum SupremeCourtInfo {

    // Перелік елементів enum з відповідними URL
    PERSONAL_RECEPTION_SCHEDULE("Графік особистого прийому громадян", "https://supreme.court.gov.ua/supreme/gromadyanam/grafic_op/"),
    PERSONAL_RECEPTION_ORDER("Порядок особистого прийому громадян", "https://supreme.court.gov.ua/supreme/gromadyanam/poryadok_op/"),
    POSTAL_ADDRESSES("Поштові адреси Верховного Суду", "https://supreme.court.gov.ua/supreme/gromadyanam/kontakts/"),
    EMAIL_ADDRESSES("Електронні адреси Верховного Суду", "https://supreme.court.gov.ua/supreme/gromadyanam/kontakts/"),
    ELECTRONIC_RECEPTION("Електронна приймальня", "https://supreme.court.gov.ua/supreme/gromadyanam/elec_priyam/"),
    PERSONAL_RECEPTION_REQUEST_SAMPLE("Зразок заяви щодо особистого прийому", "https://supreme.court.gov.ua/supreme/gromadyanam/zapus/"),
    GET_ECOURT_INFO("Отримати детальну інформацію про роботу електронного суду", "https://court.gov.ua/reyestri-ta-sistemi/ecourt/"),
    SAMPLE_DOCUMENTS_E("Зразки документів", "https://supreme.court.gov.ua/supreme/gromadyanam/zraz_zayav/"),
    HOW_TO_BEHAVE_IN_COURT_E("Як поводитися в суді", "https://supreme.court.gov.ua/supreme/gromadyanam/povedinka_v_sydi/ "),
    LOGIN_TO_ECOURT_CABINET("Увійти до електронного кабінету", "https://cabinet.court.gov.ua/login");

    // Поля для збереження назви та URL
    private final String title;
    private final String url;

    // Конструктор для ініціалізації полів
    SupremeCourtInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // Геттери для полів
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
