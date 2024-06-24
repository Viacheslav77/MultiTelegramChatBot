package com.info.chatbot.dto;

import lombok.Data;

@Data
public class AnalisesParsed {

    private String title;
    private String href;

    @Override
    public String toString() {
        return "<a href=\"" + href + "\" >" + title + "</a>";
    }
}
