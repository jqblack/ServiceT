package tk

import java.sql.Timestamp

class Ttickets {
    static mapping = {
         table 'tk.t_tickets'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fFechaVencimiento column:'f_fecha_vencimiento';
         fHechoPor column:'f_hecho_por';
         fAsignado column:'f_asignado';
         fFechaCancelacion column:'f_fecha_cacelado';
         fCerrado column:'f_cerrado';
         fCerradoCliente column:'f_cerrado_cliente';
         fFechaCerradoCliente column:'f_fecha_cerrado_cliente';
         fTiempoSolucionado column:'f_tiempo_solucionado';
        fFechaAsignacion column:'f_fecha_asignacion';
        fBroadcast column:'f_broadcast';
        fTipoTk column:'f_tipo_tk';
        fStatus column: "f_status";
    }

    Long fIdcliente
    Timestamp fFecha
    Long fIdproyecto
    String fTitulo
    String fDescipcion
    Long fIdprioridad
    Long fIdposteado
    Long fIdtecnicoasignado
    Long fIdquienasigno
    Timestamp fFechaCerrado
    Timestamp fFechaCerradoCliente
    Timestamp fFechaVencimiento
    Timestamp fFechaCancelacion
    Timestamp fFechaAsignacion
    Long fTiempoResolucion
    Boolean fCancelado
    Long fIdcancelo
    Long fHechoPor
    Boolean fAsignado
    Boolean fCerrado
    Boolean fCerradoCliente
    Boolean fBroadcast
    BigDecimal fTiempoSolucionado
    Long fTipoTk
    Long fStatus;

    static constraints = {

        fIdcliente(max: 9999999999L)
        fFecha(nullable: true)
        fIdproyecto(nullable: true, max: 9999999999L)
        fTitulo(size: 0..100)
        fDescipcion(nullable: true)
        fIdprioridad(nullable: true, max: 9999999999L)
        fIdposteado(nullable: true, max: 9999999999L)
        fIdtecnicoasignado(nullable: true, max: 9999999999L)
        fIdquienasigno(nullable: true, max: 9999999999L)
       // fFechaCerrado(nullale: true)
        fFechaCerradoCliente(nullable: true)
        fTiempoResolucion(nullable: true, max: 9999999999L)
        fCancelado(nullable: true)
        fFechaVencimiento(nullable: true)
        fIdcancelo(nullable: true, max: 9999999999L)
        fFechaCerrado nullable: true;
        fHechoPor nullable: true;
        fAsignado nullable: true;
        fFechaCancelacion nullable: true;
        fCerrado nullable: true;
        fCerradoCliente nullable: true;
        fTiempoSolucionado nullable: true;
        fFechaAsignacion nullable: true;
        fBroadcast nullable: true;
        fTipoTk nullable: true;
        fStatus nullable: true;

    }

}
