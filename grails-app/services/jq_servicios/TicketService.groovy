package jq_servicios

import FuncionesGenerales.Funciones
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import proyecto.Templeados
import publico.Tusuario
import tk.TDistribucionCorreos
import tk.Ttickets
import tk.TticketsFiles
import tk.TticketsPrioridad
import tk.TticketsUpdates
import tk.Tusuarios

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Transactional
class TicketService {
    static transactional = true;

    def sqlService;
    Funciones funciones = new Funciones();
    PublicoService service = funciones.getServicios(PublicoService);

//    def Long crear_ticket(String titulo,Long idusuario,Long idprioridad,String descripcion,Long idproyecto,String json){
//
//        Tusuario u = Tusuario.findById(idusuario);
//        
//        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
//        TticketsPrioridad prioridad = TticketsPrioridad.findById(idprioridad);
//
//
//        Ttickets tickets =  Ttickets.findById(idticket);
//        tickets.setfIdquienasigno(idusuario);
//        tickets.setfIdtecnicoasignado(idtecnico);
//        tickets.save(failOnError: true);
//
//         TticketsUpdates update = new TticketsUpdates(
//                 fFecha: sqlService.GetNow(),
//                 fHechoPor: idusuario,
//                 fHoras: 0,
//                 fIdticket: tickets.id,
//                 fUpdate: '');
//        update.save(failOnError: true);
//
//        if
//
//
//
//        return "1";
//    }

    def crear_ticket(String titulo, Long idUsuario, Long idprioridad, String descripcion, Long idProyecto, String json, Boolean broadcast, Long tipo) {

        Tusuario u = Tusuario.findById(idUsuario);
        


        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);

        TticketsPrioridad prioridad = TticketsPrioridad.findById(idprioridad);

        // println prioridad.fTiempoSolucion+" pabel lopez mena ";
        // println sqlService.Get_sumar_Horas(sqlService.GetNow().toTimestamp(), prioridad.fTiempoSolucion.toInteger()).toTimestamp().toString()+" pabel lopez mena ";

        Ttickets tickets = new Ttickets(
                fIdproyecto: idProyecto,
                fIdprioridad: idprioridad,
                fTitulo: titulo,
                fDescipcion: descripcion,
                fIdcliente: usuario_ticket.fIdcliente,
                fFecha: sqlService.GetNow().toTimestamp(),
                fTiempoResolucion: prioridad.fTiempoSolucion,
                fCancelado: false,
                fHechoPor: u.id,
                fAsignado: false,
                fCerrado: false,
                fCerradoCliente: false,
                fTipoTk: tipo,
                fBroadcast: broadcast,
                fStatus: 1,
                fFechaVencimiento: sqlService.Get_sumar_Horas(sqlService.GetNow().toTimestamp(), prioridad.fTiempoSolucion.toInteger()).toTimestamp()
        )
        tickets.save(failOnError: true);

        List<Map<String, Object>> list = JSON.parse(json).collect();
        String ids = ".";
        for (Map<String, Object> map : list) {
            TticketsFiles files = new TticketsFiles(
                    fIdticket: tickets.id,
                    fNombre: map.get("f_nombre"),
                    fExtencion: map.get("f_extencion")
            );
            files.save(failOnError: true);
            ids += "," + files.id;
        }
        ids = ids.replace(".,", "");

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session.setAttribute("json_imagenes", ids);



        if (list.size() == 0) {
            Funciones funciones = new Funciones();
            String html = funciones.generador_reporte_tickets(tickets);

            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(usuario_ticket.fIdcliente);
            String correos_distribucion = "";
            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

            String emails = funciones.GetEmailEmpleadosJQ() + ',' + usuario_ticket.fEmail + ',' + correos_distribucion;



            emails = emails.replace("'", "");
            emails = emails.replace(",,", ",");

            print(emails);
            service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);

        }


        return "1-" + tickets.id.toString();
    }

    def salvar_ticket_admin(String titulo, Long idUsuario, Long idprioridad, String descripcion, Long idProyecto, String json, Boolean broadcast, Long tipo) {

        Tusuario u = Tusuario.findById(idUsuario);
        
        // Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
        TticketsPrioridad prioridad = TticketsPrioridad.findById(idprioridad);

        Ttickets tickets = new Ttickets(
                fIdproyecto: idProyecto,
                fIdprioridad: idprioridad,
                fTitulo: titulo,
                fDescipcion: descripcion,
                fIdcliente: 0,
                fBroadcast: broadcast,
                fFecha: sqlService.GetNow().toTimestamp(),
                fTiempoResolucion: prioridad.fTiempoSolucion,
                fCancelado: false,
                fHechoPor: u.id,
                fAsignado: false,
                fCerrado: false,
                fTipoTk: tipo,
                fCerradoCliente: false,
                fStatus: 1,
                fFechaVencimiento: sqlService.Get_sumar_Horas(sqlService.GetNow().toTimestamp(), prioridad.fTiempoSolucion.toInteger()).toTimestamp()
        )
        tickets.save(failOnError: true);

        List<Map<String, Object>> list = JSON.parse(json).collect();
        String ids = ".";
        for (Map<String, Object> map : list) {
            TticketsFiles files = new TticketsFiles(
                    fIdticket: tickets.id,
                    fNombre: map.get("f_nombre"),
                    fExtencion: map.get("f_extencion")
            );
            files.save(failOnError: true);
            ids += "," + files.id;
        }
        ids = ids.replace(".,", "");

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session.setAttribute("json_imagenes_admin", ids);



        if (list.size() == 0) {
            Funciones funciones = new Funciones();
            String html = funciones.generador_reporte_tickets(tickets);
            String emails = funciones.GetEmailEmpleadosJQ();
            emails = emails.replace("'", "");



//            println emails;

            service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);
//
        }

