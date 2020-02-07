<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Archivos Tickets</title>
</head>

<body>

%{--<input id="url_imagen" value="/images/avatar.png" style="display: none"/>--}%
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

<asset:javascript src="js_proyecto/tickets/ver_archivos_tickets.js"/>

<div class="page-header">

    <div class="row">
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-ticket page-header-icon"></i>Reporte</h1>
    </div>

</div> <!-- / .page-header -->
<div class="panel">

    <div class="panel-body">
        <div class="row">
            <div class="col-md-12 col-sm-12 rango_fechas">
                <form method="post" action="ver_archivos_tickets" >

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
                                <label class="control-label">Numero</label>
                                <input type="text" name="numero_ticket" id="numero_ticket" value="${params.numero_ticket.toString().replace('null','')}" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group no-margin-hr">
                                <label class="control-label">Titulo</label>
                                <input type="text" name="titulo" id="titulo" value="${params.titulo.toString().replace('null','')}" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-1">
                            <div class="form-group has-feedback ">
                                <label>Abierto</label><br/>
                                <input type="checkbox" name="abierto" class="form-control" id="abierto" >&nbsp;&nbsp;

                            </div>

                            <%
                                if (!params.abierto.is(null) && !params.abierto.toString().equals(""))
                                {
                            %>

                            <g:javascript>
                                poner_check_abierto();
                            </g:javascript>

                            <%
                                }else
                                {
                            %>
                            <g:javascript>
                                quitar_check_abierto();
                            </g:javascript>
                            <%
                                }
                            %>

                        </div>
                        <div class="col-md-1">
                            <div class="form-group has-feedback ">
                                <label>Cerrado</label><br/>
                                <input type="checkbox" name="cerrado" class="form-control" id="cerrado" >&nbsp;&nbsp;

                            </div>

                            <%
                                if (!params.cerrado.is(null) && !params.cerrado.toString().equals(""))
                                {
                            %>

                            <g:javascript>
                                poner_check();
                            </g:javascript>

                            <%
                                }else
                                {
                            %>
                            <g:javascript>
                                quitar_check();
                            </g:javascript>
                            <%
                                }
                            %>

                        </div>
                        <div class="col-md-1">
                            <div class="form-group no-margin-hr">
                                <label class="control-label">&nbsp;&nbsp;&nbsp;</label><br/>
                                <span class="floatR">
                                    <input type="submit" id="actualizar" class="btn btn-success" type="button" value="Buscar" />

                                </span>
                            </div>

                        </div>

                    </div>

                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-sm-12">

                <div class="table-primary">
                    <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="jq-datatables-example">
                        <thead>
                        <tr>
                            <th >#</th>
                            <th style="text-align: center">Titulos</th>
                            <th style="text-align: center">Tecnico</th>
                            <th style="text-align: center">Tipo</th>
                            <th style="text-align: center">Hecho Por</th>
                            <th style="text-align: center">Fecha</th>
                            <th style="text-align: center">Fecha Modificacion</th>
                            <th style="text-align: center">Fecha Vencimiento</th>
                            <th style="text-align: center">Archvios</th>
                            <th style="text-align: center">Comentarios</th>

                            %{--<th style="text-align: center">Proridad</th>--}%

                        </tr>
                        </thead>
                        <tbody id="cuerpo_reporte">

                        <%
                            for(Map<String,Object> tupla: list_tickets)
                            {
                        %>

                        <tr class="odd gradeX">
                            <td >${tupla.get("f_idrecord").toString().padLeft(6,"0")}</td>
                            <td>${tupla.get("f_titulo")}</td>
                            <td>${tupla.get("f_tecnico")}</td>
                            <td >${tupla.get("f_tipo_espanol").toString()}</td>

                            <td>${tupla.get("f_hecho_por")}</td>
                            <td>${tupla.get("f_fecha")}</td>
                            <td class="center">${tupla.get("f_fecha_modificacion")}</td>
                            <td class="center">${tupla.get("f_fecha_vencimiento")}</td>
                            <td class="center">

                                <span class="floatR">
                                    <button class="btn btn-primary" id="get_archivos${tupla.get("f_idrecord").toString().encodeAsBase64().replace("=","")}" name="${tupla.get("f_idrecord")}" onclick="get_archivos('get_archivos${tupla.get("f_idrecord").toString().encodeAsBase64().replace("=","")}')"   type="button" data-toggle="modal"    data-target="#modal_archivos"><span class="fa fa-eye"></span></button>
                                </span>
                            </td>
                            <td style="text-align: center!important;" >
                                <span class="floatR">

                                    <button class="btn btn-primary" name="${tupla.get("f_idrecord")}" type="button" data-toggle="modal" id="ver_comentarios${tupla.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}" onclick="get_comentario('ver_comentarios${tupla.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}')"  data-target="#modal_ver_comentarios"><span class="fa fa-eye"></span></button>
                                </span>
                            </td>

                        </tr>

                        <%
                            }
                        %>

                        </tbody>
                    </table>
                </div>


            </div>
        </div>

        <div id="modal_ver_comentarios" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">${Grails.i18n("comentario",locale).toUpperCase()}</h4>
                    </div>
                    %{--body--}%
                    <div class="modal-body">

                        %{--<div class="panel-body">--}%
                        <div class="row">

                            <div class="col-md-12">


                                <div class="search-text">
                                    %{--<strong>$lista_tickets.size()}</strong> Tickets encontrados <!--span class="text-primary">Lorem ipsum</span-->--}%
                                </div> <!-- / .search-text -->

                            <!-- Tabs -->
                                <div class="search-tabs">
                                    <ul class="nav nav-tabs">
                                        <li class="active">
                                            <a href="#search-tabs-all" data-toggle="tab" ><span id="titulo_principal" ></span></a>
                                        </li>

                                    </ul> <!-- / .nav -->
                                </div>
                                <!-- / Tabs -->

                                <!-- Panel -->
                                <div class="panel search-panel">


                                    <!-- Search results -->
                                    <div class="panel-body tab-content">


                                        <!-- Classic search -->
                                        %{--<ul class="search-classic tab-pane fade in active" id="search-tabs-all">--}%
                                        <ul class="search-classic tab-pane fade in active" id="lista_cometario">


                                        </ul>

                                    </div>
                                    <!-- / Search results -->

                                    <!-- Panel Footer -->
                                    <div class="panel-footer">

                                    </div> <!-- / .panel-footer -->

                                </div>
                                <!-- / Panel -->


                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <button class="btn btn-danger" type="button" id="" data-dismiss="modal">${Grails.i18n("atras",locale)}</button>
                            </div>

                        </div>

                    </div>
                    %{--body--}%
                </div> <!-- / .modal-content -->
            </div> <!-- / .modal-dialog -->
        </div>

        <div id="modal_archivos" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">ARCHIVOS</h4>
                    </div>
                    %{--body--}%
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="table-primary">
                                    %{--<div class="table-header">--}%
                                    %{--<div class="table-caption">--}%
                                    %{--Primary Table--}%
                                    %{--</div>--}%
                                    %{--</div>--}%
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Extencion</th>
                                            <th>Descargar</th>
                                        </tr>
                                        </thead>
                                        <tbody id="cuerpo_archivos">

                                        </tbody>
                                    </table>
                                    <div class="table-footer">

                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                    %{--body--}%
                </div> <!-- / .modal-content -->
            </div> <!-- / .modal-dialog -->
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




<g:javascript>
    $(document).ready(function () {
        ejecutar();

    });

</g:javascript>
</body>
</html>