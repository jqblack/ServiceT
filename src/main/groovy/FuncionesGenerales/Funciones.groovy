package FuncionesGenerales

import grails.web.context.ServletContextHolder
import grails.web.servlet.mvc.GrailsHttpSession
import jq_servicios.PublicoService
import jq_servicios.SqlService
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.GrailsApplicationAttributes
import org.springframework.context.ApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import proyecto.Templeados
import publico.Tclientes
import publico.Tproductos
import publico.Tusuario
import tk.Ttickets
import tk.TticketsFiles
import tk.TticketsPrioridad
import tk.Ttipotickets

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by Jose Santos on 09/08/2016.
 */

class Funciones {




    PublicoService service =getServicios(PublicoService);
    SqlService sqlService = getServicios(SqlService);
    def String CurrencyFormat(Object obj) {
        NumberFormat fm = NumberFormat.getCurrencyInstance(new Locale('en'));
        try {
            if (obj.toString().substring(0, 1).equals("-")) {
                return "-" + fm.format(obj).substring(2, fm.format(obj).length());
            } else {
                return fm.format(obj).substring(1, fm.format(obj).length());
            }
        } catch (Exception e) {
            return fm.format(0).substring(1, fm.format(0).length());
        }
    }
        def String crearJSON(String [] campos, String [] datos)
    {
        String json ;
        json = "{";

        for (int i = 0; i < campos.size() ; i++) {
            json = json + "'${campos[i]}'";
            json = json + ":";
            json = json + "'${datos[i]}'";
            if (i < campos.size() - 1)
            {
                json = json + ",";
            }

        }

        json = json + "}";

        return json.toString();
    }

