package jq_servicios

import FuncionesGenerales.Funciones
import FuncionesGenerales.Grails
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.MessageSource
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import proyecto.Templeados
import publico.Tpreferencia
import publico.Tusuario
import tk.TticketsUpdates
import tk.Tusuarios

import java.sql.Timestamp

class TicketsController {

    def index() { }

    TicketService ticketService;
    SeguridadService seguridadService;
    Funciones funciones = new Funciones();

    def Boolean validar_sesion() {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        if (user != null) {
            return true
        } else {
            return false;
        }
    }

    def index_tickets()
    {
        if (validar_sesion())
        {
            Grails grails = new Grails();
            session.setAttribute("Grails",grails);

            Locale locale = new Locale(session.getAttribute("idioma") as String)
            session.setAttribute("locale",locale);

            Tpreferencia tpreferencia = seguridadService.GetPreferencias();
            session.setAttribute("tpreferencia",tpreferencia);

            Tusuario tusuario = seguridadService.GetUsuarioSession()
            session.setAttribute("tusuario",tusuario);
            render(view: '/layouts/template/FrmIndexTickets',)
        }
        else
        {
            redirect(uri: '/login');
        }
    }

    def asignar_tickets() {

        if (validar_sesion()) {

            def list_empleados = Templeados.findAllByFActivo(true);
            List lista_prioridades = ticketService.ListarPrioridadesTickets();
            List lista_tipo_tickets = ticketService.ListarTipoTickets();
            List ls = ticketService.GetTiketsById(params.idt.toString().toLong());
            List lista_update = ticketService.ListarTicketsUpdate(params.idt.toString().toLong());

            render(view: '/layouts/template/tickets/FrmAsignarTickets',model: [list_empleados:list_empleados,lista_prioridades:lista_prioridades,
                                                                               lista_tipo_tickets:lista_tipo_tickets,tickets_service:ticketService,ls:ls,
                                                                               lista_update:lista_update])
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_asignacion_tickets() {

        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        Long id = ticketService.salvar_asignacion(params.f_nota.toString(),
                params.f_idticket.toString().toLong(),
                params.f_idprioridad.toString().toLong(),
                params.f_idtecnico.toString().toLong(),
                user.id,
                params.f_broadcast.toString().toBoolean(),
                params.f_tipo.toString().toLong()
        );
        render '1';
    }

    def salvar_cancelacion_tickets() {

        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        Long id = ticketService.salvar_cancelacion_tickets(params.f_nota.toString(),
                params.f_idticket.toString().toLong(),
                user.id
        );
        render '1';
    }

    def tickets_sin_asignar() {

        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';

            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }

            List lista_tickets = ticketService.ListarTodosTicketsNoCancelados(f1,f2,titulo,numero_ticket);

            render(view: '/layouts/template/tickets/FrmTicketsSinAsignar',model: [lista_tickets:lista_tickets,funciones:funciones])

        } else {
            redirect(uri: '/login');
        }
    }

    def reasignar_tickets() {

        if (validar_sesion()) {
            def list_empleados = Templeados.findAllByFActivo(true);
            List lista_prioridades = ticketService.ListarPrioridadesTickets();
            render(view: '/layouts/template/tickets/FrmReasignarTickets',model: [list_empleados:list_empleados,lista_prioridades:lista_prioridades, ticketService:ticketService])
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_reasignacion_tickets() {

        Tusuario user = session.getAttribute("Usuario") as Tusuario;

        Long id = ticketService.salvar_reasignacion_tickets(
                params.f_nota.toString(),
                params.f_idticket.toString().toLong(),
                params.f_idprioridad.toString().toLong(),
                params.f_idtecnico.toString().toLong(),
                user.id,
                params.f_fecha.toString()

        );

        render '1';
    }

    def tickets_asignados() {

        if (validar_sesion()) {
            //  List lista_prioridades = ticketService.ListarPrioridadesTickets();

            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';

            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }

            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }

            List lista_tickets = ticketService.ListarTodosTicketsAsignados(f1,f2,titulo,numero_ticket);
            render(view: '/layouts/template/tickets/FrmTicketsAsignados',model: [lista_tickets:lista_tickets,funciones: funciones])
        } else {
            redirect(uri: '/login');
        }
    }

