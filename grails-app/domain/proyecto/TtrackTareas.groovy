package proyecto

/**
 * The proyecto.TtrackTareas entity.
 *
 * @author    
 *
 *
 */
class TtrackTareas {
    static mapping = {
         table 'proyectos.t_track_tareas'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fIdtareaTtareas column:'f_idtarea'
    }

    Date fFecha
    String fComentario
    Long fIdempleado
    java.math.BigDecimal fHorasConsumidas
    // Relation
    Ttareas fIdtareaTtareas

    static constraints = {
        fFecha()
        fComentario(blank: false)
        fIdempleado(max: 9999999999L)
        fHorasConsumidas()
        fIdtareaTtareas()
    }
    String toString() {
    }
}
