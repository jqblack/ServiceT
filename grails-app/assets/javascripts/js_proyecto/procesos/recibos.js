/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function ejecutar() {


    $("#ver_estado_cuenta").prop('disabled', true);

    $("#boton_enviar").click(function () {
        // Expresion regular para validar el correo
        var regex = /[\w-\.]{2,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;

        // Se utiliza la funcion test() nativa de JavaScript
        if (regex.test($('#correo_alternativo').val().trim())) {


            $.ajax({
                type: "POST",
                url: window.location + '/get_enviar_estado_cuenta_con_correo',
                data: {idrecord: $("#id_cliente").val(), email: $('#correo_alternativo').val().trim()},
                success: function (data) {
                    var dato = data;
                    // $("#pdf").html('');
                    if (dato == '1') {
                        notificacion_si('Factura Enviada Con Exito');
                        $("#close_modal_actura").click();
                        $('#correo_alternativo').val("");

                    } else if (dato == '-3') {
                        notificacion_no("Esta factura no posee cliente no puede ser enviada");
                    } else if (dato == '-4') {
                        notificacion_no("Error al enviar Factura");
                    }
                    else if (dato == '0') {
                        notificacion_no('Esta Factura ya Fue Enviada');
                    }
                }
            });
        } else {
            notificacion_no('La direccón de correo no es valida');
            return;
        }


    })
    $("#ver_estado_cuenta").click(function () {


        if ($("#id_cliente").val()=='')
        {
            notificacion_no("Seleccione un cliente");
            return;
        }

        var id = $("#id_cliente").val();

        $.ajax({
            type: "POST",
            url: window.location + '/get_reporte_estado_cuenta_by_id',
            data: {idrecord: id},
            success: function (data) {
                var dato = data;
                $("#pdf").html('');
                if (dato != '-1') {
                    $("#pdf").html('<embed src="data:application/pdf;base64,' + dato + '" width="100%" height="600"></embed>')

                    $("#mostrar_factura").click();
                }

            }
        });


    });



    $("#fecha").jqxDateTimeInput({ width: '100%', height: '31px',theme:get_tema(),template: 'primary' });
    crear_tabla_clientes();
    crear_tabla();


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

            var json_header='';
            var json_header_array={};
            var json_detalles='';
            var json_detalles_array={};
            var concepto='';


            var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
            var rowscounts = datainformations.rowscount;
            var t = 0;




            for (var i = 0 ;i < rowscounts;i++) {
                var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);
                if (parseFloat(dataRecord.f_a_pagar) > parseFloat('0'))
                {
                    concepto = concepto+ dataRecord.f_concepto+' : '+dataRecord.f_factura+','+dataRecord.f_a_pagar+';';
                    
                    json_detalles_array.f_no_factura = dataRecord.f_id;
                    json_detalles_array.f_documento = dataRecord.f_factura;
                    json_detalles_array.f_monto = parseFloat(dataRecord.f_a_pagar);


                    if (i==0)
                    {
                        json_detalles=json_detalles+JSON.stringify(json_detalles_array);
                    }
                    else
                    {
                        json_detalles=json_detalles+','+JSON.stringify(json_detalles_array);
                    }
                }

            }
            json_detalles = '['+json_detalles+']';
            json_detalles = json_detalles.replace('[,','[');
           
      
            json_header_array.f_concepto=concepto;
            json_header_array.f_monto=parseFloat($("#monto_recibo").val());
            json_header_array.f_tipo_recibo='REC';
            json_header_array.f_idcliente= $("#id_cliente").val();
            json_header_array.f_tipo_moneda_recibo=$("#moneda").val();
            json_header=json_header+JSON.stringify(json_header_array);

            var vector ={recibo:json_header,detalle:json_detalles};

            $.ajax({
                type: "POST",
                url: window.location + '/salvar_recibo',
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

    // $('#tabla_servicios_contratados').on('rowdoubleclick', function (event) {
    //     var pocision = args.rowindex;
    //     var data = $('#tabla_servicios_contratados').jqxGrid('getrowdata', pocision);
    //
    //     // alert(data.f_id);
    //     $.ajax({
    //         type: "POST",
    //         url: window.location + '/get_servicios_contratados_by_id',
    //         data: {idrecord:data.f_id},
    //         success: function (data) {
    //             var tupla = eval(data);
    //
    //             $("#idrecord").val(tupla[0].f_id);
    //             $("#id_producto").val(tupla[0].f_id_producto);
    //             $("#producto").val(tupla[0].f_producto);
    //             $("#id_cliente").val(tupla[0].f_idcliente);
    //             $("#cliente").val(tupla[0].f_cliente);
    //             $("#id_referente").val(tupla[0].f_id_referente);
    //             $("#referente").val(tupla[0].f_referente);
    //             $("#precio").val(tupla[0].f_precio);
    //             $("#itebis").val(tupla[0].f_itebis);
    //             // $("#porcentaje").val(tupla[0].f_porcentaje);
    //             $("#comision").val(tupla[0].f_comision);
    //             $("#cantidad").val(tupla[0].f_cantidad);
    //             $("#dia_factura").val(tupla[0].f_dia_factura);
    //             $("#serial").val(tupla[0].f_serial);
    //
    //
    //             if (String(tupla[0].f_enviar)=='true')
    //             {
    //                 $("#enviar").parent().addClass('checked');
    //                 $("#enviar").prop('checked',true);
    //             }
    //             else {
    //                 $("#enviar").parent().removeClass('checked');
    //                 $("#enviar").prop('checked',false);
    //             }
    //
    //             if (String(tupla[0].f_activo)=='true')
    //             {
    //                 $("#activo").parent().addClass('checked');
    //                 $("#activo").prop('checked',true);
    //             }
    //             else {
    //                 $("#activo").parent().removeClass('checked');
    //                 $("#activo").prop('checked',false);
    //             }
    //             $("#nuevo_servicio").click();
    //
    //
    //         }
    //     });
    //
    // });





    $( "#a_pagar" ).keypress(function(e) {
        if(e.which == 13) {
            distribuir();
        }

    });
    $("#a_pagar").focus(function(){
        distribuir();
    });
    $("#a_pagar").blur(function(){
        distribuir();
    });
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
                    {name: 'f_moneda'},
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
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "40%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "35%"},
                        {text: 'Seleccionar', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Seleccionar';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_clientes").jqxGrid('getrowdata', row);
                                $("#cliente").val(dataRecord.f_nombre_empresa);
                                $("#id_cliente").val(dataRecord.f_id);
                                $("#correo_alternativo").val(dataRecord.f_email);
                                $("#moneda").val(dataRecord.f_moneda);
                                $("#ver_estado_cuenta").prop('disabled', false);

                                crear_tabla_by_id(dataRecord.f_id);
                                $("#atras_clientes").click();
                                $("#a_pagar").focus();

                            }, theme: get_tema(), width: "15%"
                        }

                    ]
                });
             $('#tabla_clientes').jqxGrid('hidecolumn', 'f_id');
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

    var t = 0;

    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;

    if ($("#id_cliente").val() == '') {
        notificacion_no('Seleccione el Cliente');
        valor = false;
    }else if (parseFloat(rowscounts)== parseFloat('0'))
    {
        notificacion_no('No existen Facturas para Aplicar Recibos');
        valor = false;
    }
    //validar tabla_datos


    for (var i = 0 ;i < rowscounts;i++) {
        var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);
        t = t + parseFloat(dataRecord.f_a_pagar);
    }
    if (t==0)
    {
        notificacion_no('No hay Monto distribuido entre las Facturas');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#id_cliente").val('');
    $("#cliente").val('');
    $("#sald_deudor").val('');
    $("#monto_recibo").val('');
    $("#a_pagar").val('');
    $('#tabla_datos').jqxGrid('clear');
    $("#ver_estado_cuenta").prop('disabled', true);

}


