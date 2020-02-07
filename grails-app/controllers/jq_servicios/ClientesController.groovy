package jq_servicios

import clientes.Tusuarioclientes
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

class ClientesController {

    def index() { }

    ClientesService clientesService;

    def logincliente(){
        if (!validarsession()){
            render view: "/layouts/template/clientes/loginClientes"
        }else {
            redirect(uri: "/clientejq")
        }

    }

    Boolean validarsession(){
        if (session.getAttribute("clientelogin")){
            return true;
        }
        else{
            return false;
        }
    }

    def iniciar(){

        Tusuarioclientes tusuarioclientes = clientesService.iniciar(
                params.username as Long,
                params.pass as String
        );

        if (tusuarioclientes){
            session.setAttribute("clientelogin",tusuarioclientes);
            redirect(uri: "/clientejq")
        }
        else{
            redirect(uri:"/logincliente")
        }
    }

    def clientejq(){
        if (validarsession()){
            Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");


            if (clientesService.validarbyid(tusuarioclientes.id)){
                render view: "/layouts/template/clientes/cambiofirtspass"
            }
            else{
                Map mapa = clientesService.Dash();
                render view: "/layouts/template/clientes/FrmMainCliente", model: [dash:mapa];
            }

        }else{
            redirect(uri:"/logincliente")
        }
    }

    def cambiarfirstpass(){
        if (validarsession()){
            String pass = params.pass1 as String;
            String pass2 = params.pass2 as String;
            if (pass == pass2){
                Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin")
                Tusuarioclientes tusuarioclientes1 = clientesService.cambiarfirstpass(
                        tusuarioclientes.id,
                        pass
                );
//                if(tusuarioclientes1){
//                    redirect(uri:"/clientejq")
//                }

                render true;
            }
        }
        else {
            redirect(uri:"/logincliente")
        }
    }

    def cambiarpass(){

        if (validarsession()){
            render view:"/layouts/template/clientes/cambiarpass";
        }else{
            redirect(uri:"/logincliente")
        }

    }

    def cambiarpassword(){
        if (validarsession()){
            String pass = params.pass1 as String;
            String pass2 = params.pass2 as String;
            if (pass == pass2){
                Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin")
                Tusuarioclientes tusuarioclientes1 = clientesService.cambiarfirstpass(
                        tusuarioclientes.id,
                        pass
                );

                render true;
            }
        }else{
            redirect(uri:"/logincliente")
        }
    }

    def logoutclientes(){

        if (validarsession()){
            session.setAttribute("clientelogin",null);
            redirect(uri:"/logincliente")
        }else{
            redirect(uri:"/logincliente")
        }
    }

}