    def enviar()
    {
        String to = "plopez@jqmicro.com";//change accordingly
        String from = "jqmicro@gmail.com";//change accordingly
        String host = "localhost";//or IP address

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Ping");
            message.setText("Hello, this is example of sending email  ");

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException mex) {mex.printStackTrace();}

    }

    def GetEmailEmpleadosJQ()
    {
        String email = "";
        List<Templeados> emp = Templeados.findAllByFActivo(true);

        for (Templeados e : emp)
        {
            if (!e.fEmailtickets.equals("") && !e.fEmailtickets.is(null)){
                email = email + e.fEmailtickets+',';
            }
        }
        email = email.substring(0,email.size()-1);
        return email.replace('`','');
    }

    def generador_reporte_tickets(Ttickets ttickets)
    {


        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
        GrailsHttpSession session = request.session;
        Locale locale= new Locale(session.getAttribute("idioma").toString());

        Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

        String prioridad = "";

        TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);
        Tusuario u = Tusuario.findById(ttickets.fHechoPor);

        Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

        String nombre_cliente = 'ADMINISTRADOR JQ';
        if (cli)
        {
          nombre_cliente = cli.fNombreEmpresa;
        }

        switch (tticketsPrioridad.fPrioridad)
        {
            case 1 :
                prioridad = 'Critica'
                break
            case 2 :
                prioridad = 'Alta'
                break
            case 3 :
                prioridad = 'Media'
                break
            case 4 :
                prioridad = 'Baja';
                break

        }


        switch (tticketsPrioridad.fTipoTk)
        {
            case 1 :
                prioridad = 'Error - '+prioridad
                break

            case 2 :
                prioridad = 'Peticion - '+prioridad
                break
        }

        String tipo_tk = "Otros";
        Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
        if (ttipotickets)
        {
            if (session.getAttribute("idioma").toString().equals("es")){

                tipo_tk = ttipotickets.fDescripcion;
            }else{
                tipo_tk = ttipotickets.fDescripcionIngles;
            }
        }

        String actualizaciones = get_html_actualizaciones_tickets(ttickets.id);


        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta\n" +
                "            content='text/html; charset=ISO-8859-1'\n" +
                "            http-equiv='content-type'>\n" +
                "    <title></title>\n" +
                "    <style>\n" +
                "    table {\n" +
                "        border-collapse: collapse;\n" +
                "        width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    th, td {\n" +
                "        padding: 5px;\n" +
                "        text-align: left;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table\n" +
                "        style='text-align: left; height: 248px; width: 100%;'\n" +
                "        border='0' cellpadding='2' cellspacing='2'>\n" +
                "    <tbody>\n" +
                "    <tr style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td style='text-align: center; background-color: rgb(51, 204, 255); width: 215px;' colspan='4' rowspan='1'>TICKETS </td>\n" +
                "    </tr>\n" +
                "    <tr align='right'>\n" +
                "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
                "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
                "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
                "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
                "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${formatDate(ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
                "        <td style='width: 215px;' colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${prioridad}</td>\n" +

                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("hecho_por",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan='4' rowspan='1'\n" +
                "            style='width: 215px;'></td>\n" +
                "    </tr>\n" +
                "    <tr\n" +
                "            style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td\n" +
                "                style='text-align: center; background-color: rgb(51, 204, 255); width: 215px;'\n" +
                "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: left; width: 215px;' colspan='4'\n" +
                "                rowspan='1'>${ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='background-color: rgb(51, 204, 255); width: 215px;'\n" +
                "                colspan='4' rowspan='1'></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "    <tr align='left'>\n" +
                "        <td colspan='4' rowspan='1'><span\n" +
                "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
                "    </tr></tbody>\n" +
                "</table>\n" +
                "<br>\n" +
                "</body>\n" +
                "</html>";


//        println(html);


        return  html;
    }


    def generador_reporte_tickets_reasignado(Ttickets ttickets)
    {

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
        GrailsHttpSession session = request.session;
        Locale locale= new Locale(session.getAttribute("idioma").toString());

        Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

        String prioridad = "";

        TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);
        Tusuario u = Tusuario.findById(ttickets.fHechoPor);
        Templeados emp = Templeados.findById(ttickets.fIdtecnicoasignado);
        switch (tticketsPrioridad.fPrioridad)
        {
            case 1 :
                prioridad = 'Critica'
                break
            case 2 :
                prioridad = 'Alta'
                break
            case 3 :
                prioridad = 'Media'
                break
            case 4 :
                prioridad = 'Baja';
                break

        }

        switch (tticketsPrioridad.fTipoTk)
        {
            case 1 :
                prioridad = 'Error - '+prioridad
                break

            case 2 :
                prioridad = 'Peticion - '+prioridad
                break
        }

        Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

        String nombre_cliente = 'ADMINISTRADOR JQ';
        if (cli)
        {
            nombre_cliente = cli.fNombreEmpresa;
        }

        String tipo_tk = "Otros";
        Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
        if (ttipotickets)
        {
            if (session.getAttribute("idioma").toString().equals("es")){

                tipo_tk = ttipotickets.fDescripcion;
            }else{
                tipo_tk = ttipotickets.fDescripcionIngles;
            }
        }

        String actualizaciones = get_html_actualizaciones_tickets(ttickets.id);




        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta\n" +
                "            content='text/html; charset=ISO-8859-1'\n" +
                "            http-equiv='content-type'>\n" +
                "    <title></title>\n" +
                "    <style>\n" +
                "    table {\n" +
                "        border-collapse: collapse;\n" +
                "        width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    th, td {\n" +
                "        padding: 5px;\n" +
                "        text-align: left;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table\n" +
                "        style='text-align: left; height: 248px; width: 100%;'\n" +
                "        border='0' cellpadding='2' cellspacing='2'>\n" +
                "    <tbody>\n" +
                "    <tr style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td style='text-align: center; background-color: rgb(251, 179, 70); width: 215px;' colspan='4' rowspan='1'>${Grails.i18n("reasignacion_tickets",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr align='right'>\n" +
                "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
                "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
                "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
                "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
                "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${formatDate(ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${prioridad}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
//                "        <td\n" +
//                "                style='text-align: right; background-color: rgb(204, 204, 204);'>Por :</td>\n" +
//                "        <td style='width: 215px;'\n" +
//                "            colspan='1' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("para",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${emp.fDescripcion}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan='4' rowspan='1'\n" +
                "            style='width: 215px;'></td>\n" +
                "    </tr>\n" +
                "    <tr\n" +
                "            style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td\n" +
                "                style='text-align: center; background-color: rgb(251, 179, 70); width: 215px;'\n" +
                "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: left; width: 215px;' colspan='4'\n" +
                "                rowspan='1'>${ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='background-color: rgb(251, 179, 70); width: 215px;'\n" +
                "                colspan='4' rowspan='1'></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "    <tr align='left'>\n" +
                "        <td colspan='4' rowspan='1'><span\n" +
                "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
                "    </tr></tbody>\n" +
                "</table>\n" +
                "<br>\n" +
                "</body>\n" +
                "</html>";


      //  println(html);


        return  html;
    }






