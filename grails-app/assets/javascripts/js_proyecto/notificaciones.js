function notificaciones() {
    document.addEventListener('DOMContentLoaded', function () {
        if (!Notification) {
            alert('Desktop notifications not available in your browser. Try Chromium.');
            return;
        }
        if (Notification.permission !== "granted")
            Notification.requestPermission();
    });
    window.setInterval(notifyMe, 60000*1);
}
function notifyMe() {
    $.ajax({
        type: "POST",
        url:window.location.protocol+"//"+ window.location.host + '/jq/get_notificaciones',
        //data: {idrecord:$('#tag_documento').attr('name')},
        success: function (data) {
            if (data!='[]')
            {
                var tupla = eval(data);
                for (var i =0 ;i< tupla.length;i++)
                {
                  mostrar('Nuevo Ticket',tupla[i].f_titulo,tupla[i].f_idticket,tupla[i].f_idrecord);
                }
            }



        }

    });

}
function mostrar(titulo,descripcion,id,idrecord) {
    if (Notification.permission !== "granted")
        Notification.requestPermission();
    else {
        var notification = new Notification(titulo, {
            icon: window.location.protocol+"//"+ window.location.host + '/jq/images/Logo_pequeno.png',
            body: descripcion,
        });
        notification.onclick = function () {
            $.ajax({
                type: "POST",
                url:window.location.protocol+"//"+ window.location.host + '/jq/set_visualizado',
                data: {idrecord:idrecord},
                success: function (data) {
                    window.open(window.location.protocol+"//"+ window.location.host + "/jq/asignar_tickets?idt="+id);
                    notification.close();
                }
            });
        };

    }
}
