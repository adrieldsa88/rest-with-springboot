package https.github.com.Adrieldsa88.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    @RequestMapping("/sum/{number1}/{number2}")
    public Double sum(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!isNumeric(number1) || !isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return convertToDouble(number1) + convertToDouble(number2);
    }

    @RequestMapping("/subtraction/{number1}/{number2}")
    public Double subtraction(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!isNumeric(number1) || !isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return convertToDouble(number1) - convertToDouble(number2);
    }

    @RequestMapping("/multiplication/{number1}/{number2}")
    public Double multiplication(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!isNumeric(number1) || !isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return convertToDouble(number1) * convertToDouble(number2);
    }

    @RequestMapping("/division/{number1}/{number2}")
    public Double division(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!isNumeric(number1) || !isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return convertToDouble(number1) / convertToDouble(number2);
    }

    @RequestMapping("/mean/{number1}/{number2}")
    public Double mean(@PathVariable("number1") String number1, @PathVariable("number2") String number2) throws UnsupportedOperationException {
        if (!isNumeric(number1) || !isNumeric(number2)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return (convertToDouble(number1) + convertToDouble(number2)) / 2;
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(@PathVariable("number") String number) throws UnsupportedOperationException {
        if (!isNumeric(number)) {
            throw new UnsupportedOperationException("Please set a numeric value");
        }
        return Math.sqrt(convertToDouble(number));
    }

    public boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public Double convertToDouble(String strNumber) throws UnsupportedOperationException{
        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedOperationException("Please set a numeric value.");
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }
}