def generador_reporte_tickets_cancelacion(Ttickets ttickets, String nota, Tusuario u)
{

    GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
    GrailsHttpSession session = request.session;
    Locale locale= new Locale(session.getAttribute("idioma").toString());

    Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

    String prioridad = "";

    TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);


    String actualizaciones = get_html_actualizaciones_tickets(ttickets.id);
//    Tusuario u = Tusuario.findById(ttickets.fHechoPor);
   // Templeados emp = Templeados.findById(ttickets.fIdcancelo);
    switch (tticketsPrioridad.fPrioridad)
    {
        case 1 :
            prioridad = 'Critica'
            break
        case 2 :
            prioridad = 'Alta'
            break
        case 3 :
            prioridad = 'Media'
            break
        case 4 :
            prioridad = 'Baja';
            break

    }
    switch (tticketsPrioridad.fTipoTk)
    {
        case 1 :
            prioridad = 'Error - '+prioridad
            break

        case 2 :
            prioridad = 'Peticion - '+prioridad
            break
    }

    Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

    String nombre_cliente = 'ADMINISTRADOR JQ';
    if (cli)
    {
        nombre_cliente = cli.fNombreEmpresa;
    }

    String tipo_tk = "Otros";
    Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
    if (ttipotickets)
    {
        if (session.getAttribute("idioma").toString().equals("es")){

            tipo_tk = ttipotickets.fDescripcion;
        }else{
            tipo_tk = ttipotickets.fDescripcionIngles;
        }
    }
    String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta\n" +
            "            content='text/html; charset=ISO-8859-1'\n" +
            "            http-equiv='content-type'>\n" +
            "    <title></title>\n" +
            "    <style>\n" +
            "    table {\n" +
            "        border-collapse: collapse;\n" +
            "        width: 100%;\n" +
            "    }\n" +
            "\n" +
            "    th, td {\n" +
            "        padding: 5px;\n" +
            "        text-align: left;\n" +
            "        border-bottom: 1px solid #ddd;\n" +
            "    }\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<table\n" +
            "        style='text-align: left; height: 248px; width: 100%;'\n" +
            "        border='0' cellpadding='2' cellspacing='2'>\n" +
            "    <tbody>\n" +
            "    <tr style='font-weight: bold; color: rgb(255, 255, 255);' align='center'>\n" +
            "        <td style='text-align: center; background-color: rgb(235, 12, 12); width: 215px;' colspan='4' rowspan='1'>${Grails.i18n("cancelacion_tickets",locale).toUpperCase()}</td>\n" +
            "    </tr>\n" +
            "    <tr align='right'>\n" +
            "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
            "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
            "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
            "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
            "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
            "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
            "        <td style='text-align: left; width: 215px;'>${formatDate(ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss") }</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
            "        <td style='width: 215px;'\n" +
            "            colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
            "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
            "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td\n" +
            "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
            "        <td style='width: 215px;'\n" +
            "            colspan='3' rowspan='1'>${prioridad}</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("hecho_por",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
