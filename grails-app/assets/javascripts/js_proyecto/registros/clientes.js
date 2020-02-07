/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'web';
}
function ejecutar() {


    crear_tabla();
    $("#mostrar_todos").click(function () {
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
                        {name: 'f_fecha'},
                        {name: 'f_email'}
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
                            {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "25%"},
                            {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "40%"},
                            {text: 'U.Factura', filtertype: 'textbox', datafield: 'f_fecha', width: "15%"},
                            {text: 'G.Factura', datafield: '', columntype: 'button', cellsrenderer: function () {
                                return 'G.Factura';
                            },
                                buttonclick: function (row) {
                                    var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);


                                    bootbox.confirm({
                                        message: "Desea Generar la Factura ?",
                                        callback: function(result) {

                                            if (result == true)
                                            {
                                                $.ajax({
                                                    type: "POST",
                                                    url: window.location + '/generar_factura_clientes',
                                                    data: {idcliente:dataRecord.f_id},
                                                    success: function (data) {
                                                        var tupla = eval(data);
                                                        notificacion_si('Datos Guardados Correctamente');
                                                        nuevo();
                                                        $("#atras").click();


                                                    }
                                                });

                                            }else
                                            {
                                                return;

                                            }
                                         //   alert("Confirm result: " + result);
                                        },
                                        className: "bootbox-sm"
                                    });
                                }, theme: get_tema(), width: "10%"
                            }

                        ]
                    });
                // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_idrecord');
            }
        });
    });

    $("#solo_activos").click(function () {
        crear_tabla();
    });

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_cliente").click(function () {
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

            var envia_email = 'false';
            var retiene_isr = 'false';
            var retiene_itbis = 'false';
            var facturar_tax = 'false';
            var es_empresa = 'false';


            if ($("#envia_email").is(':checked')) {
                envia_email = 'true';
            }
            if ($("#retiene_isr").is(':checked')) {
                retiene_isr = 'true';
            }
            if ($("#retiene_itebis").is(':checked')) {
                retiene_itbis = 'true';
            }
            if ($("#facturar_tax").is(':checked')) {
                facturar_tax = 'true';
            }
            if ($("#es_empresa").is(':checked')) {
                es_empresa = 'true';
            }


            var vector = {
                idrecord: $("#idrecord").val(),
                nombre: $("#empresa").val(),
                contacto: $("#contacto").val(),
                email: $("#email").val(),
                identificacion: $("#identificacion").val(),
                direccion: $("#direcicon").val(),
                ciudad: $("#ciudad").val(),
                estado: $("#estado").val(),
                fax: $("#fax").val(),
                pais: $("#pais").val(),
                zip_code: $("#zip_code").val(),
                telefono_1: $("#telefono_1").val(),
                telefono_2: $("#telefono_2").val(),
                telefono_contacto: $("#telefono_contacto").val(),
                contacto_cobros: $("#contacto_cobros").val(),
                moneda: $("#moneda").val(),
                envia_email: envia_email,
                retiene_isr: retiene_isr,
                retiene_itebis: retiene_itbis,
                facturar_tax: facturar_tax,
                es_empresa: es_empresa,



            };


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_cliente',
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

    $('#tabla_clientes').on('rowdoubleclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_clientes').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_clientes_by_id',
            data: {idrecord:data.f_id},
            success: function (data)
            {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_id);
                $("#empresa").val(tupla[0].f_nombre_empresa);
                $("#contacto").val(tupla[0].f_contacto);
                $("#email").val(tupla[0].f_email);
                $("#identificacion").val(tupla[0].f_id_identificacion);
                $("#direcicon").val(tupla[0].f_direccion);
                $("#ciudad").val(tupla[0].f_ciudad);
                $("#estado").val(tupla[0].f_estado);
                $("#pais").val(tupla[0].f_pais);
                $("#zip_code").val(tupla[0].f_zipcode);
                $("#telefono_1").val(tupla[0].f_telefono1);
                $("#telefono_2").val(tupla[0].f_telefono2);
                $("#telefono_contacto").val(tupla[0].f_cel_contacto);
                $("#contacto_cobros").val(tupla[0].f_contacto_cobro);
                $("#fax").val(tupla[0].f_fax);
                $("#moneda").val(tupla[0].f_moneda);



                if (String(tupla[0].f_retiene_itbis)=='true')
                {
                    $("#retiene_itebis").parent().addClass('checked');
                    $("#retiene_itebis").prop('checked',true);
                }
                else {
                    $("#retiene_itebis").parent().removeClass('checked');
                    $("#retiene_itebis").prop('checked',false);
                }

                if (String(tupla[0].f_enviar_email)=='true')
                {
                    $("#envia_email").parent().addClass('checked');
                    $("#envia_email").prop('checked',true);
                }
                else {
                    $("#envia_email").parent().removeClass('checked');
                    $("#envia_email").prop('checked',false);
                }
                if (String(tupla[0].f_retiene_isr)=='true')
                {
                    $("#retiene_isr").parent().addClass('checked');
                    $("#retiene_isr").prop('checked',true);
                }
                else {
                    $("#retiene_isr").parent().removeClass('checked');
                    $("#retiene_isr").prop('checked',false);
                }
                if (String(tupla[0].f_empresa_persona)=='1')
                {
                    $("#es_empresa").parent().addClass('checked');
                    $("#es_empresa").prop('checked',true);
                }
                else {
                    $("#es_empresa").parent().removeClass('checked');
                    $("#es_empresa").prop('checked',false);
                }
                if (String(tupla[0].f_facturarle_tax)=='true')
                {
                    $("#facturar_tax").parent().addClass('checked');
                    $("#facturar_tax").prop('checked',true);
                }
                else
                {
                    $("#facturar_tax").parent().removeClass('checked');
                    $("#facturar_tax").prop('checked',false);

                }
                $("#nuevo_cliente").click();


            }
        });

    });

}

