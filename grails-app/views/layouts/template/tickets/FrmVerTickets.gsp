<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="mainTickets">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ver Tickets</title>
</head>

<body>

%{--<link rel="stylesheet" href="${resource(dir: 'js/bootstrap/assets/stylesheets/', file: 'pages.min.css')}" type="text/css">--}%
<asset:image id="url_imagen" value="avatar.png" src="avatar.png" style="display: none"/>
%{--<input id="url_imagen" value="${getRequest().getContextPath()}/images/avatar.png" style="display: none"/>--}%

<div class="panel">

    <div class="panel-body">
        <div class="row">

            <div class="col-md-12">


                <div class="page-header">
                    <h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Search results</h1>
                </div> <!-- / .page-header -->


                <div class="row">
                    <div class="col-md-12 col-sm-12 rango_fechas">
                        <form method="post" action="ver_tickets" >

                            <div class="row">

                                <div class="col-md-4">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">${session.Grails.i18n("rango", session.locale)}</label>
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
                                        <label class="control-label">${session.Grails.i18n("titulo",session.locale)}</label>
                                        <input type="text" name="titulo" id="titulo" value="${params.titulo.toString().replace('null','')}" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">&nbsp;&nbsp;&nbsp;</label><br/>
                                        <span class="floatR">
                                            <input type="submit" class="btn btn-success" type="button" value="${session.Grails.i18n("buscar",session.locale)}" />

                                        </span>
                                    </div>

                                </div>
                            </div>

                        </form>
                    </div>
                </div>
                <div class="search-text">
                    %{--<strong>21</strong> results found for: <span class="text-primary">Lorem ipsum</span>--}%
                </div> <!-- / .search-text -->

            <!-- Tabs -->
                <div class="search-tabs">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#search-tabs-all" data-toggle="tab">${session.Grails.i18n("tickets_encontrados",session.locale)}<span class="label label-primary">${lista_tickets.size()}</span></a>
                        </li>

                    </ul> <!-- / .nav -->
                </div>
                <!-- / Tabs -->

                <!-- Panel -->
                <div class="panel search-panel">


                    <!-- Search results -->
                    <div class="panel-body tab-content">







                        <!-- Classic search -->
                        <ul class="search-classic tab-pane fade in active" id="search-tabs-all">


                            <%
                                for (Map<String,java.lang.Object> map : lista_tickets){

                            %>
                            <li>

                                <div class="table-info">
                                    %{--<div class="table-header">--}%
                                    %{--<div class="table-caption">--}%
                                    %{--Primary Table--}%
                                    %{--</div>--}%
                                    %{--</div>--}%
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th >#</th>
                                            <th style="text-align: center">${session.Grails.i18n("proyecto",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("fecha",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("fecha_vencimiento",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("prioridad",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("fecha_modificacion",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("tipo",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("estatus",session.locale)}</th>
                                            <th style="text-align: center">${session.Grails.i18n("ver",session.locale)}</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td >${map.get("f_idrecord").toString().padLeft(6,"0")}</td>
                                            <td style="text-align: center">${map.get("f_nombre").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha_vencimiento").toString()}</td>
                                            <td style="text-align: center!important;" > <div class="label ${map.get("f_color").toString()} ticket-label">${map.get("f_tipo_tk").toString().equals("1")?'Error-':'Peticion-'}${map.get("f_prioridad").toString()}</div> </td>
                                            <td style="text-align: center">${map.get("f_fecha_modificacion").toString()}</td>
                                            <td style="text-align: center">

                                                <%
                                                        if(session.getAttribute("idioma").toString().equals("es")) {

                                                %>
                                                ${map.get("f_tipo_espanol").toString()}
                                                <%
                                                    }else {

                                                %>
                                                ${map.get("f_tipo_ingles").toString()}
                                                <%

                                                        }
                                                %>



                                            </td>
                                            <%


                                                    String texto =map.get("f_descripcion_status").toString();
                                                    String color = map.get("f_color_status").toString();
                                                    String icono = map.get("f_icono_status").toString();

//

                                            %>

                                            <td style="text-align: center"><div class="label ${color} ticket-label"><span class="fa ${icono}"></span> ${texto}</div></td>
                                            <td style="text-align: center!important;" >
                                                <span class="floatR">

                                                    <button class="btn btn-primary" name="${map.get("f_idrecord").toString()}" type="button" data-toggle="modal" id="ver_comentarios${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}" onclick="get_comentario('ver_comentarios${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}')"  data-target="#modal_ver_comentarios"><span class="fa fa-eye"></span></button>
                                                </span>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <a class="search-title">${map.get("f_titulo").toString()}</a>
                                %{--<a href="#" class="search-url">http://example.com/some/page</a>--}%
                                <div class="search-content">
                                    ${raw(map.get("f_descipcion").toString())}
                                </div> <!-- / .search-content -->

                            </li>

                            <%
                                }
                            %>
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

    </div>

</div>

<div id="modal_ver_comentarios" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h4 class="modal-title">${session.Grails.i18n("comentario",session.locale).toUpperCase()}</h4>
            </div>
            %{--body--}%
            <div class="modal-body">

                %{--<div class="panel">--}%

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
                        <button class="btn btn-danger" type="button" id="" data-dismiss="modal">${session.Grails.i18n("atras",session.locale)}</button>
                    </div>

                </div>



            </div>
            %{--body--}%
        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>


<script type="text/javascript">
    init.push(function () {
        $('.add-tooltip').tooltip();
    });
    //window.PixelAdmin.start(init);
</script>

<asset:javascript src="js_proyecto/tickets/ver_tickets_clientes.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })
</g:javascript>
</body>
</html>