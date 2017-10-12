package com.matthewglover.util;

import java.util.ArrayList;

public class HtmlBuilder {

    private final ArrayList<String> bodyElements = new ArrayList();

    private final String pageHeader = "<html>" +
            "<head>" +
            "<title></title>" +
            "</head>" +
            "<body>";

    private final String pageFooter = "</body>" +
            "</html>";

    public void addLink(String url, String description) {
        String link = String.format("<a href=\"%s\">%s</a>", url, description);
        bodyElements.add(link);
    }

    public void addBr() {
        bodyElements.add("<br>");
    }

    public String build() {
        return pageHeader + String.join("", bodyElements) + pageFooter;
    }
}
