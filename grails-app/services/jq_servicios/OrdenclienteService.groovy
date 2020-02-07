package jq_servicios

import FuncionesGenerales.Funciones
import clientes.Tarchivoordenes
import clientes.Tordentrabajo
import grails.gorm.transactions.Transactional
import publico.Tclientes

@Transactional
class OrdenclienteService {

    Funciones funciones = new Funciones();
    SqlService sqlService;

    def serviceMethod() { }

    def GuardarOrden(String titulo, String des, List tabla, Long idcliente, Long usercli){

        try{

            Tordentrabajo tordentrabajo = new Tordentrabajo(
                    fIdcliente: idcliente,
                    fTipodoc: "ORD",
                    fStatus: 1,
                    fTitulo: titulo,
                    fComentario: des,
                    fFecha: sqlService.GetNow()
            );

            tordentrabajo.save(failOnError: true);

            for (int i = 0; i < tabla.size(); i++) {

                Tarchivoordenes tarchivoordenes = new Tarchivoordenes(
                        fArchivo: tabla[i].archivo,
                        fOrden: tabla[i].f_orden as Boolean,
                        fNamearchivo: tabla[i].namearchivo as String,
                        fIdordentrabajo: tordentrabajo.id
                );

                tarchivoordenes.save(failOnError: true);
            }

            Tclientes tclientes = Tclientes.findById(usercli);

            String sql = "  SELECT\n" +
                    "  concat(o.f_tipodoc,to_char(o.f_numorden,'FM000000')) as num\n" +
                    "  FROM\n" +
                    "  clientes.t_ordentrabajo AS o\n" +
                    "  WHERE o.f_idrecord = ?";

            Collection collection = [];
            collection.add(tordentrabajo.id);

            Map m = sqlService.GetQueryForMap(sql,collection);

            String emails = funciones.GetEmailEmpleadosJQ() + ','+tclientes.fEmail;
            emails = emails.replace("'", "");
            emails = emails.replace(",,", ",");

            EnviarEmail(emails.split(','), 'Orden de Trabajo '.toUpperCase()+m.get("num"),tordentrabajo.fComentario);

            return funciones.getRespuesta(true,"Orden registrada correctamente");

        }catch(Exception e){
            e.printStackTrace();
            return funciones.getRespuesta(false,"La orden no pudo ser registrada correctamente");
        }
    }

    def EnviarEmail(String[] correo, String asunto, String mensaje) {

        try {
            def nombre = sqlService.GetNow().toString() + ".pdf";
            sendMail {
                multipart true
                to correo
                //from "pabellopez1091@gmail.com";
                from "noreply@jqmicro.com";
                subject asunto
//                body mensaje
                html mensaje
                // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
                //To get started quickly, try the following
                //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
                // attachBytes nombre,'text/pdf', archivo;


            }
        }
        catch (Exception e) {
            println("Error al enviar mensaje es ----> " + e.getMessage())
            //  throw e;
        }
    }
}
