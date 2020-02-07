package publico
/**
 * The Tdirectorio entity.
 *
 * @author    
 *
 *
 */
class Tdirectorio {
    static mapping = {
         table 't_directorio'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id_contacto'
    }
    String fNombre
    String fApellido
    String fDireccion
    String fProvincia
    Boolean fPublico
    String fTelefono
    String fTelefono2
    String fTelefono3
    String fCelular
    String fBeeper
    String fEmail
    String fWeb
    Integer fDiaCumple
    Integer fMesCumple
    String fComentario
    String fEmail2
    String fFax
    Long fIdUsuario
    String fOrigenDato

    static constraints = {
        fNombre(size: 1..40, blank: false)
        fApellido(size: 1..40, blank: false)
        fDireccion(size: 0..60)
        fProvincia(size: 0..30)
        fPublico(nullable: true)
        fTelefono(size: 0..15)
        fTelefono2(size: 0..15)
        fTelefono3(size: 0..15)
        fCelular(size: 0..15)
        fBeeper(size: 0..15)
        fEmail(size: 0..120)
        fWeb(size: 0..50)
        fDiaCumple(nullable: true, max: 99999)
        fMesCumple(nullable: true, max: 99999)
        fComentario()
        fEmail2(size: 0..120)
        fFax(size: 0..20)
        fIdUsuario(max: 9999999999L)
        fOrigenDato(size: 1..2, blank: false)
    }
    String toString() {
    }
}
