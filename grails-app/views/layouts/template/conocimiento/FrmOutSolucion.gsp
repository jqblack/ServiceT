<!DOCTYPE html>
<!--[if IE 8]>         <html class="ie8"> <![endif]-->
<!--[if IE 9]>         <html class="ie9 gt-ie8"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="gt-ie8 gt-ie9 not-ie"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Solución</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

    <!-- Open Sans font from Google CDN -->
    <asset:link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300&subset=latin" rel="stylesheet" type="text/css"/>

    <asset:stylesheet href="assets/stylesheets/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>

    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.base.css"/>
    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.office.css"/>


    <asset:javascript src="assets/javascripts/jquery-3.4.1.min.js" />
    <asset:javascript src="assets/javascripts/bootstrap.min.js"/>

</head>

<body class="theme-default main-menu-animated" style="background-color: white">

<script>var init = [];</script>

<div id="main-wrapper" >
    <script>
        window.onload = function () {
            // window.print();
        };
    </script>

    <div class="invoice">

        <textarea style="display: none;" class="form-control" rows="5"
                  id="text_area_solucion">${print(solucion)}</textarea>

        <div class="invoice-header">
            <h3>
                <div>
                    <small><strong>Solución</strong>&nbsp;&nbsp;A :</small><br>
                    ${titulo.toString().toUpperCase()}
                </div>
            </h3>
            <address>
                <% if (documento.toString().equals('true')) { %>
                <button id="tag_documento" name="${print(idrecord)}" class="label label-primary label-tag">Ver Documento</button>
                %{--<form action="${request.getRequestURL().toString().replace("/grails","")-".dispatch"}/descargar_documento" method="post">--}%
                %{--<button id="tag_documento_descargar" name="${print(idrecord)}" class="label label-primary label-tag">Descargar Documento</button>--}%
                %{--</form>--}%

                <br/>
                <%
                    } else { %> <br/> <% } %>
                <h2><strong>SOLUCIÓN</strong></h2>

            </address>

            %{--<div class="invoice-date">--}%
            %{--<small><strong>Fecha</strong></small><br>--}%
            %{--${new java.util.Date().format("EEEE d MMMM , yyyy")}--}%
            %{--</div>--}%
        </div> <!-- / .invoice-header -->
    %{--<div>--}%

        <div class="row" style="padding-left: 10px;padding-right: 10px">
            <div class="col-md-12 col-sm-12 col-lg-12">
                <span id="texto" ></span>
                <div class="invoice-table">

                </div>
            </div>
        </div>

        <hr>

        <!-- / .invoice-table -->
    </div> <!-- / .invoice -->
<g:javascript>

    $(document).ready(function () {
        // alert($("#text_area_solucion").val())
        $("#texto").html($("#text_area_solucion").val());
        $("#tag_documento").click(function () {


            $.ajax({
                type: "POST",
                url: window.location + '/get_documento_by_id',
                data: {idrecord: $('#tag_documento').attr('name')},
                success: function (data) {
                    var tupla = eval(data);
                    var pdfAsDataUri = '';
                    if (String(tupla[0].f_tipo_doc) == 'pdf') {

                        pdfAsDataUri = "data:application/pdf;base64," + tupla[0].f_documento;
                        window.open(pdfAsDataUri);
                    }
                    if (String(tupla[0].f_tipo_doc) == 'jpg') {

                        window.open("data:image/" + tupla[0].f_tipo_doc + ";base64," + tupla[0].f_documento);
                    }
                    if (String(tupla[0].f_tipo_doc) == 'png') {
                        window.open("data:image/" + tupla[0].f_tipo_doc + ";base64," + tupla[0].f_documento);
                    }

                }

            });
        });

    })
</g:javascript>


<asset:javascript src="assets/javascripts/pixel-admin.min.js"/>
%{--<g:javascript src="bootstrap/assets/javascripts/pixel-admin.min.js"/>--}%
    <script type="text/javascript">
        init.push(function () {
            // Javascript code here
        });
        window.PixelAdmin.start(init);
    </script>

</body>
</html>