function crear_tabla() {


            var datas = '';
            var source;

            datas = datas;
            source =
            {
                datatype: "array",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_factura'},
                    {name: 'f_fecha'},
                    {name: 'f_vencimiento'},
                    {name: 'f_monto'},
                    {name: 'f_a_pagar'},
                    {name: 'f_concepto'},
                    {name: 'f_abonar'},
                    {name: 'f_saldar'}
                ],
                localdata: datas

            };
            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#tabla_datos").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    height:'200px',
                   // showfilterrow: true,
                  //  filterable: true,
                   // pageable: true,
                    autoheight: false,
                    editable: false,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "5%"},
                        {text: 'Factura', filtertype: 'textbox', datafield: 'f_factura', width: "10%"},
                        {text: 'Fecha', filtertype: 'textbox', datafield: 'f_fecha', width: "10%"},
                        {text: 'Vencimiento', filtertype: 'textbox', datafield: 'f_vencimiento', width: "15%"},
                        {text: 'Monto', filtertype: 'textbox', datafield: 'f_monto', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'A Pagar', filtertype: 'textbox', datafield: 'f_a_pagar', width: "15%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Concepto', filtertype: 'textbox', datafield: 'f_concepto', width: "20%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Abonar', datafield: 'f_abonar', columntype: 'button', cellsrenderer: function () {
                            return 'Abonar';
                        },buttonclick: function (row) {
                                // var dataRecord = $("#tabla_productos").jqxGrid('getrowdata', row);
                                // $("#id_producto").val(dataRecord.f_id);
                                // $("#producto").val(dataRecord.f_referencia+' '+dataRecord.f_descripcion);
                                // $("#precio").val(dataRecord.f_precio);
                                // $("#atras_producto").click();

                                calcular_totales();
                            }, theme: get_tema(), width: "10%"
                        },
                        {text: 'Saldar', datafield: 'f_saldar', columntype: 'button', cellsrenderer: function () {
                            return 'Saldar';
                        },
                            buttonclick: function (row) {
                                // var dataRecord = $("#tabla_productos").jqxGrid('getrowdata', row);
                                // $("#id_producto").val(dataRecord.f_id);
                                // $("#producto").val(dataRecord.f_referencia+' '+dataRecord.f_descripcion);
                                // $("#precio").val(dataRecord.f_precio);
                                // $("#atras_producto").click();

                                calcular_totales();
                            }, theme: get_tema(), width: "10%"
                        }

                    ]
                });
            $('#tabla_datos').jqxGrid('hidecolumn', 'f_id');

}

