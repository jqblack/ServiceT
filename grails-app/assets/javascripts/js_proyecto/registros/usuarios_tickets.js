/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {


    crear_tabla();

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);


    $("#nuevo").click(function () {
        nuevo();
    });


    $("#usuario").val("");
    $("#password").val("");
    $("#usuario").blur(function () {
        validar_usuario();
    });



    $("#salvar").click(function () {




        if (validaciones() == true) {
            var activo = 'false';
            if ($("#activo").is(':checked')) {
                activo = 'true';
            }

            var vector = {
                idrecord: "",
                nombre: $("#nombre").val(),
                apellido: $("#apellido").val(),
                usuario: $("#usuario").val(),
                password: $("#password").val(),
                email: $("#email").val(),
                cliente: $("#cliente").val(),
                activo:activo
            };



            $.ajax({
                type: "POST",
                url: window.location + '/salvar_usuario_tickets',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();

                }
            });

        }
        else {
            return;
        }

    });

    // $('#tabla_datos').on('rowclick', function (event) {
    //     var pocision = args.rowindex;
    //     var data = $('#tabla_datos').jqxGrid('getrowdata', pocision);
    //
    //     // alert(data.f_id);
    //     $.ajax({
    //         type: "POST",
    //         url: window.location + '/get_usuarios_tickets_by_id',
    //         data: {idrecord:data.f_idrecord},
    //         success: function (data) {
    //             var tupla = eval(data);
    //
    //             $("#idrecord").val(tupla[0].f_idrecord);
    //             $("#nombre").val(tupla[0].f_nombre);
    //             $("#apellido").val(tupla[0].f_apellido);
    //             $("#usuario").val(tupla[0].f_usuario)
    //             $("#email").val(tupla[0].f_email);
    //             if (String(tupla[0].f_activo)=="true")
    //             {
    //                 $("#activo").parent().addClass('checked');
    //                 $("#activo").prop('checked',true);
    //             }
    //             else {
    //                 $("#activo").parent().removeClass('checked');
    //                 $("#activo").prop('checked',false);
    //             }
    //
    //
    //
    //         }
    //     });
    //
    // });

}

function crear_tabla() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_usuarios_tickets',
        //data: ,
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_idrecord'},
                    {name: 'f_usuario'},
                    {name: 'f_nombre'},
                    {name: 'f_apellido'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_datos").jqxGrid(
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
                        {text: 'USUARIO', filtertype: 'textbox', datafield: 'f_usuario', width: "20%"},
                        {text: 'NOMBRE', filtertype: 'textbox', datafield: 'f_nombre', width: "40%"},
                        {text: 'APELLIDO', filtertype: 'textbox', datafield: 'f_apellido', width: "40%"}

                    ]
                });
            $('#tabla_datos').jqxGrid('hidecolumn', 'f_idrecord');
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
        notificacion_no('Digite el Nombre');
        valor = false;
    }
    else if ($("#apellido").val() == '') {
        notificacion_no('Digite el Apellido');
        valor = false;
    }else if ($("#usuario").val() == '') {
        notificacion_no('Digite el Usuario');
        valor = false;
    }else if ($("#password").val() == '') {
        notificacion_no('Digite el Password');
        valor = false;
    }else if ($("#email").val() == '') {
        notificacion_no('Digite el E-Mail');
        valor = false;
    }else if ($("#cliente").val() == '0') {
        notificacion_no('Seleccione el cliente');
        valor = false;
    }
    return valor;
}

function nuevo() {

    $("#idrecord").val('');
    $("#nombre").val('');
    $("#apellido").val('');
    $("#usuario").val('');
    $("#password").val('');
    $("#cliente").val('0');
    $("#email").val('');
    $("#activo").parent().add('checked');
    $("#activo").prop('checked',true);

    crear_tabla();
}

function validar_usuario() {

    if ($("#usuario").val()=="")
    {
        return;
    }



    $.ajax({
        type: "POST",
        url: window.location + '/get_validar_usuarios',
        data: 'usuario='+$("#usuario").val(),
        success: function (data) {

            if (data=='1')
            {
                notificacion_no("Existe un usuario con este ID");

            }


        }
    });
}
