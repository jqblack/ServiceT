<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>

<body>

<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-inbox page-header-icon"></i>&nbsp;&nbsp;Ditribución Correos</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_referente"  data-toggle="modal" data-target="#modal_referente" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Referente</a></div>--}%

                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>

                <!-- Search field -->
                %{--<form action="" class="pull-right col-xs-12 col-sm-6">--}%
                %{--<div class="input-group no-margin">--}%
                %{--<span class="input-group-addon" style="border:none;background: #fff;background: rgba(0,0,0,.05);"><i class="fa fa-search"></i></span>--}%
                %{--<input type="text" placeholder="Search..." class="form-control no-padding-hr" style="border:none;background: #fff;background: rgba(0,0,0,.05);">--}%
                %{--</div>--}%
                %{--</form>--}%
            </div>
        </div>
    </div>
</div> <!-- / .page-header -->

<div class="row">
    <div class="col-md-12">
        <div class="panel">
            %{--<div class="panel-heading">--}%
            %{--<span class="panel-title">Clientes</span>--}%
            %{--</div>--}%
            <div class="panel-body">
                <H2 style="font-weight: bold;text-align: center" id="nombre_del_cliente">NO SE HA SELECCIONADO UN CLIENTE</H2>
            </div>
        </div>


    </div>
</div>
<div class="row">
    <div class="col-md-6">





        <div class="panel">
            <div class="panel-heading">
                <span class="panel-title">Clientes</span>
            </div>
            <div class="panel-body">

                <div id="tabla_clientes"></div>

            </div>
        </div>


    </div>
    <div class="col-md-6">

        <div class="panel panel-primary">
            <div class="panel-heading">
                <span class="panel-title" style="color: white">Correos Broadcast</span><button disabled="true" style="margin-top: -7px;" type="submit" id="add" class="btn btn-success pull-right">+</button>
            </div>
            <div class="panel-body">

                <div id="tabla_correos"></div>

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


    </div>

</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel">

            <div class="panel-body" style="text-align: center">
                <button  type="submit" id="salvar" class="btn btn-primary">Salvar</button>
            </div>
        </div>


    </div>
</div>


<asset:javascript src="js_proyecto/registros/distribucion_correo.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    });
</g:javascript>
</body>
</html>