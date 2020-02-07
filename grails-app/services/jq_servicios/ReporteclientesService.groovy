package jq_servicios

import clientes.Tarchivoordenes
import grails.gorm.transactions.Transactional
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import grails.plugins.jasper.JasperService
import org.apache.commons.collections.map.HashedMap
import publico.Tclientes
import publico.Tfacturas
import publico.Tpreferencia

import javax.swing.ImageIcon

@Transactional
class ReporteclientesService {

    SqlService sqlService;
    JasperService jasperService;
    def serviceMethod() {}

    List<Map<String, Object>> ListarVerFacturas(String f1, String f2,Long idcliente,Long tipobusqueda) {
        String sql = "SELECT f.f_idrecord as f_id," +
                "c.f_id as f_idcliente,\n" +
                " COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_factura,\n" +
                "       COALESCE(f.f_monto+f.f_tax,0) as f_monto,\n" +
                "       COALESCE(f.f_balance, 0) as f_balance,\n" +
                "        CASE \n" +
                "       WHEN f.f_moneda = 1 THEN 'RD'\n" +
                "       WHEN f.f_moneda = 2 THEN 'US'\n" +
                "       WHEN f.f_moneda = 3 THEN 'EUR'\n" +
                "       END AS f_moneda,\n" +
                "       f.f_moneda as f_idmoneda,\n" +
                "       f.f_comentario,\n" +
                "       false as f_pagar\n" +
                "FROM t_facturas as f\n" +
                "     LEFT JOIN t_clientes as c on f.f_id_cliente = c.f_id where 1=1 and c.f_id = ${idcliente}" +
                "" +
                "";

        if (tipobusqueda == 1){
            sql += " AND f.f_pagada = false ";
        }
        if (tipobusqueda == 2){
            sql += " AND f.f_pagada = TRUE ";
        }


        if (!f1.equals("") && !f2.equals("")) {
            sql = sql + " and f.f_fecha::date BETWEEN '$f1'::date and '$f2'::date ";
        } else {
            if (!f1.equals("")) {
                sql = sql + " and f.f_fecha::date >= '$f1'::date ";
            } else if (!f2.equals("")) {
                sql = sql + " and f.f_fecha::date <= '$f2'::date ";
            }
        }

        sql = sql + " ORDER BY  f_cliente,f.f_fecha desc;";

//        println(sql);
        return sqlService.GetQuery(sql);
    }

