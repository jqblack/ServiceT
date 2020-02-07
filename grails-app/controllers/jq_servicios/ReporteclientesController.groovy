package jq_servicios

import clientes.Tarchivoordenes
import clientes.Tstatus
import clientes.Tusuarioclientes
import grails.converters.JSON
import groovy.json.JsonOutput

class ReporteclientesController {

    ReporteclientesService reporteclientesService;
    def index() { }

    Boolean validarsession(){
        if (session.getAttribute("clientelogin")){
            return true;
        }
        else{
            return false;
        }
    }

    def facturasclientes(){
        if (validarsession()){
            render view:"/layouts/template/clientefacturas/resportefactura";
        }
        else{
            redirect(uri: "/logincliente");
        }
    }

    def get_ver_facturas() {
        if (validarsession()) {
            Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");

            List list = reporteclientesService.ListarVerFacturas(params.f1.toString(),
                    params.f2.toString(),
                    tusuarioclientes.fUsername,
                    params.tipobusqueda as Long);

            render JsonOutput.toJson(list);
        } else {
            redirect(uri:"/logincliente")
        }
    }

    def get_reporte_factura_by_id() {
        if (validarsession()) {

            render reporteclientesService.VerReporteFacturaByIdBase64(params.idrecord.toString().toLong());
        } else {
            redirect(uri:"/logincliente")
        }
    }

    def get_enviar_factura_cliente() {
        if (validarsession()) {

            String r = reporteclientesService.enviarFacturaClienteString(params.idrecord.toString().toLong(),params.idcliente.toString().toLong());
            render r;
        } else {
            redirect(uri:"/logincliente")
        }
    }


    def get_reporte_estado_cuenta_by_id() {
        if (validarsession()) {
            Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");
            render reporteclientesService.VerReporteEstadoCuentaByIdBase64(tusuarioclientes.fUsername);
        } else {
            redirect(uri:"/logincliente")
        }
    }

    def get_enviar_estado_cuenta_con_correo()
    {
        if (validarsession()) {

            Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");
            String r =   reporteclientesService.get_enviar_estado_cuenta_con_correo(tusuarioclientes.fUsername,params.email.toString());
            render r;
        } else {
            redirect(uri:"/logincliente")
        }
    }


    def ordenesclientes(){
        if (validarsession()){
            List<Tstatus> tstatusList = Tstatus.findAll();
            render view:"/layouts/template/clientefacturas/reporteOrdenesClientes", model: [listsatus:tstatusList];
        }
        else{
            redirect(uri: "/logincliente");
        }
    }

    def get_ver_ordenesclientes(){
        if (validarsession()) {
            Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");

            List list = reporteclientesService.get_ver_ordenesclientes(params.f1.toString(),
                    params.f2.toString(),
                    tusuarioclientes.id,
                    params.tipobusqueda as Long);

            render JsonOutput.toJson(list);
        } else {
            redirect(uri:"/logincliente")
        }
    }

    def get_archivos_ordenes(){
        if (validarsession()) {

            List list = reporteclientesService.get_archivos_ordenes(params.idrecord as Long)

            render JsonOutput.toJson(list);
        } else {
            redirect(uri:"/logincliente")
        }
    }
    def getDescargarArchivo(){

       Tarchivoordenes archivo = reporteclientesService.getArchivo(params.id as Long);
        render(file: archivo.fArchivo, fileName: archivo.fNamearchivo,contentType: "application/*");
    }
}
