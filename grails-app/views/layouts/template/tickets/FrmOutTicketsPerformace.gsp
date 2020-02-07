<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Tickets Performance</title>
</head>

<body>

%{--<input id="url_imagen" value="${getRequest().getContextPath()}/images/avatar.png" style="display: none"/>--}%
<asset:image id="url_imagen" value="avatar.png" src="avatar.png" style="display: none"/>
<style>
.btn-file {
    position: relative;
    overflow: hidden;
}
.btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: deepskyblue;
    cursor: inherit;
    display: block;
}
</style>

<div class="page-header">

    <div class="row">
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-ticket page-header-icon"></i>Reporte</h1>
    </div>

</div> <!-- / .page-header -->
<div class="panel">

    <div class="panel-body">
        <div class="row">
            <div class="col-md-12 col-sm-12 rango_fechas">


                %{--<input type="text" name="name" id="titulo" class="form-control">--}%

                <div class="row">

                    <div class="col-md-4">
                        <div class="form-group no-margin-hr">
                            <label class="control-label">Rango</label>
                            <div class="input-daterange input-group" id="bs-datepicker-range">
                                <input type="text" class="form-control" id="fecha_desde" name="fecha_desde" placeholder="Start date" value="${params.fecha_desde==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_desde}">
                                <span class="input-group-addon">to</span>
                                <input type="text" class=" form-control" id="fecha_hasta" name="fecha_hasta" placeholder="End date" value="${params.fecha_hasta==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_hasta}">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <div class="form-group no-margin-hr">
                            <label class="control-label">&nbsp;&nbsp;&nbsp;</label><br/>
                            <span class="floatR">
                                <input type="submit" id="actualizar" class="btn btn-success" type="button" value="Actualizar" />

                            </span>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-sm-12">

                <div id="tabla_datos">
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-sm-12">




            </div>
        </div>
    </div>

</div>

<div id="uidemo-modals-alerts-success" class="modal modal-alert modal-success fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-check-circle"></i>
            </div>
            <div id="titulo_notifificacion_si" class="modal-title">Alerta</div>
            <div id="body_notifificacion_si" class="modal-body">Some alert text</div>
            <div class="modal-footer">
                <button id="ok_notifificacion_si" type="button" class="btn btn-success" data-dismiss="modal">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div> <!-- / .modal -->
<!-- / Success -->

<!-- Danger -->
<div id="uidemo-modals-alerts-danger" class="modal modal-alert modal-danger fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <i class="fa fa-times-circle"></i>
            </div>
            <div id="titulo_notifificacion_no" class="modal-title">Alerta</div>
            <div id="body_notifificacion_no" class="modal-body">Some alert text</div>
            <div class="modal-footer">
                <button id="ok_notifificacion_no" type="button" class="btn btn-danger" data-dismiss="modal"  aria-hidden="true">OK</button>
            </div>
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div> <!-- / .modal -->
<!-- / Danger -->
<button  id="notificacion_si" style="display:none"  class="btn btn-success" data-toggle="modal" data-target="#uidemo-modals-alerts-success">Success</button>&nbsp;&nbsp;&nbsp;
<button id="notificacion_no" style="display:none" class="btn btn-danger" data-toggle="modal" data-target="#uidemo-modals-alerts-danger">Danger</button>&nbsp;&nbsp;&nbsp;




<asset:javascript src="js_proyecto/tickets/reporte_tickets_performace.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();

    });

</g:javascript>
</body>
</html>