    String VerReporteFacturaByIdBase64(Long id) {

        String reporte = '';

        String sql_data_cliente =
                "SELECT \n" +
                        "   to_char(f.f_fecha,'YYYY-MM-dd') as fechafac,\n" +
                        "       f.f_whole as factura,\n" +
                        "       COALESCE(to_char(c.f_id, 'FM0000'),'') as codigo,\n" +
                        "       COALESCE(c.f_nombre_empresa,'') as cliente,\n" +
                        "       COALESCE(c.f_direccion,'') as direccion_cliente,\n" +
                        "       COALESCE (c.f_ciudad,' ') as provincia,\n" +
                        "       COALESCE(c.f_telefono1, '') || ', ' || COALESCE(c.f_telefono2, '') as celular,\n" +
                        "       CURRENT_DATE as fecha,\n" +
                        "       f.f_fecha_vencimiento as vence,\n" +
                        "  CASE \n" +
                        "       WHEN f.f_moneda = 1 THEN 'PESO DOMINICANO'\n" +
                        "       WHEN f.f_moneda = 2 THEN 'DOLLAR'\n" +
                        "       WHEN f.f_moneda = 3 THEN 'EURO'\n" +
                        "       END AS f_moneda,\n" +
                        "       " +
                        "" +
                        "" +
                        "       COALESCE(c.f_contacto,'') as f_contacto,\n" +
                        "       p.f_nombre as Empresa,\n" +
                        "       p.f_direccion as Direccion,\n" +
                        "       p.telefono as telefono,\n" +
                        "       p.f_email as email,\n" +
                        "       p.f_web as pemail,\n" +
                        "       p.f_mensaje_factura,\n" +
                        "       COALESCE (c.f_email, ' ') as cemail,\n" +
                        "       to_char(f.f_fecha, 'yyyy-MM-dd') as fechaenvio,\n" +
                        "       'Email' as embarque,\n" +
                        "       'Contra Entrega' as pertenece,\n" +
                        "       --p.f_logo as image,\n" +
                        "         case when f.f_tipofactura='FCR' then 'Factura Credito' else 'Facutra Contado' end as titulo, \n" +
                        "       (COALESCE(usr.f_nombre,'') ||' '|| COALESCE(usr.f_apellido,'')) as pusuario,  \n" +
                        "       f.f_comentario as comentario\n" +
                        "FROM t_facturas f \n" +
                        "     LEFT JOIN t_clientes c on f.f_id_cliente = c.f_id \n" +
                        "     INNER JOIN t_usuario  usr on   f.f_hecho_por = usr.f_codigo_usuario ,\n" +
                        "     t_preferencia p\n" +
                        "WHERE  \n" +
                        "  f.f_idrecord =$id";

        //println(sql_data_cliente)

        List list_clientes = sqlService.GetQuery(sql_data_cliente);


        String sql = "select\n" +
                "   p.f_referencia as proref,\n" +
                "       p.f_descripcion as producto,\n" +
                "       df.f_precio as precio,\n" +
                "       df.f_cantidad as cantidad,\n" +
                "       lower( COALESCE(df.f_comentario,'')) as comentario,\n" +
                "       df.f_itbis as itbis,\n" +
                "       (df.f_precio + df.f_itbis)*df.f_cantidad as total,\n" +
                "       0 as descuento,\n" +
                "       0 as shiping\n" +
                " from \n" +
                " t_facturas f,\n" +
                "     t_det_factura df,\n" +
                "     t_productos p\n" +
                "where \n" +
                "      f.f_idrecord = $id and\n" +
                "      f.f_nofactura = df.f_nofactura and\n" +
                "      f.f_tipofactura = df.f_tipodoc and\n" +
                "      df.f_id_producto = p.f_id";

        // println(sql);

        def Report = new JasperReportDef();
        //JasperCompileManager.compileReportToFile("E:\\proyectos\\ServerDesarrollo\\jq_servicios\\jq_servicios\\trunk\\web-app\\reports\\FacturaImprimirJQ.jrxml")
        Report.setName("FacturaImprimirJQ.jasper");
        Report.setFileFormat(JasperExportFormat.PDF_FORMAT);
        List list = sqlService.GetQuery(sql);
        if (list_clientes.size() > 0) {
            Map<String, Object> parametros = list_clientes.first();
            Tpreferencia preferencia = Tpreferencia.list().first();
            if (preferencia) {
                if (preferencia.getfLogo()) {
                    ImageIcon img = new ImageIcon(preferencia.getfLogo());
                    parametros.put("imagen", img.getImage());
                }

            }
            Report.setParameters(parametros);
            // println parametros;
            if (list.size() > 0) {
                // println list;
                Report.setReportData(list);
            }
            ByteArrayOutputStream byt = jasperService.generateReport(Report);
            reporte = byt.toByteArray().encodeBase64();
        } else {
            reporte = '-1';
        }

        return reporte;
    }

