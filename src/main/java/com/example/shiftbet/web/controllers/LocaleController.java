package com.example.shiftbet.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class LocaleController {

    private final LocaleResolver localeResolver;

    private  final MessageSource messageSource;
    @Autowired
    public LocaleController(LocaleResolver localeResolver, MessageSource messageSource) {
        this.localeResolver = localeResolver;
        this.messageSource = messageSource;
    }

    @GetMapping("/locale/change")
    public String changeLocale(@RequestParam("lang") String lang,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);
        return "redirect:/";
    }
}
