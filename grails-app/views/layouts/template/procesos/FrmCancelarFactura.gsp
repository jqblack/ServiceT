<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cancelar Factura</title>

</head>
<body>

<style>
/* === card component ======
* Variation of the panel component
* version 2018.10.30
* https://codepen.io/jstneg/pen/EVKYZj
*/
.card{ background-color: #fff; border: 1px solid transparent; border-radius: 6px; }
.card > .card-link{ color: #333; }
.card > .card-link:hover{  text-decoration: none; }
.card > .card-link .card-img img{ border-radius: 6px 6px 0 0; }
.card .card-img{ position: relative; padding: 0; display: table; }
.card .card-img .card-caption{
    position: absolute;
    right: 0;
    bottom: 16px;
    left: 0;
}
.card .card-header{ border-radius: 6px 6px 0 0; padding: 8px; }
.card .card-footer{ border-radius: 0 0 6px 6px; padding: 8px; }
.card .card-left{ position: relative; float: left; padding: 0 0 8px 0; }
.card .card-right{ position: relative; float: left; padding: 8px 0 0 0; }
.card .card-body h1:first-child,
.card .card-body h2:first-child,
.card .card-body h3:first-child,
.card .card-body h4:first-child,
.card .card-body .h1,
.card .card-body .h2,
.card .card-body .h3,
.card .card-body .h4{ margin-top: 0; }
.card .card-body .heading{ display: block;  }
.card .card-body .heading:last-child{ margin-bottom: 0; }

.card .card-body .lead{ text-align: center; }

@media( min-width: 768px ){
    .card .card-left{ float: left; padding: 0 8px 0 0; }
    .card .card-right{ float: left; padding: 0 0 0 8px; }

    .card .card-4-8 .card-left{ width: 33.33333333%; }
    .card .card-4-8 .card-right{ width: 66.66666667%; }

    .card .card-5-7 .card-left{ width: 41.66666667%; }
    .card .card-5-7 .card-right{ width: 58.33333333%; }

    .card .card-6-6 .card-left{ width: 50%; }
    .card .card-6-6 .card-right{ width: 50%; }

    .card .card-7-5 .card-left{ width: 58.33333333%; }
    .card .card-7-5 .card-right{ width: 41.66666667%; }

    .card .card-8-4 .card-left{ width: 66.66666667%; }
    .card .card-8-4 .card-right{ width: 33.33333333%; }
}

/* -- default theme ------ */
.card-default{
    border-color: #ddd;
    background-color: #fff;
    margin-bottom: 24px;
}
.card-default > .card-header,
.card-default > .card-footer{ color: #333; background-color: #ddd; }
.card-default > .card-header{ border-bottom: 1px solid #ddd; padding: 8px; }
.card-default > .card-footer{ border-top: 1px solid #ddd; padding: 8px; }
.card-default > .card-body{  }
.card-default > .card-img:first-child img{ border-radius: 6px 6px 0 0; }
.card-default > .card-left{ padding-right: 4px; }
.card-default > .card-right{ padding-left: 4px; }
.card-default p:last-child{ margin-bottom: 0; }
.card-default .card-caption { color: #fff; text-align: center; text-transform: uppercase; }


/* -- price theme ------ */
.card-price{ border-color: #999; background-color: #ededed; margin-bottom: 24px; }
.card-price > .card-heading,
.card-price > .card-footer{ color: #333; background-color: #fdfdfd; }
.card-price > .card-heading{ border-bottom: 1px solid #ddd; padding: 8px; }
.card-price > .card-footer{ border-top: 1px solid #ddd; padding: 8px; }
.card-price > .card-img:first-child img{ border-radius: 6px 6px 0 0; }
.card-price > .card-left{ padding-right: 4px; }
.card-price > .card-right{ padding-left: 4px; }
.card-price .card-caption { color: #fff; text-align: center; text-transform: uppercase; }
.card-price p:last-child{ margin-bottom: 0; }

.card-price .price{
    text-align: center;
    color: #337ab7;
    font-size: 3em;
    text-transform: uppercase;
    line-height: 0.7em;
    margin: 24px 0 16px;
}
.card-price .price small{ font-size: 0.4em; color: #66a5da; }
.card-price .details{ list-style: none; margin-bottom: 24px; padding: 0 18px; }
.card-price .details li{ text-align: center; margin-bottom: 8px; }
.card-price .buy-now{ text-transform: uppercase; }
.card-price table .price{ font-size: 1.2em; font-weight: 700; text-align: left; }
.card-price table .note{ color: #666; font-size: 0.8em; }
</style>

<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Cancelar Facturas</h1>

        <div class="col-xs-12 col-sm-1">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>


            </div>
        </div>
    </div>
</div> <!-- / .page-header -->

<div class="row">

    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div class="panel">
            <div class="panel-heading">
                <span class="panel-title">Busqueda</span>
            </div>
            <div class="panel-body">

                %{--<div class="graph-container">--}%
                %{----}%
                %{--</div>--}%


                <div class="row">
                    <div class="col-md-4"> </div>
                    <div class="col-md-12">
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-9">
                                    <div class="form-group ">
                                        <label>Numero Factura</label>
                                        <input type="text" id="parametro"     class="form-control" placeholder="Parametro Busqueda">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group ">
                                        <label>&nbsp;&nbsp;&nbsp;</label><br/>
                                        <button type="" id="boton_buscar" onclick="buscar()"  class="btn btn-primary btn-block">Buscar</button>
                                    </div>
                                    %{--<div class="form-group ">--}%
                                    %{--<label>&nbsp;&nbsp;&nbsp;</label><br/>--}%

                                    %{--</div>--}%
                                </div>

                            </div>
                            <div class="row">

                                <div class="col-md-12 col-xl-12">

                                </div>
                                %{--</form>--}%
                            </div><!-- /.box -->


                        </div>

                    </div>
                    <div class="col-md-0">
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
        </div>
    </div>
    <div class="col-md-4"></div>

</div>
<div class="row">

    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div class="card card-default" id="carta" style="display: none;" >
            <div class="card-body" style="padding: 12px;">
                %{--<h5 class="card-title">Card title</h5>--}%
                <h3 class="card-subtitle mb-2 text-muted" id="numero">FCR0000025</h3>
                <h4 class="text-bold">Cliente :  </h4><h4 id="cliente">Juan Carlos Arias</h4>
                <h4 class="text-bold">Monto &nbsp;: </h4><h4 class="text-bold" style="color: darkred;" id="monto">5,000.00</h4>
                <h4 class="text-bold">Fecha &nbsp;&nbsp;: </h4><h4 id="fecha">2019/05/10</h4>
                <button  class="btn btn-danger btn-block" id="cancelar" onclick="cancelar()">Cancelar</button>
            </div>
        </div>
    </div>
    <div class="col-md-4"></div>

</div>


<asset:javascript src="js_proyecto/procesos/cancelar_factura.js"/>

</body>
</html>