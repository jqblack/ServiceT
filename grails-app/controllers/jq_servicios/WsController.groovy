package jq_servicios

import FuncionesGenerales.Funciones
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import publico.Tfacturas
import publico.TserialFormula
import publico.TserviciosContratados
import publico.TservidoresActualizaciones
import publico.Tusuario

class WsController {

    SqlService sqlService;
    SeguridadService seguridadService;
    TicketService ticketService;
    PublicoService publicoService;

    def Boolean validar_sesion() {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session
        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        if (user != null) {
            return true
        } else {
            return false;
        }
    }


    def index()
    {
        if (validar_sesion())
        {
            Tusuario tusuario = seguridadService.GetUsuarioSession();

            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/FrmIndex',model: [tusuario:tusuario])
        }
        else
        {
            redirect(uri: '/login');
        }
    }

    def login() {

        sqlService.GetNow();
        if (validar_sesion()) {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            Tusuario usr = session.getAttribute("Usuario") as Tusuario;
            if (usr.fTickets)
            {
                redirect(uri: '/index_tickets');
            }
            else {
                redirect(uri: '/index');
            }
        } else {
            render(view: '/layouts/template/FrmLogin')
        }
    }


    def iniciar_sesion() {

        if (validar_sesion()) {
            redirect(uri: '/index');
        } else {


            Tusuario tusuario = seguridadService.getUsuarios(params.user.toString(), params.pass.toString());
            if (tusuario) {

                List lista_tickets = ticketService.ListarTodosTicketsNoCancelados(null,null,'','');
                GrailsWebRequest request = RequestContextHolder.currentRequestAttributes();
                GrailsHttpSession session = request.session;
                session.setAttribute("Usuario", tusuario);
                session.setAttribute("idioma",'es');
                session.setAttribute("lista_tickets",lista_tickets);
                session.setAttribute("Tpreferencia",seguridadService.GetPreferencias());


                List<Map<String,java.lang.Object>> lista_permisos = seguridadService.GetPermisosUsuarios(tusuario.id);
                session.setAttribute("permisos",lista_permisos);

                if (tusuario.fTickets)
                {
                    redirect(uri: "/index_tickets");
                }else {
                    redirect(uri: "/index");
                }
            } else {
                redirect(uri: "/login")
            }


        }
    }

    def logout() {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        session.setAttribute("Usuario", null);
        redirect(uri: "/login")
    }