//        Funciones funciones = new Funciones();
//        String html = funciones.generador_reporte_tickets(tickets);
//
//
//        PublicoService service = Grails.get(PublicoService);
//
//        String emails = funciones.GetEmailEmpleadosJQ();
//
//        emails = emails.replace("'", "");
//        service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);
//


        return "1-" + tickets.id.toString();
    }

    def prueba() {

        def t = Ttickets.findById(35);
        Funciones funciones = new Funciones();
        String html = funciones.generador_reporte_tickets(Ttickets.findById(35));


        String emails = funciones.GetEmailEmpleadosJQ();

        emails = emails.replace("'", "");
        service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + t.fTitulo.toUpperCase(), html);

    }

    def salvar_asignacion(String nota, Long idticket, Long idprioridad, Long idtecnico, Long idusuario, Boolean broadcast, Long tipo) {

        Tusuario u = Tusuario.findById(idusuario);
        
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
        TticketsPrioridad prioridad = TticketsPrioridad.findById(idprioridad);
        Templeados emp = Templeados.findById(idtecnico);


        Ttickets tickets = Ttickets.findById(idticket);
        tickets.setfIdquienasigno(u.fIdempleado);
        tickets.setfIdtecnicoasignado(idtecnico);
        tickets.setfAsignado(true);
        tickets.setfTipoTk(tipo);
        tickets.setfBroadcast(broadcast);
        //tickets.setfStatus(2)
        tickets.setfFechaAsignacion(sqlService.GetNow());

        tickets.save(failOnError: true);


        String texto_update = 'ASIGNACION DE TICKET POR ' + u.fNombre + ' ' + u.fApellido + ' A ' + emp.fDescripcion + ".... <br/>"

        if (idprioridad != tickets.getfIdprioridad()) {
            tickets.setfIdprioridad(idprioridad);
            tickets.setfFechaVencimiento(sqlService.Get_sumar_Horas(tickets.fFecha, prioridad.fTiempoSolucion.toInteger()).toTimestamp());
            tickets.save(failOnError: true);

            texto_update = texto_update + "<br/><strong>NOTA :</strong><br/>" + nota;
        }

        Funciones f = new Funciones();



        TticketsUpdates update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: 0,
                fIdticket: tickets.id,
                fUpdate: texto_update);
        update.save(failOnError: true);



        String txt = f.generador_reporte_tickets_asignacion(tickets, u, texto_update);

        Tusuario u1 = Tusuario.findById(tickets.fHechoPor);

        String emails = emp.fEmailtickets + ',' + u1.fEmail;

        String correos_distribucion = "";
        if (tickets.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(tickets.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            emails = emails + ',' + correos_distribucion;
        }

        emails = emails.replace("'", "");
//        service.EnviarEmail(emp.fEmail,'ASIGNACION',txt);

//        println(emails);
        service.EnviarEmail(emails.split(","), 'ASIGNACION', txt);

        return "1";
    }


    def salvar_reasignacion_tickets(String nota, Long idticket, Long idprioridad, Long idtecnico, Long idusuario, String fecha) {

        Tusuario u = Tusuario.findById(idusuario);
        
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
        TticketsPrioridad prioridad = TticketsPrioridad.findById(idprioridad);
        Templeados emp = Templeados.findById(idtecnico);

        Ttickets tickets = Ttickets.findById(idticket);
        def fv = tickets.fFechaVencimiento;
//        String tiempo = fv.format("HH:mm:ss");

        String texto_update = 'REASIGNACION DE TICKET POR ' + u.fNombre + ' ' + u.fApellido + ' A ' + emp.fDescripcion + ".... <br/> ";

        if (idprioridad != tickets.getfIdprioridad()) {

            tickets.setfIdprioridad(idprioridad);
            tickets.setfFechaVencimiento(sqlService.Get_sumar_Horas(tickets.fFecha, prioridad.fTiempoSolucion.toInteger()).toTimestamp());
            tickets.save(failOnError: true);
            texto_update = texto_update + nota;
        }


        if (!fecha.equals("") && !fecha.is(null)) {
            fv = new Date(fecha);
            tickets.setfFechaVencimiento(fv.toTimestamp());
            texto_update = texto_update + 'CAMBIO LA FECHA DE VENCIMIENTO A ' + funciones.formatDate(fv,"yyyy/MM/dd HH:mm:ss") + '...<br/>';

        }


        tickets.setfIdquienasigno(idusuario);
        tickets.setfIdtecnicoasignado(idtecnico);
        tickets.setfAsignado(true);
        tickets.save(failOnError: true);


        TticketsUpdates update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: 0,
                fIdticket: tickets.id,
                fUpdate: texto_update);
        update.save(failOnError: true);


        Funciones f = new Funciones();
        String mensaje = f.generador_reporte_tickets_reasignado(tickets);

        Tusuario u1 = Tusuario.findById(tickets.fHechoPor);

        String emails = emp.fEmailtickets + ',' + u1.fEmail;

        String correos_distribucion = "";
        if (tickets.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(tickets.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            emails = emails + ',' + correos_distribucion;
        }
        emails = emails.replace("'", "");

        service.EnviarEmail(emails.split(","), 'REASIGNACION DE TICKETS', mensaje);


        return "1";
    }


    def salvar_cancelacion_tickets(String nota, Long idticket, Long idusuario) {

        Tusuario u = Tusuario.findById(idusuario);
        


        Ttickets tickets = Ttickets.findById(idticket);
        tickets.setfCancelado(true);
        tickets.setfFechaCancelacion(sqlService.GetNow());
        tickets.setfIdcancelo(u.fIdempleado);

        tickets.save(failOnError: true);

        TticketsUpdates update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: 0,
                fIdticket: tickets.id,
                fUpdate: 'CANCELACION DEL TICKET ' + nota);
        update.save(failOnError: true);

        Funciones funciones = new Funciones();
        Tusuario u1 = Tusuario.findById(tickets.fHechoPor);

        String emails = funciones.GetEmailEmpleadosJQ();
        if (u1.fTickets == true) {
            Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u1.fIdUsuario);
            emails = emails + ',' + usuario_ticket.fEmail;
        } else {
            emails = emails + ',' + u1.fEmail;
        }


        String correos_distribucion = "";
        if (tickets.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(tickets.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            emails = emails + ',' + correos_distribucion;
        }

        emails = emails.replace("'", "");

        Funciones f = new Funciones();
        String mensaje = f.generador_reporte_tickets_cancelacion(tickets, nota, u);
        service.EnviarEmail(emails.split(','), 'CANCELACION DE TICKETS', mensaje);

        return "1";
    }

    def salvar_ticket_archivo(byte[] archivo) {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        String[] ids = session.getAttribute("json_imagenes").toString().split(",");

        TticketsFiles files = TticketsFiles.findById(ids[0].toString().replace("]", '').toLong());
        files.setfFile(archivo);
        files.save(failOnError: true);
        if (ids.length > 1) {
            session.setAttribute("json_imagenes", ids.toString().substring(ids.toString().indexOf(",") + 1, ids.toString().length()));
        } else {
            session.removeAttribute("json_imagenes");
        }
    }

    def salvar_ticket_archivo_admin(byte[] archivo) {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        String[] ids = session.getAttribute("json_imagenes_admin").toString().split(",");

        TticketsFiles files = TticketsFiles.findById(ids[0].toString().replace("]", '').toLong());
        files.setfFile(archivo);
        files.save(failOnError: true);
        if (ids.length > 1) {
            session.setAttribute("json_imagenes_admin", ids.toString().substring(ids.toString().indexOf(",") + 1, ids.toString().length()));
        } else {
            session.removeAttribute("json_imagenes_admin");
        }
    }

    def ListarPrioridadesTickets() {



        String sql = "SELECT\n" +
                "       p.f_idrecord as f_idrecord,\n" +
                "       (case p.f_prioridad  WHEN \n" +
                "        1 THEN 'Critico'\n" +
                "       WHEN 2 THEN 'Alta'\n" +
                "       WHEN 3 THEN 'Media'\n" +
                "       WHEN 4 THEN 'Baja' END) as f_descripcion,\n" +
                "       p.f_tipo_tk,\n" +
                "       p.f_prioridad\n" +
                "      \n" +
                "       from tk.t_tickets_prioridad as p order by f_tipo_tk,p.f_prioridad ASC";

        return sqlService.GetQuery(sql);
    }


    def ListarTipoTickets() {

        
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_descripcion,\n" +
                "  f_descripcion_ingles\n" +
                "FROM \n" +
                "  tk.t_tipo_tikets  order by f_idrecord asc;";

        return sqlService.GetQuery(sql);
    }

    def ListarTicketsByClientes(Long id) {
        
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "tp.f_tipo_tk" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "where t.f_idcliente = $id  order by t.f_idrecord asc \n";

        return sqlService.GetQuery(sql);

    }

    def ListarTicketsByClientes(Long id, Date f1, Date f2, String titulo, String numero_ticket) {
        
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "tp.f_tipo_tk,\n" +
                "sta.f_descripcion as f_descripcion_status,\n" +
                "sta.f_color as f_color_status,\n" +
                "sta.f_icono as f_icono_status,\n" +
                "t.f_status as f_status,\n" +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad  \n" +
                "INNER JOIN tk.t_status as sta on sta.f_id = t.f_status \n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                "where t.f_idcliente = $id \n";


        if (!numero_ticket.equals("")) {
            sql = sql + " and t.f_idrecord::text ='$numero_ticket' ";
        } else {
            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }
        }



        sql = sql + " order by t.f_idrecord asc \n";

        return sqlService.GetQuery(sql);

    }


    def ListarTicketsByClientesNoCancelados(Long id, Date f1, Date f2, String titulo) {
        
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "tp.f_tipo_tk,\n" +
                "t.f_cancelado\n," +
                "t.f_asignado,\n" +
                "t.f_cerrado" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "where t.f_idcliente = $id and t.f_cancelado =false \n";






        if (!titulo.equals("")) {
            sql = sql + " and t.f_titulo ilike '%$titulo%' ";
        }


        if (!f1.is(null) && !f2.is(null)) {
            sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
        } else {
            if (!f1.is(null)) {
                sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
            } else {
                if (!f2.is(null)) {
                    sql = sql + " and  t.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                }
            }
        }


        sql = sql + " order by t.f_idrecord asc \n";
        return sqlService.GetQuery(sql);

    }

    def ListarTicketsByClientesNoCanceladosNoCerrados(Long id, Date f1, Date f2, String titulo, String numero_ticket) {
        
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "tp.f_tipo_tk,\n" +
                "t.f_cancelado\n," +
                "t.f_asignado,\n" +
                "t.f_cerrado,\n" +
                "t.f_cerrado_cliente," +
                "sta.f_descripcion as f_descripcion_status,\n" +
                "sta.f_color as f_color_status,\n" +
                "sta.f_icono as f_icono_status,\n" +
                "t.f_status,\n" +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                "INNER JOIN tk.t_status as sta on sta.f_id = t.f_status\n" +
                "where t.f_idcliente = $id and t.f_status !=4\n";

        if (!numero_ticket.equals("")) {
            sql = sql + " and t.f_idrecord::text='$numero_ticket' ";
        } else {
            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }
        }

        sql = sql + " order by t.f_idrecord asc \n";
        return sqlService.GetQuery(sql);

    }

    def ListarTodosTickets() {
        
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                " where t.f_asignado = false order by t.f_idrecord asc";

        return sqlService.GetQuery(sql);

    }


    def ListarTodosTickets(Date f1, Date f2, String titulo, Boolean cerrado, String numero_ticket) {
//        String sql ="select t.f_idrecord,\n" +
//                "t.f_titulo,\n" +
//                "pr.f_descripcion as f_nombre,\n" +
//                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'Critico'\n" +
//                "WHEN 2 THEN 'Alta'\n" +
//                "WHEN 3 THEN 'Media'\n" +
//                "WHEN 4 THEN 'Baja' \n" +
//                "END as f_prioridad,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'label-danger'\n" +
//                "WHEN 2 THEN 'label-warning' \n" +
//                "WHEN 3 THEN 'label-info'\n" +
//                "WHEN 4 THEN 'label-success' \n" +
//                "END as f_color," +
//                "" +
//                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
//                "t.f_descipcion,\ntp.f_tipo_tk,\n" +
//                "t.f_cancelado\n" +
//                ",t.f_asignado,\n" +
//                "t.f_cerrado,\n" +
//                "t.f_fecha_vencimiento as f_fecha_vencimiento,\n" +
//                "t.f_cerrado_cliente,\n" +
//                "COALESCE(usr.f_nombre,'')||' '||COALESCE(usr.f_apellido,'') as f_hecho_por,\n" +
//                "COALESCE(emp.f_descripcion,'') as f_tecnico," +
//                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
//                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
//                "\n" +
//                "FROM \n" +
//                "tk.t_tickets t\n" +
//                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
//                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
//                "INNER JOIN public.t_usuario as usr on usr.f_codigo_usuario = t.f_hecho_por\n" +
//                "inner JOIN proyectos.t_empleados as emp on emp.f_id = t.f_idtecnicoasignado\n" +
//                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
//                " where 1=1 and t.f_cerrado = $cerrado " ;


        String sql_status = "";
        if (cerrado == true) {
            sql_status = " and t.f_status = 3 ";
        } else {
            sql_status = " and (t.f_status = 1 or t.f_status = 2) ";
        }
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\ntp.f_tipo_tk,\n" +
                "t.f_cancelado\n" +
                ",t.f_asignado,\n" +
                "t.f_cerrado,\n" +
                "t.f_fecha_vencimiento as f_fecha_vencimiento,\n" +
                "t.f_cerrado_cliente,\n" +
                "COALESCE(usr.f_nombre,'')||' '||COALESCE(usr.f_apellido,'') as f_hecho_por,\n" +
                "COALESCE(emp.f_descripcion,'') as f_tecnico," +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "INNER JOIN public.t_usuario as usr on usr.f_codigo_usuario = t.f_hecho_por\n" +
                "inner JOIN proyectos.t_empleados as emp on emp.f_id = t.f_idtecnicoasignado\n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                " where 1=1 $sql_status ";




        if (!numero_ticket.equals("")) {
            sql = sql + " and t.f_idrecord::text = '$numero_ticket' ";
        } else {

            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date <= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }

        }
        sql = sql + " order by t.f_idrecord asc \n";
        return sqlService.GetQuery(sql);
    }


    def ListarTodosTicketsNoCancelados(Date f1 = null, Date f2 = null, String titulo, String numero) {

        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "COALESCE(cli.f_nombre_empresa,'') as f_cliente,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color,COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "(COALESCE(u.f_nombre,'')||' '||COALESCE(u.f_apellido,'')) as f_hecho_por,\n" +
                "tp.f_tipo_tk,\n" +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "LEFT JOIN t_clientes as cli on cli.f_id = t.f_idcliente\n" +
                "INNER JOIN t_usuario as u on u.f_codigo_usuario = t.f_hecho_por\n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                "WHERE t.f_status = 1 ";

        if (!numero.equals("")) {
            sql = sql + " and t.f_idrecord::text = '$numero' ";
        } else {
            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date <= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }

        }


        sql = sql + " order by t.f_idrecord asc \n";

