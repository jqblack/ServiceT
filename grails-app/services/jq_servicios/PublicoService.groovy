package jq_servicios

import FuncionesGenerales.Funciones
import cfg.TpermisosUsuarios
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import grails.util.Holders
import grails.web.servlet.mvc.GrailsHttpSession
import org.apache.commons.collections.map.HashedMap
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.ApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import publico.TaplicacionesRecibo
import publico.Tclientes
import publico.TdetFactura
import publico.Tfacturas
import publico.TmsgClientes
import publico.Tpreferencia
import publico.Tproductos
import publico.Trecibos
import publico.Treferentes
import publico.TserialFormula
import publico.TserviciosContratados
import publico.Tusuario
import tk.TDistribucionCorreos
import tk.Tusuarios

import javax.swing.ImageIcon

@Transactional
class PublicoService {

    static transactional = true;
    def jasperService;

    def sqlService ;
    SeguridadService seguridadService ;

    List<Map<String, Object>> ListarClientes() {
        String sql = "SELECT f_id\n," +
                "f_nombre_empresa\n," +
                "f_email\n," +
                "f_moneda\n," +
                "f_facturarle_tax as f_itbis\n," +
                "(select f_fecha from t_facturas as f where f.f_id_cliente = f_id ORDER by f_fecha DESC limit 1)::text as f_fecha \n" +
                "from t_clientes order by f_nombre_empresa asc";
        return sqlService.GetQuery(sql);
    }


    List<Map<String,Object>> getClientesDashboard(){
        String sql="\n" +
                "SELECT \n" +
                "cli.f_id as idrecord,\n" +
                "sc.f_id as idservicio,\n" +
                "pr.f_descripcion as servicio,\n" +
                "cli.f_nombre_empresa as cliente,\n" +
                "to_char(sc.f_fecha_entrada::date,'yyyy/MM/dd') as entrada,\n" +
                "to_char(sc.f_fecha_entrada::date,'yyyy/MM/dd') as activacion,\n" +
                "CURRENT_DATE as actualizacion,\n" +
                "(SELECT COALESCE(sum(f.f_balance),0) from t_facturas as f where f.f_id_cliente = cli.f_id and f.f_pagada = false and f.f_cancelada = false) as balance,\n" +
                "25000::numeric as ventas,\n" +
                "CASE cli.f_moneda\n" +
                "WHEN 1 THEN 'PESO'\n" +
                "WHEN 2 THEN 'DOLLAR'\n" +
                "ELSE 'EURO' END as moneda,\n" +
                "COALESCE( sc.f_activo,false) as activo\n" +
                "FROM t_servicios_contratados sc \n" +
                "INNER JOIN  t_clientes as cli on sc.f_id_cliente = cli.f_id\n" +
                "INNER JOIN  t_productos as pr on sc.f_id_producto = pr.f_id\n" +
                "WHERE sc.f_activo = true \n" +
                "ORDER BY cli.f_id ASC";
        return  sqlService.GetQuery(sql);
    }

    def DesactivarServicioContratado(Long idservicio){
        TserviciosContratados tserviciosContratados = TserviciosContratados.findById(idservicio);
        if (tserviciosContratados){
            tserviciosContratados.setfActivo(false);
            tserviciosContratados.save(failOnError: true);
        }
    }

    def DesactivarMensajesTodos(){
        try{
            TmsgClientes.executeUpdate("update TmsgClientes set fActivo = false");
            return true;
        }catch(Exception e){
            return  false
        }

    }