    def clientes() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutClientes')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_clientes()
    {
        if (validar_sesion()) {
            render publicoService.ListarClientes() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def generar_factura_clientes()
    {
        if (validar_sesion()) {

            publicoService.gerenar_factura_cliente(params.idcliente.toString());

            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

    def get_clientes_activos_no_activos()
    {
        if (validar_sesion()) {
            render publicoService.ListarClientesActivos(params.activo.toString().toBoolean()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def get_clientes_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarClientesById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_cliente()
    {
        if (validar_sesion())
        {
            publicoService.salvar_cliente(
                    params.idrecord.toString(),
                    params.nombre.toString(),
                    params.contacto.toString(),
                    params.email.toString(),
                    params.identificacion.toString(),
                    params.direccion.toString(),
                    params.ciudad.toString(),
                    params.estado.toString(),
                    params.pais.toString(),
                    params.zip_code.toString(),
                    params.telefono_1.toString(),
                    params.telefono_2.toString(),
                    params.telefono_contacto.toString(),
                    params.fax.toString(),
                    params.contacto_cobros.toString(),
                    params.envia_email.toString().toBoolean(),
                    params.retiene_isr.toString().toBoolean(),
                    params.retiene_itebis.toString().toBoolean(),
                    params.facturar_tax.toString().toBoolean(),
                    params.es_empresa.toString().toBoolean(),
                    params.moneda.toString().toLong()
            );
            render 'true';
        }else {



            redirect(uri: '/login');
        }
    }


    def productos() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutProductos')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_productos_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarProductosById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_productos()
    {
        if (validar_sesion())
        {
            publicoService.salvar_producto(
                    params.idrecord.toString(),
                    params.referencia.toString(),
                    params.descripcion.toString(),
                    params.version.toString(),
                    params.sistema.toString(),
                    params.precio.toString().toBigDecimal(),
                    params.cantidad.toString().toInteger(),
                    params.servicio.toString().toBoolean(),
                    params.genera_serial.toString().toBoolean(),
                    params.tiene_itbis.toString().toBoolean(),
                    params.activo.toString().toBoolean()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def get_productos()
    {
        if (validar_sesion()) {
            render publicoService.ListarProductos() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }
    def get_productos_activos_no_activos()
    {
        if (validar_sesion()) {
            render publicoService.ListarProductosActivos(params.activo.toString().toBoolean()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def referentes() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutReferentes')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_referentes()
    {
        if (validar_sesion()) {
            render publicoService.ListarReferentes() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }
    def get_referentes_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarReferentesbyId(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_referentes()
    {
        if (validar_sesion())
        {
            publicoService.salvar_referente(
                    params.idrecord.toString(),
                    params.nombre.toString(),
                    params.apellido.toString(),
                    params.email.toString(),
                    params.cuenta_deposito.toString(),
                    params.direccion.toString(),
                    params.ciudad.toString(),
                    params.pais.toString(),
                    params.telefono1.toString(),
                    params.telefono2.toString(),
                    params.porciento.toString().toBigDecimal(),
                    params.banco.toString()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def usuarios() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutUsuarios')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_usuarios()
    {
        if (validar_sesion()) {
            render publicoService.ListarUsuarios() as JSON;
        } else {
            redirect(uri: '/login');
        }

    }


    def get_usuarios_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarUsuariosById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }


    def get_validar_usuarios()
    {
        if (validar_sesion()) {
            List list =  publicoService.ListarUsuariosByIdusuario(params.usuario.toString()) ;
            if (list.size()>0)
            {
                render '1'
            }else{
                render '0';
            }

        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_usuarios()
    {
        if (validar_sesion())
        {
            publicoService.salvar_usuario(
                    params.idrecord.toString(),
                    params.idempleado.toString().toLong(),
                    params.nombre.toString(),
                    params.apellido.toString(),
                    params.email.toString(),
                    params.telefono.toString(),
                    params.usuario.toString(),
                    params.password.toString(),
                    params.direccion.toString(),
                    params.activo.toString().toBoolean(),
                    params.fecha.toString()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def salvar_permisos_usuarios()
    {
        if (validar_sesion())
        {
            publicoService.salvar_permisos_usuario(
                    params.idrecord.toString().toLong(),
                    params.opciones.toString()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def get_permisos_usuarios()
    {
        if (validar_sesion()) {

            render publicoService.ListarPermisosUsuariosById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def seriales_formula() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutSerialesFormula')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_seriales_formulas()
    {
        if (validar_sesion()) {
            render publicoService.ListarSerialesFormula() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def get_seriales_formulas_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarSerialesFormulaById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_serial_formula()
    {
        if (validar_sesion()) {
            publicoService.salvarSerialFormula(
                    params.idrecord.toString(),
                    params.serial.toString(),
                    params.cliente.toString(),
                    params.imei.toString(),
                    params.activo.toString().toBoolean(),
                    params.server.toString(),
                    params.tipo.toString().toString().toLong()
            );
            render 'true';
        } else {
            redirect(uri: '/login');
        }
    }

    def get_serial_formula(String dato)
    {
        TserialFormula serial = TserialFormula.findByFImeiAndFActivo(dato,true);
        if (serial)
        {
            render '1';
        }
        else {
            render '0';
        }
    }

    def distribucion_correos()
    {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmInDistribucionCorreos');
        } else {
            redirect(uri: '/login');
        }
    }

    def get_distribucion_correos_by_cliente()
    {
        if (validar_sesion()) {

            List lista =  publicoService.ListarDistribucionCorreosClientes(params.idrecord.toLong()) ;
            render lista as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def  salvar_distribucion_correos()
    {
        if (validar_sesion()) {
            publicoService.salvar_distribucion_correos(
                    params.idrecord.toString().toLong(),
                    params.correos.toString()
            );

            render 'true';
        } else {
            redirect(uri: '/login');
        }
    }

    def usuarios_tickets()
    {
        if (validar_sesion()) {
            List lista_cliente = publicoService.ListarClientes();

            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/registros/FrmOutUsuariosTickets',model: [lista_cliente:lista_cliente]);
        } else {
            redirect(uri: '/login');
        }
    }

    def get_usuarios_tickets()
    {
        if (validar_sesion())
        {
            List list =  publicoService.GetUsuarioTickets();
            render list as JSON;
        }else
        {
            redirect(uri: '/login');
        }
    }

    def get_usuarios_tickets_by_id()
    {
        if (validar_sesion())
        {
            List list =  publicoService.GetUsuarioTicketsById(params.idrecord.toString().toLong());
            render list as JSON;
        }else
        {
            redirect(uri: '/login');
        }
    }

    def salvar_usuario_tickets()
    {
        if (validar_sesion())
        {
            publicoService.salvar_usuario_tickets(
                    params.idrecord.toString(),
                    params.cliente.toString().toLong(),
                    params.nombre.toString(),
                    params.apellido.toString(),
                    params.email.toString(),
                    params.usuario.toString(),
                    params.password.toString(),
                    params.activo.toString().toBoolean()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def get_enviar_estado_cuenta_con_correo()
    {
        if (validar_sesion()) {

            String r =   publicoService.get_enviar_estado_cuenta_con_correo(params.idrecord.toString().toLong(),params.email.toString());
            render r;
        } else {
            redirect(uri: '/login');
        }
    }

    def recibos() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmInRecibos');
        } else {
            redirect(uri: '/login');
        }
    }

    def get_reporte_estado_cuenta_by_id() {
        if (validar_sesion()) {

            render publicoService.VerReporteEstadoCuentaByIdBase64(params.idrecord.toString().toLong());
        } else {
            redirect(uri: '/login');
        }
    }

    def pagos_generales() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmPagosGenerales');
        } else {
            redirect(uri: '/login');
        }
    }

    def get_facturas_pagos_generales() {
        if (validar_sesion()) {
            render publicoService.ListarFactutrasPagosGenerales() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_pagos_generales() {
        if (validar_sesion()) {
            publicoService.salvar_pagos_generales(params.json.toString(),params.tipo_moneda_recibo.toString());
            render true;
        } else {
            redirect(uri: '/login');
        }
    }

    def get_enviar_factura_cliente() {
        if (validar_sesion()) {

            String r = publicoService.enviarFacturaClienteString(params.idrecord.toString().toLong(),params.idcliente.toString().toLong());
            render r;
        } else {
            redirect(uri: '/login');
        }
    }

    def nota_credito() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmInNotaDeCredito');
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_nota_credito()
    {
        if (validar_sesion()) {
            publicoService.salvar_nota_credito(params.recibo.toString(),params.detalle.toString())

            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

    def get_facturas_cxc_by_id()
    {

        if (validar_sesion()) {
            render  publicoService.ListarCxCPorClientes(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def nota_debito() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmInNotaDebito');
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_nota_debito()
    {
        if (validar_sesion()) {
            publicoService.salvar_nota_debito(params.idrecord.toString().toLong(),params.monto.toString().toBigDecimal(),params.fecha.toString(),params.comentario.toString())

            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

    def servicios_contratados() {
        if (validar_sesion()) {
            List lista_servidores = TservidoresActualizaciones.findAll();

            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmOutServiciosContrados',model: [lista_servidores:lista_servidores,hola:3])
        } else {
            redirect(uri: '/login');
        }
    }

    def get_servicios_contratados()
    {
        if (validar_sesion()) {
            render publicoService.ListarServiciosContratados() as JSON;
        } else {
            redirect(uri: '/login');
        }

    }
    def get_servicios_contratados_activos()
    {
        if (validar_sesion()) {
            render publicoService.ListarServiciosContratadosActivos(params.activo.toString().toBoolean()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def get_servicios_contratados_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarServiciosContratadosById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_servicios_contratados()
    {
        if (validar_sesion())
        {
            publicoService.salvar_servicios_contratdos(
                    params.idrecord.toString(),
                    params.idprodcuto.toString().toLong(),
                    params.idcliente.toString().toLong(),
                    params.precio.toString().toBigDecimal(),
                    params.dias.toString().toLong(),
                    params.activo.toString().toBoolean(),
                    params.key.toString(),
                    params.enviar.toString().toBoolean(),
                    params.idreferente.toString().toLong(),
                    params.monto_comision.toString().toBigDecimal(),
                    params.cantidad.toString().toBigDecimal(),
                    params.itebis.toString().toBigDecimal(),
                    params.variable.toString().toBoolean(),
                    params.porciento_comision.toString().toBigDecimal(),
                    params.cobrable.toString().toBoolean(),
                    params.servidor?params.servidor.toString().toLong():0

            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def mensajes() {
        if (validar_sesion()) {

            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmInOutMensajes');
        } else {
            redirect(uri: '/login');
        }
    }

    def get_mensajes()
    {
        if (validar_sesion()) {
            render publicoService.ListarMensajes() as JSON;
        } else {
            redirect(uri: '/login');
        }

    }
    def get_mensajes_by_id()
    {
        if (validar_sesion()) {
            render publicoService.ListarMensajesById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_mensajes()
    {
        if (validar_sesion()) {
            publicoService.salvar_mensajes(params.idrecord.toString(),
                    params.idcliente.toString().toLong(),
                    params.mensaje.toString(),
                    params.activo.toString().toBoolean(),
                    params.tipo.toString().toLong())

            render 'true';
        } else {
            redirect(uri: '/login');
        }
    }

    def desactivar_todo_los_mensajes(){
        if (validar_sesion()) {
            render  publicoService.DesactivarMensajesTodos();
        } else {
            redirect(uri: '/login');
        }
    }

    def facturas() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmInFacturas')
        } else {
            redirect(uri: '/login');
        }
    }
    def ver_facturas() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmOutVerFacturas')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_enviar_facturas_con_correo()
    {
        if (validar_sesion()) {

            String r =   publicoService.enviarFacturaClienteString(params.idrecord.toString().toLong(),params.email.toString());
            render r;
        } else {
            redirect(uri: '/login');
        }
    }

    def get_ver_facturas() {
        if (validar_sesion()) {
            render publicoService.ListarVerFacturas(params.parametro.toString(),params.f1.toString(),params.f2.toString()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def duplicar_factura()
    {
        if (validar_sesion()) {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            Tusuario user = session.getAttribute("Usuario") as Tusuario;
            try {
                String id = publicoService.duplicar_factura(user,params.idrecord.toString()).toString() ;
                render id.toString();
            }catch (Exception  e)
            {
                println e.getMessage()+" mensaje";
                render "-1"
            }
        } else {
            redirect(uri: '/login');
        }
    }

    def get_reporte_factura_by_id() {
        if (validar_sesion()) {

            render publicoService.VerReporteFacturaByIdBase64(params.idrecord.toString().toLong());
        } else {
            redirect(uri: '/login');
        }
    }

    def out_reporte_recibos() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmOutRecibos');
        } else {
            redirect(uri: '/login');
        }
    }

    def get_out_recibos()
    {

        if (validar_sesion()) {
            render  publicoService.ListarRecibos(params.parametro.toString(),params.f1.toString(),params.f2.toString()) as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def dashboard()
    {
        if (validar_sesion())
        {

            Long activos = TserviciosContratados.findAllByFActivo( true).size();
            Long total_clientes = TserviciosContratados.findAll().size();
            List lista_clientes = publicoService.getClientesDashboard();

            Funciones funciones = new Funciones()

            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/procesos/FrmOutDasboard',model: [
                    activos:activos,
                    total_clientes:total_clientes,
                    lista_clientes:lista_clientes,
                    seguridadService:seguridadService,
                    funciones:funciones
            ]);
        }
        else
        {
            redirect(uri: '/login');
        }
    }

    def get_utima_factura() {
        if (validar_sesion()) {

//            String ultimo_id = Tfacturas.findAllByFIdCliente(params.idcliente as Long,[sort:"fNofactura", order:"asc"]).last().id;

            List<Tfacturas> tfacturasList = Tfacturas.findAllByFIdCliente(params.idcliente as Long,[sort:"fNofactura", order:"asc"]);
            String ultimo_id = "0";

            if (tfacturasList.size() > 0){
                ultimo_id = Tfacturas.findAllByFIdCliente(params.idcliente as Long,[sort:"fNofactura", order:"asc"]).last().id;
            }

            render publicoService.VerReporteFacturaByIdBase64(ultimo_id as Long);
        } else {
            redirect(uri: '/login');
        }
    }

    def set_desactivar_servicio_contratado()
    {
        if (validar_sesion()) {
            publicoService.DesactivarServicioContratado(
                    params.idrecord as Long
            );
            render 'true';
        } else {
            redirect(uri: '/login');
        }
    }

    def set_mensajes_clientes(){
        if (validar_sesion()) {
            publicoService.SetMensajesClientes(
                    params.ids as String,
                    params.mensaje as String,
                    params.tipo as Long,
                    params.activo.toString().toBoolean(),

            );
            render 'true';
        } else {
            redirect(uri: '/login');
        }
    }

    def cancelar_factura(){
        if (seguridadService.GetUsuarioSession().fTickets)
            redirect(uri: "/index_tickets");
        else
        render view: "/layouts/template/procesos/FrmCancelarFactura";
    }


    def get_factura_cancelar()
    {
        String numero = params.numero.toString().isNumber()?params.numero.toString():"0";
        render publicoService.getFacturaCancelar(numero) as JSON;
    }
    def get_cancelar_factura()
    {
        render publicoService.get_cancelar_factura(params.numero as String)
    }

    def empleados() {
        if (validar_sesion()) {
            if (seguridadService.GetUsuarioSession().fTickets)
                redirect(uri: "/index_tickets");
            else
            render(view: '/layouts/template/proyectos/FrmOutEmpleados')
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_recibo()
    {
        if (validar_sesion()) {
            publicoService.salvar_recibo(params.recibo.toString(),params.detalle.toString())

            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_facturas()
    {
        if (validar_sesion()) {
            String r =  publicoService.salvar_factura(params.factura.toString(),params.detalle.toString())

            render r;
        } else {
            redirect(uri: '/login');
        }

    }
}
