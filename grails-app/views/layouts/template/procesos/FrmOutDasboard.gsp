<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Dashboard</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i
                class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Dasboard</h1>

        <div class="col-xs-12 col-sm-1">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">

                <div class="visible-xs clearfix form-group-margin"></div>

            </div>
        </div>
    </div>
</div> <!-- / .page-header -->


<div class="row">
    <div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 col-xs-12">

        <!-- 7. $EXAMPLE_ICON_PANEL ========================================================================

				Icon panel example
-->
        <div class="stat-panel" style="height: 100px;">
            <div class="stat-row">
                <!-- Info background, without padding, horizontally centered text, super large text -->
                <div class="stat-cell bg-success no-padding text-center text-lg">
                    <i class="fa  fa-check-square-o"><span style="font-family: Roboto, HelveticaNeue, sans-serif; ">Activos</span></i>
                </div>
            </div> <!-- /.stat-row -->
            <div class="stat-row">
                <!-- Bordered, without top border, horizontally centered text, large text -->
                <div class="stat-cell bordered no-border-t text-center text-lg">
                    <strong>${activos as String}</strong>
                    %{--<small><small>${activos as String}</small></small>--}%
                </div>
            </div> <!-- /.stat-row -->
        </div> <!-- /.stat-panel -->
    <!-- /7. $EXAMPLE_ICON_PANEL -->


    </div>
    <div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 col-xs-12">

        <!-- 7. $EXAMPLE_ICON_PANEL ========================================================================

				Icon panel example
-->
        <div class="stat-panel" style="height: 100px;">
            <div class="stat-row">
                <!-- Info background, without padding, horizontally centered text, super large text -->
                <div class="stat-cell bg-info no-padding text-center text-lg">
                    <i class="fa  fa-users" ><span style="font-family: Roboto, HelveticaNeue, sans-serif; ">Total Servicios</span> </i>
                </div>
            </div> <!-- /.stat-row -->
            <div class="stat-row">
                <!-- Bordered, without top border, horizontally centered text, large text -->
                <div class="stat-cell bordered no-border-t text-center text-lg">
                    <strong>${total_clientes}</strong>
                    %{--<small><small>PM</small></small>--}%
                </div>
            </div> <!-- /.stat-row -->
        </div> <!-- /.stat-panel -->
    <!-- /7. $EXAMPLE_ICON_PANEL -->

    </div>

    <div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 col-xs-12">

        <!-- 7. $EXAMPLE_ICON_PANEL ========================================================================

				Icon panel example