function crear_tabla() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_clientes_activos_no_activos',
        data: {activo:true},
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
                    {name: 'f_fecha'},
                    {name: 'f_email'}
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
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "25%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "40%"},
                        {text: 'U.Factura', filtertype: 'textbox', datafield: 'f_fecha', width: "15%"},
                        {text: 'G.Factura', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'G.Factura';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);

                                bootbox.confirm({
                                    message: "Desea Generar la Factura ?",
                                    callback: function(result) {

                                        if (result == true)
                                        {
                                            $.ajax({
                                                type: "POST",
                                                url: window.location + '/generar_factura_clientes',
                                                data: {idcliente:dataRecord.f_id},
                                                success: function (data) {
                                                    var tupla = eval(data);
                                                    notificacion_si('Datos Guardados Correctamente');
                                                    nuevo();
                                                    $("#atras").click();
                                                }
                                            });

                                        }else
                                        {
                                            return;

                                        }
                                        //   alert("Confirm result: " + result);
                                    },
                                    className: "bootbox-sm"
                                });




                            }, theme: get_tema(), width: "10%"
                        }

                    ]
                });
            // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_idrecord');
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
    if ($("#empresa").val() == '') {
        notificacion_no('Digite el Nombre De la Empresa');
        valor = false;
    }
    else if ($("#contacto").val() == '') {
        notificacion_no('Digite el Contacto');
        valor = false;
    }else if ($("#moneda").val() == '' || $("#moneda").val() == null || $("#moneda").val() =='0') {
        notificacion_no('Seleccione la moneda');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#empresa").val('');
    $("#contacto").val('');
    $("#email").val('');
    $("#identificacion").val('');
    $("#direcicon").val('');
    $("#ciudad").val('');
    $("#estado").val('');
    $("#pais").val('');
    $("#zip_code").val('');
    $("#telefono_1").val('');
    $("#telefono_2").val('');
    $("#telefono_contacto").val('');

    $("#fax").val('');
    $("#contacto_cobros").val('');
    $("#retiene_itebis").parent().removeClass('checked');
    $("#retiene_itebis").prop('checked',false);
    $("#envia_email").parent().removeClass('checked');
    $("#envia_email").prop('checked',false);
    $("#retiene_isr").parent().removeClass('checked');
    $("#retiene_isr").prop('checked',false);
    $("#es_empresa").parent().removeClass('checked');
    $("#es_empresa").prop('checked',false);
    $("#facturar_tax").parent().removeClass('checked');
    $("#facturar_tax").prop('checked',false);

    // $("#facturar_tax").parent().addClass('checked');

    

}
