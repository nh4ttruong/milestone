package com.example.learnathon;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class XSSParameterDemo {
    @GetMapping("/")
	public String index(@RequestParam(name="name", required = false) String name, Model model) {
		model.addAttribute("name", name);
        if (name == null) return "Hello 54010N";
		return "Hello " + name;
	}
}