function crear_tabla_by_id(id) {
    $.ajax({
        type: "POST",
        url: window.location + '/get_facturas_cxc_by_id',
         data: 'idrecord='+id,
        success: function (data) {

            var datas='';
            var source;
            datas = data;
            source =
            {
                datatype: "json",

                datafields: [
                    {name: 'f_id'},
                    {name: 'f_factura'},
                    {name: 'f_fecha'},
                    {name: 'f_vencimiento'},
                    {name: 'f_monto'},
                    {name: 'f_balance'},
                    {name: 'f_a_pagar'},
                    {name: 'f_concepto'},
                    {name: 'f_abonar'},
                    {name: 'f_saldar'}
                ],
                localdata: datas

            };
            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#tabla_datos").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    height:'200px',
                    // showfilterrow: true,
                    //  filterable: true,
                    // pageable: true,
                    autoheight: false,
                    editable: false,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "5%"},
                        {text: 'Factura', filtertype: 'textbox', datafield: 'f_factura', width: "15%"},
                        {text: 'Fecha', filtertype: 'textbox', datafield: 'f_fecha', width: "10%"},
                        {text: 'Vencimiento', filtertype: 'textbox', datafield: 'f_vencimiento', width: "10%"},
                        {text: 'Monto', filtertype: 'textbox', datafield: 'f_monto', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Balance', filtertype: 'textbox', datafield: 'f_balance', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'A Pagar', filtertype: 'textbox', datafield: 'f_a_pagar', width: "10%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Concepto', filtertype: 'textbox', datafield: 'f_concepto', width: "15%",cellsalign: 'right',cellsformat: 'c2' },
                        {text: 'Abonar', datafield: 'f_abonar', columntype: 'button', cellsrenderer: function () {
                            return 'Abonar';
                        },buttonclick: function (row) {
                            var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', row);
                            // $('#ui-bootbox-prompt').on('click', function () {
                                bootbox.prompt({
                                    title: "Digite el Monto..",
                                    callback: function(result) {
                                        if (result === null) {
                                            return;
                                        } else {

                                            if (parseFloat(dataRecord.f_balance) == parseFloat(result))
                                            {
                                                $("#tabla_datos").jqxGrid('setcellvalue',row, "f_a_pagar",result );
                                                $("#tabla_datos").jqxGrid('setcellvalue',row, "f_concepto", "Saldado");
                                            }
                                            else {
                                                if (parseFloat(dataRecord.f_balance) < parseFloat(result))
                                                {
                                                    $("#tabla_datos").jqxGrid('setcellvalue',row, "f_a_pagar",dataRecord.f_balance );
                                                    $("#tabla_datos").jqxGrid('setcellvalue',row, "f_concepto", "Saldado");

                                                }
                                                else {
                                                    $("#tabla_datos").jqxGrid('setcellvalue',row, "f_a_pagar",result );
                                                    $("#tabla_datos").jqxGrid('setcellvalue',row, "f_concepto", "Abono");
                                                }
                                            }
                                            calcular_totales();

                                        }
                                    },
                                    className: "bootbox-sm"
                                });
                            // });



                            calcular_totales();
                        }, theme: get_tema(), width: "10%"
                        },
                        {text: 'Saldar', datafield: 'f_saldar', columntype: 'button', cellsrenderer: function () {
                            return 'Saldar';
                        },
                            buttonclick: function (row) {
                                 var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', row);
                                $("#tabla_datos").jqxGrid('setcellvalue',row, "f_a_pagar",dataRecord.f_balance );
                                $("#tabla_datos").jqxGrid('setcellvalue',row, "f_concepto", "Saldado");
                                calcular_totales();
                            }, theme: get_tema(), width: "10%"
                        }

                    ]
                });
           $('#tabla_datos').jqxGrid('hidecolumn', 'f_id');
            calcular_totales();
        }
    });
}

