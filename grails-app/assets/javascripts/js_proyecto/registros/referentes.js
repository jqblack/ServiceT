/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {


    crear_tabla();

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_referente").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });

    $("#atras").click(function () {
        nuevo();
        crear_tabla();
    });

    $("#salvar").click(function () {




        if (validaciones() == true) {


            var vector = {
                idrecord: $("#idrecord").val(),
                nombre: $("#nombre").val(),
                apellido: $("#apellido").val(),
                email: $("#email").val(),
                cuenta_deposito: $("#cuenta_deposiito").val(),
                direccion: $("#direccion").val(),
                ciudad: $("#ciudad").val(),
                pais: $("#pais").val(),
                telefono1: $("#telefono1").val(),
                porciento: $("#porcentaje").val(),
                banco: $("#banco").val(),
                telefono2: $("#telefono2").val()

            };

          //   var v ='';v = v + vector.idrecord+',';
          //   v = v + vector.referencia+',';
          //   v = v + vector.descripcion+',';
          //   v = v + vector.version+',';
          //   v = v + vector.sistema+',';
          //   v = v + vector.precio+',';
          //   v = v + vector.cantidad+',';
          //   v = v + vector.servicio+',';
          //   v = v + vector.genera_serial+',';
          //   v = v + vector.tiene_itbis;
          //
          // //  alert(v);


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_referentes',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    $("#atras").click();
                }
            });

        }
        else {
            return;
        }

    });

    $('#tabla_referentes').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_referentes').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_referentes_by_id',
            data: {idrecord:data.f_idrecord},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_idrecord);
                $("#nombre").val(tupla[0].f_nombre);
                $("#apellido").val(tupla[0].f_apellido);
                $("#email").val(tupla[0].f_email);
                $("#cuenta_deposiito").val(tupla[0].f_cuenta_deposito);
                $("#direccion").val(tupla[0].f_direccion);
                $("#ciudad").val(tupla[0].f_ciudad);
                $("#pais").val(tupla[0].f_pais);
                $("#telefono1").val(tupla[0].f_telefono1);
                $("#telefono2").val(tupla[0].f_telefono2);
                $("#porcentaje").val(tupla[0].f_porciento);
                $("#banco").val(tupla[0].f_banco);
                $("#nuevo_referente").click();

            }
        });

    });

}

function crear_tabla() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_referentes',
        // data: 'parametro='+$("#parametro").val(),
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_idrecord'},
                    {name: 'f_nombre'},
                    {name: 'f_email'},
                    {name: 'f_telefono1'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_referentes").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: true,
                    autoheight: false,
                    editable: false,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre', width: "45%"},
                        {text: 'E-Mail', filtertype: 'textbox', datafield: 'f_email', width: "45%"},
                        {text: 'Telefono', filtertype: 'textbox', datafield: 'f_telefono1', width: "10%"}

                    ]
                });
            $('#tabla_referentes').jqxGrid('hidecolumn', 'f_idrecord');
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

function validaciones() {
    var valor = true;
    if ($("#nombre").val() == '') {
        notificacion_no('Digite la Nombre');
        valor = false;
    }
    else if ($("#apellido").val() == '') {
        notificacion_no('Digite los Apellido');
        valor = false;
    }
    else if ($("#porcentaje").val() == '' || parseFloat($("#porcentaje").val() < 0)) {
        notificacion_no('Digite el Porciento de Comisión');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#nombre").val('');
    $("#apellido").val('');
    $("#email").val('');
    $("#cuenta_deposiito").val('');
    $("#direccion").val('');
    $("#ciudad").val('');
    $("#pais").val('');
    $("#telefono1").val('');
    $("#telefono2").val('');
    $("#banco").val('');
    $("#porcentaje").val('0');

}
