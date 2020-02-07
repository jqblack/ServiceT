<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    %{--TEMPLATE--}%
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300&subset=latin" rel="stylesheet" type="text/css">

    <!-- Pixel Admin's stylesheets -->
    <asset:stylesheet href="assets/stylesheets/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>

    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.base.css"/>
    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.material.css"/>


    <asset:javascript src="assets/javascripts/jquery-3.4.1.min.js" />
    <asset:javascript src="assets/javascripts/bootstrap.min.js"/>
    %{--<asset:stylesheet src="application.css"/>--}%

</head>

<body class="theme-frost main-menu-animated main-navbar-fixed main-menu-fixed page-search">


<script>var init = [];</script>
%{--<!-- Demo script --> <script src="assets/demo/demo.js"></script> <!-- / Demo script -->--}%

<div id="main-wrapper">


    <!-- 2. $MAIN_NAVIGATION ===========================================================================

	Main navigation
-->
    <input type="hidden" id="itebis_preferencia" class="form-control" placeholder="Nombre" value="${session.Tpreferencia.fTax}">
    <div id="main-navbar" class="navbar navbar-inverse" role="navigation">
        <!-- Main menu toggle -->
        <button type="button" id="main-menu-toggle"><i class="navbar-icon fa fa-bars icon"></i><span class="hide-menu-text">HIDE MENU</span></button>

        <div class="navbar-inner">
            <!-- Main navbar header -->
            <div class="navbar-header">

                <!-- Logo -->
                <a href="index.html" class="navbar-brand">
                    <div><asset:image alt="Pixel Admin" src="assets/images/pixel-admin/main-navbar-logo.png"/></div>
                    JQ servicios
                </a>

                <!-- Main navbar toggle -->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar-collapse"><i class="navbar-icon fa fa-bars"></i></button>

            </div> <!-- / .navbar-header -->

            <div id="main-navbar-collapse" class="collapse navbar-collapse main-navbar-collapse">
                <div>


                    <div class="right clearfix">
                        <ul class="nav navbar-nav pull-right right-navbar-nav">

                            <!-- 3. $NAVBAR_ICON_BUTTONS =======================================================================

							Navbar Icon Buttons

							NOTE: .nav-icon-btn triggers a dropdown menu on desktop screens only. On small screens .nav-icon-btn acts like a hyperlink.

							Classes:
							* 'nav-icon-btn-info'
							* 'nav-icon-btn-success'
							* 'nav-icon-btn-warning'
							* 'nav-icon-btn-danger'
