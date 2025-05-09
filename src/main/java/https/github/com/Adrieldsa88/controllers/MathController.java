package https.github.com.Adrieldsa88.controllers;

import https.github.com.Adrieldsa88.math.SimpleMath;
import https.github.com.Adrieldsa88.request.converters.NumberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {


    SimpleMath math = new SimpleMath();

    @RequestMapping("/sum/{number1}/{number2}")
    public Double sum(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number1) || !NumberConverter.isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.sum(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
    }

    @RequestMapping("/subtraction/{number1}/{number2}")
    public Double subtraction(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number1) || !NumberConverter.isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.subtraction(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
    }

    @RequestMapping("/multiplication/{number1}/{number2}")
    public Double multiplication(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number1) || !NumberConverter.isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.multiplication(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
    }

    @RequestMapping("/division/{number1}/{number2}")
    public Double division(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number1) || !NumberConverter.isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.division(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
    }

    @RequestMapping("/mean/{number1}/{number2}")
    public Double mean(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number1) || !NumberConverter.isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.mean(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(@PathVariable("number") String number) throws UnsupportedOperationException {
        if (!NumberConverter.isNumeric(number)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return math.squareRoot(NumberConverter.convertToDouble(number));
    }


}
