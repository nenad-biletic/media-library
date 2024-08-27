package com.medialibrary.controller;

import static com.medialibrary.constants.Templates.LIBRARY;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class MediaLibraryController {

  @GetMapping
  public String homePage() {
    return LIBRARY;
  }
}
