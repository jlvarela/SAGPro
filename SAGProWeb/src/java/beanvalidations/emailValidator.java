/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beanvalidations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Marco
 */
@FacesValidator("emailValidator")
public class emailValidator implements Validator {
    
    @Override
    public void validate(FacesContext facesContext, UIComponent uIComponent, Object value)throws 
            ValidatorException{
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
        Matcher matcher = pattern.matcher(
                (CharSequence)value);
        HtmlInputText htmlInputText = (HtmlInputText) uIComponent;
        String label;
        
        if (htmlInputText.getLabel()==null||htmlInputText.getLabel().trim().equals("")){
            label = htmlInputText.getId();
        }
        else{
            label = htmlInputText.getLabel();
        }
        
        if(!matcher.matches()){
            FacesMessage facesMessage = new FacesMessage(label + ": no es una direccion de email valido");
            
            throw new ValidatorException(facesMessage);
        }
    }
    
    
}