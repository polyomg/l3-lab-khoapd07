package poly.edu.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/tinhHCN")
public class TinhHCNController {

    @Autowired
    HttpServletRequest request;

    // Hiển thị form
    @RequestMapping("/form")
    public String form() {
        return "demo/bai5";
    }


    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public String calc(Model model) {
        double cd = Double.parseDouble(request.getParameter("length"));
        double cr = Double.parseDouble(request.getParameter("width"));

        double dienTich = cd * cr;
        double chuVi = 2 * (cd + cr);

        model.addAttribute("message",
                "Diện tích: " + dienTich + " - Chu vi: " + chuVi);

//        model.addAttribute("tongThe1","Chu vi: " + "(" + cd + "+" + cr + ")" + "x2 = " + chuVi );

        return "demo/bai5";
    }
}