    def enviarFacturaClienteString(Long id, Long idcliente) {

        String reporte = '';
        String sql_data_cliente =
                "SELECT \n" +
                        "   to_char(f.f_fecha, 'YYYY-MM-dd') as fechafac,\n" +
                        "       f.f_whole as factura,\n" +
                        "       COALESCE(to_char(c.f_id, 'FM0000'),'') as codigo,\n" +
                        "       COALESCE(c.f_nombre_empresa,'') as cliente,\n" +
                        "       COALESCE(c.f_direccion,'') as direccion_cliente,\n" +
                        "       COALESCE (c.f_ciudad,' ') as provincia,\n" +
                        "       COALESCE(c.f_telefono1, '') || ', ' || COALESCE(c.f_telefono2, '') as celular,\n" +
                        "       CURRENT_DATE as fecha,\n" +
                        "       f.f_fecha_vencimiento as vence,\n" +
                        "  CASE \n" +
                        "       WHEN f.f_moneda = 1 THEN 'PESO DOMINICANO'\n" +
                        "       WHEN f.f_moneda = 2 THEN 'DOLLAR'\n" +
                        "       WHEN f.f_moneda = 3 THEN 'EURO'\n" +
                        "       END AS f_moneda,\n" +
                        "       " +
                        "" +
                        "" +
                        "       COALESCE(c.f_contacto,'') as f_contacto,\n" +
                        "       p.f_nombre as Empresa,\n" +
                        "       p.f_direccion as Direccion,\n" +
                        "       p.telefono as telefono,\n" +
                        "       p.f_email as email,\n" +
                        "       p.f_web as pemail,\n" +
                        "       p.f_mensaje_factura,\n" +
                        "       COALESCE (c.f_email, ' ') as cemail,\n" +
                        "       to_char(f.f_fecha, 'MONTH - YYYY'),\n" +
                        "       'Email' as mbarque,\n" +
                        "       'Contra Entrega' as pertenece,\n" +
                        "       --p.f_logo as image,\n" +
                        "         case when f.f_tipofactura='FCR' then 'Factura Credito' else 'Facutra Contado' end as titulo, \n" +
                        "       (COALESCE(usr.f_nombre,'') ||' '|| COALESCE(usr.f_apellido,'')) as pusuario,  \n" +
                        "       f.f_comentario as comentario\n" +
                        "FROM t_facturas f \n" +
                        "     LEFT JOIN t_clientes c on f.f_id_cliente = c.f_id \n" +
                        "     INNER JOIN t_usuario  usr on   f.f_hecho_por = usr.f_codigo_usuario ,\n" +
                        "     t_preferencia p\n" +
                        "WHERE  \n" +
                        "  f.f_idrecord =$id";


        List list_clientes = sqlService.GetQuery(sql_data_cliente);


        String sql = "select\n" +
                "   p.f_referencia as proref,\n" +
                "       p.f_descripcion as producto,\n" +
                "       df.f_precio as precio,\n" +
                "       df.f_cantidad as cantidad,\n" +
                "       df.f_itbis as itbis,\n" +
                "       (df.f_precio + df.f_itbis)*df.f_cantidad as total,\n" +
                "       0 as descuento,\n" +
                "       0 as shiping\n" +
                " from \n" +
                " t_facturas f,\n" +
                "     t_det_factura df,\n" +
                "     t_productos p\n" +
                "where \n" +
                "      f.f_idrecord = $id and\n" +
                "      f.f_nofactura = df.f_nofactura and\n" +
                "      f.f_tipofactura = df.f_tipodoc and\n" +
                "      df.f_id_producto = p.f_id";




        def Report = new JasperReportDef();
        Report.setName("FacturaImprimirJQ.jasper");
        Report.setFileFormat(JasperExportFormat.PDF_FORMAT);

        List list = sqlService.GetQuery(sql);

        // println sql;
        if (list_clientes.size() > 0) {

            Map<String, Object> parametros = list_clientes.first();
            Tpreferencia preferencia = Tpreferencia.list().first();
//            ImageIcon img=new ImageIcon((byte));
//            parametros.put("image",img.getImage());
            if (preferencia) {
                if (preferencia.getfLogo()) {
                    ImageIcon img = new ImageIcon(preferencia.getfLogo());
                    parametros.put("imagen", img.getImage());
                }
            }
            Report.setParameters(parametros);

            if (list.size() > 0) {
                Report.setReportData(list);
            }
            ByteArrayOutputStream byt = jasperService.generateReport(Report);

            Tfacturas f = Tfacturas.findById(id);
            if (f) {
                if (f.fEnviada == true) {
                    try {

                        Tclientes c = Tclientes.findById(idcliente);
                        if (c) {
                            //EnviarEmail(c.fEmail,byt.toByteArray(),'Factura','Factura');
                            String whole = sqlService.Get_Concatenar_tipo_numero_to_char(f.fTipofactura, f.fNofactura.toString(), 6.toInteger())
                            def email = c.fEmail.split(',');
                            EnviarEmail(email, byt.toByteArray(), 'Factura ' + whole, 'Factura ' + whole + "    \n" + f.fComentario);
                            f.setfEnviada(true);
                            f.save(failOnError: true);
                        } else {
                            return '-3';
                        }


                    } catch (Exception e) {

                        println(e.getMessage());
                        return '-4';
                    }

                    return '1';
                } else {

                    try {

                        Tclientes c = Tclientes.findById(idcliente);
                        if (c) {
                            //EnviarEmail(c.fEmail,byt.toByteArray(),'Factura','Factura');
                            String whole = sqlService.Get_Concatenar_tipo_numero_to_char(f.fTipofactura, f.fNofactura.toString(), 6.toInteger())


                            def email = c.fEmail.split(',');
                            EnviarEmail(email, byt.toByteArray(), 'Factura ' + whole, 'Factura ' + whole + "    \n" + f.fComentario);
                            f.setfEnviada(true);
                            f.save(failOnError: true);
                        } else {
                            return '-3';
                        }


                    } catch (Exception e) {

                        println(e.getMessage());
                        return '-4';
                    }

                    return '1';
                }
            }

        } else {
            reporte = '-1';
        }

        return reporte;
    }

