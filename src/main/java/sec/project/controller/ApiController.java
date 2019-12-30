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
import org.springframework.web.bind.annotation.RestController;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@RestController
public class ApiController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("/api/signups")
    public List<Signup> list(Model model) {
        return signupRepository.findAll();
    }
  

}
