package FuncionesGenerales

import org.springframework.context.MessageSource

class Grails {
     static i18n(String key,Locale locale){
         Funciones funciones = new Funciones();
         MessageSource messageSource = funciones.getServicios(MessageSource);
         return  messageSource.getMessage(key,null,locale);

     }
}
