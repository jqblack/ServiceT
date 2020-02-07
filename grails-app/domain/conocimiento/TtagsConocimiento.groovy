package conocimiento
/**
 * The TtagsConocimiento entity.
 *
 * @author    
 *
 *
 */
class TtagsConocimiento {
    static mapping = {
         table 'conocimiento.t_tags_conocimiento'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIdtag
    Long fIdconocimiento

    static constraints = {
        fIdtag(nullable: true, max: 9999999999L)
        fIdconocimiento(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
