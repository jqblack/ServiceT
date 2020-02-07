package proyecto
/**
 * The proyecto.Tproyectos entity.
 *
 * @author    
 *
 *
 */
class Tproyectos {
    static mapping = {
         table 'proyectos.t_proyectos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idproyecto'
    }
    String fNombre
    Date fFechaCarga
    Date fFechaInicio
    Date fFechaFinal
    Date fFechaRealInicio
    Date fFechaRealFinal
    Long fIdempleado
    Boolean fCongelado
    String fComentario
    java.math.BigDecimal fPorcentaje
    Long fIdproyectoPadre
    Date fFechaCongelado

    static constraints = {
        fNombre(blank: false)
        fFechaCarga()
        fFechaInicio()
        fFechaFinal()
        fFechaRealInicio(nullable: true)
        fFechaRealFinal(nullable: true)
        fIdempleado(max: 9999999999L)
        fCongelado(nullable: true)
        fComentario(nullable: true);
        fPorcentaje(nullable: true)
        fIdproyectoPadre(nullable: true, max: 9999999999L)
        fFechaCongelado(nullable: true)
    }
    String toString() {
    }
}