-->
        <div class="stat-panel" style="height: 100px;">
            <div class="stat-row">
                <!-- Info background, without padding, horizontally centered text, super large text -->
                <div class="stat-cell bg-primary no-padding text-center text-lg">
                    <i class="fa fa-money"><span style="font-family: Roboto, HelveticaNeue, sans-serif; ">Total Ventas</span ></i>
                </div>
            </div> <!-- /.stat-row -->
            <div class="stat-row">
                <!-- Bordered, without top border, horizontally centered text, large text -->
                <div class="stat-cell bordered no-border-t text-center text-lg">
                    <strong id="total_ventas"></strong>
                    %{--<small><small>PM</small></small>--}%
                </div>
            </div> <!-- /.stat-row -->
        </div> <!-- /.stat-panel -->
    <!-- /7. $EXAMPLE_ICON_PANEL -->

    </div>
    <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12 col-xl-12 ">
        <div class="btn-group btn-group-lg pull-right" style="padding-bottom: 10px">
            <button type="button" id="mensaje_todos" class="btn btn-primary"><span class="fa fa-location-arrow text-lg">&nbsp;Mensajes a Todos</span></button>
            <button type="button" id="mensaje_seleccionados" class="btn btn-success"><span class="fa fa-location-arrow text-lg">&nbsp;Mensajes a Selccionados</span></button>

        </div>

    </div>

    <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12 col-xl-12">

        <!-- Primary table -->
        <div class="table-primary">
            <div class="table-header">
                <div class="table-caption">
                    Datos Clientes
                </div>
            </div>
            <table  class="table table-bordered" style="background: white">
                <thead>
                <tr>
                    <th style="display: none">Idservicios</th>
                    <th>Codigo</th>
                    <th>Cliente</th>
                    <th>Servicio</th>
                    <th>Entrada</th>
                    <th>Activación</th>
                    <th>U. Actualización</th>
                    <th style="text-align: right">Balance</th>
                    <th style="text-align: right">Ventas</th>
                    <th>Moneda</th>
                    <th>Seleccionar</th>
                    <th>Opciones</th>
                </tr>
                </thead>
                <tbody id="tabla_datos">
                <g:each in="${lista_clientes}" >
                    <% Map map =seguridadService.getActyualizacionesServidores(it.idservicio as Long); %>
                    <tr>
                        <td style="display: none">${it.idservicio}</td>
                        <td>${it.idrecord}</td>
                        <td>${it.cliente}</td>
                        <td>${it.servicio}</td>
                        <td>${it.entrada}</td>
                        <td>${it.activacion}</td>
                        <td>${map.ultima_actualizacion}</td>
                        <td  style="text-align: right;color: red;font-weight: bold ">${funciones.CurrencyFormat(it.balance as BigDecimal)}</td>
                        <td style="text-align: right ;color: darkgreen;font-weight: bold">${funciones.CurrencyFormat(map.total as BigDecimal)}</td>
                        <td>${it.moneda}</td>
                        <td><input id="${it.idrecord}" type="checkbox" data-class="switcher-primary" class="activos"  ></td>
                        <td>
                            %{--<div class="buttons-with-margins">--}%
                            <div class="pull-right">
                                <button class="btn btn-primary btn_tooltip" onclick="enviarMensajeCliente('${it.idrecord}')" data-toggle="tooltip" data-placement="top" title
                                        data-original-title="Enviar Mensaje"><span
                                        class="fa fa-comments-o"></span></button> &nbsp;

                                <button class="btn btn-danger btn_tooltip" onclick="getFactura('${it.idrecord}')" data-toggle="tooltip" data-placement="top" title
                                        data-original-title="Enviar Factura"><span
                                        class="fa fa-money"></span></button> &nbsp;

                                <button class="btn btn-success btn_tooltip" onclick="verFactura('${it.idrecord}')" data-toggle="tooltip" data-placement="top" title
                                        data-original-title="Ver Factura"><span
                                        class="fa fa-eye"></span></button> &nbsp;

                                <button class="btn btn-danger btn_tooltip" onclick="desactivarServicio('${it.idservicio}')" data-toggle="tooltip" data-placement="top" title
                                        data-original-title="Desactivar"><span
                                        class="fa fa-arrow-circle-down"></span></button> &nbsp;

                            </div>
                        </td>
                    </tr>
                </g:each>



                </tbody>
            </table>

            <div class="table-footer">

            </div>
        </div>
        <!-- / Primary table -->

    </div>







    %{--MODAL MENSAJES--}%
    <div id="modal_mensajes" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-md">
            <div class="modal-content">
                <div class="modal-header">
                    %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                    <h4 class="modal-title">Registrar Mensajes</h4>
                </div>
                <div class="row">
                    <input type="hidden" id="idrecord"  class="form-control" placeholder="idrecord  ">
                    <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                    <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                %{--<h3 class="box-title">Clientes</h3>--}%
                            </div><!-- /.box-header -->
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-md-12 col-sm-12">
                                        <div class="row">
                                            <div class="col-md-4 col-sm-12 col-xl-12 col-lg-4">

                                                <div class="form-group has-feedback">
                                                    <label>Tipo</label>
                                                    <select  class="form-control" id="tipo">
                                                        <option value="">-SELECCIONAR-</option>
                                                        <option value="1">Aviso</option>
                                                        <option value="2">Bloqueado</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-12">

                                                <div class="form-group">
                                                    <label for="mensaje">Mensaje</label>
                                                    <textarea class="form-control" rows="5" id="mensaje"></textarea>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="row">

                                        </div>

                                        <div class="row">

                                            <div class="col-md-2 col-sm-6 col-xl-6 col-lg-2">

                                                <div class="form-group has-feedback">
                                                    <label>Activo</label><br/>
                                                    <input type="checkbox" class="activos" data-class="switcher-primary" id="activo_mensaje" >&nbsp;&nbsp;
                                                </div>
                                            </div>
                                            <div class="col-md-2 col-sm-6 col-xl-6 col-lg-2">

                                            </div>
                                            <div class="col-md-2 col-sm-0 col-xl-0 col-lg-4">
                                                <div class="form-group has-feedback">
                                                </div>
                                            </div>

                                        </div>

                                        <div class="box-footer">
                                            <div class="form-group has-feedback">
                                                %{--<a href="" class="btn btn-primary ">--}%
                                                %{--<span class="fa fa-arrow-left"></span> Atras--}%
                                                %{--</a>--}%
                                                <button type="" id="atras_mensaje"  class="btn btn-danger" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                                <button type="submit" id="salvar_mensaje" class="btn btn-primary">Salvar</button>
                                            </div>

                                            <br><br>

                                        </div>
                                    </div><!-- /.box-body -->
                                %{--</form>--}%
                                </div><!-- /.box -->
                            </div>
                            <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                        </div>

                    </div> <!-- / .modal-content -->
                </div> <!-- / .modal-dialog -->
            </div> <!-- / .modal -->
        <!-- / Large modal -->
        </div>
    </div>
    %{--FIN MODAL MENSAJES--}%




    <!-- Template -->
    <div id="modal_reporte" class="modal fade modal-blur" tabindex="-1" role="dialog" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">Reporte</h4>
                </div>
                <div class="modal-body">
                    <div id="pdf">

                    </div>

                </div>
            </div> <!-- / .modal-content -->
        </div> <!-- / .modal-dialog -->
    </div> <!-- / .modal -->
<!-- / Template -->








%{--modales de notificaciones--}%
    <div id="uidemo-modals-alerts-success" class="modal modal-alert modal-success fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <i class="fa fa-check-circle"></i>
                </div>

                <div id="titulo_notifificacion_si" class="modal-title">Alerta</div>

                <div id="body_notifificacion_si" class="modal-body">Some alert text</div>

                <div class="modal-footer">
                    <button id="ok_notifificacion_si" type="button" class="btn btn-success"
                            data-dismiss="modal">OK</button>
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
                    <button id="ok_notifificacion_no" type="button" class="btn btn-danger" data-dismiss="modal"
                            aria-hidden="true">OK</button>
                </div>
            </div> <!-- / .modal-content -->
        </div> <!-- / .modal-dialog -->
    </div> <!-- / .modal -->
<!-- / Danger -->

    <button id="notificacion_si" style="display:none" class="btn btn-success" data-toggle="modal"
            data-target="#uidemo-modals-alerts-success">Success</button>&nbsp;&nbsp;&nbsp;
    <button id="notificacion_no" style="display:none" class="btn btn-danger" data-toggle="modal"
            data-target="#uidemo-modals-alerts-danger">Danger</button>&nbsp;&nbsp;&nbsp;

</div>

<asset:javascript src="js_proyecto/procesos/dashboard.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
        $('.activos ').switcher({theme: 'modern'});
        $('.btn_tooltip').tooltip();
        calcular();
    })

</g:javascript>

</body>
</html>