/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
function invertir(cadena) {
    var x = cadena.length;
    var cadenaInvertida = "";

    while (x>=0) {
        cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        x--;
    }
    return cadenaInvertida;
}
function ejecutar() {


    var tipo_documento='';
    var nombre_doc='';

    $('#solucion').jqxEditor({
        height: "300px",
        width: '100%'
    });

        $('#documento').jqxFileUpload({ accept: '.pdf,.jpg,.png,',
            rtl : true,
            browseTemplate: 'success',
            uploadTemplate: 'primary',
            cancelTemplate: 'danger',
            multipleFilesUpload: false,
            width: '100%',
            uploadUrl: window.location + '/salvar_documento',
            fileInputName: 'documento' 
			});


    $('#documento').on('select', function (event) {
        var args = event.args;
        var fileName = args.file;
        var fileSize = args.size; // Note: Internet Explorer 9 and earlier do not support getting the file size.

        fileName = invertir(fileName);
        fileName = fileName.substring(0,fileName.indexOf('.'));
        fileName = invertir(fileName);
        tipo_documento = fileName;
        nombre_doc = args.file;
    });



  
    crear_conocimiento();

    $("#activo").parent().addClass('checked');
    $("#activo").prop('checked',true);

    $("#enviar").parent().addClass('checked');
    $("#enviar").prop('checked',true);

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_conocimiento").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });

    $("#atras").click(function () {
        nuevo();
        crear_conocimiento();
    });



   


    $("#tag_documento").click(function () {


        $.ajax({
            type: "POST",
            url: window.location + '/get_documento_by_id',
            data: {idrecord:$('#tag_documento').attr('name')},
            success: function (data) {
                var tupla = eval(data);
                var pdfAsDataUri='';
                if (String(tupla[0].f_tipo_doc)=='pdf')
                {
                    
                     pdfAsDataUri = "data:application/pdf;base64,"+ tupla[0].f_documento;
                    window.open(pdfAsDataUri);
                }
                if (String(tupla[0].f_tipo_doc)=='jpg')
                {
                    
                    window.open("data:image/"+tupla[0].f_tipo_doc+";base64,"+ tupla[0].f_documento);
                }
                if (String(tupla[0].f_tipo_doc)=='png')
                {
                    window.open("data:image/"+tupla[0].f_tipo_doc+";base64,"+ tupla[0].f_documento);
                }

            }

        });
    });

    $("#salvar").click(function () {


        if (validaciones() == true) {


            var vector = {
                idrecord: $("#idrecord").val(),
                idcategoria: $("#categoria").val(),
                solucion: $("#solucion").val(),
                tags: $("#tags").text(),
                titulo: $("#titulo").val(),
                documento:$("#docuemento").val(),
                tipo_doc:tipo_documento,
                nombre_doc:nombre_doc
            };



            $.ajax({
                type: "POST",
                url: window.location + '/salvar_conocimiento',
                data: vector,
                success: function (data) {
                    var tupla = eval(data);

                    if ($('.jqx-file-upload-file-name').text()!='')
                    {
                        $('#documento').jqxFileUpload('uploadFile', 0);
                    }

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

    $('#tabla_conocimiento').on('rowdoubleclick', function (event) {
        var pocision = args.rowindex;
        var data = $('#tabla_conocimiento').jqxGrid('getrowdata', pocision);

        // alert(data.f_id);
        $.ajax({
            type: "POST",
            url: window.location + '/get_conocimiento_by_id',
            data: {idrecord:data.f_idrecord},
            success: function (data) {
                var tupla = eval(data);

                $("#idrecord").val(tupla[0].f_idrecord);
                $("#titulo").val(tupla[0].f_titulo);
                $("#categoria").val(tupla[0].f_idcategoria);
                $("#solucion").val(tupla[0].f_solucion);
                $("#tags").html(tupla[0].f_tags);


                $("#nuevo_conocimiento").click();


            }
        });

    });

}




function crear_conocimiento() {
    $.ajax({
        type: "GET",
        url: window.location + '/get_conocimiento',
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
                    {name: 'f_titulo'},
                    {name: 'f_nombre'}
                ],
                localdata: datas

            };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_conocimiento").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: false,
                    autoheight: false,
                    editable: false,
                    sortable: true,
                    //groupable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "0%"},
                        {text: 'Descripción', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_titulo', width: "70%"},
                        {text: 'Categoria', filtertype: 'textbox', datafield: 'f_nombre', width: "20%"},
                        {text: 'Ver Solución', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Ver Solución';
                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_conocimiento").jqxGrid('getrowdata', row);
                                // alert(data.f_id);

                                $.ajax({
                                    type: "POST",
                                    url: window.location + '/crear_id_solucion',
                                    data: {idrecord:dataRecord.f_idrecord},
                                    success: function (data) {
                                        window.open(window.location+"/ver_solucion");
                                    }
                                });
                                // $.ajax({
                                //     type: "POST",
                                //     url: window.location + '/get_conocimiento_by_id',
                                //     data: {idrecord:dataRecord.f_idrecord},
                                //     success: function (data) {
                                //         var tupla = eval(data);
                                //         $("#titulo_solucion").text(tupla[0].f_titulo.toUpperCase());
                                //         $("#texto_solucion").html(tupla[0].f_solucion);
                                //         $('#panel_solucion').show();
                                //
                                //         if (String(tupla[0].f_documento)=='true')
                                //         {
                                //             // $("#tag_documento").css('display','block');
                                //             $('#tag_documento').show();
                                //             $('#tag_documento').attr('name', tupla[0].f_idrecord);
                                //             // //document.getElementById("ItemPreview").src = "data:image/png;base64," + tupla[0].f_documento;
                                //             // var pdfAsDataUri = "data:application/docx;base64,"+ tupla[0].f_documento;
                                //             // window.open(pdfAsDataUri);
                                //          }
                                //         else {
                                //             // $("#tag_documento").css('display','none');
                                //             $('#tag_documento').hide();
                                //
                                //         }
                                //     }
                                // });


                            }, theme: get_tema(), width: "10%"
                        }

                    ]
                });
            $('#tabla_conocimiento').jqxGrid('hidecolumn', 'f_idrecord');
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
    if ($("#titulo").val() == '') {
        notificacion_no('Digite el Titulo');
        valor = false;
    }
    else if ($("#categoria").val() == '') {
        notificacion_no('Seleccione la Categoria');
        valor = false;
    }
    else if ($("#solucion").val() == '') {
        notificacion_no('Digite la Solución');
        valor = false;
    }
    return valor;
}

function nuevo() {
    $("#idrecord").val('');
    $("#categoria").val('');
    $("#solucion").val('');
    $("#tags").val('');
    $("#titulo").val('');
    $('#panel_solucion').hide();
    $('#tag_documento').hide();

}


function calcular() {

    var precio_producto = 0 ;
    var porciento_comision = 0;
    var cantidad = 0;

    if ($("#precio").val()!= '')
    {
        precio_producto =parseFloat($("#precio").val());
    }
    if ($("#porciento_referente").val()!= '')
    {
        porciento_comision =parseFloat($("#porciento_referente").val());
    }

    if ($("#cantidad").val()!= '')
    {
        cantidad =parseFloat($("#cantidad").val());
    }



    var itebis_preferencia =parseFloat($("#itebis_preferencia").val());
    var aux =parseFloat(precio_producto) * (itebis_preferencia/100);

    $("#itebis").val(aux);
    $("#comision").val(precio_producto * (porciento_comision/100) );
    $("#monto").val(ConvertToCurrency(precio_producto * cantidad));

}

function ConvertToCurrency(number) {
    var number = number.toString(),
        dollars = number.split('.')[0],
        cents = (number.split('.')[1] || '') +'00';
    dollars = dollars.split('').reverse().join('')
        .replace(/(\d{3}(?!$))/g, '$1,')
        .split('').reverse().join('');
    return dollars + '.' + cents.slice(0, 2);
    // return '$' +dollars + '.' + cents.slice(0, 2);
}