    List<Map<String, Object>> ListarSerialesFormula() {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_serial,\n" +
                "  f_cliente,\n" +
                "  f_imei,\n" +
                "  f_fecha,\n" +
                "  f_server,\n" +
                "  CASE f_tipo_proyecto \n" +
                "  WHEN 1 THEN 'LOTO TOUCH'\n" +
                "  WHEN 2 THEN 'FORMULA 43'\n" +
                "  END f_tipo," +
                "  f_tipo_proyecto ,\n" +
                "  f_activo\n" +
                "FROM \n" +
                "  public.t_serial_formula ;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarSerialesFormulaById(Long id) {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_serial,\n" +
                "  f_cliente,\n" +
                "  f_imei,\n" +
                "  f_fecha,\n" +
                "  f_server,\n" +
                "  f_tipo_proyecto,\n" +
                "  f_activo\n" +
                "FROM \n" +
                "  public.t_serial_formula where f_idrecord = $id  ;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarClientesActivos(Boolean activo) {

        String sql = "SELECT f_id\n," +
                "f_nombre_empresa\n," +
                "f_email\n," +
                "f_moneda\n," +
                "(select f_fecha from t_facturas as f where f.f_id_cliente = f_id ORDER by f_fecha DESC limit 1)::text as f_fecha \n" +
                " from t_clientes \n" +
                "where \n" +
                "f_activo =$activo order by f_id asc";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarDistribucionCorreosClientes(String idcliente) {

        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_idcliente,\n" +
                "  f_correos\n" +
                "FROM \n" +
                "  tk.t_distribucion_correos  where f_idcliente =$idcliente;";
        return sqlService.GetQuery(sql);
    }


    List<Map<String, Object>> ListarPermisosUsuariosById(Long id) {
        String sql = "SELECT op.f_idrecord,\n" +
                "op.f_idpadre,\n" +
                "op.f_descripcion,\n" +
                "(SELECT op.f_idrecord in (SELECT p.f_idopcion FROM cfg.t_permisos_usuarios p WHERE p.f_idusuario =$id )) as f_activo\n" +
                "from cfg.t_opciones_menu op ORDER BY op.f_idrecord ASC";

//        println(sql);
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarProductos() {
        String sql = "\n" +
                "SELECT \n" +
                "  f_id,\n" +
                "  COALESCE(f_referencia, '') as f_referencia,\n" +
                "  COALESCE(f_descripcion, '') as f_descripcion,\n" +
                "  COALESCE(f_cantidad, 0) as f_cantidad,\n" +
                "  f_servicio,\n" +
                "  COALESCE(f_precio, 0) as f_precio,\n" +
                "  f_genera_serial,\n" +
                "  COALESCE(f_version, '') as f_version,\n" +
                "  f_sistema,\n" +
                "  f_itbis\n" +
                "FROM \n" +
                "  public.t_productos ;";

        return sqlService.GetQuery(sql);

    }

    List<Map<String, Object>> ListarProductosActivos(Boolean activo) {
        String sql = "\n" +
                "SELECT \n" +
                "  f_id,\n" +
                "  COALESCE(f_referencia, '') as f_referencia,\n" +
                "  COALESCE(f_descripcion, '') as f_descripcion,\n" +
                "  COALESCE(f_cantidad, 0) as f_cantidad,\n" +
                "  f_servicio,\n" +
                "  COALESCE(f_precio, 0) as f_precio,\n" +
                "  f_genera_serial,\n" +
                "  COALESCE(f_version, '') as f_version,\n" +
                "  f_sistema,\n" +
                "  f_itbis\n" +
                "FROM \n" +
                "  public.t_productos where f_activo = $activo ;";

        return sqlService.GetQuery(sql);

    }

    List<Map<String, Object>> ListarClientesById(Long id) {
        String sql = "SELECT f_id,\n" +
                "       f_nombre_empresa,\n" +
                "       f_contacto,\n" +
                "       COALESCE(f_direccion, '') as f_direccion,\n" +
                "       COALESCE(f_ciudad, '') as f_ciudad,\n" +
                "       COALESCE(f_estado, '') as f_estado,\n" +
                "       COALESCE(f_zipcode, '') as f_zipcode,\n" +
                "       COALESCE(f_pais, '') as f_pais,\n" +
                "       COALESCE(f_telefono1, '') as f_telefono1,\n" +
                "       COALESCE(f_telefono2, '') as f_telefono2,\n" +
                "       COALESCE(f_cel_contacto, '') as f_cel_contacto,\n" +
                "       COALESCE(f_fax, '') as f_fax,\n" +
                "       COALESCE(f_contacto_cobro, '') as f_contacto_cobro,\n" +
                "       COALESCE(f_id_identificacion, '') as f_id_identificacion,\n" +
                "       COALESCE(f_email, '') as f_email,\n" +
                "       f_enviar_email,\n" +
                "       f_retiene_isr,\n" +
                "       f_retiene_itbis,\n" +
                "       f_facturarle_tax,\n" +
                "       f_empresa_persona,\n" +
                "       f_moneda\n" +
                "FROM public.t_clientes WHERE f_id=$id;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarProductosById(Long id) {
        String sql = "\n" +
                "SELECT \n" +
                "  f_id,\n" +
                "  COALESCE(f_referencia, '') as f_referencia,\n" +
                "  COALESCE(f_descripcion, '') as f_descripcion,\n" +
                "  COALESCE(f_cantidad, 0) as f_cantidad,\n" +
                "  f_servicio,\n" +
                "  COALESCE(f_precio, 0) as f_precio,\n" +
                "  f_genera_serial,\n" +
                "  COALESCE(f_version, '') as f_version,\n" +
                "  f_sistema,\n" +
                "  f_itbis,\n" +
                "  f_activo\n" +
                "FROM \n" +
                "  public.t_productos  WHERE f_id=$id;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarServiciosContratados() {
        String sql = " SELECT\n" +
                "    s.f_id,\n" +
                "    COALESCE(p.f_descripcion,'') as f_servicio,\n" +
                "    COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
                "    COALESCE(s.f_precio,0)as f_precio,\n" +
                "    ((COALESCE(s.f_precio,0)*COALESCE(s.f_cantidad,0))+(COALESCE(s.f_itbis,0))*COALESCE(s.f_cantidad,0)) as f_monto,\n" +
                "    COALESCE(s.f_cantidad,0) as f_cantidad,\n" +
                "    COALESCE(s.f_itbis,0) as f_itbis\n" +
                "    FROM t_servicios_contratados as s\n" +
                "    INNER JOIN t_productos as  p on s.f_id_producto = p.f_id\n" +
                "    INNER JOIN t_clientes  as c on c.f_id = s.f_id_cliente\n" +
                "    LEFT JOIN t_referentes as r on r.f_idrecord = s.f_idreferente";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarServiciosContratadosActivos(Boolean activo) {
        String sql = " SELECT\n" +
                "    s.f_id,\n" +
                "    COALESCE(p.f_descripcion,'') as f_servicio,\n" +
                "    COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
                "    COALESCE(s.f_precio,0)as f_precio,\n" +
                "    ((COALESCE(s.f_precio,0)*COALESCE(s.f_cantidad,0))+(COALESCE(s.f_itbis,0))*COALESCE(s.f_cantidad,0)) as f_monto,\n" +
                "    COALESCE(s.f_cantidad,0) as f_cantidad,\n" +
                "    COALESCE(s.f_itbis,0) as f_itbis\n" +
                "    FROM t_servicios_contratados as s\n" +
                "    INNER JOIN t_productos as  p on s.f_id_producto = p.f_id\n" +
                "    INNER JOIN t_clientes  as c on c.f_id = s.f_id_cliente\n" +
                "    LEFT JOIN t_referentes as r on r.f_idrecord = s.f_idreferente where s.f_activo =$activo";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarServiciosContratadosById(Long id) {
        String sql = "\n" +
                "SELECT\n" +
                "s.f_id,\n" +
                "s.f_cvariable,\n" +
                "s.f_cobrable,\n" +
                "s.f_url_servidor_actualizacion,\n" +
                "p.f_id as f_id_producto,\n" +
                "COALESCE(p.f_referencia,'')||' '||COALESCE(p.f_descripcion,'') as f_producto,\n" +
                "c.f_id as f_idcliente,\n" +
                "COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
                "r.f_idrecord as f_id_referente,\n" +
                "COALESCE(r.f_nombre,'')||' '||COALESCE(r.f_apellido,'') as f_referente,\n" +
                "COALESCE(s.f_precio,0) as f_precio,\n" +
                "COALESCE(s.f_itbis,0) as f_itebis,\n" +
                "COALESCE(s.f_porcentaje,0) as f_porcentaje,\n" +
                "COALESCE(s.f_monto_comision,0) as f_comision,\n" +
                "COALESCE(s.f_cantidad,0) as f_cantidad,\n" +
                "COALESCE(s.f_dia_facturacion,0) as f_dia_factura,\n" +
                "COALESCE(r.f_porciento,0) as f_porciento_referente,\n" +
                "COALESCE(s.f_keyserial,'') as f_serial,\n" +
                "s.f_enviar,\n" +
                "COALESCE(c.f_facturarle_tax,false) as f_tiene_itbis,\n" +
                "s.f_activo\n" +
                "FROM t_servicios_contratados as s \n" +
                "INNER JOIN t_productos as  p on s.f_id_producto = p.f_id\n" +
                "INNER JOIN t_clientes  as c on c.f_id = s.f_id_cliente \n" +
                "LEFT JOIN t_referentes as r on r.f_idrecord = s.f_idreferente " +
                " where s.f_id = $id";
        return sqlService.GetQuery(sql);
    }


    List<Map<String, Object>> ListarReferentes() {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  COALESCE(f_nombre, '') ||' '|| COALESCE(f_apellido, '') as f_nombre,\n" +
                "  COALESCE(f_direccion, '') as f_direccion,\n" +
                "  COALESCE(f_email, '') as f_email,\n" +
                "  COALESCE(f_telefono1, '') as f_telefono1,\n" +
                "  COALESCE(f_telefono2, '') as f_telefono2,\n" +
                "  COALESCE(f_ciudad, '') as f_ciudad,\n" +
                "  COALESCE(f_pais, '') as f_pais,\n" +
                "  COALESCE(f_cuenta_deposito, '') as f_cuenta_deposito,\n" +
                "  COALESCE(f_porciento, 0) as f_porciento\n" +
                "FROM \n" +
                "  public.t_referentes ;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarUsuarios() {
        String sql = "SELECT COALESCE(f_codigo_usuario, 0) as f_codigo_usuario,\n" +
                "       COALESCE(f_nombre, '') ||' '|| COALESCE(f_apellido, '') as f_nombre,\n" +
                "       COALESCE(f_direccion, '') as f_direccion,\n" +
                "       COALESCE(f_telefono, '') as f_telefono,\n" +
                "       COALESCE(f_email, '') as f_email,\n" +
                "       COALESCE(f_id_usuario, '') as f_id_usuario,\n" +
                "       COALESCE(f_fecha_caducidad,null) as f_fecha_caducidad,\n" +
                "       COALESCE(f_activo, false) as f_activo,\n" +
                "       COALESCE(f_password, '') as f_password,\n" +
                "       COALESCE(f_idempleado,0) as f_idempleado\n" +
                "FROM public.t_usuario;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarUsuariosById(Long id) {
        String sql = "SELECT COALESCE(f_codigo_usuario, 0) as f_codigo_usuario,\n" +
                "       COALESCE(f_nombre, '') as f_nombre," +
                "       COALESCE(f_apellido, '') as f_apellido,\n" +
                "       COALESCE(f_direccion, '') as f_direccion,\n" +
                "       COALESCE(f_telefono, '') as f_telefono,\n" +
                "       COALESCE(f_email, '') as f_email,\n" +
                "       COALESCE(f_id_usuario, '') as f_id_usuario,\n" +
                "       to_char(COALESCE(f_fecha_caducidad,null),'MM/dd/yyyy') as f_fecha_caducidad,\n" +
                "       COALESCE(f_activo, false) as f_activo,\n" +
                "       COALESCE(f_password, '') as f_password,\n" +
                "       COALESCE(f_idempleado,0) as f_idempleado\n" +
                "FROM public.t_usuario where f_codigo_usuario = $id;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarUsuariosByIdusuario(String user) {
        String sql = "SELECT COALESCE(f_codigo_usuario, 0) as f_codigo_usuario,\n" +
                "       COALESCE(f_nombre, '') as f_nombre," +
                "       COALESCE(f_apellido, '') as f_apellido,\n" +
                "       COALESCE(f_direccion, '') as f_direccion,\n" +
                "       COALESCE(f_telefono, '') as f_telefono,\n" +
                "       COALESCE(f_email, '') as f_email,\n" +
                "       COALESCE(f_id_usuario, '') as f_id_usuario,\n" +
                "       to_char(COALESCE(f_fecha_caducidad,null),'MM/dd/yyyy') as f_fecha_caducidad,\n" +
                "       COALESCE(f_activo, false) as f_activo,\n" +
                "       COALESCE(f_password, '') as f_password,\n" +
                "       COALESCE(f_idempleado,0) as f_idempleado\n" +
                "FROM public.t_usuario where f_id_usuario = '$user';";

        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarReferentesbyId(Long id) {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  COALESCE(f_banco,'') as f_banco,\n" +
                "  COALESCE(f_nombre, '') as f_nombre,\n" +
                "  COALESCE(f_apellido, '') as f_apellido,\n" +
                "  COALESCE(f_direccion, '') as f_direccion,\n" +
                "  COALESCE(f_email, '') as f_email,\n" +
                "  COALESCE(f_telefono1, '') as f_telefono1,\n" +
                "  COALESCE(f_telefono2, '') as f_telefono2,\n" +
                "  COALESCE(f_ciudad, '') as f_ciudad,\n" +
                "  COALESCE(f_pais, '') as f_pais,\n" +
                "  COALESCE(f_cuenta_deposito, '') as f_cuenta_deposito,\n" +
                "  COALESCE(f_porciento, 0) as f_porciento\n" +
                "FROM \n" +
                "  public.t_referentes  where f_idrecord =$id ;";
        return sqlService.GetQuery(sql);
    }

    def salvar_cliente(String id, String nombre, String contacto, String email, String identificacion, String direccion, String ciudad, String estado, String pais, String zip_code, String telefono_1, String telefono_2, String telefono_contacto, String fax, String contacto_cobros, Boolean enviar_email, Boolean retiene_isr, Boolean retiene_itebis, Boolean facturar_tax, Boolean es_empresa, Long moneda) {
        if (!id.equals("")) {
            Tclientes c = Tclientes.findById(id.toLong());
            if (c) {
                c.setfNombreEmpresa(nombre);
                c.setfContacto(contacto);
                c.setfEmail(email);
                c.setfIdIdentificacion(identificacion);
                c.setfDireccion(direccion);
                c.setfCiudad(ciudad);
                c.setfEstado(estado);
                c.setfPais(pais);
                c.setfZipcode(zip_code);
                c.setfTelefono1(telefono_1);
                c.setfTelefono2(telefono_2);
                c.setfCelContacto(telefono_contacto);
                c.setfFax(fax);
                c.setfContactoCobro(contacto_cobros);
                c.setfEnviarEmail(enviar_email);
                c.setfRetieneIsr(retiene_isr);
                c.setfRetieneItbis(retiene_itebis);
                c.setfFacturarleTax(facturar_tax);
                c.setfMoneda(moneda);


                Integer emp = 0;
                if (es_empresa) {
                    emp = 1;
                }
                c.setfEmpresaPersona(emp);
                c.save(failOnError: true);

            } else {
                Integer emp = 0;
                if (es_empresa) {
                    emp = 1;
                }
                def cliente = new Tclientes(fActivo: true, fContactoCobro: contacto_cobros, fCelContacto: telefono_contacto, fCiudad: ciudad, fContacto: contacto, fDireccion: direccion, fEmail: email, fEmpresaPersona: emp, fIdIdentificacion: identificacion, fEnviarEmail: enviar_email, fEstado: estado, fFacturarleTax: facturar_tax, fFax: fax, fNombreEmpresa: nombre, fPais: pais, fRetieneIsr: retiene_isr, fRetieneItbis: retiene_itebis, fTelefono1: telefono_1, fTelefono2: telefono_2, fZipcode: zip_code, fMoneda: moneda)
                cliente.save(failOnError: true);
            }
        } else {
            Integer emp = 0;
            if (es_empresa) {
                emp = 1;
            }
            def cliente = new Tclientes(fActivo: true,fContactoCobro: contacto_cobros, fCelContacto: telefono_contacto, fCiudad: ciudad, fContacto: contacto, fDireccion: direccion, fEmail: email, fEmpresaPersona: emp, fIdIdentificacion: identificacion, fEnviarEmail: enviar_email, fEstado: estado, fFacturarleTax: facturar_tax, fFax: fax, fNombreEmpresa: nombre, fPais: pais, fRetieneIsr: retiene_isr, fRetieneItbis: retiene_itebis, fTelefono1: telefono_1, fTelefono2: telefono_2, fZipcode: zip_code, fMoneda: moneda)
            cliente.save(failOnError: true);
        }

    }


    def salvar_producto(String id, String referencia, String descripcion, String version, String sistema, BigDecimal precio, Integer cantidad, Boolean servicio, Boolean genera_serial, Boolean tiene_itebis, Boolean activo) {
        if (!id.equals("")) {
            Tproductos c = Tproductos.findById(id.toLong());
            if (c) {
                c.setfReferencia(referencia);
                c.setfDescripcion(descripcion);
                c.setfVersion(version);
                c.setfSistema(sistema);
                c.setfPrecio(precio);
                c.setfCantidad(cantidad);
                c.setfServicio(servicio);
                c.setfGeneraSerial(genera_serial);
                c.setfItbis(tiene_itebis);
                c.setfActivo(activo);

                c.save(failOnError: true);

            } else {
                def producto = new Tproductos(fCantidad: cantidad, fDescripcion: descripcion, fGeneraSerial: genera_serial, fItbis: tiene_itebis, fPrecio: precio, fReferencia: referencia, fServicio: servicio, fSistema: sistema, fVersion: version, fActivo: activo);
                producto.save(failOnError: true);
            }
        } else {
            def producto = new Tproductos(fCantidad: cantidad, fDescripcion: descripcion, fGeneraSerial: genera_serial, fItbis: tiene_itebis, fPrecio: precio, fReferencia: referencia, fServicio: servicio, fSistema: sistema, fVersion: version, fActivo: activo);
            producto.save(failOnError: true);
        }
    }

    def salvar_referente(String id, String nombre, String apellido, String email, String cuenta_deposito, String direccion, String ciudad, String pais, String telefono1, String telefono2, BigDecimal porciento, String banco) {
        if (!id.equals("")) {
            Treferentes c = Treferentes.findById(id.toLong());
            if (c) {
                c.setfNombre(nombre);
                c.setfApellido(apellido);
                c.setfEmail(email);
                c.setfCuentaDeposito(cuenta_deposito);
                c.setfDireccion(direccion);
                c.setfCiudad(ciudad);
                c.setfPais(pais);
                c.setfTelefono2(telefono2);
                c.setfTelefono1(telefono1);
                c.setfPorcentaje(porciento);
                c.setfBanco(banco);

                c.save(failOnError: true);

            } else {
                def referentes = new Treferentes(fApellido: apellido, fCiudad: ciudad, fCuentaDeposito: cuenta_deposito, fDireccion: direccion, fEmail: email, fNombre: nombre, fPais: pais, fTelefono1: telefono1, fTelefono2: telefono2, fPorcentaje: porciento, fBanco: banco)
                referentes.save(failOnError: true);
            }
        } else {
            def referentes = new Treferentes(fApellido: apellido, fCiudad: ciudad, fCuentaDeposito: cuenta_deposito, fDireccion: direccion, fEmail: email, fNombre: nombre, fPais: pais, fTelefono1: telefono1, fTelefono2: telefono2, fPorcentaje: porciento, fBanco: banco)
            referentes.save(failOnError: true);
        }


    }

    def salvar_servicios_contratdos(String id, Long idprodcuto, Long idcliente, BigDecimal precio, Long dia_factura, Boolean activo, String key, Boolean enviar, Long idreferente, BigDecimal monto_comision, BigDecimal cantidad, BigDecimal itebis, Boolean variable, BigDecimal porciento_comision,Boolean cobrable,Long idservidor) {
        if (!id.equals("")) {
            TserviciosContratados c = TserviciosContratados.findById(id.toLong());
            if (c) {
                c.setfIdProducto(idprodcuto);
                c.setfIdCliente(idcliente);
                c.setfPrecio(precio);
                c.setfDiaFacturacion(dia_factura);
                c.setfActivo(activo);
                c.setfKeyserial(key);
                c.setfEnviar(enviar);
                c.setfIdreferente(idreferente);
                c.setfMontoComision(monto_comision);
                c.setfPorcentaje(porciento_comision);
                c.setfCantidad(cantidad);
                c.setfItbis(itebis);
                c.setfCVariable(variable);
                c.setfCobrable(cobrable);
                c.setfUrlServidorActualizacion(idservidor);


                c.save(failOnError: true);

            } else {
                def servicio = new TserviciosContratados(fUrlServidorActualizacion: idservidor, fCobrable: cobrable, fItbis: itebis, fCantidad: cantidad, fPorcentaje: porciento_comision, fActivo: activo, fDiaFacturacion: dia_factura, fEnviar: enviar, fIdCliente: idcliente, fIdProducto: idprodcuto, fIdreferente: idreferente, fKeyserial: key, fMontoComision: monto_comision, fPrecio: precio, fCVariable: variable);
                servicio.save(failOnError: true);
            }
        } else {
            def servicio = new TserviciosContratados(fUrlServidorActualizacion: idservidor, fCobrable: cobrable, fItbis: itebis, fCantidad: cantidad, fPorcentaje: porciento_comision, fActivo: activo, fDiaFacturacion: dia_factura, fEnviar: enviar, fIdCliente: idcliente, fIdProducto: idprodcuto, fIdreferente: idreferente, fKeyserial: key, fMontoComision: monto_comision, fPrecio: precio, fCVariable: variable);
            servicio.save(failOnError: true)
        }


    }

    List<Map<String, Object>> ListarCxCPorClientes(Long id) {
        String sql = "SELECT f.f_nofactura as f_id,\n" +
                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_factura,\n" +
                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
                "       COALESCE(f.f_monto, 0) as f_monto,\n" +
                "       COALESCE(f.f_balance, 0) as f_balance,\n" +
                "       0 as f_a_pagar,\n" +
                "       'No Accion' as f_concepto\n" +
                "FROM t_facturas as f\n" +
                "     INNER JOIN t_clientes as c on c.f_id = f.f_id_cliente\n" +
                "WHERE c.f_id = $id and f.f_pagada = false\n" +
                "ORDER BY f.f_fecha_vencimiento ASC";


        return sqlService.GetQuery(sql);

    }


    List<Map<String, Object>> ListarRecibos(String id, String f1, String f2) {
        String sql = "select\n" +
                "r.f_whole as f_numero,\n" +
                "to_char(r.f_fecha,'yyyy/MM/dd') as f_fecha,\n" +
                "COALESCE( cli.f_nombre_empresa,'-') as f_cliente,\n" +
                "r.f_concepto as f_concepto,\n" +
                "COALESCE(r.f_monto,0) as f_monto\n" +
                " from t_recibos as r\n" +
                "LEFT JOIN t_clientes as cli on cli.f_id = r.f_idcliente \n" +
                "where 1=1 ";

        if (!f1.equals("") && !f2.equals("")) {
            sql = sql + " and r.f_fecha::date BETWEEN '$f1'::date and '$f2'::date ";
        } else {
            if (!f1.equals("")) {
                sql = sql + " and r.f_fecha::date >= '$f1'::date ";
            } else if (!f2.equals("")) {
                sql = sql + " and r.f_fecha::date <= '$f2'::date ";
            }
        }


        if (!id.toString().equals("") && !id.toString().is(null)) {

            sql = sql + " and r.f_idcliente =$id ";
        }


        sql = sql + " ORDER BY f_numero ASC;";

        //   println(sql);
        return sqlService.GetQuery(sql);

    }


    def salvar_recibo(String header, String detalle) {
        Map<String, Object> recibo = JSON.parse(header);

        Tusuario user = seguridadService.GetUsuarioSession() as Tusuario;

        Long numero = sqlService.GetSecuencia(recibo.get("f_tipo_recibo").toString()) as Long;
        def r = new Trecibos(fConcepto: recibo.get("f_concepto").toString(), fFecha: sqlService.GetNow(), fHechoPor: user.id, fMonto: recibo.get("f_monto").toString().toBigDecimal(), fTipoRecibo: recibo.get("f_tipo_recibo").toString(), fWhole: sqlService.Get_Concatenar_tipo_numero_to_char(recibo.get("f_tipo_recibo").toString(), numero.toString(), 6.toInteger()), fNoRecibo: numero, fMoneda: recibo.get("f_tipo_moneda_recibo").toString().toLong(), fIdcliente: recibo.get("f_idcliente").toString().toLong());
        r.save(failOnError: true);
        def json = JSON.parse(detalle).collect();
        for (Map<String, Object> tupla : json) {
            def a = new TaplicacionesRecibo(fNoRecibo: r.fNoRecibo, fMonto: tupla.get("f_monto").toString().toBigDecimal(), fFecha: sqlService.GetNow(), fDocumento: tupla.get("f_documento").toString(), fFactura: tupla.get("f_no_factura"), fHechoPor: user.id, fTipoRecibo: r.fTipoRecibo);
            a.save(failOnError: true);
        }

    }

    def salvar_factura(String header, String detalle) {
        Map<String, Object> factura = JSON.parse(header);

        Tusuario user = seguridadService.GetUsuarioSession() as Tusuario;
        //contado con el tipo 1
        if (factura.get("f_tipo").toString().equals('1')) {


            Long numero = sqlService.GetSecuencia(factura.get("f_tipofactura").toString()) as Long;
            // Date fecha_vencimiento = new Date(factura.get("f_fecha_vencimiento").toString());
            String[] split_fecha = factura.get("f_fecha_vencimiento").toString().split('/');
            String aux = split_fecha[2].toString() + "/" + split_fecha[1].toString() + "/" + split_fecha[0].toString();
            Date fecha_vencimiento = new Date(aux);

            def r = new Tfacturas(fCancelada: false, fBalance: 0.toBigDecimal(), fComentario: factura.get("f_comentario").toString(), fEnviada: true, fEnvio: 0.toBigDecimal(), fFecha: sqlService.GetNow(), fFechaVencimiento: fecha_vencimiento, fIdCliente: factura.get("f_id_cliente").toString().toLong(), fMonto: factura.get("f_monto").toString().toBigDecimal(), fMontoIsr: 0.toBigDecimal(), fNofactura: numero, fPagada: true, fTax: factura.get("f_tax").toString().toBigDecimal(), fTipofactura: factura.get("f_tipofactura").toString(), fHechoPor: user.id, fMoneda: factura.get("f_moneda").toString().toLong());
            r.save(failOnError: true);

            def json = JSON.parse(detalle).collect();
            for (Map<String, Object> tupla : json) {
                def a = new TdetFactura(fNofactura: numero, fCantidad: tupla.get("f_cantidad").toString().toBigDecimal(), fIdProducto: tupla.get("f_id_producto").toString().toLong(), fItbis: tupla.get("f_itbis").toString().toBigDecimal(), fPrecio: tupla.get("f_precio").toString().toBigDecimal(), fTipodoc: r.fTipofactura, fComentario: tupla.get("f_comentario").toString());
                a.save(failOnError: true);
            }


            return r.id + "<-*->" + VerReporteFacturaByIdBase64(r.id);

        }//credito con el tipo 2
        else if (factura.get("f_tipo").toString().equals('2')) {

            Long numero = sqlService.GetSecuencia(factura.get("f_tipofactura").toString()) as Long;
            // Date fecha_vencimiento = new Date(factura.get("f_fecha_vencimiento").toString());
            String[] split_fecha = factura.get("f_fecha_vencimiento").toString().split('/');
            String aux = split_fecha[2].toString() + "/" + split_fecha[1].toString() + "/" + split_fecha[0].toString();
            Date fecha_vencimiento = new Date(aux);
            def r = new Tfacturas(fCancelada: false, fBalance: 0.toBigDecimal(), fComentario: factura.get("f_comentario").toString(), fEnviada: true, fEnvio: 0.toBigDecimal(), fFecha: sqlService.GetNow(), fFechaVencimiento: fecha_vencimiento, fIdCliente: factura.get("f_id_cliente").toString().toLong(), fMonto: factura.get("f_monto").toString().toBigDecimal(), fMontoIsr: 0.toBigDecimal(), fNofactura: numero, fPagada: false, fTax: factura.get("f_tax").toString().toBigDecimal(), fTipofactura: factura.get("f_tipofactura").toString(), fHechoPor: user.id, fMoneda: factura.get("f_moneda").toString().toLong());
            r.save(failOnError: true);

            def json = JSON.parse(detalle).collect();
            for (Map<String, Object> tupla : json) {
                def a = new TdetFactura(fNofactura: numero, fCantidad: tupla.get("f_cantidad").toString().toBigDecimal(), fIdProducto: tupla.get("f_id_producto").toString().toLong(), fItbis: tupla.get("f_itbis").toString().toBigDecimal(), fPrecio: tupla.get("f_precio").toString().toBigDecimal(), fTipodoc: r.fTipofactura, fComentario: tupla.get("f_comentario").toString());
                a.save(failOnError: true);
            }

            return r.id + "<-*->" + VerReporteFacturaByIdBase64(r.id);
        }

    }

    List<Map<String, Object>> ListarFactutrasPagosGenerales() {
//        String sql = "\n" +
//                "SELECT f.f_nofactura as f_id,\n" +
//                " COALESCE(c.f_nombre_empresa,'') as f_cliente,\n" +
//                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
//                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
//                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_factura,\n" +
//                "       COALESCE(f.f_balance, 0) as f_balance,\n" +
//                "       false as f_pagar\n" +
//                "FROM t_facturas as f\n" +
//                "     INNER JOIN t_clientes as c on c.f_id = f.f_id_cliente\n" +
//                "WHERE  f.f_pagada = false\n" +
//                "ORDER BY  f.f_fecha_vencimiento desc";


        String sql = "SELECT f.f_nofactura as f_id,\n" +
                "       COALESCE(c.f_nombre_empresa, '') as f_cliente,\n" +
                "       to_char(f.f_fecha, 'yyyy/MM/dd') as f_fecha,\n" +
                "       to_char(f.f_fecha_vencimiento, 'yyyy/MM/dd') as f_vencimiento,\n" +
                "       f.f_tipofactura || to_char(f.f_nofactura, 'FM0000000') as f_factura,\n" +
                "       COALESCE(f.f_balance, 0) as f_balance,\n" +
                "       COALESCE(f.f_monto + f.f_tax, 0) as f_monto,\n" +
                "       false as f_pagar,\n" +
                "       CASE\n" +
                "         WHEN f.f_moneda = 1 THEN 'RD'\n" +
                "         WHEN f.f_moneda = 2 THEN 'US'\n" +
                "         WHEN f.f_moneda = 3 THEN 'EUR'\n" +
                "       END AS f_moneda,\n" +
                "f.f_moneda as f_idmoneda,\n" +
                "f.f_comentario,\n" +
                "f.f_id_cliente,\n" +
                "f.f_idrecord as f_idfactura" +
                "\n" +
                "FROM t_facturas as f\n" +
                "     INNER JOIN t_clientes as c on c.f_id = f.f_id_cliente\n" +
                "WHERE f.f_pagada = false and f.f_cancelada = false\n" +
                "ORDER BY f.f_fecha asc";

        // println sql;


        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarVerFacturas(String dato, String f1, String f2) {
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
                "     LEFT JOIN t_clientes as c on f.f_id_cliente = c.f_id where 1=1 " +
                "" +
                "";

        if (!f1.equals("") && !f2.equals("")) {
            sql = sql + " and f.f_fecha::date BETWEEN '$f1'::date and '$f2'::date ";
        } else {
            if (!f1.equals("")) {
                sql = sql + " and f.f_fecha::date >= '$f1'::date ";
            } else if (!f2.equals("")) {
                sql = sql + " and f.f_fecha::date <= '$f2'::date ";
            }
        }


        if (!dato.toString().equals("")) {

            sql = sql + "  and   (c.f_nombre_empresa ilike '%$dato%'" +
                    " or c.f_telefono1 ilike '%$dato%'" +
                    " or c.f_telefono2 ilike '%$dato%'" +
                    " or c.f_id_identificacion ilike '%$dato%'" +
                    " or c.f_email ilike '%$dato%'" +
                    "" +
                    ") ";
        }





        sql = sql + " ORDER BY  f_cliente,f.f_fecha desc;";

         println(sql);


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

    ApplicationContext getApplicationContext() {
        return Holders.grailsApplication.mainContext
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
            // reporte = byt.toByteArray().encodeBase64();

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

    def EnviarEmailArchivoComprimido(String[] correo, byte[] archivo, String nombre_archivo, String asunto, String mensaje) {

        sendMail {

            multipart true
            to correo
            from "noreply@jqmicro.com";
            subject asunto
            html mensaje
            // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
            //To get started quickly, try the following
            //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
//              attachBytes nombre, 'application/pdf', archivo;
            attachBytes nombre_archivo, "application/x-compressed", archivo;

        }
    }


    def EnviarEmail(String correo, String asunto, String mensaje) {
        def nombre = sqlService.GetNow().toString() + ".pdf";
        sendMail {

            multipart true
            to correo
            from "noreply@jqmicro.com";
            subject asunto
            html mensaje
            // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
            //To get started quickly, try the following
            //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
//            attachBytes nombre, 'application/pdf', archivo;

        }
    }

    List<Map<String, Object>> ListarMensajes() {
        String sql = "SELECT m.f_id,\n" +
                "       c.f_id as f_idcliente,\n" +
                "       c.f_nombre_empresa as f_cliente,\n" +
                "       m.f_msg as f_mensaje,\n" +
                "       CASE m.f_tipo \n" +
                "       WHEN 1 THEN 'Aviso'\n" +
                "       WHEN 2 THEN 'Bloqueado'\n" +
                "       END as f_tipo,\n" +
                "       m.f_activo\n" +
                "FROM public.t_msg_clientes as m\n" +
                "     inner JOIN t_clientes as c on m.f_idcliente = c.f_id";

        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarMensajesById(Long id) {
        String sql = "SELECT m.f_id,\n" +
                "       c.f_id as f_idcliente,\n" +
                "       c.f_nombre_empresa as f_cliente,\n" +
                "       m.f_msg as f_mensaje,\n" +
                "       CASE m.f_tipo \n" +
                "       WHEN 1 THEN 'Aviso'\n" +
                "       WHEN 2 THEN 'Bloqueado'\n" +
                "       END as f_tipo,\n" +
                "       m.f_activo\n" +
                "FROM public.t_msg_clientes as m\n" +
                "     inner JOIN t_clientes as c on m.f_idcliente = c.f_id where m.f_id =$id";

        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarMensajesByEmpresa(Long id) {
        String sql = " SELECT \n" +
                "  f_id,\n" +
                "  f_msg,\n" +
                "  f_activo,\n" +
                "  f_fecha,\n" +
                "  f_idcliente,\n" +
                "  f_tipo\n" +
                "FROM \n" +
                "  public.t_msg_clientes where   f_idcliente = $id and f_activo = true;";



        return sqlService.GetQuery(sql);
    }


    List<Map<String, Object>> ListarDistribucionCorreosClientes(Long id) {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_idcliente,\n" +
                "  f_correos\n" +
                "FROM \n" +
                "  tk.t_distribucion_correos where f_idcliente = $id ;";



        return sqlService.GetQuery(sql);
    }

    def salvar_pagos_generales(String json, String tipo_moneda_recibo) {
        List<String> lista_clientes = new ArrayList<String>();
        def list = JSON.parse(json).collect();
        for (Map<String, Object> tupla : list) {
            if (validar_lista(lista_clientes, tupla.get('f_cliente').toString())) {
                continue;
            } else {
                lista_clientes.add(tupla.get('f_cliente').toString());
            }
        }
        BigDecimal monto_recibo = 0.toBigDecimal();
        String concepto = '';
        for (int i = 0; i < lista_clientes.size(); i++) {

            for (Map<String, Object> tupla : list) {
                if (lista_clientes.get(i).toString() == tupla.get("f_cliente").toString()) {
                    monto_recibo = monto_recibo + tupla.get("f_balance").toString().toBigDecimal();
                    concepto = concepto + 'Saldado : ' + tupla.get("f_factura").toString() + ';'
                }

            }

            Tusuario user = seguridadService.GetUsuarioSession() as Tusuario;

            Long numero = sqlService.GetSecuencia('REC') as Long;
            def r = new Trecibos(fConcepto: concepto, fFecha: sqlService.GetNow(), fHechoPor: user.id, fMonto: monto_recibo, fTipoRecibo: 'REC', fWhole: sqlService.Get_Concatenar_tipo_numero_to_char('REC', numero.toString(), 6.toInteger()), fNoRecibo: numero, fMoneda: tipo_moneda_recibo.toLong());
            r.save(failOnError: true);

            for (Map<String, Object> tupla : list) {
                if (lista_clientes.get(i).toString() == tupla.get("f_cliente").toString()) {
                    def a = new TaplicacionesRecibo(fNoRecibo: r.fNoRecibo, fMonto: tupla.get("f_balance").toString().toBigDecimal(), fFecha: sqlService.GetNow(), fDocumento: tupla.get("f_factura").toString(), fFactura: tupla.get("f_id"), fHechoPor: user.id, fTipoRecibo: r.fTipoRecibo);
                    a.save(failOnError: true);
                }

            }

            monto_recibo = 0.toBigDecimal();
            concepto = '';

        }
    }

    Boolean validar_lista(List list, String cliente) {
        Boolean valor = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString() == cliente) {
                valor = true;
            }

        }

        return valor;

    }

    def salvar_nota_credito(String header, String detalle) {
        Map<String, Object> recibo = JSON.parse(header);

        Tusuario user = seguridadService.GetUsuarioSession() as Tusuario;

        Long numero = sqlService.GetSecuencia(recibo.get("f_tipo_recibo").toString()) as Long;
        def r = new Trecibos(fConcepto: recibo.get("f_concepto").toString() + " \n\n Nota :\n\n" + recibo.get("f_nota").toString(), fFecha: sqlService.GetNow(), fHechoPor: user.id, fMonto: recibo.get("f_monto").toString().toBigDecimal(), fTipoRecibo: recibo.get("f_tipo_recibo").toString(), fWhole: sqlService.Get_Concatenar_tipo_numero_to_char(recibo.get("f_tipo_recibo").toString(), numero.toString(), 6.toInteger()), fNoRecibo: numero, fIdcliente: recibo.get("f_idcliente").toString().toLong());
        r.save(failOnError: true);
        def json = JSON.parse(detalle).collect();
        for (Map<String, Object> tupla : json) {
            def a = new TaplicacionesRecibo(fNoRecibo: r.fNoRecibo, fMonto: tupla.get("f_monto").toString().toBigDecimal(), fFecha: sqlService.GetNow(), fDocumento: tupla.get("f_documento").toString(), fFactura: tupla.get("f_no_factura"), fHechoPor: user.id, fTipoRecibo: r.fTipoRecibo);
            a.save(failOnError: true);
        }

    }

    def salvar_nota_debito(Long id, BigDecimal monto, String fecha, String nota) {
        Tusuario user = seguridadService.GetUsuarioSession() as Tusuario;

        Long numero = sqlService.GetSecuencia('NDB') as Long;
        Date d = new Date(fecha);

        def f = new Tfacturas(fFecha: sqlService.GetNow(), fMonto: monto, fBalance: monto, fCancelada: false, fComentario: nota, fEnviada: false, fEnvio: 0.toBigDecimal(), fFechaVencimiento: d, fIdCliente: id, fMontoIsr: 0.toBigDecimal(), fNofactura: numero, fPagada: false, fTax: 0.toBigDecimal(), fTipofactura: 'NDB', fWhole: sqlService.Get_Concatenar_tipo_numero_to_char('NDB', numero.toString(), 6.toInteger()));
        f.save(failOnError: true);

    }

    def salvar_mensajes(String id, Long idcliente, String mensaje, Boolean activo, Long tipo) {
        if (!id.equals("")) {
            TmsgClientes c = TmsgClientes.findById(id.toLong());
            if (c) {
                c.setfMsg(mensaje);
                c.setfActivo(activo);
                c.setfIdcliente(idcliente);
                c.setfTipo(tipo);
                c.save(failOnError: true);

            } else {
                def m = new TmsgClientes(fActivo: activo, fFecha: sqlService.GetNow(), fIdcliente: idcliente, fMsg: mensaje, fTipo: tipo);
                m.save(failOnError: true);
            }
        } else {
            def m = new TmsgClientes(fActivo: activo, fFecha: sqlService.GetNow(), fIdcliente: idcliente, fMsg: mensaje, fTipo: tipo);
            m.save(failOnError: true);
        }


    }


    def salvarSerialFormula(String id, String serial, String cliente, String imei, Boolean activo, String server, Long tipo) {
        if (!id.equals("")) {
            TserialFormula c = TserialFormula.findById(id.toLong());
            if (c) {
                c.setfSerial(serial);
                c.setfActivo(activo);
                c.setfCliente(cliente);
                c.setfImei(imei);
                c.setfServer(server.toLong());
                c.setfTipoProyecto(tipo);
                c.save(failOnError: true);

            } else {
                def m = new TserialFormula(
                        fActivo: activo,
                        fCliente: cliente,
                        fFecha: sqlService.GetNow(),
                        fImei: imei,
                        fSerial: serial,
                        fServer: server.toLong(),
                        fTipoProyecto: tipo.toLong()
                );
                m.save(failOnError: true);
            }
        } else {
            def m = new TserialFormula(
                    fActivo: activo,
                    fCliente: cliente,
                    fFecha: sqlService.GetNow(),
                    fImei: imei,
                    fSerial: serial,
                    fServer: server.toLong(),
                    fTipoProyecto: tipo.toLong()
            );
            m.save(failOnError: true);
        }


    }


    List<Map<String, Object>> get_verificar_actualizacion(String proyecto) {
        String sql = "select \n" +
                "  \t*\n" +
                "  from\n" +
                "  \tcfg.t_actualizacion_sistema a\n" +
                "  where\n" +
                "  \ta.f_proyecto ilike '$proyecto'";
        return sqlService.GetQuery(sql);
    }

    def EnviarEmail(String[] correo, String asunto, String mensaje) {

        try {
            def nombre = sqlService.GetNow().toString() + ".pdf";
            sendMail {
                multipart true
                to correo
                //from "pabellopez1091@gmail.com";
                from "noreply@jqmicro.com";
                subject asunto
//                body mensaje
                html mensaje
                // attachBytes "Some-File-Name.xml", "text/xml", contentOrder.getBytes("UTF-8")
                //To get started quickly, try the following
                //attachBytes './web-app/images/grails_logo.jpg','image/jpg', new File('./web-app/images/grails_logo.jpg').readBytes()
                // attachBytes nombre,'text/pdf', archivo;


            }
        }
        catch (Exception e) {
            println("Error al enviar mensaje es ----> " + e.getMessage())
            //  throw e;
        }
    }


    def salvar_usuario(String id, Long idempleado, String nombre, String apellido, String email, String telefono, String usuario, String password, String direccion, Boolean activo, String fecha) {
        if (!id.equals("")) {
            Tusuario c = Tusuario.findById(id.toLong());
            if (c) {
                c.setfActivo(activo);
                c.setfApellido(apellido);
                c.setfNombre(nombre);
                c.setfDireccion(direccion);
                def f = null;
                if (!fecha.is("null") && !fecha.equals("")) {
                    f = new Date(fecha);
                }
                c.setfFechaCaducidad(f);
                c.setfIdempleado(idempleado);
                c.setfIdUsuario(usuario);
                if (!password.equals("") && c.getfPassword() != password.encodeAsMD5()) {
                    c.setfPassword(password.encodeAsMD5());
                }
                c.setfTelefono(telefono);
                c.setfEmail(email);
                c.save(failOnError: true);

            } else {
                def f = null;
                if (!fecha.is("null") && !fecha.equals("")) {
                    f = new Date(fecha);
                }
                def m = new Tusuario(fNombre: nombre, fActivo: activo, fApellido: apellido, fDireccion: direccion, fPassword: password.encodeAsMD5(), fEmail: email, fFechaCaducidad: f, fIdempleado: idempleado, fTelefono: telefono, fIdUsuario: usuario);
                m.save(failOnError: true);
            }
        } else {
            def f = null;
            if (!fecha.is("null") && !fecha.equals("")) {
                f = new Date(fecha);
            }
            def m = new Tusuario(fNombre: nombre, fActivo: activo, fApellido: apellido, fDireccion: direccion, fPassword: password.encodeAsMD5(), fEmail: email, fFechaCaducidad: f, fIdempleado: idempleado, fTelefono: telefono, fIdUsuario: usuario);
            m.save(failOnError: true);
        }


    }

    def salvar_permisos_usuario(Long idusuario, String ids) {

        String sql = "delete from TpermisosUsuarios as b where b.fIdusuario = ?0";
        Collection e = [];
        e.add(idusuario);
        TpermisosUsuarios.executeUpdate(sql, e);
        String[] opciones = ids.split(',');

        for (int i = 0; i < opciones.size(); i++) {

            def p = new TpermisosUsuarios(fIdopcion: opciones[i].toString().toLong(), fIdusuario: idusuario);
            p.save(failOnError: true);
        }
    }


    def gerenar_factura_cliente(String id_cliente) {
        sqlService.Beginwork();


        BigDecimal monto_factura = 0.0;
        BigDecimal monto_tax = 0.0;
        BigDecimal tax = 0.0;

        String numero_factura = sqlService.GetQuery("select getsecuencia('FCR') as f_secuencia").first().get("f_secuencia");

        String sql_servicios_contratados = "SELECT * FROM t_servicios_contratados  WHERE  f_activo=TRUE and f_id_cliente=" + id_cliente;

        List list_rs_servicios_contratados = sqlService.GetQuery(sql_servicios_contratados);

        for (Map<String, Object> rs_servicios_contratados : list_rs_servicios_contratados) {

            if (sqlService.GetQuery("select cobrartax($id_cliente) as f_cobra").first().get("f_cobra").toString().toBoolean() == true) {
                BigDecimal precio_servicio = rs_servicios_contratados.get("f_precio").toString().toBigDecimal();
                BigDecimal tax_preferencia = sqlService.GetQuery("select f_tax from t_preferencia limit 1").first().get("f_tax").toString().toBigDecimal() / 100;
                tax = precio_servicio * tax_preferencia;
            }

            String insert_detalle = "insert " +
                    "into t_det_factura " +
                    "VALUES(" +
                    numero_factura + "," +
                    "'FCR'," +
                    rs_servicios_contratados.get("f_id_producto").toString() + "," +
                    rs_servicios_contratados.get("f_precio").toString().toBigDecimal() + "," +
                    "'1'," +
                    tax + ")";
            sqlService.EjecutarQuery(insert_detalle);

            monto_factura = monto_factura + rs_servicios_contratados.get("f_precio").toString().toBigDecimal();
            monto_tax = monto_tax + tax;
            BigDecimal isr = 0.0;


            if (sqlService.GetQuery("select f_retiene_isr from t_clientes where f_id =$id_cliente").first().get("f_retiene_isr").toString().toBoolean() == true) {
                isr = monto_factura * (sqlService.GetQuery("select f_isr from t_preferencia limit 1").first().get("f_isr").toString().toBigDecimal() / 100);

            }

            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            Tusuario user = session.getAttribute("Usuario") as Tusuario;

            String insert_factura = "INSERT INTO t_facturas " +
                    "(" +
                    "  f_nofactura," +
                    "  f_tipofactura," +
                    "  f_fecha," +
                    "  f_fecha_vencimiento," +
                    "  f_monto," +
                    "  f_id_cliente," +
                    "  f_pagada," +
                    "  f_tax," +
                    "  f_monto_isr," +
                    "  f_enviada," +
                    "  f_balance," +
                    "  f_hecho_por," +
                    "  f_moneda" +
                    ")" +
                    " VALUES(" +
                    numero_factura + "," +
                    "'FCR'," +
                    "now()," +
                    "(SELECT (now()+'7 days'::INTERVAL )::TIMESTAMP)," +
                    monto_factura + "," +
                    id_cliente + "," +
                    "false," +
                    monto_tax + "," +
                    isr + "," +
                    "false," +
                    monto_factura + "," +
                    "${user.id}," +
                    "1" +
                    ")";

            sqlService.EjecutarQuery(insert_factura);
            Tfacturas f = Tfacturas.findByFNofactura(numero_factura.toString().toLong());
            enviarFacturaClienteString(f.id, id_cliente.toLong());

        }




        sqlService.Commit();

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

    def salvar_distribucion_correos(Long idcliente, String correos) {
        TDistribucionCorreos distribucionCorreos = TDistribucionCorreos.findByFIdcliente(idcliente);
        if (distribucionCorreos) {
            distribucionCorreos.setfCorreos(correos);
            distribucionCorreos.save(failOnError: true);
        } else {
            def a = new TDistribucionCorreos(fIdcliente: idcliente, fCorreos: correos);
            a.save(failOnError: true);
        }

    }

    def GetUsuarioTickets() {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_usuario,\n" +
                "  f_pass,\n" +
                "  f_idcliente,\n" +
                "  f_activo,\n" +
                "  f_email,\n" +
                "  f_nombre,\n" +
                "  f_apellido\n" +
                "FROM \n" +
                "  tk.t_usuarios ;";

        List list1 = sqlService.GetQuery(sql);

        return list1
    }

    def GetUsuarioTicketsById(Long id) {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_usuario,\n" +
                "  f_pass,\n" +
                "  f_idcliente,\n" +
                "  f_activo,\n" +
                "  f_email,\n" +
                "  f_nombre,\n" +
                "  f_apellido\n" +
                "FROM \n" +
                "  tk.t_usuarios where f_idrecord = $id ;";

        List list1 = sqlService.GetQuery(sql);

        return list1
    }


    def GetListarVisualizaciones() {
        String sql = "SELECT n.f_idrecord, n.f_idticket,n.f_titulo,t.f_descipcion FROM tk.t_notificaciones AS n\n" +
                "INNER JOIN tk.t_tickets as t on t.f_idrecord = n.f_idticket\n" +
                "WHERE n.f_visualizado = FALSE OR n.f_visualizado IS NULL;";
        List list1 = sqlService.GetQuery(sql);

        return list1
    }

    def SetVisualizado(Long id) {
        sqlService.Beginwork();
        String sql = "update tk.t_notificaciones  SET f_visualizado = true WHERE f_idrecord =$id ;";
        List list1 = sqlService.EjecutarQuery(sql);
        sqlService.Commit();

        return list1
    }

    def salvar_usuario_tickets(String id, Long idcliente, String nombre, String apellido, String email, String usuario, String password, Boolean activo) {
        if (!id.equals("")) {
            Tusuarios c = Tusuarios.findById(id.toLong());
            if (c) {
                c.setfActivo(activo);
                c.setfApellido(apellido);
                c.setfNombre(nombre);

                c.setfIdcliente(idcliente);
                c.setfUsuario(usuario);
                if (!password.equals("") && c.getfPass() != password.encodeAsMD5()) {
                    c.setfPass(password.encodeAsMD5());
                }
                c.setfEmail(email);
                c.save(failOnError: true);

            } else {

                def m = new Tusuarios(fNombre: nombre, fActivo: activo, fApellido: apellido, fPass: password.encodeAsMD5(), fEmail: email, fIdcliente: idcliente, fUsuario: usuario);
                m.save(failOnError: true);
            }
        } else {
            def m = new Tusuarios(fNombre: nombre, fActivo: activo, fApellido: apellido, fPass: password.encodeAsMD5(), fEmail: email, fIdcliente: idcliente, fUsuario: usuario);
            m.save(failOnError: true);
        }


    }


    def duplicar_factura(Tusuario user, String numero_factura) {
        Tfacturas tfacturas = Tfacturas.findById(numero_factura.toLong());
        Funciones funciones = new Funciones();
        if (tfacturas) {
            Long numero = sqlService.GetSecuencia(tfacturas.fTipofactura) as Long;
            Tclientes tclientes = Tclientes.findById(tfacturas.fIdCliente);
            Date fecha_vencimiento = new Date();

            if (tclientes) {

                Integer dias = sqlService.GetDiferenciasDias(tfacturas.fFecha, tfacturas.fFechaVencimiento);
                fecha_vencimiento = sqlService.Get_Sumar_Dias(sqlService.GetNow(), dias);
            }
            def nueva_factura = new Tfacturas(
                    fMonto: tfacturas.fMonto,
                    fHechoPor: user.id,
                    fFecha: sqlService.GetNow(),
                    fTax: tfacturas.fTax,
                    fNofactura: numero,
                    fFechaVencimiento: fecha_vencimiento,
                    fTipofactura: tfacturas.fTipofactura,
                    fIdCliente: tfacturas.fIdCliente,
                    fComentario: tfacturas.fComentario,
                    fMoneda: tfacturas.fMoneda,
                    fBalance: tfacturas.fMonto,
                    fCancelada: false,
                    fEnviada: true,
                    fEnvio: tfacturas.fEnvio,
                    fMontoIsr: tfacturas.fMontoIsr,
                    fPagada: false,
            );
            nueva_factura.save(failOnError: true);
            List<TdetFactura> lista = TdetFactura.findAllByFNofactura(tfacturas.fNofactura);
            for (TdetFactura e : lista) {
                def detalle = new TdetFactura(
                        fNofactura: nueva_factura.fNofactura,
                        fCantidad: e.fCantidad,
                        fPrecio: e.fPrecio,
                        fItbis: e.fItbis,
                        fIdProducto: e.fIdProducto,
                        fTipodoc: e.fTipodoc,
                        fComentario: e.fComentario
                );
                detalle.save(failOnError: true);
            }
            return nueva_factura.id.toString() + "<-*->" + tclientes.fEmail;
        }

    }

    def SetMensajesClientes(String ids,String mensaje, Long tipo,Boolean activo){

        ids.split(",").each {idcliente->
            TmsgClientes tmsgClientes = new TmsgClientes(
                    fActivo: activo,
                    fIdcliente: idcliente as Long,
                    fMsg: mensaje,
                    fFecha: sqlService.GetNow(),
                    fTipo: tipo
            );
            tmsgClientes.save(failOnError: true);

        }
    }

    def getFacturaCancelar(String numero) {
        String sql = "SELECT\n" +
                "\tconcat(f.f_tipofactura,' ',to_char(f.f_nofactura,'FM000000')) as numero,\n" +
                "\tf.f_monto as monto,\n" +
                "\t to_char(f.f_fecha::date, 'yyyy-MM-dd') as fecha,\n" +
                "\tcli.f_nombre_empresa as cliente\n" +
                "FROM t_facturas AS f\n" +
                "INNER JOIN t_clientes AS cli on cli.f_id = f.f_id_cliente\n" +
                "WHERE f.f_nofactura = ${numero}  and f.f_cancelada = false and f.f_pagada = false ";


        List list = sqlService.GetQuery(sql);

        return list
    }
    def get_cancelar_factura(String numero) {
        Tfacturas tfacturas  = Tfacturas.findByFNofactura(numero as Long);
        if (tfacturas){
            tfacturas.setfCancelada(true);
            tfacturas.save(failOnError: true);
        }

        return true
    }
    def serviceMethod() {

    }
}
