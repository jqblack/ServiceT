package jq_servicios

import conocimiento.Tcategorias
import conocimiento.Tconocimiento
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile

//import org.springframework.web.multipart.commons.CommonsMultipartFile
import publico.Tusuario

class ConocimientosController {

    def index() { }

    ConocimientoService conocimientoService;

    Boolean validar_sesion() {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        Tusuario user = session.getAttribute("Usuario") as Tusuario;
        if (user != null) {
            return true
        } else {
            return false;
        }
    }

    def get_documento_by_id()
    {
        if (validar_sesion()) {
            render conocimientoService.ListarDocumentoById(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def conocimiento() {
        if (validar_sesion()) {
            List<Tcategorias> tcategoriases = conocimientoService.ListarCategorias()
            render(view: '/layouts/template/conocimiento/FrmOuConocimiento',model: [categorias:tcategoriases]);
        } else {
            redirect(uri: '/login');
        }
    }

    def get_conocimiento()
    {
        if (validar_sesion()) {
            render conocimientoService.ListarConocimiento() as JSON;
        } else {
            redirect(uri: '/login');
        }

    }
    def get_conocimiento_by_id()
    {
        if (validar_sesion()) {
            render conocimientoService.ListarConocimiento(params.idrecord.toString().toLong()) as JSON;
        } else {
            redirect(uri: '/login');
        }

    }

    def ver_solucion() {
        if (validar_sesion()) {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            Long id = session.getAttribute("id_conocimiento") as Long;

            Map<String,Object> tupla = conocimientoService.ListarConocimiento(id).first();

            render(view: '/layouts/template/conocimiento/FrmOutSolucion',model: [titulo:tupla.get('f_titulo'),documento:tupla.get('f_documento'),solucion:tupla.get('f_solucion'),idrecord:id]);
        } else {
            redirect(uri: '/login');
        }
    }

    def descargar_documento()
    {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session

        if (validar_sesion()) {

            byte [] archivo =  conocimientoService.descargar_documento(params.idrecord.toString().toLong());

            Tconocimiento c = Tconocimiento.findById(params.idrecord.toString().toLong());

            String nombre = "";
            if (c)
            {

                nombre = c.fNombreArchivo;
            }

            if (c.fDocumento)
            {

                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${nombre}")
                response.outputStream << archivo // Performing a binary stream copy

            }

        } else {
            redirect(uri: '/login');
        }

    }

    def crear_id_solucion() {
        if (validar_sesion()) {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            session.setAttribute("id_conocimiento",params.idrecord.toString().toLong());
            render true;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_conocimiento()
    {
        if (validar_sesion()) {
            conocimientoService.salvar_conocimiento(params.idrecord.toString(),
                    params.idcategoria.toString().toLong(),
                    params.solucion.toString(),
                    params.tags.toString(),
                    params.titulo.toString(),
                    params.tipo_doc.toString(),
                    params.nombre_doc.toString()


            );
            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

    def salvar_documento()
    {
//        CommonsMultipartFile file = (CommonsMultipartFile)params.documento;
        MultipartFile file = (MultipartFile)params.documento;
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session

        Long id = session.getAttribute("id_conocimiento") as Long;

        if (validar_sesion()) {
            conocimientoService.salvar_documento_conocimiento(id,file.getBytes());
            render 'true';
        } else {
            redirect(uri: '/login');
        }

    }

}
