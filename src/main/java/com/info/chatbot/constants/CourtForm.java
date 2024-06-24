package com.info.chatbot.constants;

import lombok.Getter;

// Enum для форми судочинства
@Getter
public enum CourtForm {

    ADMINISTRATIVE("Адміністративне", 0),
    COMMERCIAL("Господарське", 1),
    CRIMINAL("Кримінальне", 2),
    CIVIL("Цивільне", 3);

    private final String label;
    private final int number;


    CourtForm(String label, int number) {
        this.label = label;
        this.number = number;
    }


}