//            "        <td\n" +
//            "                style='text-align: right; background-color: rgb(204, 204, 204);'>Por :</td>\n" +
//            "        <td style='width: 215px;'\n" +
//            "            colspan='3' rowspan='1'>${emp.fDescripcion}</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan='4' rowspan='1'\n" +
            "            style='width: 215px;'></td>\n" +
            "    </tr>\n" +
            "    <tr\n" +
            "            style='font-weight: bold; color: rgb(255, 255, 255);' align='center'>\n" +
            "        <td\n" +
            "                style='text-align: center; background-color: rgb(235, 12, 12); width: 215px;'\n" +
            "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td\n" +
            "                style='text-align: left; width: 215px;' colspan='4'\n" +
            "                rowspan='1'>${ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td\n" +
            "                style='background-color: rgb(235, 12, 12); width: 215px;'\n" +
            "                colspan='4' rowspan='1'></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td></td>\n" +
            "        <td></td>\n" +
            "        <td></td>\n" +
            "        <td></td>\n" +
            "    </tr>\n" +
            "    <tr align='left'>\n" +
            "        <td colspan='4' rowspan='1'><span\n" +
            "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
            "    </tr></tbody>\n" +
            "</table>\n" +
            "<br>\n" +
            "</body>\n" +
            "</html>";


    //  println(html);


    return  html;
}

    def generador_reporte_tickets_asignacion(Ttickets ttickets, Tusuario u, String nota)
    {



        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
        GrailsHttpSession session = request.session;
        Locale locale= new Locale(session.getAttribute("idioma").toString());

        Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

        String prioridad = "";

        TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);

        Templeados emp = Templeados.findById(ttickets.fIdtecnicoasignado);
        switch (tticketsPrioridad.fPrioridad)
        {
            case 1 :
                prioridad = 'Critica'
                break
            case 2 :
                prioridad = 'Alta'
                break
            case 3 :
                prioridad = 'Media'
                break
            case 4 :
                prioridad = 'Baja';
                break

        }
        switch (tticketsPrioridad.fTipoTk)
        {
            case 1 :
                prioridad = 'Error - '+prioridad
                break

            case 2 :
                prioridad = 'Peticion - '+prioridad
                break
        }

        String actualizaciones = get_html_actualizaciones_tickets(ttickets.id);

        Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

        String nombre_cliente = 'ADMINISTRADOR JQ';
        if (cli)
        {
            nombre_cliente = cli.fNombreEmpresa;
        }

        String tipo_tk = "Otros";
        Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
        if (ttipotickets)
        {
            if (session.getAttribute("idioma").toString().equals("es")){

                tipo_tk = ttipotickets.fDescripcion;
            }else{
                tipo_tk = ttipotickets.fDescripcionIngles;
            }
        }
        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta\n" +
                "            content='text/html; charset=ISO-8859-1'\n" +
                "            http-equiv='content-type'>\n" +
                "    <title></title>\n" +
                "    <style>\n" +
                "    table {\n" +
                "        border-collapse: collapse;\n" +
                "        width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    th, td {\n" +
                "        padding: 5px;\n" +
                "        text-align: left;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table\n" +
                "        style='text-align: left; height: 248px; width: 100%;'\n" +
                "        border='0' cellpadding='2' cellspacing='2'>\n" +
                "    <tbody>\n" +
                "    <tr style='font-weight: bold; color: rgb(255, 255, 255);' align='center'>\n" +
                "        <td style='text-align: center; background-color: rgb(55, 177, 115); width: 215px;' colspan='4' rowspan='1'>${Grails.i18n("asignacion_tickets",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr align='right'>\n" +
                "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
                "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
                "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
                "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
                "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${formatDate( ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
                "        <td style='width: 215px;'  colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${prioridad}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("hecho_por",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='1' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("para",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='1' rowspan='1'>${emp.fDescripcion}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan='4' rowspan='1'\n" +
                "            style='width: 215px;'></td>\n" +
                "    </tr>\n" +
                "    <tr\n" +
                "            style='font-weight: bold; color: rgb(255, 255, 255);' align='center'>\n" +
                "        <td\n" +
                "                style='text-align: center; background-color: rgb(55, 177, 115); width: 215px;'\n" +
                "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: left; width: 215px;' colspan='4'\n" +
