package com.Expense.Tracker.Controller;


import com.Expense.Tracker.Dto.LanguageDto;
import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.SignupDto;
import com.Expense.Tracker.Entites.User;
import com.Expense.Tracker.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
@Autowired
    UserService userService;
    private Map<String, Map<String, String>> translationMap = Map.of(
            "greeting", Map.of("english", "Hello", "french", "Bonjour", "spanish", "Hola"),
            "username", Map.of("english", "Username", "french", "Nom d'utilisateur", "spanish", "Usuario"),
            "password", Map.of("english", "Password", "french", "Mot de passe", "spanish", "Contraseña")
    );
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/greet")
    public String greet(@RequestBody LanguageDto dto) {
//    Locale usLocale=Locale.US;
////        BigDecimal number = new BigDecimal(102300.456d);
////        NumberFormat usNumberFormat = NumberFormat.getCurrencyInstance(usLocale);
////String USCurrency=usNumberFormat.format(number);
////System.out.println(USCurrency);
//        Locale.setDefault(Locale.US);
//        String inputDate = "05072025";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
//        LocalDate requestedDate=LocalDate.parse(inputDate,formatter);
//        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 15, 50, 500);
//        String pattern = "dd-MMMM-yyyy HH:mm:ss.SSS";
//        String productpattern1="MM dd yyyy";
//        String productpattern2="dd/MM/yyyy";
//        DateTimeFormatter defaultTimeFormatter = DateTimeFormatter.ofPattern(pattern);
//        DateTimeFormatter deTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.GERMANY);
//        DateTimeFormatter defaultTimeFormatter1 = DateTimeFormatter.ofPattern(productpattern1);
//        DateTimeFormatter deTimeFormatter1 = DateTimeFormatter.ofPattern(productpattern1, Locale.GERMANY);
//        DateTimeFormatter defaultTimeFormatter2 = DateTimeFormatter.ofPattern(productpattern2);
//        DateTimeFormatter deTimeFormatter2= DateTimeFormatter.ofPattern(productpattern2, Locale.GERMANY);
//        DateTimeFormatter defaultTimeFormatter3 = DateTimeFormatter.ofPattern(productpattern1);
//        DateTimeFormatter deTimeFormatter3= DateTimeFormatter.ofPattern(productpattern1, Locale.GERMANY);
//        System.out.println("defaultTimeFormatter: "+defaultTimeFormatter);
//        System.out.println("deTimeFormatter: "+deTimeFormatter);
//        System.out.println("defaultTimeFormatterResult: "+defaultTimeFormatter.format(localDateTime));
//        System.out.println("deTimeFormatterResult: "+deTimeFormatter.format(localDateTime));
//        System.out.println("defaultTimeFormatter1: "+defaultTimeFormatter1);
//        System.out.println("deTimeFormatter1: "+deTimeFormatter1);
//        System.out.println("defaultTimeFormatterResult1: "+defaultTimeFormatter1.format(localDateTime));
//        System.out.println("deTimeFormatterResult1: "+deTimeFormatter1.format(localDateTime));
//        System.out.println("defaultTimeFormatter2: "+defaultTimeFormatter2);
//        System.out.println("deTimeFormatter2: "+deTimeFormatter2);
//        System.out.println("defaultTimeFormatterResult2: "+defaultTimeFormatter2.format(localDateTime));
//        System.out.println("deTimeFormatterResult2: "+deTimeFormatter2.format(localDateTime));
//
//        System.out.println("defaultTimeFormatterResult3: "+defaultTimeFormatter3.format(requestedDate));
//        System.out.println("deTimeFormatterResult3: "+deTimeFormatter3.format(requestedDate));









        Locale locale = convertToLocale(dto.getLanguage());
        //System.out.println(locale.getCountry());
        Locale arr []=Locale.getAvailableLocales();
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]+ "   " + arr[i].getDisplayCountry());
        }

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        Map<String, String> messages = new HashMap<>();
        messages.put("greeting", bundle.getString("greeting"));
        messages.put("change", bundle.getString("change"));
        messages.put("eng", bundle.getString("eng"));
        System.out.println(messages);
        //   return messageSource.getMessage("greeting", null, locale);
        //return getTranslation(dto.getHeader(),dto.getLanguage());
        // return "greet";
        return "null";
    }

    private Locale convertToLocale(String language) {
        return Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> locale.getDisplayLanguage(Locale.ENGLISH)
                        .equalsIgnoreCase(language))
                .findFirst()
                .orElse(Locale.ENGLISH);
    }
    public String getTranslation(String key, String language) {
        Map<String, String> translations = translationMap.get(key);
        if (translations != null) {
            return translations.getOrDefault(language, translations.get("en"));
        }
        return key;
    }

@PostMapping("/registerUser")
    public User saveUserLoginDetails(String userName, String password){
        return userService.saveLoginDetails(userName,password);
    }

    @PostMapping("/updateUser")
    public ResponseDto saveUser(@RequestParam String userId,@RequestParam  float budget){
    return userService.saveUser(userId,budget);
    }
@PostMapping("/signup")
    public ResponseDto signupDetails(@RequestBody SignupDto signupDto){
    return userService.saveUserDetails(signupDto);
}

@GetMapping("/signin")
    public ResponseDto signin(@RequestParam String emailId, @RequestParam String password){
//    Locale currentLocale=request.getLocale();
////    Locale.get
//    String coutryCode=currentLocale.getCountry();
//    String countryName=currentLocale.getDisplayCountry();
//    String langCode=currentLocale.getLanguage();
//    String langName=currentLocale.getDisplayLanguage();
//    System.out.println(coutryCode+" "+countryName+" "+langCode+" "+langName);
   // log.info("CoutnryCode :{},CountryName:{},LangCode:{},LangName:{}",coutryCode,countryName,langCode,langName);
    return userService.checkUser(emailId,password);
}

@GetMapping("/getUser")
    public ResponseDto getUserDetails(@RequestParam String emailId){
        return userService.getUser(emailId);
}


}
