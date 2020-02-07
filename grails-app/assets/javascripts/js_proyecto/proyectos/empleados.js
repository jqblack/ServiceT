/**
 * Created by Jose Santos on 05/08/2016.
 */
function get_tema() {
    return 'web';
}
function ejecutar() {

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);

    crearTabla();
    $("#atras").click(function () {
        nuevo();
        crearTabla();
    });
    
    
    $("#salvar").click(function () {


        if (validaciones() == true) {

            var activo = 'false';



            if ($("#activo").is(':checked')) {
                activo = 'true';
            }


            var vector = {
                idrecord: $("#idrecord").val(),
                nombre: $("#nombre").val(),
                email: $("#email").val(),
                activo: activo
            };


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_empleado',
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

    $('#tabla_empleados').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_empleados').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_empleado_by_id',
            data: {idrecord:data.f_id},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_id);
                $("#nombre").val(tupla[0].f_descripcion);
                $("#email").val(tupla[0].f_email);

                if (String(tupla[0].f_activo)=='true')
                {
                    $("#activo").parent().addClass('checked');
                    $("#activo").prop('checked',true);
                }
                else {
                    $("#activo").parent().removeClass('checked');
                    $("#activo").prop('checked',false);
                }

                $("#nuevo_empleado").click();
            }
        });

    });
}

function crearTabla() {
    
    $.ajax({
        type: "Post",
        url: window.location +"/getEmpleado",
        
        success: function (data) {
            
             var datas=data;
            var source;

            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_descripcion'},
                    {name: 'f_email'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_empleados").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_descripcion', width: "45%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "45%"}

                    ]
                });
        }
    })
}

function validaciones() {
    var valor = true;
    if ($("#nombre").val() == '') {
        notificacion_no('Digite el Nombre del Empleado');
        valor = false;
    }
    else if ($("#email").val() == '') {
        notificacion_no('Digite el Email');
        valor = false;
    }

    return valor;
}


function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}

function nuevo() {

    $("#nombre").val('');
    $("#email").val('');
    $("#idrecord").val('');
    $("#idrecord").parent().removeClass('checked');
}