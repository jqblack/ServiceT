/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {


    //$('#fecha').jqxDateTimeInput({ formatString: "yyyy/MM/dd"});
    $("#fecha").jqxDateTimeInput({ width: '100%', height: '31px',theme:get_tema(),template: 'primary' });
    crear_tabla_clientes();

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });

    $("#nuevo").click(function () {
       // distribuir();
        nuevo();
        // crear_tabla_servicios_contratados();
    });

    $("#salvar").click(function () {


        if (validaciones() == true) {


            var value = $('#fecha').jqxDateTimeInput('getText');

            
            var vector ={idrecord:$("#id_cliente").val(),monto:$("#monto").val(),fecha:value,comentario:$("#comentario").val()};

            $.ajax({
                type: "POST",
                url: window.location + '/salvar_nota_debito',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);
                    notificacion_si('Datos Guardados Correctamente');
                    nuevo();
                    // $("#atras").click();
                }
            });

        }
        else {
            return;
        }

    });


    // $( "#a_pagar" ).keypress(function(e) {
    //     if(e.which == 13) {
    //         distribuir();
    //     }
    // });
    //
    // $("#a_pagar").focus(function(){
    //     distribuir();
    // });
    // $("#a_pagar").blur(function(){
    //     distribuir();
    // });
}


function crear_tabla_clientes() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_clientes',
        // data: 'parametro='+$("#parametro").val(),
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_nombre_empresa'},
                    {name: 'f_email'},
                    {name: 'f_seleccionar'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_clientes").jqxGrid(
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
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "40%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'Seleccionar', datafield: 'f_seleccionar', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#cliente").val(dataRecord.f_nombre_empresa)
                                $("#id_cliente").val(dataRecord.f_id)
                              //  crear_tabla_by_id(dataRecord.f_id);
                                $("#atras_clientes").click();
                                $("#monto").focus();
                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
            // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_id');
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

    // var t = 0;
    //
    // var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    // var rowscounts = datainformations.rowscount;

    if ($("#id_cliente").val() == '') {
        notificacion_no('Seleccione el Cliente');
        valor = false;
    }else if (parseFloat($("#monto").val())== parseFloat('0') || $("#monto").val() == '')
    {
        notificacion_no('Digite el Monto');
        valor = false;
    }else if ($("#comentario").val() == '' ) {
        notificacion_no('Digite el Comentario');
        //$("#nota").focus();
        valor = false;
    }
        return valor;
}

function nuevo() {
    $("#id_cliente").val('');
    $("#cliente").val('');
    $("#monto").val('');
    $("#comentario").val('');
    $("#direccion").val('');
}
