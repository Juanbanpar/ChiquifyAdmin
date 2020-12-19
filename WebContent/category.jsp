<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="g16.model.*" import="java.util.List" import="java.util.ArrayList" %> 
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
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/themify-icons.css">
	<link rel="stylesheet" href="css/nice-select.css">
	<link rel="stylesheet" href="css/nouislider.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/main.css">
</head>

<body id="category">

	<!-- Start Header Area -->
	<header class="header_area sticky-header">
		<div class="main_menu">
			<nav class="navbar navbar-expand-lg navbar-light main_box">
				<div class="container">
					<!-- Brand and toggle get grouped for better mobile display -->
					<a class="navbar-brand logo_h" href="index"><img src="img/logo.png" alt=""></a>
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

	<div class="container">
		<div class="row">
			<div class="col-xl-3 col-lg-4 col-md-5">
				<div class="sidebar-categories">
					<% ProductManager pm = new ProductManager(); %>
		        	<% String cadena = (String) session.getAttribute("cadena"); %>
                    <% List <List<Producto>> productosCategoria = pm.getAllProductsbyCategory(cadena); %>
                    <% int [] categoria = new int [8]; %>
                    <% for(int i = 0; i < 8; i++) { %>
                    	<% if(productosCategoria == null) {
                    		categoria[i] = 0;
                    	} else if (productosCategoria.get(i) == null) {
                    		categoria[i] = 0;
                    	} else {
                    		categoria[i] = productosCategoria.get(i).size(); } %>
                   	<% } %>
					<div class="head">Categorias</div>
						<form METHOD=GET ACTION="complexsearch">
							<div class="input-group dropdown" style="width: 100%">
			                    <span class="input-group-addon" id="basic-addon1"><span
			                        class="glyphicon glyphicon-tasks" aria-hidden="true"></span></span>
			                     <select name="category">
			                        <option value="Abrigo">Abrigo(<% out.print(categoria[0]); %>)</option>
			                        <option value="Pantalon">Pantalon(<% out.print(categoria[1]); %>)</option>
			                        <option value="Zapato">Zapato(<% out.print(categoria[2]); %>)</option>
			                        <option value="Vestido">Vestido(<% out.print(categoria[3]); %>)</option>
			                        <option value="Camiseta">Camiseta(<% out.print(categoria[4]); %>)</option>
			                        <option value="Chandal">Chandal(<% out.print(categoria[5]); %>)</option>
			                        <option value="Bolso">Bolso(<% out.print(categoria[6]); %>)</option>
			                        <option value="Accesorio">Accesorio(<% out.print(categoria[7]); %>)</option>
			                    </select>
			                </div>
							<button type="submit" value="submit" class="primary-btn">Aplicar filtro</button>
						</form>
					</div>
			</div>
			<div class="col-xl-9 col-lg-8 col-md-7">

				<!-- Start Best Seller -->
				<section class="lattest-product-area pb-40 category-list">
					<h3>Results for your search: <% out.print("'" + cadena + "'"); %></h3>
					<div class="row">
                    <% ArrayList<String> categories = new ArrayList<String>(); 
                    categories.add("Abrigo"); categories.add("Pantalon"); categories.add("Zapato"); categories.add("Vestido"); categories.add("Camiseta"); categories.add("Chandal"); categories.add("Bolso"); categories.add("Accesorio");
                    List <Producto> productos;
                    if(session.getAttribute("category")==null) productos= pm.getAllProductsbyString(cadena); 
                    else productos= productosCategoria.get(categories.indexOf(session.getAttribute("category"))); %>
                    <% for(int i = 0; i < productos.size(); i++){ %>
						<!-- single product -->
						<div class="col-lg-4 col-md-6">
							<form METHOD=POST ACTION="showproduct">
							<div class="single-product">
								<input type="hidden" name="Id" value = <% out.print(productos.get(i).getIdproduct()); %> readonly>
								<img src= <% out.print("'" + "data:image/jpeg;base64," + productos.get(i).getImagen() + "'"); %> style="width: 80%" name="foto" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Imagen'">
								<div class="product-details">
									<h6> <% out.print(productos.get(i).getTitulo()); %> </h6>
									<div class="price">
										<h6> <% out.print(productos.get(i).getPrecio()); %> euros</h6>
									</div>
									<div class="prd-bottom">
                                        <div class="col-md-12 form-group">
                                            <button type="submit" value="submit" class="primary-btn">View</button>
                                        </div>
									</div>
								</div>
							</div>
							</form>
						</div>
                    <% } %>
					</div>
				</section>
				<!-- End Best Seller -->
			</div>
		</div>
	</div>


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