//         println(sql);

        return sqlService.GetQuery(sql);

    }

    def ListarTodosTicketsAsignados(Date f1, Date f2, String titulo, String numero) {
//        String sql ="select t.f_idrecord,\n" +
//                "t.f_titulo,\n" +
//                "COALESCE(cli.f_nombre_empresa,'') as f_cliente,\n" +
//                "pr.f_descripcion as f_nombre,\n" +
//                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
//                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'Critico'\n" +
//                "WHEN 2 THEN 'Alta'\n" +
//                "WHEN 3 THEN 'Media'\n" +
//                "WHEN 4 THEN 'Baja' \n" +
//                "END as f_prioridad,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'label-danger'\n" +
//                "WHEN 2 THEN 'label-warning' \n" +
//                "WHEN 3 THEN 'label-info'\n" +
//                "WHEN 4 THEN 'label-success' \n" +
//                "END as f_color,COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
//                "t.f_descipcion,\n" +
//                "(COALESCE(u.f_nombre,'')||' '||COALESCE(u.f_apellido,'')) as f_hecho_por,\n" +
//                "tp.f_tipo_tk,\n" +
//                "COALESCE(emp.f_descripcion,'') as f_tecnico,\n" +
//                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
//                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
//                "FROM \n" +
//                "tk.t_tickets t\n" +
//                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
//                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
//                "LEFT JOIN t_clientes as cli on cli.f_id = t.f_idcliente\n" +
//                "INNER JOIN t_usuario as u on u.f_codigo_usuario = t.f_hecho_por\n" +
//                "INNER JOIN proyectos.t_empleados as emp on emp.f_id = t.f_idtecnicoasignado\n" +
//                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
//                "WHERE t.f_asignado = true and t.f_cerrado = false and t.f_cancelado = false " ;


        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "COALESCE(cli.f_nombre_empresa,'') as f_cliente,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color,COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "(COALESCE(u.f_nombre,'')||' '||COALESCE(u.f_apellido,'')) as f_hecho_por,\n" +
                "tp.f_tipo_tk,\n" +
                "COALESCE(emp.f_descripcion,'') as f_tecnico,\n" +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "LEFT JOIN t_clientes as cli on cli.f_id = t.f_idcliente\n" +
                "INNER JOIN t_usuario as u on u.f_codigo_usuario = t.f_hecho_por\n" +
                "INNER JOIN proyectos.t_empleados as emp on emp.f_id = t.f_idtecnicoasignado\n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                "WHERE t.f_status = 2 ";




        if (!numero.equals("")) {
            sql = sql + " and t.f_idrecord::text = '$numero' ";
        } else {
            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date <= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }

        }

        sql = sql + " order by t.f_idrecord asc \n";

        //  println(sql);

        return sqlService.GetQuery(sql);

    }


    def ListarCantidad() {
        String sql = "select t.f_idrecord \n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "inner JOIN t_clientes as cli on cli.f_id = t.f_idcliente\n" +
                " INNER JOIN t_usuario as u on u.f_codigo_usuario = t.f_hecho_por\n" +
                "WHERE t.f_asignado = false and t.f_cerrado = false and t.f_cancelado = false";



        return sqlService.GetQuery(sql).size();

    }


    def GetTiketsById(Long id) {
        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha," +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento," +
                "f_idprioridad as f_idprioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "t.f_idquienasigno,\n" +
                "t.f_idtecnicoasignado,\n" +
                "t.f_asignado,\n" +
                "t.f_cancelado,\n" +
                "t.f_cerrado,\n" +
                "t.f_broadcast,\n" +
                "t.f_status,\n" +
                "(COALESCE(u.f_nombre,'')||' '||COALESCE(u.f_apellido,'')) as f_hecho_por,\n" +
                "tp.f_tipo_tk, \n" +
                "t.f_tipo_tk as f_tipo \n" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                " INNER JOIN t_usuario as u on u.f_codigo_usuario = t.f_hecho_por\n" +
                " where t.f_idrecord = $id";

        //  println sql;

        return sqlService.GetQuery(sql);

    }


    def ListarTicketsUpdate(Long id) {

        String sql = "SELECT \n" +
                "  tu.f_idrecord,\n" +
                "  tu.f_update,\n" +
                "  tu.f_picture,\n" +
                "  to_char(tu.f_fecha,'yyyy/MM/dd HH:mm pm') as f_fecha,\n" +
                "  tu.f_horas,\n" +
                "  (COALESCE(u.f_nombre,'')||' '||COALESCE(u.f_apellido,'')) as f_hecho_por,\n" +
                "  t.f_titulo\n" +
                "FROM \n" +
                "  tk.t_tickets_updates tu\n" +
                "  INNER JOIN t_usuario as u on u.f_codigo_usuario = tu.f_hecho_por\n" +
                "  INNER JOIN tk.t_tickets as t on t.f_idrecord = tu.f_idticket\n" +
                "  \n" +
                "  \n" +
                "   where tu.f_idticket  = $id  order by tu.f_idrecord desc;";




        return sqlService.GetQuery(sql);

    }


    def get_archivos_by_idtickets(Long id) {
        String sql = " SELECT \n" +
                "  f_idrecord,\n" +
                "  f_idticket,\n" +
                "  f_file,\n" +
                "  f_nombre,\n" +
                "  f_extencion\n" +
                "FROM \n" +
                "  tk.t_tickets_files WHERE f_idticket = $id;";

        return sqlService.GetQuery(sql);
    }

    def salvar_comentario(Long idtickets, Long idusuario, String nota, BigDecimal horas, String nombre_archivo, String extencion) {
        Tusuario u = Tusuario.findById(idusuario);
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);

        def ext = null;
        def nom = null;

        if (!extencion.equals("")) {
            ext = extencion;
        }
        if (!nombre_archivo.equals("")) {
            nom = nombre_archivo;
        }
        def update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: horas,
                fIdticket: idtickets,
                fUpdate: nota,
                fNombreArchivo: nom,
                fExtencionArchivo: ext
        );
        update.save(failOnError: true);

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session.setAttribute("idrecord_comentario_archivo", update.id);

        Funciones funciones = new Funciones();

        Ttickets ttickets = Ttickets.findById(idtickets.toLong());

        String mensaje = funciones.generador_reporte_actualizacion_tickets(ttickets);


        Templeados emp = Templeados.findById(ttickets.fIdtecnicoasignado);

        Tusuario usr = Tusuario.findById(ttickets.fHechoPor);

        String email = usr.fEmail;
        email = email + ',' + emp.fEmailtickets;



        String correos_distribucion = "";
        if (ttickets.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(ttickets.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            email = email + ',' + correos_distribucion;
        }

        if(email.substring(0,1).equals(",")){
            email = email.substring(1,email.length());
        }

        email = email.replace("'", "");


        service.EnviarEmail(email.split(','), 'ACTUALIZACION DE TICKETS', mensaje);


    }


    def cerrar_tickets(Long idtickets, Long idusuario, String nota, BigDecimal hora) {
        Tusuario u = Tusuario.findById(idusuario);



        Ttickets t = Ttickets.findById(idtickets);
        t.setfFechaCerrado(sqlService.GetNow());
        t.setfCerrado(true);
        t.setfTiempoSolucionado(hora);
        t.save(failOnError: true);


        def update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: hora,
                fIdticket: idtickets,
                fUpdate: '<strong>CIERRE DE TICKET</strong><br/><br/><strong>NOTA :</strong><br/>' + nota,
                fNombreArchivo: null,
                fExtencionArchivo: null
        );
        update.save(failOnError: true);

        Tusuario u1 = Tusuario.findById(t.fHechoPor);
        Funciones funciones = new Funciones();
        String email = funciones.GetEmailEmpleadosJQ();
        if (u1.fTickets == true) {
            Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u1.fIdUsuario);
            email = email + ',' + usuario_ticket.fEmail;
        }
        //Templeados emp = Templeados.findById(t.fIdtecnicoasignado);
        //   email = email +','+emp.fEmail;

