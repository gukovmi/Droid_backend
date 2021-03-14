package ru.focus.start.loan.features

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class GeneralController {

    @GetMapping("/")
    fun redirectHome(): RedirectView {
        return RedirectView("/swagger-ui/")
    }
}