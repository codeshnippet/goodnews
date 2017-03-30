package ua.goodnews.processors.impl;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;
import ua.goodnews.processors.TextProcessor;

@Component
public class HtmlSanitizer implements TextProcessor {

    @Override
    public String process(String text) {
        PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
        String result = policy.sanitize(text);
        return result;
    }
}