function calcular() {

    var precio_producto = 0 ;
    var porciento_comision = 0;

    if ($("#precio").val()!= '')
    {
        precio_producto =parseFloat($("#precio").val());
    }
    if ($("#porciento_referente").val()!= '')
    {
        porciento_comision =parseFloat($("#porciento_referente").val());
    }


    var itebis_preferencia =parseFloat($("#itebis_preferencia").val());
    var aux =parseFloat(precio_producto) * (itebis_preferencia/100);

    $("#itebis").val(aux);
    $("#comision").val(precio_producto * (porciento_comision/100) );

}

function distribuir() {
    var monto =0;

    if ($("#a_pagar").val()!=''|| parseFloat($("#a_pagar").val())>0) {
        monto = parseFloat($("#a_pagar").val());
    }
    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var t = 0;


    for (var i = 0 ;i < rowscounts;i++)
    {
        var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);

        if (monto==0)
        {
            $("#tabla_datos").jqxGrid('setcellvalue',i, "f_a_pagar", "0");
            $("#tabla_datos").jqxGrid('setcellvalue',i, "f_concepto", "No Acción");
        }
        else {

            if (monto >= parseFloat(dataRecord.f_balance) && monto >= parseFloat(dataRecord.f_balance)-1)
            {
                $("#tabla_datos").jqxGrid('setcellvalue',i, "f_a_pagar",dataRecord.f_balance);
                t = parseFloat(dataRecord.f_balance );
                $("#tabla_datos").jqxGrid('setcellvalue',i, "f_concepto", "Saldado");
                monto = monto - t;


            }
            else
            if(monto < dataRecord.f_balance && monto >0 )
            {
                $("#tabla_datos").jqxGrid('setcellvalue',i, "f_a_pagar",monto);
                $("#tabla_datos").jqxGrid('setcellvalue',i, "f_concepto", "Abono");
                monto =0;
            }
        }

        // $("#tabla_datos").jqxGrid('setcellvalue',i, "f_concepto", "New Value");
    }
    calcular_totales();
    
}

function calcular_totales() {
    var datainformations = $('#tabla_datos').jqxGrid('getdatainformation');
    var rowscounts = datainformations.rowscount;
    var t = 0;
    var a = 0;

    for (var i = 0 ;i < rowscounts;i++) {
        var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', i);
        t = t + dataRecord.f_balance;
        a = a + dataRecord.f_a_pagar;
    }
    $("#sald_deudor").val(t);
    $("#monto_recibo").val(a);

}
