package jq_servicios

import conocimiento.Tcategorias
import conocimiento.Tconocimiento
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

@Transactional
class ConocimientoService {

    def serviceMethod() {

    }

    static transactional = true;
    SqlService sqlService;
    SeguridadService seguridadService;
    List<Tcategorias>ListarCategorias()
    {
        return Tcategorias.list();
    }

    List<Map<String,Object>> ListarConocimiento(Long id)
    {


        String sql = "SELECT c.f_idrecord,\n" +
                "       c.f_fecha,\n" +
                "       c.f_hechopor,\n" +
                "       c.f_solucion,\n" +
                "       c.f_titulo,\n" +
//                "       encode(c.f_documento, 'base64') as f_documento,\n" +
                "      conocimiento.get_tiene_documento(c.f_idrecord) as f_documento,\n" +
                "       c.f_idcategoria,\n" +
                "       c.f_tags,\n" +
                "       cat.f_nombre\n" +
                "FROM conocimiento.t_conocimiento as c\n" +
                "     inner JOIN conocimiento.t_categorias as cat on cat.f_idrecord =\n" +
                "       c.f_idcategoria WHERE c.f_idrecord = $id";

        return  sqlService.GetQuery(sql);
    }

    List<Map<String,Object>> ListarDocumentoById(Long id)
    {
        String sql = "SELECT encode(c.f_documento,'base64') as f_documento,c.f_tipo_doc\n" +
                "FROM conocimiento.t_conocimiento as c  WHERE c.f_idrecord = $id";
        //  println(sql);
        return  sqlService.GetQuery(sql);
    }
    List<Map<String,Object>> ListarConocimiento()
    {
        String sql = "SELECT c.f_idrecord,\n" +
                "       c.f_fecha,\n" +
                "       c.f_hechopor,\n" +
                "       c.f_solucion,\n" +
                "       c.f_titulo,\n" +
                "       conocimiento.get_tiene_documento(c.f_idrecord) as f_documento,\n" +
                "       c.f_idcategoria,\n" +
                "       c.f_tags,\n" +
                "       cat.f_nombre\n" +
                "FROM conocimiento.t_conocimiento as c\n" +
                "     inner JOIN conocimiento.t_categorias as cat on cat.f_idrecord =\n" +
                "       c.f_idcategoria;";

        return  sqlService.GetQuery(sql);
    }


    def salvar_conocimiento(String id,Long idcategoria,String solucion,String tags,String titulo,String tipo_doc,String nombre_doc)
    {
        if (!id.equals(""))
        {
            Tconocimiento c = Tconocimiento.findById(id.toLong());
            if (c)
            {

                c.setfIdcategoria(idcategoria);
                c.setfSolucion(solucion);
                c.setfTags(tags);
                c.setfTitulo(titulo);
                c.setfNombreArchivo(nombre_doc);
                if (!tipo_doc.equals(""))
                {
                    c.setfTipoDoc(tipo_doc);
                }

                c.save(failOnError: true);

                GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
                GrailsHttpSession session = request.session
                session.setAttribute("id_conocimiento",c.id);

            }
            else
            {
                def a = new Tconocimiento(fFecha: sqlService.GetNow(),fHechopor: seguridadService.GetUsuarioSession().id,fIdcategoria: idcategoria,fSolucion: solucion,fTitulo: titulo,fTags: tags,fDocumento: new  byte[0],fTipoDoc: tipo_doc,fNombreArchivo: nombre_doc);
                a.save(failOnError: true);
                GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
                GrailsHttpSession session = request.session
                session.setAttribute("id_conocimiento",a.id);
            }
        }
        else
        {
            def a = new Tconocimiento(fFecha: sqlService.GetNow(),fHechopor: seguridadService.GetUsuarioSession().id,fIdcategoria: idcategoria,fSolucion: solucion,fTitulo: titulo,fTags: tags,fDocumento: new  byte[0],fTipoDoc: tipo_doc,fNombreArchivo: nombre_doc);
            a.save(failOnError: true);
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            session.setAttribute("id_conocimiento",a.id);
        }


    }

    def salvar_documento_conocimiento(Long id,byte [] file)
    {
        Tconocimiento c = Tconocimiento.findById(id.toLong());
        if (c)
        {
            c.setfDocumento(file);
            c.save(failOnError: true);

            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            session.removeAttribute("id_conocimiento");
        }
    }

    def descargar_documento(Long id)
    {
        Tconocimiento c = Tconocimiento.findById(id.toLong());
        if (c)
        {
            return  c.fDocumento;
        }
    }
}
