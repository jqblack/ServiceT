package publico
/**
 * The TpermisosGrupo entity.
 *
 * @author    
 *
 *
 */
class TpermisosGrupo {
    static mapping = {
         table 't_permisos_grupo'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fFila
    Long fColumna
    Long fUsuario
    Long fSubmenu

    static constraints = {
        fFila(nullable: true, max: 9999999999L)
        fColumna(nullable: true, max: 9999999999L)
        fUsuario(nullable: true, max: 9999999999L)
        fSubmenu(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
