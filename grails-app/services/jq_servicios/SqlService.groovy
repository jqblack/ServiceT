package jq_servicios

import FuncionesGenerales.Funciones
import grails.gorm.transactions.Transactional
import grails.util.Holders
import org.springframework.jdbc.core.JdbcTemplate

import java.sql.Timestamp

@Transactional
class SqlService {
    static  transactional =true
    def dataSource= Holders.getGrailsApplication().mainContext.getBean("dataSource");

    def  Date GetNow()
    {
        def template= new JdbcTemplate(dataSource);
        return  template.queryForObject("select now()",Date.class);
    }
    def  String GetFechaFormato(String fecha)
    {
        def template= new JdbcTemplate(dataSource);
//        println(fecha)
        return  template.queryForObject("select to_char('$fecha'::TIMESTAMP, 'yyyy/MM/dd HH:MI:SS')",String.class);

    }
    def  String GetFechaFormato(String fecha,String formato)
    {
        def template= new JdbcTemplate(dataSource);
//        println(fecha)
        return  template.queryForObject("select to_char('$fecha'::TIMESTAMP, '$formato')",String.class);

    }
    def  String GetDiaFecha(String fecha)
    {
        def template= new JdbcTemplate(dataSource);
//        println(fecha)
        return  template.queryForObject("select  EXTRACT(DOW FROM '$fecha'::timestamp) as f_dia",String.class);

    }
    def  Integer GetSecuencia(String tipodocumento)
    {
        def template= new JdbcTemplate(dataSource);
        Integer a=template.queryForObject("select getsecuencia('$tipodocumento')",Integer.class);
        //template.finalize();
        return a;
    }



    def FuncionAcualizarRecibos(Integer id)
    {
        def template= new JdbcTemplate(dataSource);
        Integer a=template.queryForInt("select acualizar_recibos($id)");
    }

    def Beginwork()
    {
        def template= new JdbcTemplate(dataSource);
        template.execute("BEGIN WORK;");

    }

    def EjecutarQuery(String sql)
    {
        def template= new JdbcTemplate(dataSource);
        template.execute(sql);
    }

    def Commit()
    {
        def template= new JdbcTemplate(dataSource);
        template.execute("COMMIT WORK;");

    }

    def List<Map<String,Object>> GetRecorridos(Integer idcobrado,Date fechaini,Date fechafin)
    {
        def template= new JdbcTemplate(dataSource);
        template.queryForList("select * from get_recoridos($idcobrado,'$fechaini','$fechafin')");

    }

    def List<Map<String,Object>> GetClientesZona(Integer idzona)
    {
        def template= new JdbcTemplate(dataSource);
        template.queryForList("select * from get_clientes_zona($idzona)");

    }
    def  String Get_Concatenar_tipo_numero_to_char(String tipo,String numero,Integer cantidad)
    {
        String ceros="";

        for (int i = 0; i <cantidad ; i++) {
            ceros=ceros+"0";
        }
        def template= new JdbcTemplate(dataSource);
        String a=template.queryForObject("SELECT '$tipo'||to_char($numero,'FM$ceros');",String.class);
        return a.toString();
    }

    def List<Map<String,Object>> GetQuery(String sql)
    {
        def template= new JdbcTemplate(dataSource);
        return   template.queryForList(sql);

    }

    Map<String,Object>GetQueryForMap(String sql,Collection collection)
    {
        def template= new JdbcTemplate(dataSource);
        return  template.queryForMap (sql,collection.toArray());

    }

    def  Timestamp Get_Sumar_Dias(Date fecha_inicial, Integer dias)
    {
        Funciones funciones = new Funciones();

        def template= new JdbcTemplate(dataSource);
        String f1= funciones.formatDate(fecha_inicial,"yyyy-MM-dd");
        Timestamp a=template.queryForObject("SELECT ('$f1'::date +'$dias days'::INTERVAL )::TIMESTAMP",Timestamp.class);
        return a;
    }


    def  Timestamp Get_sumar_Horas(Timestamp fecha_inicial,Integer horas)
    {
        def template= new JdbcTemplate(dataSource);
//        String f1=fecha_inicial.format("yyyy-MM-dd");
        String f1=fecha_inicial;
        Timestamp a=template.queryForObject("SELECT ('$f1'::TIMESTAMP + interval '$horas hour')",Timestamp.class);
        return a;
    }

    def   GetDiferenciasDias(Date fecha_inicial, Date fecha_final)
    {
         Funciones funciones = new Funciones();
        def template= new JdbcTemplate(dataSource);
        String f1=fecha_inicial;
        Integer a=template.queryForObject("select extract(days from (timestamp '${funciones.formatDate(fecha_final,"yyyy-MM-dd")}' - timestamp '${funciones.formatDate(fecha_inicial,"yyyy-MM-dd")}'))::INTEGER ",Integer.class);
        return a;
    }



    def  Timestamp Get_Restar_Dias(Date fecha_inicial,Integer dias)
    {
        Funciones funciones = new Funciones();

        def template= new JdbcTemplate(dataSource);
        String f1= funciones.formatDate(fecha_inicial,"yyyy-MM-dd");
        Timestamp a=template.queryForObject("SELECT ('$f1'::date -'$dias days'::INTERVAL )::TIMESTAMP",Timestamp.class);
        return a;
    }

    def  Date Get_Restar_Dias(Timestamp fecha_inicial,Integer dias)
    {
        Funciones funciones = new Funciones();

        def template= new JdbcTemplate(dataSource);
        String f1= funciones.formatDate(fecha_inicial,"yyyy-MM-dd");
        Date a=template.queryForObject("SELECT ('$f1'::date -'$dias days'::INTERVAL )::DATE ",Date.class);
        return a;
    }
    def  Date Get_Sumar_Dias(Timestamp fecha_inicial,Integer dias)
    {
        Funciones funciones = new Funciones();

        def template= new JdbcTemplate(dataSource);
        String f1= funciones.formatDate(fecha_inicial,"yyyy-MM-dd");
        Date a=template.queryForObject("SELECT ('$f1'::date +'$dias days'::INTERVAL )::DATE ",Date.class);
        return a;
    }
    def serviceMethod() {

    }
}
