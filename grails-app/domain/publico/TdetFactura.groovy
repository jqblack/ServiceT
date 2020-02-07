package publico
/**
 * The TdetFactura entity.
 *
 * @author    
 *
 *
 */
class TdetFactura {
    static mapping = {
         table 't_det_factura'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fComentario column: "f_comentario";
    }


    Long fNofactura
    String fTipodoc
    Long fIdProducto
    BigDecimal fPrecio
    BigDecimal fCantidad
    BigDecimal fItbis
    String fComentario 

    static constraints = {
        fNofactura(nullable: true, max: 9999999999L)
        fTipodoc(size: 0..10)
        fIdProducto(nullable: true, max: 9999999999L)
        fPrecio(nullable: true)
       // fCantidad(nullable: true, max: 9999999999L)
        fItbis(nullable: true)
        fComentario(nullable: true);
    }
    String toString() {
    }
}
