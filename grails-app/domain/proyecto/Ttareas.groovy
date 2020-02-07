package proyecto

import proyecto.Tproyectos

/**
 * The proyecto.Ttareas entity.
 *
 * @author    
 *
 *
 */

class Ttareas {
    static mapping = {
         table 'proyectos.t_tareas'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'id'
         fIdproyectoTproyectos column:'f_idproyecto'
    }
    String fDescripcionTarea
    Date fFechaInicio
    Date fFechaEntregaProgramada
    Date fFechaEntregaReal
    java.math.BigDecimal fHorasProgramada
    java.math.BigDecimal fHorasEntrega
    Long fIdEmpleado
    java.math.BigDecimal fPorcientoCumplimiento
    String fComentario
    Boolean fCerrada
    Date fUltimaActualizacion
    // Relation
    Long fIdproyectoTproyectos

    static constraints = {
        fDescripcionTarea(blank: false)
        fFechaInicio(nullable: true)
        fFechaEntregaProgramada(nullable: true)
        fFechaEntregaReal(nullable: true)
        fHorasProgramada()
        fHorasEntrega(nullable: true)
        fIdEmpleado(max: 9999999999L)
        fPorcientoCumplimiento(nullable: true)
        fComentario(nullable: true)
        fCerrada()
        fUltimaActualizacion(nullable: true)
        fIdproyectoTproyectos()
    }
    String toString() {
    }
}
