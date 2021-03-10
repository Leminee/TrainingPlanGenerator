package gui;

import java.util.regex.Pattern;

public interface Validator {
	
	default boolean usernameIsValid(String username)
    { 
        String usernameRegex = "[a-zA-Z]{3,20}"; 
                              
        Pattern usernamepat = Pattern.compile(usernameRegex); 
        if (username == null) 
            return false; 
        return usernamepat.matcher(username).matches(); 
    } 

	default boolean passwordIsValid(String password)
    { 
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?])(?=\\S+$).{8,}$"; 
                              
        Pattern passwordpat = Pattern.compile(passwordRegex); 
        if (password == null) 
            return false; 
        return passwordpat.matcher(password).matches(); 
    } 

	default boolean emailIsValid(String email)
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern emailpat = Pattern.compile(emailRegex); 
        if (email == null) {
            return false; 
        }
        return emailpat.matcher(email).matches(); 
    } 

	default boolean firstNameIsValid(String firstName)
    { 
        String firstNameRegex = "[a-zA-Z]{3,20}"; 
                              
        Pattern firstNamepat = Pattern.compile(firstNameRegex); 
        if (firstName == null) 
            return false; 
        return firstNamepat.matcher(firstName).matches(); 
    } 

	default boolean nameIsValid(String name)
    { 
        String nameRegex = "[a-zA-Z]{3,20}"; 
                              
        Pattern namepat = Pattern.compile(nameRegex); 
        if (name == null) 
            return false; 
        return namepat.matcher(name).matches(); 
    } 

}