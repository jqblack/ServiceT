package conocimiento
/**
 * The Tconocimiento entity.
 *
 * @author    
 *
 *
 */
class Tconocimiento {
    static mapping = {
         table 'conocimiento.t_conocimiento'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fTipoDoc column:'f_tipo_doc'
         fNombreArchivo column:'f_nombre_archivo'
    }

    Date fFecha
    Long fHechopor
    String fSolucion
    String fTitulo
    byte[] fDocumento
    Long fIdcategoria
    String fTags
    String fTipoDoc
    String fNombreArchivo

    static constraints = {
        fFecha(nullable: true)
        fHechopor(nullable: true, max: 9999999999L)
        fSolucion(nullable: true);
        fTitulo(nullable: true)
        fDocumento(nullable: true)
        fIdcategoria(nullable: true, max: 9999999999L)
        fTags(nullable: true);
        fTipoDoc(nullable: true);
        fNombreArchivo(nullable: true);
    }
    String toString() {
    }
}
