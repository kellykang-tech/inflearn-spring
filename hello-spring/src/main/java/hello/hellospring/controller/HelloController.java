package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        // return "hello"의 hello는 템플릿 뒤져서 hello를 찾으라는 것. == templates/hello.html
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
        // 문자? response body에 문자 그대로 반환
    }

    ////////////////////////
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        // 객체를 반환했어. (board 넘긴 거랑 같이.)
        // 클라이언트에서 받은 결과는? json 방식으로 반환받음. (json 방식으로 반환되는 것이 디폴트라고 한다.)
        // {name: "spring!!!"}
    }

    static class Hello {
        private String name;

        // alt + ins
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}


