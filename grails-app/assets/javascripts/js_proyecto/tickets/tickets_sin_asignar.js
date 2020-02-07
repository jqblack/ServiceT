/**
 * Created by Pabel on 7/2/2017.
 */


function ejecutar() {
    crear_windows();
}

function  crear_windows () {



    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'

    }
    $('#bs-datepicker-range').datepicker(options2);

    // $('#window_asignar').jqxWindow({ showCloseButton: false,resizable: false, height: 'auto', width: '50%',  position: { x: '25%' , y: '50%'},isModal: true, modalOpacity: 0.3 });
    // $('#window_asignar').jqxWindow('close');

    // $(".link_asignar").click(function () {
    //     var mainDemoContainer = $(this);
    //     var offset = mainDemoContainer.offset();
    //     $('#window_asignar').jqxWindow('show',{position: { x: offset.left + 50, y: offset.top + 50}});
    //     // })

    $("#cancelar").click(function () {
        // $('#window_asignar').jqxWindow('close');
    })
}
