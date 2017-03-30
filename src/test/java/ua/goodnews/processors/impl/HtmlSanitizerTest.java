package ua.goodnews.processors.impl;

import junit.framework.TestCase;
import org.junit.Test;

public class HtmlSanitizerTest extends TestCase {

    private HtmlSanitizer htmlSanitizer = new HtmlSanitizer();

    @Test
    public void testProcessPlainText() throws Exception {
        String result = htmlSanitizer.process("Just a plain text.");

        assertEquals("Just a plain text.", result);
    }

    @Test
    public void testProcessTextWithSpecialSymbols() {
        String result = htmlSanitizer.process("Text with special symbols: !#$%^*(){}|?");

        assertEquals("Text with special symbols: !#$%^*(){}|?", result);
    }

    @Test
    public void testProcessTextWithImageTag() {
        String result = htmlSanitizer.process("Text surrounding <img src=\"http://feeds.com/img.jpg\" />image tag.");

        assertEquals("Text surrounding image tag.", result);
    }

    @Test
    public void testProcessTextWithLinkTag() {
        String result = htmlSanitizer.process("Text surrounding <a hrf=\"http://feeds.com/img.jpg\">link</a> tag.");

        assertEquals("Text surrounding link tag.", result);
    }
}