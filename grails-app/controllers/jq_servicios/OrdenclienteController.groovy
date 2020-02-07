package jq_servicios

import clientes.Tusuarioclientes
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.web.multipart.MultipartFile

class OrdenclienteController {

    def index() {}

    OrdenclienteService ordenclienteService;

    Boolean validarsession() {
        if (session.getAttribute("clientelogin")) {
            return true;
        } else {
            return false;
        }
    }

    def ordentrabajo() {

        if (validarsession()) {
            session.removeAttribute("listaArchivos");
            render view: "/layouts/template/ordenCliente/ordencliente";
        } else {
            redirect(uri: "/logincliente");
        }
    }

    def salvar_archivo_orden() {
        if (validarsession()){
        MultipartFile archivo = (MultipartFile) params.archivo;
        List<Map<String, Object>> listaArchivos;

        if (session.listaArchivos) {
            listaArchivos = session.listaArchivos as List<Map<String, Object>>
        } else {
            listaArchivos = new ArrayList<Map<String, Object>>();
        }

        Map<String, Object> mapa = new HashMap<>();
        mapa.put("id",archivo.toString().md5());
        mapa.put("archivo",archivo.getBytes());
        mapa.put("namearchivo",archivo.originalFilename);
        listaArchivos.add(mapa);
        session.listaArchivos = listaArchivos;
        render archivo.toString().md5();
        }else{
           render false;
        }
    }

    def GuardarOrden(){

        List lista =  new JsonSlurper().parseText(params.tabla as String) as List;
        List listassesion = session.getAttribute("listaArchivos") as List;
        List lista_tabla = [];

        for (int i = 0; i < listassesion.size(); i++) {

            for (int j = 0; j < lista.size(); j++) {

                if (listassesion[i].id == lista[j].archivo){
                    Map mapa = new HashMap();
                    mapa.put("archivo",listassesion[i].archivo);
                    mapa.put("namearchivo",listassesion[i].namearchivo);
                    mapa.put("f_orden",lista[j].orden as Boolean);
                    lista_tabla.add(mapa);
                }
            }
        }

        Tusuarioclientes tusuarioclientes = session.getAttribute("clientelogin");

//        println("tabla"+lista_tabla);
        Map mapares = ordenclienteService.GuardarOrden(
                params.ttitulo as String,
                params.texto as String,
                lista_tabla,
                tusuarioclientes.id,
                tusuarioclientes.fUsername
        );

        render JsonOutput.toJson(mapares);
    }
}