//        String mensaje = 'TICKET CERRADO <strong>'+t.fTitulo+'</strong>  POR '+u.fNombre+' '+u.fApellido+'<br/><br/><strong>NOTA :</strong><br/>'+ nota;

        String mensaje = funciones.generador_reporte_cerrar_tickets(t, update.fUpdate);

        String correos_distribucion = "";
        if (t.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(t.fIdcliente);
            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }
        }

        if (!correos_distribucion.equals("")) {
            email = email + ',' + correos_distribucion;
        }

        email = email.replace("'", "");
        service.EnviarEmail(email.split(','), 'CIERRE DE TICKETS', mensaje);

    }

    def cerrar_tickets_clientes(Long idtickets, Long idusuario, String nota) {
        Tusuario u = Tusuario.findById(idusuario);



        Ttickets t = Ttickets.findById(idtickets);
        t.setfCerradoCliente(true);
        t.setfFechaCerradoCliente(sqlService.GetNow());
        t.save(failOnError: true);


        def update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: 0,
                fIdticket: idtickets,
                fUpdate: '<strong>CIERRE DE TICKET</strong><br/><br/><strong>NOTA :</strong><br/>' + nota,
                fNombreArchivo: null,
                fExtencionArchivo: null
        );
        update.save(failOnError: true);

        Tusuario u1 = Tusuario.findById(t.fHechoPor);
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u1.fIdUsuario);
        Templeados emp = Templeados.findById(t.fIdtecnicoasignado);
        Funciones funciones = new Funciones();
        String email = funciones.GetEmailEmpleadosJQ();


        String mensaje = 'TICKET CERRADO <strong>' + t.fTitulo + '</strong>  POR ' + u.fNombre + ' ' + u.fApellido + '<br/><br/><strong>NOTA :</strong><br/>' + nota;

        String correos_distribucion = "";
        if (t.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(t.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            email = email + ',' + correos_distribucion;
        }
        email = email.replace("'", "");
        service.EnviarEmail(email.split(','), 'CIERRE DE TICKETS', mensaje);

    }

    def salvar_archivo_comentario(byte[] archivo) {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        Long id = session.getAttribute("idrecord_comentario_archivo");
        TticketsUpdates update = TticketsUpdates.findById(id);
        if (update) {
            update.setfPicture(archivo);
            update.save(failOnError: true);
        }
    }


    def salvar_comentario_clientes(Long idtickets, Long idusuario, String nota, String nombre_archivo, String extencion) {
        Tusuario u = Tusuario.findById(idusuario);
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);

        def ext = null;
        def nom = null;

        if (!extencion.equals("")) {
            ext = extencion;
        }
        if (!nombre_archivo.equals("")) {
            nom = nombre_archivo;
        }
        def update = new TticketsUpdates(
                fFecha: sqlService.GetNow(),
                fHechoPor: idusuario,
                fHoras: 0,
                fIdticket: idtickets,
                fUpdate: nota,
                fNombreArchivo: nom,
                fExtencionArchivo: ext
        );
        update.save(failOnError: true);

        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session.setAttribute("idrecord_comentario_archivo_clientes", update.id);





        Funciones funciones = new Funciones();

        Ttickets ttickets = Ttickets.findById(idtickets.toLong());

        String mensaje = funciones.generador_reporte_actualizacion_tickets(ttickets);


        Templeados emp = Templeados.findById(ttickets.fIdtecnicoasignado);

        Tusuario usr = Tusuario.findById(ttickets.fHechoPor);

        String email = usr.fEmail;

        if (emp){
            email = email + ',' + emp.fEmailtickets;
        }



        String correos_distribucion = "";
        if (ttickets.fBroadcast) {
            TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(ttickets.fIdcliente);

            if (distribucionCorreos) {
                correos_distribucion = distribucionCorreos.fCorreos;
            }

        }
        if (!correos_distribucion.equals("")) {
            email = email + ',' + correos_distribucion;
        }
        email = email.replace("'", "");
        service.EnviarEmail(email.split(','), 'ACTUALIZACION DE TICKETS', mensaje);

    }

    def salvar_archivo_comentario_clientes(byte[] archivo) {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        Long id = session.getAttribute("idrecord_comentario_archivo_clientes");
        TticketsUpdates update = TticketsUpdates.findById(id);
        if (update) {
            update.setfPicture(archivo);
            update.save(failOnError: true);
        }
    }


    def ListarTicketsByClientesReportesAbiertosCerrados(Long id, Date f1, Date f2, String titulo, Boolean cerrado, String numero_ticket) {
//        String sql ="select t.f_idrecord,\n" +
//                "t.f_titulo,\n" +
//                "pr.f_descripcion as f_nombre,\n" +
//                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
//                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'Critico'\n" +
//                "WHEN 2 THEN 'Alta'\n" +
//                "WHEN 3 THEN 'Media'\n" +
//                "WHEN 4 THEN 'Baja' \n" +
//                "END as f_prioridad,\n" +
//                "case tp.f_prioridad  WHEN \n" +
//                "1 THEN 'label-danger'\n" +
//                "WHEN 2 THEN 'label-warning' \n" +
//                "WHEN 3 THEN 'label-info'\n" +
//                "WHEN 4 THEN 'label-success' \n" +
//                "END as f_color," +
//                "" +
//                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
//                "t.f_descipcion,\n" +
//                "tp.f_tipo_tk,\n" +
//                "t.f_cancelado\n," +
//                "t.f_asignado,\n" +
//                "t.f_cerrado,\n" +
//                "--t.f_fecha_vencimiento as f_fecha_vencimiento,\n" +
//                "COALESCE(usr.f_nombre,'')||' '||COALESCE(usr.f_apellido,'') as f_hecho_por," +
//                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
//                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
//                "\n" +
//                "FROM \n" +
//                "tk.t_tickets t\n" +
//                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
//                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
//                "INNER JOIN public.t_usuario as usr on usr.f_codigo_usuario = t.f_hecho_por\n" +
//                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
//                "where t.f_idcliente = $id and t.f_cancelado =false and t.f_cerrado = $cerrado \n" ;


        String sql_status = "";
        if (cerrado == true) {
            sql_status = " and t.f_status = 3 ";
        } else {
            sql_status = " and (t.f_status = 1 or t.f_status = 2) ";
        };


        String sql = "select t.f_idrecord,\n" +
                "t.f_titulo,\n" +
                "pr.f_descripcion as f_nombre,\n" +
                "to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm') as f_fecha,\n" +
                "to_char(t.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss pm') as f_fecha_vencimiento,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'Critico'\n" +
                "WHEN 2 THEN 'Alta'\n" +
                "WHEN 3 THEN 'Media'\n" +
                "WHEN 4 THEN 'Baja' \n" +
                "END as f_prioridad,\n" +
                "case tp.f_prioridad  WHEN \n" +
                "1 THEN 'label-danger'\n" +
                "WHEN 2 THEN 'label-warning' \n" +
                "WHEN 3 THEN 'label-info'\n" +
                "WHEN 4 THEN 'label-success' \n" +
                "END as f_color," +
                "" +
                "COALESCE((SELECT to_char(up.f_fecha,'yyyy/MM/dd HH:mm:ss pm') from tk.t_tickets_updates as up where up.f_idticket = t.f_idrecord ORDER BY up.f_fecha DESC limit 1),to_char(t.f_fecha,'yyyy/MM/dd HH:mm:ss pm')) AS f_fecha_modificacion,\n" +
                "t.f_descipcion,\n" +
                "tp.f_tipo_tk,\n" +
                "t.f_cancelado\n," +
                "t.f_asignado,\n" +
                "t.f_cerrado,\n" +
                "--t.f_fecha_vencimiento as f_fecha_vencimiento,\n" +
                "COALESCE(usr.f_nombre,'')||' '||COALESCE(usr.f_apellido,'') as f_hecho_por," +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "\n" +
                "FROM \n" +
                "tk.t_tickets t\n" +
                "INNER JOIN t_productos as pr on pr.f_id = t .f_idproyecto\n" +
                "INNER JOIN tk.t_tickets_prioridad tp on tp.f_idrecord = t.f_idprioridad\n" +
                "INNER JOIN public.t_usuario as usr on usr.f_codigo_usuario = t.f_hecho_por\n" +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = t.f_tipo_tk\n" +
                "where t.f_idcliente = $id $sql_status \n";

        if (!numero_ticket.equals("")) {
            sql = sql + " and t.f_idrecord::text='$numero_ticket' ";

        } else {
            if (!titulo.equals("")) {
                sql = sql + " and t.f_titulo ilike '%$titulo%' ";
            }


            if (!f1.is(null) && !f2.is(null)) {
                sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
            } else {
                if (!f1.is(null)) {
                    sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date";
                } else {
                    if (!f2.is(null)) {
                        sql = sql + " and  t.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date";
                    }
                }
            }
        }
        sql = sql + " order by t.f_idrecord asc \n";
        if (f1.is(null) && f2.is(null)) {
            return null;
        } else {
            return sqlService.GetQuery(sql);
        }


    }

    def enviar_archivos_tickets_clientes(Long id, Long idUsuario) {

        Tusuario u = Tusuario.findById(idUsuario);
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
        List<TticketsFiles> files = TticketsFiles.findAllByFIdticket(id.toLong());

        List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
        for (TticketsFiles file : files) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("f_nombre", file.fNombre);
            map.put("f_archivo", file.fFile);
            lista.add(map);
        }


        Funciones funciones = new Funciones();

        Ttickets tickets = Ttickets.findById(id);
        String html = funciones.generador_reporte_tickets(tickets);
        String emails = funciones.GetEmailEmpleadosJQ() + ',' + usuario_ticket.fEmail;

        emails = emails.replace("'", "");
        // service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);

        if (lista.size() > 0) {
//            println '3';
            Zippear(lista, "archivos_tickets.zip", emails.split(","), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);

        } else {

            service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);
        }

    }

    def Zippear(List<Map<String, Object>> lista, String pZipFile, String[] correos, String asunto, String mensaje) throws Exception {

        try {
            File file = File.createTempFile(
                    pZipFile.substring(0, pZipFile.lastIndexOf('.')),
                    pZipFile.substring(pZipFile.lastIndexOf('.'), pZipFile.length())
            );
            FileOutputStream os = new FileOutputStream(file);
            ZipOutputStream zos = new ZipOutputStream(os);

            for (Map<String, Object> map : lista) {

                zos.putNextEntry(new ZipEntry(map.get("f_nombre")));
                zos.write(map.get("f_archivo"));
                zos.closeEntry();
            }

            zos.close();
            service.EnviarEmailArchivoComprimido(correos, file.getBytes(), pZipFile, asunto, mensaje);

            file.delete();
        } catch (Exception e) {

        }
    }// end Zippear


    def enviar_archivos_tickets_admin(Long id, Long idUsuario) {

        Tusuario u = Tusuario.findById(idUsuario);
        Tusuarios usuario_ticket = Tusuarios.findByFUsuario(u.fIdUsuario);
        List<TticketsFiles> files = TticketsFiles.findAllByFIdticket(id.toLong());

        List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
        for (TticketsFiles file : files) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("f_nombre", file.fNombre);
            map.put("f_archivo", file.fFile);
            lista.add(map);
        }


        Funciones funciones = new Funciones();

        Ttickets tickets = Ttickets.findById(id);
        String html = funciones.generador_reporte_tickets(tickets);
        String emails = funciones.GetEmailEmpleadosJQ();
        emails = emails.replace("'", "");
        // service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);
        // service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);

        if (lista.size() > 0) {
            Zippear(lista, "archivos_tickets.zip", emails.split(","), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);

        } else {
            service.EnviarEmail(emails.split(','), 'Tickets '.toUpperCase() + tickets.fTitulo.toUpperCase(), html);
        }

    }

    def cambiar_contrasena(String vieja, String nueva, Long idUsuario) {
        Tusuario u = Tusuario.findById(idUsuario);
        if (u) {
            String dato = '-1';

            // println u.fPassword;
            //   println vieja.encodeAsMD5();

            if (u.fPassword.trim() != vieja.encodeAsMD5().trim()) {
                return dato;
            } else {


                u.setfPassword(nueva.encodeAsMD5());
                u.save(failOnError: true);

                if (u.fTickets == true) {
                    Tusuarios usuario_tickets = Tusuarios.findByFUsuario(u.fIdUsuario);
                    if (usuario_tickets) {
                        usuario_tickets.setfPass(nueva.encodeAsMD5());
                        usuario_tickets.save(failOnError: true);
                    }

                }
                dato = '1';
            }

            return dato;
        }
    }


    def get_reporte_tickes_asignados(Date f1, Date f2) {
        String sql = "select\n" +
                "to_char(tk.f_idrecord,'FM000000') as f_numero,\n" +
                "COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
                "tk.f_titulo as f_titulo,\n" +
                "to_char(tk.f_fecha,'yyyy/MM/dd HH:mm:ss') as f_fecha,\n" +
                "round((select extract(epoch from age(tk.f_fecha,tk.f_fecha_asignacion))/60)::numeric*(-1),2) as f_tiempo_asignacio,\n" +
                "to_char(tk.f_fecha_vencimiento,'yyyy/MM/dd HH:mm:ss') as f_due_fecha,\n" +
                "to_char(tk.f_fecha_cerrado,'yyyy/MM/dd HH:mm:ss') as f_cierre,\n" +
                "emp.f_descripcion as f_tecnico,tk.f_descipcion as f_descipcion_ticket,\n" +
                "COALESCE(tipo.f_descripcion,'Otros') as f_tipo_espanol,\n" +
                "COALESCE(tipo.f_descripcion_ingles,'Otros') as f_tipo_ingles\n" +
                "\n" +
                "from tk.t_tickets as tk \n" +
                "inner JOIN proyectos.t_empleados as emp on emp.f_id = tk.f_idtecnicoasignado\n" +
                "INNER JOIN t_clientes as c on c.f_id = tk.f_idcliente  \n " +
                "LEFT JOIN tk.t_tipo_tikets as tipo on tipo.f_idrecord = tk.f_tipo_tk\n" +
                "\n" +
                "where\n" +
                "  1=1 ";

        if (!f1.is(null) && !f2.is(null)) {
            sql = sql + " and  tk.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
        } else {
            if (!f1.is(null)) {
                sql = sql + " and  tk.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date ";
            } else {
                if (!f2.is(null)) {
                    sql = sql + " and  tk.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date ";
                }
            }
        }

        sql = sql + "  order by tk.f_idrecord asc ";


        return sqlService.GetQuery(sql);

    }


    def get_reporte_tickes_performace(Date f1, Date f2) {
        String sql = "" +
                "SELECT \n" +
                "dato.f_fecha,\n" +
                "dato.f_tecnico,\n" +
                "sum(dato.f_error) as f_error,\n" +
                "sum(dato.f_peticion) as f_peticion,\n" +
                "round(sum(dato.f_error::NUMERIC)/(sum(dato.f_error::NUMERIC)+sum(dato.f_peticion::NUMERIC)),2) as f_porciento_error,\n" +
                "round(sum(dato.f_peticion::NUMERIC)/(sum(dato.f_error::NUMERIC)+sum(dato.f_peticion::NUMERIC)),2) as f_porciento_peticion,\n" +
                "sum(dato.f_tickets_cerrados::NUMERIC) as f_tickets_cerrados,\n" +
                "sum(dato.f_horas_cerrados::NUMERIC) as f_horas_cerrados,\n" +
                "round(sum(dato.f_horas_cerrados::NUMERIC)/sum(dato.f_tickets_cerrados::NUMERIC),2)::NUMERIC*100 f_performace\n" +
                "FROM\n" +
                "\n" +
                "(" +
                "select\n" +
                "to_char(t.f_fecha::date,'yyyy/MM/dd') as f_fecha,\n" +
                "emp.f_descripcion as f_tecnico,\n" +
                "c.f_nombre_empresa,\n" +
                "tk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,1,t.f_idcliente)\n" +
                "as  f_error,\n" +
                "tk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,2,t.f_idcliente) \n" +
                "as f_peticion,\n" +
                "\n" +
                "round((\n" +
                "tk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,1,t.f_idcliente)::NUMERIC/\n" +
                "(\n" +
                "\ttk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,1,t.f_idcliente)::NUMERIC+\n" +
                "\ttk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,2,t.f_idcliente)::NUMERIC\n" +
                ")\n" +
                ")::NUMERIC ,2)*100\n" +
                "as f_porciento_error,\n" +
                "round((\n" +
                "tk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,2,t.f_idcliente)::NUMERIC/\n" +
                "(\n" +
                "\ttk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,1,t.f_idcliente)::NUMERIC+\n" +
                "\ttk.get_cantidad_ticket_prioridad(t.f_idtecnicoasignado,t.f_fecha::date,2,t.f_idcliente)::NUMERIC\n" +
                ")\n" +
                ")::NUMERIC,2)*100\n" +
                "as f_porciento_peticion,\n" +
                "round(tk.get_ticket_cerrados(t.f_idtecnicoasignado,t.f_fecha::date,t.f_idcliente),2) as  f_tickets_cerrados,\n" +
                "round(tk.get_cerrados_a_tiempo(t.f_idtecnicoasignado,t.f_fecha::date,t.f_idcliente),2) as  f_horas_cerrados,\n" +
                "\n" +
                "CASE \n" +
                "WHEN tk.get_cerrados_a_tiempo(t.f_idtecnicoasignado,t.f_fecha::date,t.f_idcliente)::NUMERIC > 0 \n" +
                "THEN\n" +
                "round((\n" +
                "tk.get_cerrados_a_tiempo(t.f_idtecnicoasignado,t.f_fecha::date,t.f_idcliente)::NUMERIC/\n" +
                "tk.get_ticket_cerrados(t.f_idtecnicoasignado,t.f_fecha::date,t.f_idcliente)::NUMERIC\n" +
                "\n" +
                ")::NUMERIC,2)*100\n" +
                "ELSE\n" +
                "0 \n" +
                "END \n" +
                "as f_performace\n" +
                "from tk.t_tickets as t \n" +
                "inner JOIN proyectos.t_empleados as emp on emp.f_id = t.f_idtecnicoasignado\n" +
                "INNER JOIN t_clientes as c on (c.f_id = t.f_idcliente)\n" +
                "\n" +
                "    where  1=1--t.f_fecha::date BETWEEN ''::date and ''::date\n ";

        if (!f1.is(null) && !f2.is(null)) {
            sql = sql + " and  t.f_fecha::date BETWEEN '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date and '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date  ";
        } else {
            if (!f1.is(null)) {
                sql = sql + " and  t.f_fecha::date >= '${funciones.formatDate(f1,"yyyy/MM/dd")}'::date ";
            } else {
                if (!f2.is(null)) {
                    sql = sql + " and  t.f_fecha::date<= '${funciones.formatDate(f2,"yyyy/MM/dd")}'::date ";
                }
            }
        }

        sql = sql + "    GROUP by f_fecha::date,\n" +
                "    f_tecnico,\n" +
                "    f_nombre_empresa,\n" +
                "    f_error,\n" +
                "    f_peticion,\n" +
                "    f_porciento_error,\n" +
                "    f_tickets_cerrados,\n" +
                "    f_horas_cerrados,\n" +
                "    f_performace\n" +
                "    order by f_fecha::date ASC\n" +
                "  ) as dato  GROUP BY dato.f_fecha,dato.f_tecnico" +
                "";

        //  "tk.f_asignado = true and tk.f_cerrado =false " ;

        println sql;


        return sqlService.GetQuery(sql);

    }

    def serviceMethod() {

    }
}
