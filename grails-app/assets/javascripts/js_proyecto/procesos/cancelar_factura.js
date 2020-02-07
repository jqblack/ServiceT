var num = 0;
$(function () {

    $("#parametro").keyup(function (event) {

        if (event.keyCode == 13) {
            buscar();
        }
    })

});

function buscar() {
    var numero = $("#parametro").val();

    if (numero == "") {
        notificacion_no("Digite el numero de factura a buscar");
        return;
    }

    $.ajax({
        type: "POST",
        url: window.location + "/get_factura_cancelar",
        data: {numero: numero},
        success: function (data) {
            var json = eval(data);

            if (json.length > 0) {

                num = numero;
                $("#cliente").text(json[0].cliente);
                $("#numero").text(json[0].numero);
                $("#fecha").text(json[0].fecha);
                $("#monto").text(json[0].monto);
                $("#carta").show();
            } else {
                notificacion_no("Factura no encontrada");
                $("#carta").hide();
            }
        }
    })
}

function cancelar() {


    bootbox.confirm({
        message: "Desea Cancelar esta factura ?",
        callback: function(result) {
          if (result){
              $.ajax({
                  type: "POST",
                  url: window.location + "/get_cancelar_factura",
                  data: {numero: num},
                  success: function (data) {
                      if (data) {
                          notificacion_no("Factura Cancelada Correctamente");
                          num = 0;
                          $("#cliente").val()
                          $("#numero").val()
                          $("#fecha").val()
                          $("#monto").val()

                          $("#carta").hide();
                      }
                  }

              });
          }
        },
        className: "bootbox-sm"
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