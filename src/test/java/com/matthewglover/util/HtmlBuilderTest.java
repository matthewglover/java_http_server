package com.matthewglover.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class HtmlBuilderTest {
    private final String pageHeader = "<html>" +
            "<head>" +
            "<title></title>" +
            "</head>" +
            "<body>";
    private final String pageFooter = "</body>" +
            "</html>";

    @Test
    public void buildsSimpleHtmlPage() {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        assertEquals(pageHeader + pageFooter, htmlBuilder.build());
    }

    @Test
    public void buildsHtmlPageWithLinks() {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.addLink("/link1", "Link 1 Description");
        htmlBuilder.addBr();
        htmlBuilder.addLink("/link2", "Link 2 Description");

        assertEquals(pageHeader +
                String.format("<a href=\"%s\">%s</a>", "/link1", "Link 1 Description") +
                "<br>" +
                String.format("<a href=\"%s\">%s</a>", "/link2", "Link 2 Description") +
                pageFooter, htmlBuilder.build());
    }
}