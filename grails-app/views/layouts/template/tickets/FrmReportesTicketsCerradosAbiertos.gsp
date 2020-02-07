<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="mainTickets">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reporte</title>
</head>

<body>
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
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-ticket page-header-icon"></i>${session.Grails.i18n("reportes",session.locale)}</h1>
    </div>

</div> <!-- / .page-header -->
<div class="panel">

    <div class="panel-body">
        <div class="row">
            <div class="col-md-12 col-sm-12 rango_fechas">
                <form method="post" action="ver_tickets_abiertos_cerrado" >

                    %{--<input type="text" name="name" id="titulo" class="form-control">--}%

                    <div class="row">

                        <div class="col-md-4">
                            <div class="form-group no-margin-hr">
                                <label class="control-label">${session.Grails.i18n("rango",session.locale)}</label>
                                <div class="input-daterange input-group" id="bs-datepicker-range">
                                    <input type="text" class="form-control" id="fecha_desde" name="fecha_desde" placeholder="Start date" value="${params.fecha_desde==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_desde}">
                                    <span class="input-group-addon">to</span>
                                    <input type="text" class=" form-control" id="fecha_hasta" name="fecha_hasta" placeholder="End date" value="${params.fecha_hasta==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_hasta}">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-1">
                            <div class="form-group no-margin-hr">
                                <label class="control-label">${session.Grails.i18n("numero",session.locale)}</label>
                                <input type="text" name="numero_ticket" id="numero_ticket" value="${params.numero_ticket.toString().replace('null','')}" class="form-control">
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group no-margin-hr">
                                <label class="control-label">${session.Grails.i18n("titulo",session.locale)}</label>
                                <input type="text" name="titulo" id="titulo" value="${params.titulo.toString().replace('null','')}" class="form-control">
                            </div>
                        </div>
                        <div class="col-md-1">
                            <div class="form-group has-feedback ">
                                <label>${session.Grails.i18n("abiertos",session.locale)}</label><br/>
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
                                <label>${session.Grails.i18n("cerrados",session.locale)}</label><br/>
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
                                    <input type="submit" id="actualizar" class="btn btn-success" type="button" value="${session.Grails.i18n("buscar",session.locale)}" />

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
                            <th>#</th>
                            <th style="text-align: center">${session.Grails.i18n("titulo",session.locale)}</th>
                            <th style="text-align: center">${session.Grails.i18n("tipo",session.locale)}</th>
                            <th style="text-align: center">${session.Grails.i18n("hecho_por",session.locale)} </th>
                            <th style="text-align: center">${session.Grails.i18n("fecha",session.locale)}</th>
                            <th style="text-align: center">${session.Grails.i18n("fecha_modificacion",session.locale)} </th>
                            <th style="text-align: center">${session.Grails.i18n("fecha_vencimiento",session.locale)} </th>
                            <th style="text-align: center">${session.Grails.i18n("ver",session.locale)}</th>

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
                            <td >

                                <%
                                        if(session.getAttribute("idioma").toString().equals("es")) {

                                %>
                                ${tupla.get("f_tipo_espanol").toString()}
                                <%
                                    }else {

                                %>
                                ${tupla.get("f_tipo_ingles").toString()}
                                <%

                                        }
                                %>



                            </td>

                            <td>${tupla.get("f_hecho_por")}</td>
                            <td>${tupla.get("f_fecha")}</td>
                            <td class="center">${tupla.get("f_fecha_modificacion")}</td>
                            <td class="center">${tupla.get("f_fecha_vencimiento")}</td>
                            <td class="center">

                                <span class="floatR">
                                    <button class="btn btn-primary"  type="button" data-toggle="modal"    data-target="#${tupla.get("f_idrecord").toString().encodeAsMD5()}${tupla.get("f_titulo").toString().replace(" ","").trim().md5()}"><span class="fa fa-eye"></span></button>
                                </span>
                                <div id="${tupla.get("f_idrecord").toString().encodeAsMD5()}${tupla.get("f_titulo").toString().replace(" ","").trim().md5()}" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">×</button>
                                                <h4 class="modal-title">${session.Grails.i18n("comentario",session.locale)}</h4>
                                            </div>
                                            %{--body--}%
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <h2>
                                                            ${raw(tupla.get("f_titulo"))}
                                                        </h2>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <span>

                                                            ${raw(tupla.get("f_descipcion"))}
                                                        </span>
                                                    </div>
                                                </div>


                                            </div>
                                            %{--body--}%
                                        </div> <!-- / .modal-content -->
                                    </div> <!-- / .modal-dialog -->
                                </div>

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


<asset:javascript src="js_proyecto/tickets/reporte_tickets_clientes_abiertos_cerrado.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();

    });

</g:javascript>
</body>
</html>