//                "                rowspan='1'>${nota+"<br/><br/>"+ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
                "                rowspan='1'>${ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='background-color: rgb(55, 177, 115); width: 215px;'\n" +
                "                colspan='4' rowspan='1'></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "    <tr align='left'>\n" +
                "        <td colspan='4' rowspan='1'><span\n" +
                "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
                "    </tr></tbody>\n" +
                "</table>\n" +
                "<br>\n" +
                "</body>\n" +
                "</html>";


        //  println(html);


        return  html;
    }


    def decargarArchivo(def response, String nombre, String extencion, byte [] archivo)
    {
        response.setContentType("APPLICATION/OCTET-STREAM")
        response.setHeader("Content-Disposition", "Attachment;Filename=\"${nombre}${extencion}\"");



        def temp = File.createTempFile(nombre,extencion);


        FileOutputStream fileOuputStream = new FileOutputStream(temp);
        fileOuputStream.write(archivo);


        def fileInputStream = new FileInputStream(temp);
        def outputStream = response.getOutputStream()
        byte[] buffer = new byte[4096];
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush()
        outputStream.close()
        fileInputStream.close()

        temp.delete();
    }




    def crear()
    {
        List<TticketsFiles> files = TticketsFiles.findAllByFIdticket(28.toLong());

        List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
        for(TticketsFiles file : files)
        {
            Map<String, Object> map  = new HashMap<String, Object>();
            map.put("f_nombre",file.fNombre);
            map.put("f_archivo",file.fFile);
            lista.add(map);

        }
        Zippear(lista,"archivos_tickets.zip");
    }

    def Zippear(List<Map<String, Object>>lista, String pZipFile) throws Exception {

        File file = File.createTempFile(
                pZipFile.substring(0,pZipFile.lastIndexOf('.')),
                pZipFile.substring(pZipFile.lastIndexOf('.'),pZipFile.length())
        );


        FileOutputStream os = new FileOutputStream(file);
        ZipOutputStream zos = new ZipOutputStream(os);

        for (Map<String, Object> map : lista)
        {

            zos.putNextEntry(new ZipEntry(map.get("f_nombre")));
            zos.write(map.get("f_archivo"));
            zos.closeEntry();
        }

        zos.close();
//        PublicoService service = Grails.get(PublicoService);

        service.EnviarEmailArchivoComprimido("plopez@jqmicro.com",file.getBytes(),pZipFile,"archivos","mensaje");

        file.delete();


    }// end Zippear

    def getServicios(Class<?> servicio){
        ApplicationContext ctx = (ApplicationContext) ServletContextHolder.getServletContext().getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
        return  ctx.getBean(servicio)
    }


    def generador_reporte_cerrar_tickets(Ttickets ttickets, String nota)
    {

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
        GrailsHttpSession session = request.session;
        Locale locale= new Locale(session.getAttribute("idioma").toString());

        Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

        String prioridad = "";

        TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);
        Tusuario u = Tusuario.findById(ttickets.fHechoPor);
        switch (tticketsPrioridad.fPrioridad)
        {
            case 1 :
                prioridad = 'Critica'
                break
            case 2 :
                prioridad = 'Alta'
                break
            case 3 :
                prioridad = 'Media'
                break
            case 4 :
                prioridad = 'Baja';
                break

        }


        switch (tticketsPrioridad.fTipoTk)
        {
            case 1 :
                prioridad = 'Error - '+prioridad
                break

            case 2 :
                prioridad = 'Peticion - '+prioridad
                break
        }


        String actualizaciones = get_html_actualizaciones_tickets(ttickets.id);


        Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

        String nombre_cliente = 'ADMINISTRADOR JQ';
        if (cli)
        {
            nombre_cliente = cli.fNombreEmpresa;
        }

        String tipo_tk = "Otros";
        Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
        if (ttipotickets)
        {
            if (session.getAttribute("idioma").toString().equals("es")){

                tipo_tk = ttipotickets.fDescripcion;
            }else{
                tipo_tk = ttipotickets.fDescripcionIngles;
            }
        }
        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta\n" +
                "            content='text/html; charset=ISO-8859-1'\n" +
                "            http-equiv='content-type'>\n" +
                "    <title></title>\n" +
                "    <style>\n" +
                "    table {\n" +
                "        border-collapse: collapse;\n" +
                "        width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    th, td {\n" +
                "        padding: 5px;\n" +
                "        text-align: left;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table\n" +
                "        style='text-align: left; height: 248px; width: 100%;'\n" +
                "        border='0' cellpadding='2' cellspacing='2'>\n" +
                "    <tbody>\n" +
                "    <tr style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td style='text-align: center; background-color: rgb(131, 135, 136); width: 215px;' colspan='4' rowspan='1'>${Grails.i18n("cierre_tickets",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr align='right'>\n" +
                "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
                "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
                "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
                "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
                "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${formatDate(ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${prioridad}</td>\n" +

                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("hecho_por",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan='4' rowspan='1'\n" +
                "            style='width: 215px;'></td>\n" +
                "    </tr>\n" +
                "    <tr\n" +
                "            style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td\n" +
                "                style='text-align: center; background-color: rgb(131, 135, 136); width: 215px;'\n" +
                "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: left; width: 215px;' colspan='4'\n" +
                "                rowspan='1'>${ttickets.fDescipcion} <br/><br/><br/>$actualizaciones</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='background-color: rgb(131, 135, 136); width: 215px;'\n" +
                "                colspan='4' rowspan='1'></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "    <tr align='left'>\n" +
                "        <td colspan='4' rowspan='1'><span\n" +
                "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
                "    </tr></tbody>\n" +
                "</table>\n" +
                "<br>\n" +
                "</body>\n" +
                "</html>";


//        println(html);


        return  html;
    }




    def generador_reporte_actualizacion_tickets(Ttickets ttickets)
    {

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
        GrailsHttpSession session = request.session;
        Locale locale= new Locale(session.getAttribute("idioma").toString());

        Tproductos tproyectos = Tproductos.findById(ttickets.fIdproyecto);

        String prioridad = "";

        TticketsPrioridad tticketsPrioridad = TticketsPrioridad.findById(ttickets.fIdprioridad);
        Tusuario u = Tusuario.findById(ttickets.fHechoPor);


        switch (tticketsPrioridad.fPrioridad)
        {
            case 1 :
                prioridad = 'Critica'
                break
            case 2 :
                prioridad = 'Alta'
                break
            case 3 :
                prioridad = 'Media'
                break
            case 4 :
                prioridad = 'Baja';
                break

        }


        switch (tticketsPrioridad.fTipoTk)
        {
            case 1 :
                prioridad = 'Error - '+prioridad
                break

            case 2 :
                prioridad = 'Peticion - '+prioridad
                break
        }


        String actualizaciones =get_html_actualizaciones_tickets(ttickets.id);


        Tclientes cli = Tclientes.findById(ttickets.fIdcliente);

        String nombre_cliente = 'ADMINISTRADOR JQ';
        if (cli)
        {
            nombre_cliente = cli.fNombreEmpresa;
        }
        String tipo_tk = "Otros";
        Ttipotickets ttipotickets = Ttipotickets.findById(ttickets.fTipoTk);
        if (ttipotickets)
        {
            if (session.getAttribute("idioma").toString().equals("es")){

                tipo_tk = ttipotickets.fDescripcion;
            }else{
                tipo_tk = ttipotickets.fDescripcionIngles;
            }
        }
        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta\n" +
                "            content='text/html; charset=ISO-8859-1'\n" +
                "            http-equiv='content-type'>\n" +
                "    <title></title>\n" +
                "    <style>\n" +
                "    table {\n" +
                "        border-collapse: collapse;\n" +
                "        width: 100%;\n" +
                "    }\n" +
                "\n" +
                "    th, td {\n" +
                "        padding: 5px;\n" +
                "        text-align: left;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table\n" +
                "        style='text-align: left; height: 248px; width: 100%;'\n" +
                "        border='0' cellpadding='2' cellspacing='2'>\n" +
                "    <tbody>\n" +
                "    <tr style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td style='text-align: center; background-color: rgb(131, 135, 136); width: 215px;' colspan='4' rowspan='1'>${Grails.i18n("actualizacion_tickets",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr align='right'>\n" +
                "        <td style='width: 72px; font-family: Times New Roman; background-color: rgb(204, 204, 204); text-align: right;'># Ticket :</td>\n" +
                "        <td style='text-align: left; width: 317px;' colspan='1' rowspan='1'>${ttickets.id.toString().padLeft(6,"0")}</td>\n" +
                "        <td style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha",locale)} :</td>\n" +
                "        <td rowspan='1' style='width: 215px;' colspan='1' style='text-align: left;'>${formatDate(ttickets.fFecha,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204,204);\">${Grails.i18n("titulo",locale)} :</td>\n" +
                "        <td style=\"width: 317px;\" colspan='1' rowspan='1'>${ttickets.fTitulo}</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("fecha_vencimiento",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${formatDate( ttickets.fFechaVencimiento,"yyyy/MM/dd HH:mm:ss")}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("proyecto",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='1' rowspan='1'>${tproyectos.fDescripcion} -  <b>CLIENTE:</b> $nombre_cliente</td>\n" +
                "        <td  style='text-align: right; width: 159px;' colspan='1'>${Grails.i18n("tipo",locale)} :</td>\n" +
                "        <td style='text-align: left; width: 215px;'>${tipo_tk}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='width: 72px; text-align: right; font-family: Times New Roman; background-color: rgb(204, 204, 204);'>${Grails.i18n("prioridad",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${prioridad}</td>\n" +

                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: right; background-color: rgb(204, 204, 204);'>${Grails.i18n("hecho_por",locale)} :</td>\n" +
                "        <td style='width: 215px;'\n" +
                "            colspan='3' rowspan='1'>${u.fNombre+' '+u.fApellido}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan='4' rowspan='1'\n" +
                "            style='width: 215px;'></td>\n" +
                "    </tr>\n" +
                "    <tr\n" +
                "            style='font-weight: bold; color: rgb(0, 0, 0);' align='center'>\n" +
                "        <td\n" +
                "                style='text-align: center; background-color: rgb(131, 135, 136); width: 215px;'\n" +
                "                colspan='4' rowspan='1'>${Grails.i18n("descripcion",locale).toUpperCase()}</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='text-align: left; width: 215px;' colspan='4'\n" +
                "                rowspan='1'>${ttickets.fDescipcion+"<br/><br/><br/>"+actualizaciones}" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td\n" +
                "                style='background-color: rgb(131, 135, 136); width: 215px;'\n" +
                "                colspan='4' rowspan='1'></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "    <tr align='left'>\n" +
                "        <td colspan='4' rowspan='1'><span\n" +
                "                style='color: rgb(153, 153, 153);'>${Grails.i18n("por_favor_no_responder_a_este_mensaje",locale)}</span></td>\n" +
                "    </tr></tbody>\n" +
                "</table>\n" +
                "<br>\n" +
                "</body>\n" +
                "</html>";


