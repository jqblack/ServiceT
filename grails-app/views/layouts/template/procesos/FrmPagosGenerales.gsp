<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pagos Generales</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Pagos Generales</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nuevo_producto"  data-toggle="modal" data-target="#modal_clientes" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Producto</a></div>--}%

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
<div class="panel">
    %{--<div class="panel-heading">--}%
    %{--<span class="panel-title">Morris.js Graph</span>--}%
    %{--</div>--}%
    <div class="panel-body">

        %{--<div class="graph-container">--}%
        %{----}%
        %{--</div>--}%


        <div class="row">

            <div class="col-md-12">
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-2">

                        </div>
                        <div class="col-md-4">
                            <div class="stat-panel">
                                <!-- Danger background, vertically centered text -->
                                <div class="stat-cell bg-primary valign-middle">
                                    <!-- Stat panel bg icon -->
                                    <i class="fa fa-money bg-icon"></i>
                                    <!-- Extra large text -->
                                    <span class="text-bg" id="texto_monto"><span class="text-lg text-slim"></span>$<strong>0</strong></span><br>
                                    <!-- Big text -->
                                    <span class="text-bg">Monto a Pagar RD</span><br>
                                    <span class="text-bg">Total : </span><span class="text-bg" id="texto_monto_adeudado"></span><br>
                                    <!-- Small text -->
                                    %{--<span class="text-sm"><a>See details in your profile</a></span>--}%
                                </div> <!-- /.stat-cell -->
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stat-panel">
                                <!-- Danger background, vertically centered text -->
                                <div class="stat-cell bg-success valign-middle">
                                    <!-- Stat panel bg icon -->
                                    <i class="fa fa-money bg-icon"></i>
                                    <!-- Extra large text -->
                                    %{--<span class="text-xlg" id="texto_monto_pagar_us"><span class="text-lg text-slim"></span>$<strong>0</strong></span><br>--}%
                                    <span class="text-bg" id="texto_monto_pagar_us"><span class="text-lg text-slim"></span>$<strong>0</strong></span><br>
                                    <!-- Big text -->
                                    <span class="text-bg">Monto a Pagar US</span><br>
                                    <span class="text-bg">Total : </span><span class="text-bg" id="texto_monto_adeudado_us"></span><br>
                                    <!-- Small text -->
                                    %{--<span class="text-sm"><a>See details in your profile</a></span>--}%
                                </div> <!-- /.stat-cell -->
                            </div>
                        </div>
                        <div class="col-md-2" style="vertical-align: bottom">


                        </div>

                        <div class="col-md-12 col-sm-12 " style="padding-bottom: 10px">
                            <div class="btn-group btn-group-sm pull-right">
                                <button type="" id="desmarcar_todo" class="btn btn-danger "
                                        data-original-title="Desmarcar Todo" data-dismiss="modal"
                                        aria-hidden="true"><span class="fa fa-square-o">&nbsp;Desmarcar Todo&nbsp;</span></button>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-primary dropdown-toggle"
                                            data-toggle="dropdown"><span
                                            class="fa fa-check-square-o"></span>&nbsp;Marcar Todo&nbsp;<i
                                            class="fa fa-caret-down"></i></button>
                                    <ul class="dropdown-menu ">
                                        <li><a id="marcar_rd">Marcar RD</a></li>
                                        <li><a id="marcar_us">Marcar US</a></li>
                                    </ul>
                                </div>
                                <br/>
                            </div>
                        </div>

                        %{--</div>--}%

                        %{--</form>--}%
                    </div><!-- /.box -->
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-lg-12 col-xl-12 ">

                            <div id="tabla_pagos">
                            </div>

                            <br/>
                            <div class="box-footer">
                                <div class="form-group has-feedback">
                                    <button type="" id="recargar"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-refresh"></span>&nbsp;&nbsp;Recargar</button>
                                    <button type="submit" id="salvar" class="btn btn-primary"><span class="fa fa-save"></span>&nbsp;&nbsp;Salvar</button>
                                </div>

                                <br/>

                            </div>
                        </div><!-- /.box-body -->
                    </div>
                </div>

            </div>
            %{--Moddal del in clientes--}%



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
</div>

<asset:javascript src="js_proyecto/procesos/pagos_generales.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
        $('#pagar_todo').tooltip();
        $('#desmarcar_todo').tooltip();
    })

</g:javascript>

</body>
</html>