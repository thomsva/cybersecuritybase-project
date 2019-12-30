package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "admin";
    }

    
    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:/guest/form";
    }

    @RequestMapping(value = "/guest/form", method = RequestMethod.GET)
    public String loadAdmin(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "form";
    }

    @RequestMapping(value = "/guest/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        //signupRepository.save(new Signup(name, address));
        String query = "insert into Signup (name,address) values ('" + name + "','" + address + "')";
        jdbcTemplate.update(query);
        return "done";
    }

}