    def actualizar_tickets() {

        if (validar_sesion()) {
            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';


//            Funciones funciones = new Funciones();
//            funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));

            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }

            List lista_tickets = ticketService.ListarTodosTicketsAsignados(f1,f2,titulo,numero_ticket);
            render(view: '/layouts/template/tickets/FrmActualizacionesTicketsAsignados',model: [lista_tickets:lista_tickets,funciones: funciones])
        } else {
            redirect(uri: '/login');
        }
    }

    def get_update_by_id()
    {
        if (validar_sesion()) {
            List list =ticketService.ListarTicketsUpdate(params.idrecord.toString().toLong());
//            println(list);
            render list as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_archivo_comentario () {
        MultipartFile file = (MultipartFile) params.archivo;
        ticketService.salvar_archivo_comentario(file.getBytes());
    }

    def cerrar_ticket() {

        if (validar_sesion()) {

            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            ticketService.cerrar_tickets(
                    params.f_idticket.toString().toLong(),
                    user.id,
                    params.f_nota.toString(),
                    params.f_horas.toString().toBigDecimal()

            );

            // ticketService.prueba();
            render '1';
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_comentario_clientes() {


        if (validar_sesion()) {

            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            ticketService.salvar_comentario_clientes(
                    params.f_idticket.toString().toLong(),
                    user.id,
                    params.f_nota.toString(),
                    params.f_nombre_archivo.toString(),
                    params.f_extencion.toString()

            );

            // ticketService.prueba();
            render '1';
        } else {
            redirect(uri: '/login');
        }
    }

    def descargarUpdate()
    {
        TticketsUpdates t = TticketsUpdates.findById(params.idrecord.toString().toLong());
        //decargarArchivo(t.fNombreArchivo,t.fExtencionArchivo,t.fPicture);
        response.setContentType("APPLICATION/OCTET-STREAM")
        response.setHeader("Content-Disposition", "Attachment;Filename=\"${t.fNombreArchivo}${t.fExtencionArchivo}\"");
        def temp = File.createTempFile(t.fNombreArchivo,t.fExtencionArchivo);
        FileOutputStream fileOuputStream = new FileOutputStream(temp);
        fileOuputStream.write(t.fPicture);


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

    def salvar_comentario() {

        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        ticketService.salvar_comentario(
                params.f_idticket.toString().toLong(),
                user.id,
                params.f_nota.toString(),
                params.f_horas.toString().toBigDecimal(),
                params.f_nombre_archivo.toString(),
                params.f_extencion.toString()

        );

        render '1';
    }

    def crear_ticket_admin() {

        Tusuario tusuario = seguridadService.GetUsuarioSession();
        List<Map<String,Object>> proyectos = seguridadService.GetProyectos();
//println(proyectos)
        if (validar_sesion()) {
            List lista_prioridades = ticketService.ListarPrioridadesTickets();
            List lista_tipo_tickets = ticketService.ListarTipoTickets();
            render(view: '/layouts/template/tickets/FrmInTicketAdm',model: [lista_prioridades:lista_prioridades,lista_tipo_tickets:lista_tipo_tickets,
                                                                            tusuario:tusuario, proyectos:proyectos])
        } else {
            redirect(uri: '/login');
        }
    }

    def ticket() {

        if (validar_sesion()) {
            List lista_prioridades = ticketService.ListarPrioridadesTickets();
            List lista_tipo_tickets = ticketService.ListarTipoTickets();
            Locale locale = new Locale(session.getAttribute("idioma"));
            Tusuario tusuario = seguridadService.GetUsuarioSession();
            List<Map<String,Object>> proyectos = seguridadService.GetProyectosUsuario(tusuario.id);
            render(view: '/layouts/template/tickets/FrmInTicket',model: [lista_prioridades:lista_prioridades,lista_tipo_tickets:lista_tipo_tickets,locale:locale,proyectos:proyectos])
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_ticket_admin() {

        if (validar_sesion()) {
            String id = ticketService.salvar_ticket_admin(params.f_titulo.toString(),
                    params.f_idusuario.toString().toLong(),
                    params.f_prioridad.toString().toLong(),
                    params.f_descripcion.toString(),
                    params.f_proyecto.toString().toLong(),
                    params.f_json.toString(),
                    params.f_broadcast.toString().toBoolean(),
                    params.f_tipo_tk.toString().toLong()
            );

            //ticketService.prueba();
            render id;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_imagen_ticket_admin() {
        MultipartFile file = (MultipartFile) params.archivo;
        ticketService.salvar_ticket_archivo_admin(file.getBytes());
    }

    def enviar_archivos_tickets_admin() {

        if (validar_sesion()) {
            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            ticketService.enviar_archivos_tickets_admin(params.idrecord.toString().toLong(),user.id);
            render "true";
        } else {
            redirect(uri: '/login');
        }
    }

    def ver_archivos_tickets()
    {
        if (validar_sesion()) {
            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';
            Funciones funciones = new Funciones();
            //  funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));
            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }
            Boolean cerrado =false;

            if (!params.cerrado.is(null) && !params.cerrado.toString().equals(""))
            {
                cerrado = params.cerrado.toString();
            }

            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            Tusuarios usuario_ticket = Tusuarios.findByFUsuario(user.fIdUsuario);

            List list = ticketService.ListarTodosTickets(f1,f2,titulo,cerrado,numero_ticket);

            Locale locale = new Locale(session.getAttribute("idioma") as String)

            Grails grails = new Grails();

            render(view: '/layouts/template/tickets/FrmVerArchivosTickets',model: [list_tickets:list, funciones: funciones,Grails:grails,locale:locale]);

        } else {
            redirect(uri: '/login');
        }

    }

    def get_archivos_by_idtickets()
    {
        if (validar_sesion()) {

            List list =ticketService.get_archivos_by_idtickets(params.idrecord.toString().toLong());
            render list as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def tickets_asignados_report()
    {
        if (validar_sesion())
        {
            render(view: '/layouts/template/tickets/FrmOutTicketsAsignados',model: [funciones: funciones])
        }
        else
        {
            redirect(uri: '/login');
        }
    }

    def get_reporte_tickes_asignados()
    {
        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;
            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            List list =ticketService.get_reporte_tickes_asignados(f1,f2);
            render list as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def tickets_performace_report()
    {
        if (validar_sesion())
        {
            render(view: '/layouts/template/tickets/FrmOutTicketsPerformace',model: [funciones: funciones])
        }
        else
        {
            redirect(uri: '/login');
        }
    }

    def get_reporte_tickes_performace()
    {
        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;
            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            List list =ticketService.get_reporte_tickes_performace(f1,f2);
            render list as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def cambiar_idioma() {

        if (validar_sesion()) {

            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
            GrailsHttpSession session = request.session;

            session.setAttribute("idioma",params.idioma.toString());
            session.locale = new Locale(params.idioma.toString());

            render true;

        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_imagen_ticket() {
        MultipartFile file = (MultipartFile) params.archivo;

        ticketService.salvar_ticket_archivo(file.getBytes());
    }

    def enviar_archivos_tickets_clientes() {

        if (validar_sesion()) {
            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            ticketService.enviar_archivos_tickets_clientes(params.idrecord.toString().toLong(),user.id);
            render "true";
        } else {
            redirect(uri: '/login');
        }
    }

    def crear_ticket() {

        if (validar_sesion()) {
            String id = ticketService.crear_ticket(params.f_titulo.toString(),
                    params.f_idusuario.toString().toLong(),
                    params.f_prioridad.toString().toLong(),
                    params.f_descripcion.toString(),
                    params.f_proyecto.toString().toLong(),
                    params.f_json.toString(),
                    params.f_broadcast.toString().toBoolean(),
                    params.f_tipo_tk.toString().toLong()
            );

            render id;
        } else {
            redirect(uri: '/login');
        }
    }

    def ver_tickets(){

        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';


            Funciones funciones = new Funciones();
            //  funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));

            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }

            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            Tusuarios tusuariosTickets = Tusuarios.findByFUsuario(user.fIdUsuario);


            List lista_tickets = ticketService.ListarTicketsByClientes(tusuariosTickets.fIdcliente,f1,f2,titulo,numero_ticket);
            render(view: '/layouts/template/tickets/FrmVerTickets',model: [lista_tickets:lista_tickets,funciones: funciones]);

        }
        else {
            redirect(uri: '/login');
        }
    }

    def modificar_ticket() {

        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';


            Funciones funciones = new Funciones();
            //  funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));

            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }




            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            Tusuarios tusuariosTickets = Tusuarios.findByFUsuario(user.fIdUsuario);


//            List lista_tickets = ticketService.ListarTicketsByClientesNoCancelados(tusuariosTickets.fIdcliente,f1,f2,titulo);
            List lista_tickets = ticketService.ListarTicketsByClientesNoCanceladosNoCerrados(tusuariosTickets.fIdcliente,f1,f2,titulo,numero_ticket);
            render(view: '/layouts/template/tickets/FrmModificarTicketsClientes',model: [lista_tickets:lista_tickets,funciones: funciones]);
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_archivo_update_clientes () {
        MultipartFile file = (MultipartFile) params.archivo;
        ticketService.salvar_archivo_comentario_clientes(file.getBytes());
    }

    def cerrar_ticket_clientes() {

        if (validar_sesion()) {

            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            ticketService.cerrar_tickets_clientes(
                    params.f_idticket.toString().toLong(),
                    user.id,
                    params.f_nota.toString()

            );

            // ticketService.prueba();
            render '1';
        } else {
            redirect(uri: '/login');
        }

    }

    def ver_tickets_abiertos_cerrado()
    {
        if (validar_sesion()) {


            Date f1 = null;
            Date f2 = null;

            String titulo = '';
            String numero_ticket = '';
            Funciones funciones = new Funciones();
            //  funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));
            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }
            if (!params.numero_ticket.is(null) && !params.numero_ticket.toString().equals(""))
            {
                numero_ticket = params.numero_ticket.toString();
            }



            Boolean cerrado =false;

            if (!params.cerrado.is(null) && !params.cerrado.toString().equals(""))
            {
                cerrado = params.cerrado.toString();
            }


            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            Tusuarios usuario_ticket = Tusuarios.findByFUsuario(user.fIdUsuario);

            Long idcliente = usuario_ticket.fIdcliente;

            List list = ticketService.ListarTicketsByClientesReportesAbiertosCerrados(idcliente,f1,f2,titulo,cerrado,numero_ticket);

            render(view: '/layouts/template/tickets/FrmReportesTicketsCerradosAbiertos',model: [list_tickets:list,funciones: funciones]);

        } else {
            redirect(uri: '/login');
        }

    }

    def get_tickets_clientes_abiertos_cerrados()
    {
        if (validar_sesion()) {

            Date f1 = null;
            Date f2 = null;
            String titulo = '';
            Funciones funciones = new Funciones();
            //  funciones.generador_reporte_tickets_reasignado(Ttickets.findById(35));
            if (!params.fecha_desde.is(null) && !params.fecha_desde.toString().equals(""))
            {
                f1 = new Date(params.fecha_desde.toString()) ;
            }

            if (!params.fecha_hasta.is(null) && !params.fecha_hasta.toString().equals(""))
            {
                f2 = new Date(params.fecha_hasta.toString()) ;
            }

            if (!params.titulo.is(null) && !params.titulo.toString().equals(""))
            {
                titulo = params.titulo.toString();
            }

            List list = ticketService.ListarTicketsByClientesReportesAbiertosCerrados(params.idcliente.toString().toLong(),f1,f2,titulo,params.cerrado.toString().toBoolean());

            render list as JSON;

        } else {

            redirect(uri: '/login');
        }
    }

    def cambiar_contrasena() {

        if (validar_sesion()) {
            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            String dato = ticketService.cambiar_contrasena(params.vieja.toString(),params.nueva.toString(),user.id);
            render dato;
        } else {
            redirect(uri: '/login');
        }
    }
}
