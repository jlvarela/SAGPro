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

@FacesValidator("rutValidator")
public class rutValidator implements Validator {
    
    public static boolean validateRut(String cadena)
    {
        int ultimoDigito= cadena.length()-1;
        char dv=(char)cadena.charAt(ultimoDigito);
        int rut=Integer.parseInt(cadena.substring(0, ultimoDigito));
        
        if (dv == 'k'){
            dv='K';
            
        }
        
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10)
        {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        
        return dv == (char) (s != 0 ? s + 47 : 75);
    }
    
    public static String getUltimoDigito(String cadena){
        int rut=Integer.parseInt(cadena);
        
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10)
        {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        char resp = (char)(s != 0 ? s + 47 : 75);
        
        return String.valueOf(resp);
    }
   
    @Override
    public void validate(FacesContext facesContext, UIComponent uIComponent, Object value)throws 
            ValidatorException{
        Pattern pattern = Pattern.compile("\\b\\d{1,8}[K|k|0-9]");
        
        //Long valor=(Long)value;
        String cadena=String.valueOf(value);
        Matcher matcher = pattern.matcher((CharSequence)cadena);
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
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",label + ": no presenta formato permitido. Reingrese rut sin puntos ni guión (ej: 171233496)");
                throw new ValidatorException(facesMessage);
            }
        }else{
            /*si no es vacío y cumple el patron regular, se revisa la validez del rut*/
            if(!validateRut((String)cadena)){
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",label + ": el RUT ingresado no es válido");
                throw new ValidatorException(facesMessage);
            }
        }
    }    
}