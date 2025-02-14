package co.edu.eci.framework;

@RestController
public class HelloController
{

    @GetMapping("/")
    public String home()
        {
            return "Greetings from MicroSpringBoot!";
        }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }


    @GetMapping("/hello")
    public String index()
    {
        return "Hello World!";
    }
}
