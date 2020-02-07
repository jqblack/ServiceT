package jq_servicios

import FuncionesGenerales.Funciones
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsHttpSession
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import proyecto.Templeados
import proyecto.Tproyectos
import proyecto.Ttareas
import publico.Tusuario

class ProyectoController {

    def index() { }

    Funciones funciones = new Funciones();
    ProyectosService service = funciones.getServicios(ProyectosService);

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

    def getEmpleados()
    {
        if (validar_sesion()) {
            render service.ListarEmpleados() as JSON;
        } else {
            redirect(uri: '/login');
        }
    }

    def salvar_empleado()
    {

        if (validar_sesion())
        {
            service.salvar_empleado(params.idrecord.toString(),params.nombre.toString(),params.email.toString(),params.activo.toString().toBoolean());
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def get_empleado_by_id()
    {
        if (validar_sesion())
        {
            render service.ListarEmpleados(params.idrecord.toString().toLong()) as JSON ;

        }else {
            redirect(uri: '/login');
        }
    }

    def proyectos()
    {
        if (validar_sesion()) {
            List<Tproyectos> lista=service.ListarProyectos();
            List<Templeados> lista_empleados=service.ListarEmpleado(true);
            render(view: '/layouts/template/proyectos/FrmOutProyectos',model: [proyectos:lista,empleados:lista_empleados])
        } else {
            redirect(uri: '/login');
        }
    }

    def getProyectos()
    {
        if(validar_sesion())
        {
            render service.BuscarProyectos() as JSON;
        }
        else {
            redirect(uri: '/login');
        }
    }

    def salvarProyectos()
    {
        if (validar_sesion())
        {

            Date fincio = null;
            Date ffin = null;
            Date fincioReal = null;
            Date ffinReal = null;

            if (!params.fechaInicio.toString().equals("") && !params.fechaInicio.toString().is(null))
            {
                fincio =  new Date(params.fechaInicio.toString());
            }

            if (!params.fechaFinal.toString().equals("") && !params.fechaFinal.toString().is(null))
            {
                ffin =  new Date(params.fechaFinal.toString());
            }

            if (!params.fechaRealInicio.toString().equals("") && !params.fechaRealInicio.toString().is(null))
            {
                fincioReal =  new Date(params.fechaRealInicio.toString());
            }

            if (!params.fechaRealFinal.toString().equals("") && !params.fechaRealFinal.toString().is(null))
            {
                ffinReal =  new Date(params.fechaRealFinal.toString());
            }

            BigDecimal porciento = 0.toBigDecimal();
            if (!params.porcentaje.toString().equals("") && !params.porcentaje.toString().is(null))
            {
                porciento =  params.porcentaje.toString().toBigDecimal();
            }

            service.salvarProyectos(params.id.toString(),
                    params.nombre.toString(),
                    fincio,
                    ffin,
                    fincioReal,
                    ffinReal,
                    params.empleado.toString().toLong(),
                    params.congelado.toString().toBoolean(),
                    params.comentario.toString(),
//                    params.porcentaje.toString().toBigDecimal(),
                    porciento,
                    params.padre.toString().toLong());

            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def get_proyectos_by_id()
    {
        if (validar_sesion())
        {
            render service.ListarProyectos(params.idrecord.toString().toLong()) as JSON ;

        }else {
            redirect(uri: '/login');
        }
    }

    def tareas()
    {
        if (validar_sesion()) {
            List<Tproyectos> lista=service.ListarProyectos();
            List<Templeados> lista_empleados=service.ListarEmpleado(true);
            List<Ttareas> lista_tareas=service.ListarTareas(false);
            render(view: '/layouts/template/proyectos/FrmOutTareas',model: [proyectos:lista,empleados:lista_empleados,tareas:lista_tareas])
        } else {
            redirect(uri: '/login');
        }
    }

    def get_tareas()
    {
        if(validar_sesion())
        {
            render service.ListarTareas() as JSON;
        }
        else {
            redirect(uri: '/login');
        }
    }


    def get_tareas_by_id()
    {
        if (validar_sesion())
        {
            render service.ListarTareaById(params.idrecord.toString().toLong()) as JSON ;

        }else {
            redirect(uri: '/login');
        }
    }

    def salvar_tareas()
    {
        if (validar_sesion())
        {


            Date fincio = null;
            Date ffin = null;
            Date fincioReal = null;
            Date ffinReal = null;

            if (!params.fechainicio.toString().equals("") && !params.fechainicio.toString().is(null))
            {
                fincio =  new Date(params.fechainicio.toString());
            }

            if (!params.fecha_entrega_programada.toString().equals("") && !params.fecha_entrega_programada.toString().is(null))
            {
                ffin =  new Date(params.fecha_entrega_programada.toString());
            }

            if (!params.fecha_entrega_real.toString().equals("") && !params.fecha_entrega_real.toString().is(null))
            {
                ffinReal =  new Date(params.fecha_entrega_real.toString());
            }

            BigDecimal porciento = 0.toBigDecimal();
            if (!params.porciento.toString().equals("") && !params.porciento.toString().is(null))
            {
                porciento =  params.porciento.toString().toBigDecimal();
            }


            BigDecimal horas_reales = 0.toBigDecimal();
            if (!params.horas_entregas.toString().equals("") && !params.horas_entregas.toString().is(null))
            {
                horas_reales =  params.horas_entregas.toString().toBigDecimal();
            }




            service.salvarTardeas(params.id.toString(),
                    params.nombre.toString(),
                    fincio,
                    ffin,
                    ffinReal,
                    params.horas_programadas.toString().toBigDecimal(),
                    horas_reales.toBigDecimal(),
                    params.empleado.toString().toLong(),
                    params.comentario.toString(),
                    porciento.toBigDecimal(),
                    params.proyecto.toString().toLong(),
                    params.cerrado.toString().toBoolean()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def salvar_track()
    {
        if (validar_sesion())
        {
            service.salvarTrack(params.idtarea.toString().toLong(),
                    params.empleado.toString().toLong(),
                    params.comentario.toString(),
                    params.horas_consumidas.toString().toBigDecimal()
            );
            render 'true';
        }else {
            redirect(uri: '/login');
        }
    }

    def ver_track()
    {
        if (validar_sesion()) {
            render(view: '/layouts/template/proyectos/FrmOutVerTracks')
        } else {
            redirect(uri: '/login');
        }
    }

    def get_tracks()
    {
        if(validar_sesion())
        {
            render service.ListarTracksByTareas(params.id.toString().toLong()) as JSON;
        }
        else {
            redirect(uri: '/login');
        }
    }
}
