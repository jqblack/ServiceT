package jq_servicios

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import publico.Tpreferencia
import publico.TserviciosContratados
import publico.TservidoresActualizaciones
import publico.Tusuario

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Transactional
class SeguridadService {
    static transactional = true;

    SqlService sqlService;

    @Transactional
    Tusuario getUsuarios(String usuario, String contaseña)
    {
        String sql=" from Tusuario as b where (b.fIdUsuario = ?0 or b.fEmail = ?1 ) and b.fPassword= ?2 and b.fActivo =true ";
        Collection colleccion = [];

        colleccion.add(usuario);
        colleccion.add(usuario);
        colleccion.add(contaseña.encodeAsMD5());
        List<Tusuario> user = Tusuario.findAll(sql,colleccion)
        if (user.size() >=1)
        {
            return user.first();
        }
        else
        {
            return  null;
        }



        //return Tusuario.findByFUserOrFEmailAndFPassAndFActivo(usuario,contaseña.encodeAsMD5(),true);

    }


    def Tusuario GetUsuarioSession()
    {
        GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
        GrailsHttpSession session = request.session
        return  session.getAttribute("Usuario") as Tusuario;
    }

    def Tpreferencia GetPreferencias()
    {

        List<Tpreferencia> tpreferenciaList = Tpreferencia.list();

        if (tpreferenciaList.size()> 0)
        {
            return tpreferenciaList.first();
        }
    }


    def List<Map<String,Object>> GetPermisosUsuarios(Long id)
    {
        
        String sql  ="SELECT op.f_descripcion,\n" +
                "       op.f_icono,\n" +
                "       op.f_url,\n" +
                "       cfg.get_hijos(op.f_idrecord,$id) as f_hijos\n" +
                "FROM cfg.t_opciones_menu op where op.f_idpadre =0 AND\n" +
                "op.f_idrecord in (SELECT u.f_idopcion from cfg.t_permisos_usuarios u WHERE u.f_idusuario = $id) ORDER by op.f_idrecord ASC;";

        return  sqlService.GetQuery(sql);
    }



    def List<Map<String,Object>> GetProyectosUsuario(Object id)
    {
        
        String sql  ="SELECT p.f_descripcion as f_nombre,p.f_id as f_idproyecto\n" +
                "        FROM tk.t_usuarios u,\n" +
                "         t_clientes c,\n" +
                "         t_productos p,\n" +
                "         t_servicios_contratados s,\n" +
                "         t_usuario as usr\n" +
                "        WHERE\n" +
                "        usr.f_codigo_usuario = $id and\n" +
                "        u.f_usuario = usr.f_id_usuario and\n" +
                "        u.f_idcliente = c.f_id and\n" +
                "        s.f_id_cliente = c.f_id and\n" +
                "        s.f_id_producto = p.f_id";

//println(sql);
        return  sqlService.GetQuery(sql);
    }


    def List<Map<String,Object>> GetProyectos()
    {
        
        String sql  ="SELECT p.f_descripcion as f_nombre,\n" +
                "       p.f_id as f_idproyecto\n" +
                "FROM t_productos p";

        //  println(sql);

        return  sqlService.GetQuery(sql);
    }

    def getActyualizacionesServidores(Long idservicio){
        TserviciosContratados tserviciosContratados = TserviciosContratados.findById(idservicio);
        if (tserviciosContratados){

            try {
                String data = "{\"f_key\":\"\",\"f_data\":\"${tserviciosContratados.fIdCliente}\"}".encodeAsBase64().replace("==","");

                TservidoresActualizaciones tservidoresActualizaciones = TservidoresActualizaciones.findById(tserviciosContratados.fUrlServidorActualizacion);
                String uri = tservidoresActualizaciones.fUrlServidor + data;
                URL url = new URL(uri);
                def conn;

                /*******************SI LA LLAMADA SE VA A REALIZAR POR HTTPS ******************/
                if (uri.toLowerCase().contains("https")){

                    def nullTrustManager = [
                            checkClientTrusted: { chain, authType ->  },
                            checkServerTrusted: { chain, authType ->  },
                            getAcceptedIssuers: { null }
                    ]

                    def nullHostnameVerifier = [
                            verify: { hostname, session -> true }
                    ]

                    SSLContext sc = SSLContext.getInstance("SSL")
                    sc.init(null, [nullTrustManager as X509TrustManager] as TrustManager[], null)
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
                    HttpsURLConnection.setDefaultHostnameVerifier(nullHostnameVerifier as HostnameVerifier)


                    conn = (HttpsURLConnection) url.openConnection();


                }/*******************SI LA LLAMADA SE VA A REALIZAR POR HTTP ******************/
                else{
                    conn = (HttpURLConnection) url.openConnection();
                }

                conn.setRequestMethod("POST");
                conn.setConnectTimeout(1000)

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                def json = new JSON().parse(br.readLine());
                return  json.f_data as Map
            } catch (Exception e) {
                if (tserviciosContratados.fIdCliente ==1020){
                    e.printStackTrace();
                }

                Map map = new HashMap();
                map.put("f_key","");
                map.put("f_data",[total:0.00,ultima_actualizacion:''])
                return map.f_data as Map
            }


        }
    }
}