    def EnviarEmail(String[] correo, byte[] archivo, String asunto, String mensaje) {
        def nombre = sqlService.GetNow().toString() + ".pdf";
        sendMail {

            multipart true
            to correo
            from "noreply@jqmicro.com";
            subject asunto
            body mensaje
            // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
            //To get started quickly, try the following
            //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
//              attachBytes nombre, 'application/pdf', archivo;
            attachBytes nombre, 'application/pdf', archivo;

        }
    }

    String VerReporteEstadoCuentaByIdBase64(Long id) {

        String reporte = '';

        String sql = "SELECT f.f_nofactura as f_id,\n" +
                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_numero,\n" +
                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
                "       COALESCE(f.f_monto, 0)::numeric as f_monto,\n" +
                "       COALESCE(f.f_balance, 0)::numeric as f_balance,\n" +
                "       COALESCE(c.f_nombre_empresa,'') as f_cliente\n" +
                "FROM t_facturas as f\n" +
                "     INNER JOIN t_clientes as c on c.f_id = f.f_id_cliente\n" +
                "       WHERE c.f_id = $id and f.f_pagada = false\n" +
                "ORDER BY f.f_fecha_vencimiento ASC;";

        def Report = new JasperReportDef();
        Report.setName("out_estado_cuenta.jasper");

        Report.setFileFormat(JasperExportFormat.PDF_FORMAT);

        List list = sqlService.GetQuery(sql);


        Map<String, Object> parametros = GetPreferencia();
        Tpreferencia preferencia = Tpreferencia.list().first();

        parametros.put("f_titulo", "Estado de Cuenta");
        if (preferencia.getfLogo()) {
            ImageIcon img = new ImageIcon(preferencia.getfLogo());
            parametros.put("imagen", img.getImage());
        }
        Report.setParameters(parametros);

        if (list.size() > 0) {
            Report.setReportData(list);
        }
        ByteArrayOutputStream byt = jasperService.generateReport(Report);
        reporte = byt.toByteArray().encodeBase64();


        return reporte;
    }

    def GetPreferencia() {
        String sql = "SELECT \n" +
                "  f_nombre as f_nombre_empresa,\n" +
                "  f_direccion,\n" +
                "  telefono as f_telefono,\n" +
                "  f_email,\n" +
                "  f_web,\n" +
                "  f_tax,\n" +
                "  f_isr,\n" +
                "  f_mensaje_factura,\n" +
                "  f_mensaje_cotizacion,\n" +
                "  f_idrecord\n" +
                "FROM \n" +
                "  public.t_preferencia ;"
        Map<String, Object> map = new HashedMap();

        List list1 = sqlService.GetQuery(sql);

        return list1.first();
    }

