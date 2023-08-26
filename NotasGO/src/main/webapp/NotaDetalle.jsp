<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="icon" type="image/x-icon" href="Image/Insignia-SRC.png">
<title>Notas</title>
<link href="css/styles.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
	<jsp:include page="navbar.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="BarraLateral.jsp"></jsp:include>
		<div id="layoutSidenav_content">

			<main>
				<div class="container-fluid px-4">
					<h1 class="mt-4">Registro de detalle de notas</h1>

					<div class="card" id="divRegistro">
						<div class="card-header">
							<i class="fa-sharp fa-light fa-book-open-reader"></i> Formulario
						</div>
						<div class="card-body">
							<br>
							<form class="row g-3 needs-validation" action="CrearNota"
								method="post" novalidate>

								<input type="hidden" id="accion" name="accion">

								<div class="col-md-4 d-none">
									<label for="frmId" class="form-label">ID</label> <input
										type="text" class="form-control" id="frmId" required>
								</div>


								<div class="row mb-3">
									<label for="studentId" class="col-sm-2 col-form-label">Estudiante</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="studentId"
											name="student_id">
									</div>
								</div>

								<div class="row mb-3">
									<label for="" class="col-sm-2 col-form-label">Competencia</label>
									<div class="col-sm-10">
										<select class="form-select" id="competence_id" name="competence_id">
											<option value="">Seleccione una opción</option>
											<option value="1">Construyendo tu identidad</option>
											<option value="2">Construye interpretaciones historicas</option>
											<option value="3">Gestiona proyectos de emprendimiento económico o social</option>
											<option value="4">Se desenvuelve de manera autonoma a través de su motricidad</option>
											<option value="5">Se comunica claramente en su lengua materna</option>
											<option value="6">Aprecia de manera critica
												manifestaciones artisticos culturales</option>
											<option value="7">Se comunica oralmente en
												castellano como segunda lengua</option>
											<option value="8">Se comunica oralmente en inglés
												como lengua extranjera</option>
											<option value="9">Resuelve problemas de cantidad</option>
											<option value="10">Indaga mediante métodos
												científicos para construir sus conocimientos</option>
											<option value="11">Construye su identidad como
												persona humana, amada por Dios, digna, libre y trascendente</option>
											<option value="12">Se desenvuelve en entornos
												virtuales generados por las TIC</option>
										</select>
									</div>
								</div>

								<div class="row mb-3">
									<label for="note" class="col-sm-2 col-form-label">Nota</label>
									<div class="col-sm-10">
										<select class="form-select" id="note" name="note">
											<option value="">Seleccione una opción</option>
											<option value="AD">AD</option>
											<option value="A">A</option>
											<option value="B">B</option>
											<option value="C">C</option>
										</select>
									</div>
								</div>

								<div class="row mb-3">
									<label for="dateNote" class="col-sm-2 col-form-label">Fecha
										del registro</label>
									<div class="col-sm-10">
										<input type="date" class="form-control" id="dateNote"
											name="date_note" value="<%=java.time.LocalDate.now()%>">
									</div>
								</div>

								<button type="submit" class="btn btn-primary" id="btnProcesar">Procesar</button>

							</form>
						</div>
					</div>
					<br>
					<h1>Tabla de detalle de notas</h1>

					<div class="card-body">
						<form method="post" action="#">
							<div class="mb-2 row">
								<div class="col-sm d-none">
									<button type="button" class="btn d-none" id="btnActualizar"
										name="btnActualizar">Actualizar</button>
								</div>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="student_Id"
										name="studentId" placeholder="Ingrese nombre">
								</div>
								<div class="col-sm-2">
									<button type="button" class="btn btn-primary mb-2"
										id="btnBuscar" name="btnBuscar">Buscar</button>
								</div>
							</div>
						</form>
					</div>

					<div class="card mb-4" id="divResultado">
						<div class="card-header">
							<i class="fas fa-table me-1"></i> Registro de notas
						</div>
						<div class="card-body">
							<!--  <button onclick="exportToCSV()">Exportar a CSV</button>
							<button onclick="exportToExcel()">Exportar a Excel</button>
							<button onclick="exportToPDF()">Exportar a PDF</button>-->
							<table class="table caption-top">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">Estudiante</th>
										<th scope="col">Criterio</th>
										<th scope="col">Nota</th>
										<th scope="col">Fecha</th>
									</tr>
								</thead>
								<tbody id="detalleTabla">
								</tbody>
							</table>
						</div>
					</div>

				</div>
			</main>
		</div>
	</div>
</body>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
	crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>

<script>

	//Función para procesar el formulario mediante AJAX
	function procesarFormulario() {
	  // Controles del formulario
	  let frmStudentId = document.getElementById('studentId');
	  let frmCompetence = document.getElementById('competence');
	  let frmNote = document.getElementById('note');
	  let frmDateNote = document.getElementById('dateNote');
	  let frmBtnProcesar = document.getElementById('btnProcesar');
	
	  // Boton Procesar
	  frmBtnProcesar.addEventListener("click", function() {
	    // Validación: Verificar que los campos no estén vacíos
	    if (frmStudentId.value === '' || frmCompetence.value === '' || frmNote.value === '') {
	      document.getElementById('error').innerHTML = 'Por favor, completa todos los campos.';
	      document.getElementById('mensaje').innerHTML = '';
	      return;
	    }
	
	    // Preparando datos
	    let datos = "student_id=" + frmStudentId.value;
	    datos += "&competence=" + frmCompetence.value;
	    datos += "&note=" + frmNote.value;
	    datos += "&date_note=" + frmDateNote.value;
	
	 // Envío con AJAX
	    let xhr = new XMLHttpRequest();
	    xhr.open("POST", "CrearNota", true);
	    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhr.onreadystatechange = function() {
	      if (xhr.readyState === 4) {
	        if (xhr.status === 200) {
	          let obj = JSON.parse(xhr.responseText);
	          if (obj.error) {
	            // Mostrar mensaje de error como alerta
	            alert(obj.error);
	            document.getElementById('mensaje').innerHTML = '';
	          } else {
	            // Mostrar mensaje de éxito como alerta
	            alert(obj.mensaje);
	            document.getElementById('mensaje').innerHTML = obj.mensaje;
	          }
	          divRegistro.style.display = "none";
	          divRespuesta.style.display = "block";
	        } else {
	          console.error("Error en la solicitud: " + xhr.status);
	          // Manejar el error de la solicitud AJAX si es necesario
	        }
	      }
	    };
	    xhr.send(datos);
	  });
</script>

</html>