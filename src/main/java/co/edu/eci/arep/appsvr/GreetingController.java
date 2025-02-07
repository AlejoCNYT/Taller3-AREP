package co.edu.eci.arep.appsvr;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello ";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name)
    {
        return "Hola " + name;
    }

    @GetMapping("/pi")
    public static String pi(String val)
    {
        return Double.toString(Math.PI);
    }
}