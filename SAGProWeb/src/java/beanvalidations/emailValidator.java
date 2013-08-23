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

/**
 *
 * @author Marco
 */
@FacesValidator("emailValidator")
public class emailValidator implements Validator {
    
    @Override
    public void validate(FacesContext facesContext, UIComponent uIComponent, Object value)throws 
            ValidatorException{
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher((CharSequence)value);
        HtmlInputText htmlInputText = (HtmlInputText) uIComponent;
        String label;
        
        if (htmlInputText.getLabel()==null||htmlInputText.getLabel().trim().equals("")){
            label = htmlInputText.getId();
        }
        else{
            label = htmlInputText.getLabel();
        }
        
        if(!matcher.matches()){
  
            if ((CharSequence)value ==""){
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",label + ": Campo Obligatorio Vacío");
                throw new ValidatorException(facesMessage);
            }
            else{
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",label + ": dato ingresado no es una dirección de email válido");
                throw new ValidatorException(facesMessage);
            }
        }
    }
    
    
}