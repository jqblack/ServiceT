var mensaje_a_todos = false;
var ids = [];

function blanquear_mensaje() {
    $("#tipo").val(0);
    $("#mensaje").val("");
    $("#activo_mensaje").parent().removeClass("checked");
}
function ejecutar() {
    $("#mensaje_todos").click(function () {
        mensaje_a_todos = true;
        ids = [];
        blanquear_mensaje();
        $("#tabla_datos tr").each(function (index) {
            var checkbox = $(this).find("td:eq(10) input");

            var idrecord = checkbox.attr("id");

            if (!ids.includes(idrecord)) {
                ids.push(idrecord);
            }


        });
        $("#modal_mensajes").modal("show");
    })


    $("#mensaje_seleccionados").click(function () {
        mensaje_a_todos = false;
        ids = [];
        blanquear_mensaje();
        $("#tabla_datos tr").each(function (index) {
            var checkbox = $(this).find("td:eq(10) input");

            if (checkbox.is(':checked')) {

                var idrecord = checkbox.attr("id");

                if (!ids.includes(idrecord)) {
                    ids.push(idrecord);
                }
            }

        });

        if (ids.length>0){

            $("#modal_mensajes").modal("show");
        } else {
            notificacion_no("No se selecciono ningun cliente");
            return;
        }

    })

    $("#salvar_mensaje").click(function () {
        var tipo = $("#tipo").val();
        var mensaje = $("#mensaje").val();

        if (tipo == 0) {
            notificacion_no("Seleccione el tipo de mensaje");
            return;
        } else if (mensaje == "") {
            notificacion_no("Digite el mensaje");
            return
        }

        var activo = false;
        if ($("#activo_mensaje").parent().hasClass("checked")) {
            activo = true;
        }

        $.ajax({
            type: "POST",
            url: window.location + '/set_mensajes_clientes',
            data: {ids: ids.join(","), mensaje: mensaje, tipo: tipo,activo:activo},
            success: function (data) {
                notificacion_si('Se Agrego el Mensaje Correctamente');
                blanquear_mensaje();
                $("#modal_mensajes").modal("hide");

            }
        });

    })
}
function enviarMensajeCliente(id) {
    ids = [];
    ids.push(id);
    $("#modal_mensajes").modal("show");

}
function desactivarServicio(id) {
    bootbox.confirm({
        message: "Desea Desactivar el Servicio ?",
        callback: function (result) {

            if (result == true) {
                $.ajax({
                    type: "POST",
                    url: window.location + '/set_desactivar_servicio_contratado',
                    data: {idrecord: id},
                    success: function (data) {
                        notificacion_si('Se Desactivo el servicio Correctamente');
                    }
                });

            } else {
                return;

            }
        },
        className: "bootbox-sm"
    });
}
function getFactura(id) {
    bootbox.confirm({
        message: "Desea Generar la Factura ?",
        callback: function (result) {

            if (result == true) {
                $.ajax({
                    type: "POST",
                    url: window.location + '/generar_factura_clientes',
                    data: {idcliente: id},
                    success: function (data) {
                        notificacion_si('Se Genero la factura Correctamente');
                    }
                });

            } else {
                return;

            }
        },
        className: "bootbox-sm"
    });
}
function verFactura(id) {
    $.ajax({
        type: "POST",
        url: window.location + '/get_utima_factura',
        data: {idcliente: id},
        success: function (data) {
            var dato = data;
            // $("#pdf").html('');
            if (dato != '-1') {
                $("#modal_reporte").modal("show");
                $("#pdf").html('<embed src="data:application/pdf;base64,' + dato + '" width="100%" height="600"></embed>')
                // $('#jqxLoader').jqxLoader('close');
                // $("#cerrar_reporte").show();

            }

        }
    });


}
function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}
function calcular() {
    var ventas = 0.00;
    $("#tabla_datos tr").each(function (index) {
        var valor = String($(this).find("td:eq(8)").text()).replace(",","");
        ventas = ventas + parseFloat(valor);
    });
    $("#total_ventas").text(ConvertToCurrency(ventas));
}
function ConvertToCurrency(number) {
    var number = number.toString(),
        dollars = number.split('.')[0],
        cents = (number.split('.')[1] || '') +'00';
    dollars = dollars.split('').reverse().join('')
        .replace(/(\d{3}(?!$))/g, '$1,')
        .split('').reverse().join('');
    return dollars + '.' + cents.slice(0, 2);
}