-->

                            <li class="nav-icon-btn nav-icon-btn-danger dropdown">
                                <a href="#notifications" class="dropdown-toggle" data-toggle="dropdown">
                                    <span class="label">${session.lista_tickets.size()}</span>
                                    <i class="nav-icon fa fa-bullhorn"></i>
                                    <span class="small-screen-text">Notifications</span>
                                </a>

                                <!-- NOTIFICATIONS -->

                                <!-- Javascript -->
                                <script>
                                    init.push(function () {
                                        $('#main-navbar-notifications').slimScroll({ height: 250 });
                                    });
                                </script>
                                <!-- / Javascript -->

                                <div class="dropdown-menu widget-notifications no-padding" style="width: 300px">
                                    <div class="notifications-list" id="main-navbar-notifications">



                                        <%

                                            for (Map<String,Object> objectMap : session.lista_tickets)
                                            {

                                                String color = objectMap.get("f_color").toString().split('-')[1];


                                        %>
                                        <div class="notification">
                                            <a style="height: 26px; margin-top: -14px;" class="notification-title text-${color}" href="./asignar_tickets?idt=${objectMap.get("f_idrecord")}">${objectMap.get("f_cliente")}  </a>
                                            <div class="notification-description"><strong>${objectMap.get("f_tipo_tk").toString().equals("1")?'Error-':'Peticion-'}${objectMap.get("f_prioridad").toString()}</strong>: ${objectMap.get("f_titulo")}.</div>
                                            <div class="notification-ago">${objectMap.get("f_fecha")}</div>
                                            <div class="notification-icon fa fa-ticket bg-${color}"></div>
                                        </div> <!-- / .notification -->

                                    <%
                                        }

                                    %>

                                    </div> <!-- / .notifications-list -->
                                %{--<a href="#" class="notifications-link">MORE NOTIFICATIONS</a>--}%
                                </div> <!-- / .dropdown-menu -->
                            </li>

                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle user-menu" data-toggle="dropdown">
                                    <asset:image src="avatar.png" alt=""/>
                                    <span>${session.Usuario.fNombre}</span>
                                </a>
                                <ul class="dropdown-menu">

                                    <li class="divider"></li>
                                    <li><a href="./logout"><i class="dropdown-icon fa fa-power-off"></i>&nbsp;&nbsp;Log Out</a></li>
                                </ul>
                            </li>
                        </ul> <!-- / .navbar-nav -->
                    </div> <!-- / .right -->
                </div>
            </div> <!-- / #main-navbar-collapse -->
        </div> <!-- / .navbar-inner -->
    </div> <!-- / #main-navbar -->

    <div id="main-menu" role="navigation">
        <div id="main-menu-inner">
            <div class="menu-content top" id="menu-content-demo">
                <!-- Menu custom content demo
					 CSS:        styles/pixel-admin-less/demo.less or styles/pixel-admin-scss/_demo.scss
					 Javascript: html/assets/demo/demo.js
				 -->

                <div>
                    <div class="text-bg"><span class="text-slim">Bienvenido</span><br/><span class="text-semibold">${session.Usuario.fNombre}</span></div>

                    %{--<img src="assets/demo/avatars/1.jpg" alt="" class="">--}%
                <asset:image src="avatar.png"/>
                    %{--<img src="${resource(dir: "/images", file: "avatar.png")}" class="message-avatar" alt="">--}%
                    <div class="btn-group">

                        <a href="#" class="btn btn-xs btn-primary btn-outline dark"><i class="fa fa-cog"></i></a>
                        <a href="logout" class="btn btn-xs btn-danger btn-outline dark"><i class="fa fa-power-off"></i></a>
                    </div>

                </div>

            </div>
            <ul class="navigation">
                <li>
                    <a href="index"><i class="menu-icon fa fa-dashboard"></i><span class="mm-text">Principal</span></a>
                </li>
                %{--<li class="mm-dropdown">--}%

                <%
                     List<Map<String,Object>> lista_permisos = session.permisos as List;
                    if (lista_permisos.size() > 0) {
                        for (Map<String, java.lang.Object> tupla : lista_permisos) {
                %>
                <li class="mm-dropdown">

                    <a href="#"><i class="menu-icon fa ${tupla.get("f_icono")}"></i><span class="mm-text">${tupla.get("f_descripcion")}</span></a>
                    <ul>
                        <%
                                if(!tupla.get("f_hijos").toString().equals('[]'))
                                {

                                    def json= new grails.converters.JSON().parse(tupla.get("f_hijos").toString());
                                    for (Map<String,Object> hijos : json.collect())
                                    {
                        %>

                        <li>
                            <a tabindex="-1"  href="${hijos.get("f_url")}"><i class="menu-icon fa ${hijos.get("f_icono")}"></i><span class="mm-text">${hijos.get("f_descripcion")}</span></a>
                        </li>

                        <%
                                    }

                                }

                        %>
                    </ul>
                </li>

                <%
                        }
                    }
                %>


                %{--<a href="#"><i class="menu-icon fa fa-edit"></i><span class="mm-text">Registros</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/clientes"><i class="menu-icon fa fa-user"></i><span class="mm-text">Clientes</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/productos"><i class="menu-icon fa fa-shopping-cart"></i><span class="mm-text">Productos</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/referentes"><i class="menu-icon fa fa-users"></i><span class="mm-text">Referentes</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/usuarios"><i class="menu-icon fa fa-users"></i><span class="mm-text">Usuarios</span></a>--}%

                %{--</li>--}%


                %{--</ul>--}%
                %{--</li>--}%
                %{--<li class="mm-dropdown">--}%
                %{--<a href="#"><i class="menu-icon fa  fa-cogs"></i><span class="mm-text">Procesos</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/recibos"><i class="menu-icon fa fa-money"></i><span class="mm-text">Recibos</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/pagos_generales"><i class="menu-icon fa fa-money"></i><span class="mm-text">Pagos Generales</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/nota_credito"><i class="menu-icon fa fa-money"></i><span class="mm-text">Nota de Credito</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/nota_debito"><i class="menu-icon fa fa-money"></i><span class="mm-text">Nota de Debito</span></a>--}%

                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/servicios_contratados"><i class="menu-icon fa fa-rss-square"></i><span class="mm-text">Servicios Contratados</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/mensajes"><i class="menu-icon fa fa-inbox"></i><span class="mm-text">Mensajes</span></a>--}%
                %{--</li>--}%
                %{--</ul>--}%
                %{--</li>--}%
                %{--<li class="mm-dropdown">--}%
                %{--<a href="#"><i class="menu-icon fa fa-comments-o"></i><span class="mm-text">Conocimiento</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/conocimiento"><i class="menu-icon fa fa-book"></i><span class="mm-text">Conocimiento</span></a>--}%

                %{--</li>--}%

                %{--</ul>--}%
                %{--</li>--}%

                %{--<li class="mm-dropdown">--}%
                %{--<a href="#"><i class="menu-icon fa fa-code"></i><span class="mm-text">Proyectos</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/empleados"><i class="menu-icon fa fa-users"></i><span class="mm-text">Empleados</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/proyectos"><i class="menu-icon fa  fa-code"></i><span class="mm-text">Proyectos</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/tareas"><i class="menu-icon fa fa-book"></i><span class="mm-text">Tareas y Track</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="${getRequest().getContextPath()}/ver_track"><i class="menu-icon fa fa-search"></i><span class="mm-text">Ver Tracks</span></a>--}%
                %{--</li>--}%
                %{--</ul>--}%
                %{--</li>--}%

                %{--<li class="mm-dropdown">--}%
                %{--<a href="#"><i class="menu-icon fa fa-ticket"></i><span class="mm-text">Ticket</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="layouts-grid.html"><span class="mm-text">Grid</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="layouts-main-menu.html"><i class="menu-icon fa fa-th-list"></i><span class="mm-text">Main menu</span><span class="label label-warning">Updated</span></a>--}%
                %{--</li>--}%
                %{--</ul>--}%
                %{--</li>--}%

                %{--<li class="mm-dropdown">--}%
                %{--<a href="#"><i class="menu-icon fa    fa-list-ul"></i><span class="mm-text">Entregas</span></a>--}%
                %{--<ul>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="layouts-grid.html"><span class="mm-text">Grid</span></a>--}%
                %{--</li>--}%
                %{--<li>--}%
                %{--<a tabindex="-1" href="layouts-main-menu.html"><i class="menu-icon fa fa-th-list"></i><span class="mm-text">Main menu</span><span class="label label-warning">Updated</span></a>--}%
                %{--</li>--}%
                %{--</ul>--}%
                %{--</li>--}%





            </ul> <!-- / .navigation -->
            %{--<div class="menu-content">--}%
                %{--<a href="pages-invoice.html" class="btn btn-primary btn-block btn-outline dark">Create Invoice</a>--}%
            %{--</div>--}%
        </div> <!-- / #main-menu-inner -->
    </div> <!-- / #main-menu -->
<!-- /4. $MAIN_MENU -->

    <div id="content-wrapper">
        <!-- 5. $CONTENT ===================================================================================

		Content
-->

        <!-- Content here -->
<g:layoutBody/>
    </div> <!-- / #content-wrapper -->
    <div id="main-menu-bg"></div>
</div> <!-- / #main-wrapper -->

<!-- Get jQuery from Google CDN -->
<!--[if !IE]> -->
<!--<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js">'+"<"+"/script>"); </script>-->
%{--<!-- <![endif]-->--}%
<!--[if lte IE 9]-->
	<!--<script type="text/javascript"> window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">'+"<"+"/script>"); </script>-->
<!--[endif]-->


<!-- Pixel Admin's javascripts -->

<asset:javascript src="assets/javascripts/pixel-admin.min.js"/>

<asset:javascript src="jqwidgets/jqwidgets/jqxcore.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdata.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxbuttons.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxscrollbar.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxmenu.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxcheckbox.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxlistbox.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdropdownlist.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.sort.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.grouping.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsresize.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsreorder.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.pager.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.selection.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.edit.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.filter.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdatatable.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxtreegrid.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdatetimeinput.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxcalendar.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxloader.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxeditor.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdropdownbutton.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxfileupload.js"/>
<asset:javascript src="jqwigets/jqwidgets/jqxdatetimeinput.js"/>


<g:javascript>
    init.push(function () {
        // Javascript code here
    });
    window.PixelAdmin.start(init);
</g:javascript>

</body>
</html>