    def get_enviar_estado_cuenta_con_correo(Long id, String email) {

        String reporte = '';

        String sql = "SELECT f.f_nofactura as f_id,\n" +
                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_numero,\n" +
                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
                "       COALESCE(f.f_monto, 0)::numeric as f_monto,\n" +
                "       COALESCE(f.f_balance, 0)::numeric as f_balance,\n" +
                "       COALESCE(c.f_nombre_empresa,'') as f_cliente\n" +
                "FROM t_facturas as f\n" +
                "     INNER JOIN t_clientes as c on c.f_id = f.f_id_cliente\n" +
                "       WHERE c.f_id = $id and f.f_pagada = false\n" +
                "ORDER BY f.f_fecha_vencimiento ASC;";

        def Report = new JasperReportDef();
        // JasperCompileManager.compileReportToFile("D:\\proyectos\\ServerDesarrollo\\jq_servicios\\jq_servicios\\trunk\\web-app\\reports\\FacturaImprimirJQ.jrxml")
        Report.setName("out_estado_cuenta.jasper");

        Report.setFileFormat(JasperExportFormat.PDF_FORMAT);

        List list = sqlService.GetQuery(sql);


        Map<String, Object> parametros = GetPreferencia();
        Tpreferencia preferencia = Tpreferencia.list().first();

        parametros.put("f_titulo", "Estado de Cuenta");
        if (preferencia.getfLogo()) {
            ImageIcon img = new ImageIcon(preferencia.getfLogo());
            parametros.put("imagen", img.getImage());
        }
        Report.setParameters(parametros);

        if (list.size() > 0) {
            Report.setReportData(list);
        }
        ByteArrayOutputStream byt = jasperService.generateReport(Report);
        try {
            EnviarEmail(email, byt.toByteArray(), "Estado de cuenta", " Estado de cuenta");
        } catch (Exception e) {
            // throw e;
            return '-4';
        }
    }

    def EnviarEmail(String correo, byte[] archivo, String asunto, String mensaje) {
        def nombre = sqlService.GetNow().toString() + ".pdf";
        sendMail {

            multipart true
            to correo
            from "noreply@jqmicro.com";
            subject asunto
            body mensaje
            // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
            //To get started quickly, try the following
            //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
//              attachBytes nombre, 'application/pdf', archivo;
            attachBytes nombre, 'application/pdf', archivo;

        }
    }

    def enviarFacturaClienteString(Long id, String email) {

        String reporte = '';

        String sql_data_cliente =
                "SELECT \n" +
                        "   to_char(f.f_fecha, 'YYYY-MM-dd') as fechafac,\n" +
                        "       f.f_whole as factura,\n" +
                        "       COALESCE(to_char(c.f_id, 'FM0000'),'') as codigo,\n" +
                        "       COALESCE(c.f_nombre_empresa,'') as cliente,\n" +
                        "       COALESCE(c.f_direccion,'') as direccion_cliente,\n" +
                        "       COALESCE (c.f_ciudad,' ') as provincia,\n" +
                        "       COALESCE(c.f_telefono1, '') || ', ' || COALESCE(c.f_telefono2, '') as celular,\n" +
                        "       CURRENT_DATE as fecha,\n" +
                        "       f.f_fecha_vencimiento as vence,\n" +
                        "  CASE \n" +
                        "       WHEN f.f_moneda = 1 THEN 'PESO DOMINICANO'\n" +
                        "       WHEN f.f_moneda = 2 THEN 'DOLLAR'\n" +
                        "       WHEN f.f_moneda = 3 THEN 'EURO'\n" +
                        "       END AS f_moneda,\n" +
                        "       " +
                        "" +
                        "" +
                        "       COALESCE(c.f_contacto,'') as f_contacto,\n" +
                        "       p.f_nombre as Empresa,\n" +
                        "       p.f_direccion as Direccion,\n" +
                        "       p.telefono as telefono,\n" +
                        "       p.f_email as email,\n" +
                        "       p.f_web as pemail,\n" +
                        "       p.f_mensaje_factura,\n" +
                        "       COALESCE (c.f_email, ' ') as cemail,\n" +
                        "       to_char(f.f_fecha, 'MONTH - YYYY'),\n" +
                        "       'Email' as mbarque,\n" +
                        "       'Contra Entrega' as pertenece,\n" +
                        "       --p.f_logo as image,\n" +
                        "         case when f.f_tipofactura='FCR' then 'Factura Credito' else 'Facutra Contado' end as titulo, \n" +
                        "       (COALESCE(usr.f_nombre,'') ||' '|| COALESCE(usr.f_apellido,'')) as pusuario,  \n" +
                        "       f.f_comentario as comentario\n" +
                        "FROM t_facturas f \n" +
                        "     LEFT JOIN t_clientes c on f.f_id_cliente = c.f_id \n" +
                        "     INNER JOIN t_usuario  usr on   f.f_hecho_por = usr.f_codigo_usuario ,\n" +
                        "     t_preferencia p\n" +
                        "WHERE  \n" +
                        "  f.f_idrecord =$id";

        // println(sql_data_cliente)

        List list_clientes = sqlService.GetQuery(sql_data_cliente);


        String sql = "select\n" +
                "   p.f_referencia as proref,\n" +
                "       p.f_descripcion as producto,\n" +
                "       df.f_precio as precio,\n" +
                "       df.f_cantidad as cantidad,\n" +
                "       df.f_itbis as itbis,\n" +
                "       (df.f_precio + df.f_itbis)*df.f_cantidad as total,\n" +
                "       0 as descuento,\n" +
                "       0 as shiping\n" +
                " from \n" +
                " t_facturas f,\n" +
                "     t_det_factura df,\n" +
                "     t_productos p\n" +
                "where \n" +
                "      f.f_idrecord = $id and\n" +
                "      f.f_nofactura = df.f_nofactura and\n" +
                "      f.f_tipofactura = df.f_tipodoc and\n" +
                "      df.f_id_producto = p.f_id";




        def Report = new JasperReportDef();
        // JasperCompileManager.compileReportToFile("D:\\proyectos\\ServerDesarrollo\\jq_servicios\\jq_servicios\\trunk\\web-app\\reports\\FacturaImprimirJQ.jrxml")
        Report.setName("FacturaImprimirJQ.jasper");
        Report.setFileFormat(JasperExportFormat.PDF_FORMAT);

        List list = sqlService.GetQuery(sql);
        if (list_clientes.size() > 0) {

            Map<String, Object> parametros = list_clientes.first();
            Tpreferencia preferencia = Tpreferencia.list().first();
//            ImageIcon img=new ImageIcon((byte));
//            parametros.put("image",img.getImage());
            if (preferencia) {
                if (preferencia.getfLogo()) {
                    ImageIcon img = new ImageIcon(preferencia.getfLogo());
                    parametros.put("imagen", img.getImage());
                }
            }
            Report.setParameters(parametros);

            if (list.size() > 0) {
                Report.setReportData(list);
            }
            ByteArrayOutputStream byt = jasperService.generateReport(Report);
            // reporte = byt.toByteArray().encodeBase64();

            Tfacturas f = Tfacturas.findById(id);
            if (f) {

                try {

                    String whole = sqlService.Get_Concatenar_tipo_numero_to_char(f.fTipofactura, f.fNofactura.toString(), 6.toInteger())

                    EnviarEmail(email.split(","), byt.toByteArray(), 'Factura ' + whole, 'Factura ' + whole + "    \n" + f.fComentario);
                    f.setfEnviada(true);
                    f.save(failOnError: true);


                } catch (Exception e) {
                    throw e;
                    return '-4';
                }

                return '1';

            }
        } else {
            reporte = '-1';
        }

        return reporte;
    }

