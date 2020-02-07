package jq_servicios

import FuncionesGenerales.Funciones
import grails.gorm.transactions.Transactional
import proyecto.Templeados
import proyecto.Tproyectos
import proyecto.Ttareas
import proyecto.TtrackTareas

@Transactional
class ProyectosService {
    static transactional = true;

    def serviceMethod() {

    }

    Funciones funciones = new Funciones();
    SqlService sqlService;
    SeguridadService seguridadService;
    PublicoService service = funciones.getServicios(PublicoService);


    List<Map<String, Object>> ListarEmpleados() {
        String sql = "select * from proyectos.t_empleados e";
        return sqlService.GetQuery(sql);
    }

    List<Templeados> ListarEmpleado() {

        return Templeados.list();
    }

    List<Templeados> ListarEmpleado(Boolean valor) {
        return Templeados.findAllByFActivo(valor);
    }

    List<Ttareas> ListarTareas(Boolean valor) {
        String sql = "from Ttareas as b where b.fCerrada = ?0";
        Collection collection = [];
        collection.add(valor);
//        return Ttareas.findByFCerrada(valor).list();
        return Ttareas.findAll(sql, collection).toList();
    }

    def salvar_empleado(String id, String nombre, String email, Boolean activo) {
        if (!id.equals('')) {
            def empleado = Templeados.findById(id.toLong());

            if (empleado) {
                empleado.setfActivo(activo);
                empleado.setfDescripcion(nombre);
                empleado.setfEmail(email);
                empleado.save(failOnError: true);
            } else {
                def emp = new Templeados(fActivo: activo, fDescripcion: nombre, fEmail: email);
                emp.save(failOnError: true);
            }
        } else {
            def emp = new Templeados(fActivo: activo, fDescripcion: nombre, fEmail: email);
            emp.save(failOnError: true);
        }
    }

