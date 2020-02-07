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
    $("#nuevo_usuario").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }
    });

    $("#atras").click(function () {
        nuevo();
        crear_tabla();
    });

    $("#usuario").blur(function () {

         validar_usuario();
    });

    $("#permisos").click(function () {
        // alert("klk");
        crear_tabla_permisos();
    });
    
    $("#salvar_permisos").click(function () {

        var ids = '';
        var checkedRows = $("#tabla_permisos").jqxTreeGrid('getCheckedRows');
        
        for (var i = 0; i < checkedRows.length; i++) {
            if (i == checkedRows.length -1 )
            {
                ids = ids +checkedRows[i].f_idrecord;
            }
            else {
                ids = ids +checkedRows[i].f_idrecord+ ',';
            }
        }

        var vector = {
            idrecord: $("#idrecord").val(),
            opciones: ids
        };

        $.ajax({
            type: "POST",
            url: window.location + '/salvar_permisos_usuarios',
            data: vector,
            success: function (data) {
                var tupla = eval(data);
                notificacion_si('Datos Guardados Correctamente');
                //nuevo();
                $("#atras_permisos").click();
            }
        });

       
    })



    $("#salvar").click(function () {

        if (validaciones() == true) {

            var activo = false;
            if ($("#activo").is(':checked')) {
                activo = 'true';
            }
            var vector = {
                idrecord: $("#idrecord").val(),
                nombre: $("#nombre").val(),
                apellido: $("#apellido").val(),
                email: $("#email").val(),
                idempleado: '0',
                direccion: $("#direccion").val(),
                telefono: $("#telefono").val(),
                usuario: $("#usuario").val(),
                telefono1: $("#telefono1").val(),
                password: $("#password").val(),
                activo: activo,
                fecha:$("#fecha_de_caducidad").val()

            };




            $.ajax({
                type: "POST",
                url: window.location + '/salvar_usuarios',
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



    $('#tabla_usuarios').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_usuarios').jqxGrid('getrowdata', pocision);

        // alert(data.f_codigo_usuario);
        $.ajax({
            type: "POST",
            url: window.location + '/get_usuarios_by_id',
            data: {idrecord:data.f_codigo_usuario},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_codigo_usuario);
                $("#nombre").val(tupla[0].f_nombre);
                $("#apellido").val(tupla[0].f_apellido);
                $("#email").val(tupla[0].f_email);
                $("#telefono").val(tupla[0].f_telefono);
                $("#direccion").val(tupla[0].f_direccion);
                $("#usuario").val(tupla[0].f_id_usuario);
                $("#fecha_de_caducidad").val(tupla[0].f_fecha_caducidad);

                if (String(tupla[0].f_activo)=="true")
                {
                    $("#activo").parent().addClass('checked');
                    $("#activo").prop('checked',true);
                }
                else {
                    $("#activo").parent().removeClass('checked');
                    $("#activo").prop('checked',false);
                }



                $('#usuario').prop('readonly', true);


                $("#nuevo_usuario").click();


            }
        });

    });

}

function validar_usuario() {

    if ($("#usuario").val()=="")
    {
        return;
    }
    if($('#usuario').prop('readonly')== true)
    {
        return;
    }


    $.ajax({
        type: "GET",
        url: window.location + '/get_validar_usuarios',
         data: 'usuario='+$("#usuario").val(),
        success: function (data) {

            if (data=='1')
            {
                notificacion_no("Existe un usuario con este ID");
                $("#usuario").focus();
            }


        }
    });
}

function crear_tabla() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_usuarios',
        // data: 'parametro='+$("#parametro").val(),
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_codigo_usuario'},
                    {name: 'f_nombre'},
                    {name: 'f_telefono'},
                    {name: 'f_email'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_usuarios").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_codigo_usuario', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre', width: "45%"},
                        {text: 'Telefono', filtertype: 'textbox', datafield: 'f_telefono', width: "10%"},
                        {text: 'E-Mail', filtertype: 'textbox', datafield: 'f_email', width: "45%"}


                    ]
                });
            $('#tabla_usuarios').jqxGrid('hidecolumn', 'f_codigo_usuario');
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
    else if ($("#usuario").val()=="" ) {
        notificacion_no('Digite el Usuario');
        valor = false;
    }
    else if ($("#idrecord").val()=="" ) {
        if ($("#password").val()=="" ) {
            notificacion_no('Digite la Contraseña');
            valor = false;
        }
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#nombre").val('');
    $("#apellido").val('');
    $("#email").val('');
    $("#telefono").val('');
    $("#direccion").val('');
    $("#usuario").val('');
    $("#password").val('');
    $("#fecha_de_caducidad").val('');
    $("#fecha").val('');
    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);
    $('#usuario').prop('readonly', false);
}


function crear_tabla_permisos() {


    $("#container_tabla_permisos").html('');
    $("#container_tabla_permisos").html('<div id="tabla_permisos"></div>');

    
    $.ajax({
        type: "POST",
        url: window.location + '/get_permisos_usuarios',
        data: {idrecord:$("#idrecord").val()},
        success: function (data) {

            var datas = '';
            var source;


            // alert(data);
            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_idrecord'},
                    {name: 'f_idpadre'},
                    {name: 'f_descripcion'},
                    {name: 'f_activo',type: 'bool'}
                ],
                hierarchy:
                {
                keyDataField: { name: 'f_idrecord' },
                parentDataField: { name: 'f_idpadre' }
                },
                id:'f_idrecord',
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);
           // alert(dataAdapter);
            $("#tabla_permisos").jqxTreeGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    pageable: false,
                    columnsResize: false,
                    checkboxes: true,
                  //  autoheight: false,
                    editable: false,
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "0%"},
                        {text: 'ID Padre', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idpadre', width: "0%"},
                        {text: 'Menu', filtertype: 'textbox', datafield: 'f_descripcion', width: "100%"}
                        //{text: 'Activo', filtertype: 'checkbox', datafield: 'f_activo', width: "20%",columntype: 'checkbox'}


                    ]
                });
            $('#tabla_permisos').jqxTreeGrid('hideColumn', 'f_idrecord');
            $('#tabla_permisos').jqxTreeGrid('hideColumn', 'f_idpadre');



            var json = eval(data);

            for (var i=0 ; i < json.length ; i++)
            {
                if (String(json[i].f_activo)=="true")
                {
                    $("#tabla_permisos").jqxTreeGrid('checkRow', json[i].f_idrecord);
                }
            }

        }
    });
}
