package by.notcz.aromian.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ConverterController {

	@CrossOrigin
	@GetMapping(path = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode convert(@RequestParam(name = "number", required = false) String request_number, @RequestParam(name = "direction", required = false) String request_direction) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode response = mapper.createObjectNode();
		ObjectNode response_data = mapper.createObjectNode();

		response.put("success", false);

		if (request_number != null) {
			switch (request_direction) {
			case "roman2arabic":
				Pattern p = Pattern.compile("[IVXLCDM]+");
				Matcher m = p.matcher(request_number);
				if (m.matches()) {
					response_data.put("result", romanToInteger(request_number));
					response.put("success", true);
				} else {
					response.put("message", "Number to convert is not a roman");
				}

				break;

			case "arabic2roman":
				try {
					int number = Integer.parseInt(request_number);
					response_data.put("result", integerToRoman(number));
					response.put("success", true);
				} catch (NumberFormatException e) {
					response.put("message", "Number to convert is not an integer arabic");
				}

				break;

			default:
				response.put("message", "Direction is not specified");
			}
		} else {
			response.put("message", "Number to convert is not specified");
		}

		response.put("data", response_data);
		return response;
	}


	public static int romanToInteger(String roman) {
		Map<Character, Integer> values = new LinkedHashMap<>();
		values.put('I', 1);
		values.put('V', 5);
		values.put('X', 10);
		values.put('L', 50);
		values.put('C', 100);
		values.put('D', 500);
		values.put('M', 1000);

		int number = 0;
		for (int i = 0; i < roman.length(); i++) {
			if (i + 1 == roman.length() || values.get(roman.charAt(i)) >= values.get(roman.charAt(i + 1))) {
				number += values.get(roman.charAt(i));
			} else {
				number -= values.get(roman.charAt(i));
			}
		}

		return number;
	}


	public static String integerToRoman(int num) {
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] romanLiterals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

		StringBuilder roman = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			while (num >= values[i]) {
				num -= values[i];
				roman.append(romanLiterals[i]);
			}
		}

		return roman.toString();
	}

}