    List<Map<String, Object>> ListarEmpleados(Long id) {
        String sql = "SELECT \n" +
                "  f_id,\n" +
                "  f_descripcion,\n" +
                "  f_activo,\n" +
                "  f_email\n" +
                "FROM \n" +
                "  proyectos.t_empleados e\n" +
                "Where\n" +
                "\te.f_id=$id";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarProyectos(Long id) {
        String sql = "SELECT \n" +
                "  f_idproyecto,\n" +
                "  f_nombre,\n" +
                "  f_fecha_carga,\n" +
                "  to_char(f_fecha_inicio,'MM/dd/yyyy') as f_fecha_inicio,\n" +
                "  to_char(f_fecha_final,'MM/dd/yyyy') as f_fecha_final,\n" +
                "  to_char(f_fecha_real_inicio,'MM/dd/yyyy') as f_fecha_real_inicio,\n" +
                "  to_char(f_fecha_real_final,'MM/dd/yyyy') as f_fecha_real_final ,\n" +
                "  f_idempleado,\n" +
                "  f_congelado,\n" +
                "  f_comentario,\n" +
                "  f_porcentaje,\n" +
                "  f_idproyecto_padre,\n" +
                "  f_fecha_congelado\n" +
                "FROM \n" +
                "  proyectos.t_proyectos where f_idproyecto = $id";

        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarTareaById(Long id) {
        String sql = " SELECT \n" +
                "  id,\n" +
                "  f_descripcion_tarea,\n" +
                "  to_char(f_fecha_inicio,'dd/MM/yyyy') as f_fecha_inicio ,\n" +
                "  to_char(f_fecha_entrega_programada,'dd/MM/yyyy') as f_fecha_entrega_programada,\n" +
                "  to_char(f_fecha_entrega_real,'dd/MM/yyyy') as f_fecha_entrega_real,\n" +
                "  f_horas_programada,\n" +
                "  f_horas_entrega,\n" +
                "  f_id_empleado,\n" +
                "  f_porciento_cumplimiento,\n" +
                "  f_comentario,\n" +
                "  f_idproyecto,\n" +
                "  f_cerrada,\n" +
                "  f_ultima_actualizacion\n" +
                "FROM \n" +
                "  proyectos.t_tareas  where id =$id";

        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> BuscarProyectos() {
        String sql = "SELECT \n" +
                "  f_idproyecto,\n" +
                "  f_nombre,\n" +
                "  f_fecha_inicio,\n" +
                " COALESCE(f_comentario,' ') as f_comentario\n" +
                "FROM \n" +
                "  proyectos.t_proyectos ;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarTareas() {
        String sql = "SELECT \n" +
                "  t.id,\n" +
                "  t.f_descripcion_tarea,\n" +
                "  p.f_nombre as f_proyecto,\n" +
                "  emp.f_descripcion as f_empleado,\n" +
                "  to_char(t.f_ultima_actualizacion,'MM/dd/yyyy') as f_ultima_actualizacion\n" +
                "FROM \n" +
                "  proyectos.t_tareas t INNER JOIN proyectos.t_empleados as emp  on t.f_id_empleado = emp.f_id\n" +
                "  INNER JOIN proyectos.t_proyectos as p on p.f_idproyecto = t.f_idproyecto  where t.f_cerrada = false;";
        return sqlService.GetQuery(sql);
    }

    List<Map<String, Object>> ListarTracksByTareas(Long id) {
        String sql = "SELECT \n" +
                "  f_idrecord,\n" +
                "  f_idtarea,\n" +
                "  f_fecha,\n" +
                "  f_comentario,\n" +
                "  f_idempleado,\n" +
                "  f_horas_consumidas,\n" +
                "  e.f_descripcion as f_empleado\n" +
                "FROM \n" +
                "  proyectos.t_track_tareas INNER JOIN proyectos.t_empleados as e on e.f_id = f_idempleado where f_idtarea = $id;";
        return sqlService.GetQuery(sql);
    }

    List<Tproyectos> ListarProyectos() {
        return Tproyectos.list();
    }

    def salvarProyectos(String id, String nombre, Date fechaInicio, Date fechaFinal, Date fechaRealInicio, Date fechaRealFinal, Long empleado, Boolean congelado, String comentario, BigDecimal porcentaje, Long padre) {


        if (!id.equals("")) {
            def proyecto = Tproyectos.findById(id.toLong());

            if (proyecto) {
                proyecto.setfComentario(comentario);
//                if(!proyecto.getfCongelado() && congelado)
//                {
//                    proyecto.setfFechaCongelado(sqlService.GetNow())
//                }
                proyecto.setfCongelado(congelado);
                proyecto.setfFechaCarga(sqlService.GetNow());
                proyecto.setfNombre(nombre);
                proyecto.setfFechaInicio(fechaInicio);
                proyecto.setfFechaFinal(fechaFinal);
                proyecto.setfFechaRealInicio(fechaRealInicio);
                proyecto.setfFechaRealFinal(fechaRealFinal);
                proyecto.setfIdempleado(empleado);
                proyecto.setfPorcentaje(porcentaje);
                proyecto.setfIdproyectoPadre(padre);
                proyecto.save(failOnError: true);

                Templeados emp = Templeados.findById(proyecto.fIdempleado);
                if (emp) {
                    println(proyecto);
                    try {
                        service.EnviarEmail(emp.fEmail, "MODIFICACION DE PROYECTO", "SE A MODIFICADO EL PROYECTO <strong>$proyecto.fNombre</strong>  DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                                "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + proyecto.fFechaInicio + "</strong> AL <strong>" + proyecto.fFechaFinal+ "</strong>");
                    } catch (Exception e) {
                        throw e;
                        println(e.getMessage().toString());
                    }

                }
            } else {
                def proyect = new Tproyectos(fComentario: comentario, fCongelado: congelado, fFechaCarga: sqlService.GetNow(), fFechaFinal: fechaFinal, fFechaInicio: fechaInicio, fFechaRealFinal: fechaRealFinal, fFechaRealInicio: fechaRealInicio, fIdempleado: empleado, fIdproyectoPadre: padre, fNombre: nombre, fPorcentaje: porcentaje);
                proyect.save(failOnError: true);

                Templeados emp = Templeados.findById(proyect.fIdempleado);
                if (emp) {
                    try {
                        service.EnviarEmail(emp.fEmail, "ASIGNACION DE PROYECTO", "SE A GENERADO LA CREACION DE UN NUEVO PROYECTO <strong>$proyect.fNombre</strong> DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                                "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + proyect.fFechaInicio + "</strong> AL <strong>" + proyect.fFechaFinal + "</strong>");
                    } catch (Exception e) {
                        throw e;
                        println(e.getMessage().toString());
                    }

                }
            }
        } else {


            def proyect = new Tproyectos(fComentario: comentario, fCongelado: congelado, fFechaCarga: sqlService.GetNow(), fFechaFinal: fechaFinal, fFechaInicio: fechaInicio, fFechaRealFinal: fechaRealFinal, fFechaRealInicio: fechaRealInicio, fIdempleado: empleado, fIdproyectoPadre: padre, fNombre: nombre, fPorcentaje: porcentaje);
            proyect.save(failOnError: true);


            Templeados emp = Templeados.findById(proyect.fIdempleado);
            if (emp) {
                try {
                    service.EnviarEmail(emp.fEmail, "ASIGNACION DE PROYECTO", "SE A GENERADO LA CREACION DE UN NUEVO PROYECTO <strong>$proyect.fNombre</strong> DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                            "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + proyect.fFechaInicio + "</strong> AL <strong>" + proyect.fFechaFinal + "</strong>");
                } catch (Exception e) {
                    throw e;
                    println(e.getMessage());
                }

            }


        }
    }


    def salvarTardeas(String id, String nombre, Date fechainicio, Date fecha_entrega_programada, Date fecha_entrega_real, BigDecimal horas_programadas, BigDecimal horas_engtregas, Long empleado, String comentario, BigDecimal porcentaje, Long proyecto, Boolean cerrada) {

        if (!id.equals("")) {
            def dato = Ttareas.findById(id.toLong());
            if (dato) {
                dato.setfComentario(comentario);
                dato.setfFechaEntregaProgramada(fecha_entrega_programada);
                dato.setfDescripcionTarea(nombre);
                dato.setfFechaInicio(fechainicio);


                dato.setfHorasProgramada(horas_programadas);
                dato.setfHorasEntrega(horas_engtregas);
                dato.setfIdEmpleado(empleado);
                dato.setfIdproyectoTproyectos(proyecto);
                dato.setfCerrada(cerrada);

                if (cerrada==true)
                {
                    dato.setfFechaEntregaReal(sqlService.GetNow());
                }
                dato.save(failOnError: true)



                Templeados emp = Templeados.findById(dato.fIdEmpleado);
                if (emp) {
                    try {
                        service.EnviarEmail(emp.fEmail, "MODIFICACION DE TAREA", "SE A MODIFICACDO LA TAREA <strong>$dato.fDescripcionTarea</strong> DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                                "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + dato.fFechaInicio + "</strong> AL <strong>" + dato.fFechaEntregaProgramada + "</strong>");
                    } catch (Exception e) {
                        throw e;
                        println(e.getMessage().toString());
                    }
                }
            } else {
                def a = new Ttareas(fCerrada: cerrada, fComentario: comentario, fDescripcionTarea: nombre, fFechaEntregaProgramada: fecha_entrega_programada, fFechaEntregaReal:fecha_entrega_real, fFechaInicio: fechainicio, fHorasEntrega: horas_engtregas, fPorcientoCumplimiento: 0.toBigDecimal(), fHorasProgramada: horas_programadas, fIdEmpleado: empleado, fIdproyectoTproyectos: proyecto);
                a.save(failOnError: true);

                if (cerrada==true)
                {
                    a.setfFechaEntregaReal(sqlService.GetNow());
                    a.save(failOnError: true);
                }

                Templeados emp = Templeados.findById(a.fIdEmpleado);
                if (emp) {
                    try {
                        service.EnviarEmail(emp.fEmail, "ASIGNACION DE TAREA", "SE A GENERADO LA CREACION DE UNA NUEVA TAREA <strong>$a.fDescripcionTarea</strong> DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                                "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + a.fFechaInicio + "</strong> AL <strong>" + a.fFechaEntregaProgramada + "</strong>");
                    } catch (Exception e) {
                        throw e;
                        println(e.getMessage().toString());
                    }

                }
            }
        } else {
            def a = new Ttareas(fCerrada: cerrada, fComentario: comentario, fDescripcionTarea: nombre, fFechaEntregaProgramada: fecha_entrega_programada, fFechaEntregaReal: fecha_entrega_real, fFechaInicio: fechainicio, fHorasEntrega: horas_engtregas, fPorcientoCumplimiento: 0.toBigDecimal(), fHorasProgramada: horas_programadas, fIdEmpleado: empleado, fIdproyectoTproyectos: proyecto);
            a.save(failOnError: true);

            if (cerrada==true)
            {
                a.setfFechaEntregaReal(sqlService.GetNow());
                a.save(failOnError: true);
            }

            Templeados emp = Templeados.findById(a.fIdEmpleado);
            if (emp) {
                try {
                    service.EnviarEmail(emp.fEmail, "ASIGNACION DE TAREA", "SE A GENERADO LA CREACION DE UNA NUEVA TAREA <strong>$a.fDescripcionTarea</strong> DESDE NUESTRA PLATAFORMA PRINCIPAL <br/>" +
                            "EL CUAL ESTA AL EMPLEADO <strong>" + emp.fDescripcion + "</strong>  EL CUAL TIENE COMO FECHA DE INCIO EL <strong>" + a.fFechaInicio + "</strong> AL <strong>" + a.fFechaEntregaProgramada + "</strong>");
                } catch (Exception e) {
                    throw e;
                    println(e.getMessage().toString());
                }

            }


        }
    }

    def salvarTrack(Long idtarea, Long empleado, String comentario, BigDecimal horas) {
        def a = new TtrackTareas(fComentario: comentario, fFecha: sqlService.GetNow(), fHorasConsumidas: horas, fIdempleado: empleado, fIdtareaTtareas: idtarea);
        a.save(failOnError: true);
    }
}