    List<Map<String, Object>> get_ver_ordenesclientes(String f1, String f2,Long idcliente,Long tipobusqueda) {
        String sql = "SELECT\n" +
                "o.f_idrecord as f_id, \n"+
                "o.f_titulo,\n" +
                "to_char(o.f_fecha, 'yyyy/MM/dd') as f_fecha\n" +
                "FROM\n" +
                "clientes.t_ordentrabajo AS o\n" +
                "WHERE o.f_idcliente = ${idcliente} AND o.f_status = ${tipobusqueda}";


        if (!f1.equals("") && !f2.equals("")) {
            sql = sql + " and o.f_fecha::date BETWEEN '$f1'::date and '$f2'::date ";
        } else {
            if (!f1.equals("")) {
                sql = sql + " and o.f_fecha::date >= '$f1'::date ";
            } else if (!f2.equals("")) {
                sql = sql + " and o.f_fecha::date <= '$f2'::date ";
            }
        }

        sql = sql + " ORDER BY o.f_fecha desc;";

        return sqlService.GetQuery(sql);
    }

    def get_archivos_ordenes(Long idorden){

        String sql = "SELECT\n" +
                "ar.f_idrecord as id,\n" +
                "ar.f_namearchivo \n" +
                "FROM\n" +
                "clientes.t_archivoordenes as ar \n" +
                "WHERE ar.f_idordentrabajo = ${idorden}";

        return sqlService.GetQuery(sql);
    }
    Tarchivoordenes  getArchivo(Long id){
        return Tarchivoordenes.findById(id)
    }

}
