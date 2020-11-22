<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="g16.model.*"%> 
<!DOCTYPE html>
<html lang="zxx" class="no-js">

<head>
	<!-- Mobile Specific Meta -->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Favicon-->
	<link rel="shortcut icon" href="img/fav.png">
	<!-- Author Meta -->
	<meta name="author" content="CodePixar">
	<!-- Meta Description -->
	<meta name="description" content="">
	<!-- Meta Keyword -->
	<meta name="keywords" content="">
	<!-- meta character set -->
	<meta charset="UTF-8">
	<!-- Site Title -->
	<title>Chiquify</title>

	<!--
		CSS
		============================================= -->
	<link rel="stylesheet" href="css/linearicons.css">
	<link rel="stylesheet" href="css/owl.carousel.css">
	<link rel="stylesheet" href="css/themify-icons.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/nice-select.css">
	<link rel="stylesheet" href="css/nouislider.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/main.css">
</head>

<body>

	<!-- Start Header Area -->
	<header class="header_area sticky-header">
		<div class="main_menu">
			<nav class="navbar navbar-expand-lg navbar-light main_box">
				<div class="container">
					<!-- Brand and toggle get grouped for better mobile display -->
					<a class="navbar-brand logo_h" href="index.jsp"><img src="img/logo.png" alt=""></a>
					<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
					 aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse offset" id="navbarSupportedContent">
						<ul class="nav navbar-nav menu_nav ml-auto">
							<li class="nav-item"><a class="nav-link" href="registration.jsp">Register user</a></li>
							
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li class="nav-item">
								<button class="search"><span class="lnr lnr-magnifier" id="search"></span></button>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
		<div class="search_input" id="search_input_box">
			<div class="container">
				<form class="d-flex justify-content-between" METHOD=GET ACTION="search">
					<input type="text" class="form-control" id="search_input" name="cadena" placeholder="Search Here">
					<button type="submit" class="btn"></button>
					<span class="lnr lnr-cross" id="close_search" title="Close Search"></span>
				</form>
			</div>
		</div>
	</header>
	<!-- End Header Area -->

	<section class="order_details section_gap"></section>

	<!--================User Box Area =================-->
	<section class="login_box_area section_gap">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="login_box_img">
						<img class="img-fluid" src="img/login.jpg" alt="">
						<div class="hover">
							<h4>Edit a product</h4>
							<p>Edit the fields you want :)</p>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="login_form_inner">
						<h3>Update product information</h3>
						<% ProductManager pm = new ProductManager(); %>
						<% Producto product = pm.getProduct(Integer.parseInt((String)session.getAttribute("idtoEdit"))); %>
						<form class="row login_form" METHOD=POST ACTION="modifyproduct" enctype="multipart/form-data">							
							<div class="col-md-12 form-group">
								<input type="text" class="form-control" id="name" name="titulo" placeholder="Título" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Título'" required value = <% out.print("'" + (String)product.getTitulo() + "'"); %>>
							</div>
							<div class="col-md-12 form-group">
								<select name="categoria" id="cars">
									<option value="abrigo"><% out.print(product.getCategoria()); %></option>
	    							<option value="abrigo">Abrigo</option>
	    							<option value="jersey">Jersey</option>
	    							<option value="sudadera">Sudadera</option>
	    							<option value="pantalon">Pantalón</option>
	    							<option value="zapato">Zapato</option>
	    							<option value="vestido">Vestido</option>
	    							<option value="falda">Falda</option>
	    							<option value="camiseta">Camiseta</option>
	    							<option value="banhador">Bañador</option>
	    							<option value="chandal">Chandal</option>
	    							<option value="bolso">Bolso</option>
	    							<option value="accesorio">Accesorio</option>
	    							<option value="bufanda">Bufanda</option>
	    							<option value="corbata">Corbata</option>
	  							</select>
							</div>
                            <div class="col-md-12 form-group">
								<input type="text" class="form-control" id="name" name="descripcion" placeholder="Descripción" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Descripción'" required value = <% out.print("'" + (String)product.getDescripcion() + "'"); %>>
							</div>
							<div class="col-md-12 form-group">
								<img src= <% out.print("'" + "data:image/jpeg;base64," + product.getImagen() + "'"); %> style="width: 80%" name="foto" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Imagen'">
							</div>
							<div class="col-md-12 form-group">
								<input type="file" class="form-control" id="name" name="imagen" accept="image/jpg" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Imagen'">
							</div>
                            <div class="col-md-12 form-group">
								<input type='number' step='1' value=<%out.print("'" + product.getPrecio() + "'"); %> placeholder='0' name="precio" pattern="^\d*(\.\d{0,2})?$" placeholder="Precio" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Precio'">euros
							</div>
							<div class="col-md-12 form-group">
								<button type="submit" value="submit" class="primary-btn">Modify product</button>
							</div>
						</form>
						<form class="row login_form" METHOD=POST ACTION="deleteproduct">
							<button type="submit" value="submit" class="primary-btn">Delete product</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--================End User Box Area =================-->


	<script src="js/vendor/jquery-2.2.4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	 crossorigin="anonymous"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
	<script src="js/jquery.ajaxchimp.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery.sticky.js"></script>
	<script src="js/nouislider.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<!--gmaps Js-->
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjCGmQ0Uq4exrzdcL6rvxywDDOvfAu6eE"></script>
	<script src="js/gmaps.min.js"></script>
	<script src="js/main.js"></script>
</body>

</html>