//        println(html);


        return  html;
    }



    def get_html_actualizaciones_tickets(Long id)
    {

        String sql = "SELECT \n" +
                "  t.f_update,\n" +
                "  to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss') as f_fecha,\n" +
                "  COALESCE(usr.f_nombre,'')||' '||COALESCE(usr.f_apellido,'') as f_hecho_por \n" +
                "FROM \n" +
                "  tk.t_tickets_updates as t\n" +
                "  inner JOIN t_usuario as usr  on usr.f_codigo_usuario = t.f_hecho_por \n" +
                "  \n" +
                "  where f_idticket  =  $id order BY  t.f_idrecord DESC ;";

        List list = sqlService.GetQuery(sql);

        String actualizaciones = "";
        int contador = list.size();
        for (Map<String, Object> map : list)
        {
            actualizaciones = actualizaciones +"" +
                    "<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td  colspan=\"4\" rowspan=\"1\" style=\"text-align: left;font-weight: bold; width: 100px;background-color: khaki;font-size: 14px; \"><strong>#${contador}</strong> - ${map.get("f_fecha").toString()} -  ${map.get("f_hecho_por").toString()}</td>\n" +

                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"4\" rowspan=\"1\" style=\"width: 100px;font-size: 14px;\">${map.get("f_update").toString()}</td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table><br/>";
            contador--;
        }

       // println actualizaciones;

        return actualizaciones;
    }

    /*background-color: rgb(204, 204, 204);*/


    def get_fecha_vencimiento_ticket(Date f1, Date f2, Long horas)
    {


        Date nueva_fecha =null;
        Calendar c = Calendar.getInstance();
        c.setTime(f2);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        Long horas_a_sumar  = 0;
        if (dayOfWeek==1)
        {/*********************Daterminamos si el dia de la semana es domingo para sumar 24 horas******************************/
            horas_a_sumar = horas_a_sumar + 24;

        }else {


            /***********************preguntamos si la hora de la fecha de vencim*******************************/
            if(f2.format("HH").toString().toLong() >18)
            {
                horas_a_sumar = horas_a_sumar + 15;
            }


        }

    }

    String formatDate(Date fecha,String formato){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.format(fecha);
    }

    Map getRespuesta(Boolean key,String mensaje){
        Map mapa = new HashMap()
        mapa.key = key;
        mapa.msj = mensaje;
        return mapa
    }
}
