<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>products</title>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/assets/admin/plugins/fontawesome-free/css/all.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/assets/admin/css/adminlte.min.css">
    <!-- summernote -->
    <link rel="stylesheet" href="/assets/admin/plugins/summernote/summernote-bs4.css">
    <!-- Google Font: Source Sans Pro -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="/assets/admin/css/input.css">
    <!-- jQuery -->
    <script src="/assets/admin/plugins/jquery/jquery.min.js"></script>
</head>


<body class="hold-transition sidebar-mini layout-fixed">



<div class="wrapper">

    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
            <li class="nav-item d-none d-sm-inline-block">
                <a href="dashboard" class="nav-link">Home</a>
            </li>
        </ul>

    </nav>
    <!-- /.navbar -->

    <%@include  file="sidebar.jsp" %>

    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0 text-dark">Product</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="dashboard">Home</a></li>
                            <li class="breadcrumb-item active">Product</li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div>
        </div>

    <div class="row d-flex justify-content-center py-5 pl-5">
        <div class="col-6">
            <div class="card card-success">
                <div class="card-header">
                    <h3 class="card-title">Carica Prodotto</h3>
                </div>
                <!-- /.card-header -->
                <!-- form start -->
                <form action="/admin/create/product" method="post">

                    <div class="card-body">
                        <div class="form-group">
                            <label>Nome</label>
                            <input class="form-control" name="name" placeholder="Nome Prodotto">
                        </div>
                        <div class="form-group">
                            <label>Prezzo</label>
                            <input class="form-control" name="price" placeholder="Prezzo">
                        </div>
                        <div class="form-group">
                            <label>Giacenza</label>
                            <input class="form-control" name="stock" placeholder="Giacenza">
                        </div>
                        <div class="form-group">
                            <label>Categoria</label>
                            <div>
                                <div class="overflow-auto" style = "height:120px;" >
                                    <c:forEach var="category" items="${categories}">
                                        <input type="checkbox" id = "category" name="category" value="${category.id}">
                                        <label for="category">${category.name}</label><br>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Tag</label>
                            <div>
                                <div class="overflow-auto" style = "height:120px;" >
                                    <c:forEach var="tag" items="${tags}">
                                        <input type="checkbox" id = "tag" name="tag" value="${tag.id}">
                                        <label for="tag">${tag.hashtag}</label><br>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Descrizione Breve</label>
                            <input class="form-control" name="info" placeholder="Descrizione breve">
                        </div>
                        <div class="form-group">
                            <label>Sconto</label>
                            <input class="form-control" name="discount" placeholder="Sconto">
                        </div>

                        <div class="form-group">
                            <label>Stato</label>
                            <div>
                                <select class="form-control" name="status">
                                    <option value="AVAILABLE">DISPONIBILE</option>
                                    <option value="NOT_AVAILABLE">NON DISPONIBILE</option>
                                    <option value="AVAILABLE_SOON">DISPONIBILE A BREVE</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputFile">File</label>
                            <div class="input-group">
                                <div class="custom-file">
                                    <input name = "files" type="file" multiple class="custom-file-input"  accept=".jpg, .jpeg, .png" id="exampleInputFile">
                                    <label class="custom-file-label" for="exampleInputFile">Scegli File</label>
                                </div>
                                <div class="input-group-append">
                                    <span class="input-group-text" id="">Carica</span>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <textarea id="compose-textarea" name="description" class="form-control"></textarea>
                        </div>
                    </div>
                    <!-- /.card-body -->
                    <div class="card-footer">
                        <button type="submit" class="btn btn-primary">Carica</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
    <!-- /.card -->

    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<footer class="main-footer">
    <strong>Copyright &copy; <a href="http://adminlte.io">Bioagri Shop</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
    </div>
</footer>

</div>

<!-- Bootstrap 4 -->
<script src="/assets/admin/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="/assets/admin/js/adminlte.min.js"></script>
<!-- Summernote -->
<script src="/assets/admin/plugins/summernote/summernote-bs4.min.js"></script>
<!-- Page Script -->
<script>
    $(function() {
        //Add text editor
        $('#compose-textarea').summernote()
    })
</script>

</body>

</html>