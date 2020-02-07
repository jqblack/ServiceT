/**
 * Created by Pabel on 22/8/2016.
 */
function get_tema() {
    return 'material';
}

function ejecutar() {
    crear_tabla('0');
    crear_combo();
};

function crear_combo() {

    $.ajax({
        type: "post",
        url: window.location +"/get_tareas",
        success: function (data) {

            var datas= data;
            var source;

            source=
            {
                datatype: "json",
                dataFields: [
                    {name: "id"},
                    {name: "f_descripcion_tarea"},
                    {name: "f_proyecto"},
                    {name: "f_ultima_actualizacion"},
                    {name: "f_empleado"}
                ],
                localdata: datas
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#tabla_tareas").jqxGrid(
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
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'id', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_descripcion_tarea', width: "70%"},
                        {text: 'Proyecto', filtertype: 'textbox', datafield: 'f_proyecto', width: "30%"}
                    ]
                }
            );

            $('#tabla_tareas').jqxGrid('hidecolumn', 'id');
        }




    });

    // initialize jqxGrid
    $("#combo").jqxDropDownButton({
        width: '100%', height: 25,theme:get_tema(),closeDelay:500
    });
    $("#tabla_tareas").on('rowselect', function (event) {
        var args = event.args;
        var row = $("#tabla_tareas").jqxGrid('getrowdata', args.rowindex);
        var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' + row['f_descripcion_tarea'] + '</div>';
        $("#combo").jqxDropDownButton('setContent', dropDownContent);
        crear_tabla(row['id']);

        $("#combo").jqxDropDownButton("close");
    });
    $("#tabla_tareas").jqxGrid('selectrow', 0);


}


function crear_tabla(id) {


    if(id=='0')
    {


                var datas= [];
                var source;

                source=
                {
                    datatype: "array",
                    dataFields: [
                        {name: "f_idrecord"},
                        {name: "f_comentario"},
                        {name: "f_empleado"}
                    ],
                    localdata: datas
                };

                var dataAdapter = new $.jqx.dataAdapter(source);

                $("#tabla_track").jqxGrid(
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
                            {text: 'Comentario', filtertype: 'textbox', datafield: 'f_comentario', width: "80%"},
                            {text: 'Empleado', filtertype: 'textbox', datafield: 'f_empleado', width: "20%"}

                        ]
                    }
                );

                $('#tabla_track').jqxGrid('hidecolumn', 'f_idrecord');

    }
    else
    {

        $.ajax({
            type: "post",
            url: window.location +"/get_tracks",
            data:{id:id},
            success: function (data) {

                var datas= data;
                var source;

                source=
                {
                    datatype: "json",
                    dataFields: [
                        {name: "f_idrecord"},
                        {name: "f_comentario"},
                        {name: "f_empleado"}
                    ],
                    localdata: datas
                };

                var dataAdapter = new $.jqx.dataAdapter(source);

                $("#tabla_track").jqxGrid(
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
                            {text: 'Comentario', filtertype: 'textbox', datafield: 'f_comentario', width: "70%"},
                            {text: 'Empleado', filtertype: 'textbox', datafield: 'f_empleado', width: "20%"},
                            {text: 'Ver', datafield: '', columntype: 'button', cellsrenderer: function () {
                                return 'Ver';
                            },
                                buttonclick: function (row) {
                                    var dataRecord = $("#tabla_track").jqxGrid('getrowdata', row);
                                    $("#texto").html(dataRecord.f_comentario);
                                    $("#mostrar_modal").click();
                                }, theme: get_tema(), width: "10%"
                            }

                        ]
                    }
                );

                $('#tabla_track').jqxGrid('hidecolumn', 'f_idrecord');
            }

        })
    }



}