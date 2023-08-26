<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="icon" type="image/x-icon" href="Image/Insignia-SRC.png">
<title>Usuarios</title>
<link href="css/styles.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>


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
					<h1 class="mt-4">Usuarios Activos</h1>
					<div class="card-body">
						<form method="post" action="#">
							<div class="mb-2 row">
								<div class="col-sm d-none">
									<button type="button" class="btn d-none" id="btnActualizar"
										name="btnActualizar">Actualizar</button>
								</div>

								<div class="col-sm-3">
									<input type="text" class="form-control" id="names" name="names"
										placeholder="Ingrese nombre">
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="last_name"
										name="last_name" placeholder="Ingrese apellido">
								</div>
								
								<div class="col-sm-2">
									<input type="text" class="form-control" id="number_document"
										name="number_document" placeholder="Ingrese numero documento">
								</div>
										
								<div class="col-sm-1 d-flex">
									<button type="button" class="btn btn-primary mb-2"
										id="btnBuscar" name="btnBuscar">Buscar</button>
								</div>
								<div class="col-sm-1 d-flex">
									<button type="button" class="btn btn-success float-end mb-2"
										id="btnNuevo" name="btnNuevo">Nuevo</button>
								</div>
							</div>
							</div>
						</form>
					</div>
					<div class="card mb-4" id="divResultado">
						<div class="card-header">
							<i class="fa-sharp fa-light fa-book-open-reader"></i> Registros
						</div>
						<div class="card-body">
							<button onclick="exportToCSV()">Exportar a CSV</button>
							<button onclick="exportToExcel()">Exportar a Excel</button>
							
							
							
							
							<!-- EXPORTACI”N PDF -->

	<script>      
      function exportToPDF() {
          const excludedColumns = [10]; // Õndice de la columna "Acciones"
          // Copia de la tabla para eliminar las columnas no deseadas
          const table = document.querySelector("table").cloneNode(true);
          table.querySelectorAll("th, td").forEach((cell) => {
            const columnIndex = cell.cellIndex;
            if (excludedColumns.includes(columnIndex)) {
              cell.remove();
            } 
          });
          const element = document.createElement("div");
          // Agregar tÌtulo a la hoja
          const title = document.createElement("h1");
          title.classList.add("pdf-title");
          title.textContent = "Tabla de Personas";
          element.appendChild(title);
          // Agregar lÌneas divisorias a las columnas
          const tableWithLines = document.createElement("table");
          tableWithLines.classList.add("pdf-table");
          tableWithLines.style.width = "85%"; // Establecer el ancho de la tabla al 50% del contenedor
          tableWithLines.style.fontSize = "12px"; // Reducir el tamaÒo de fuente de la tabla
          const rows = table.rows;
          const columns = rows[0].cells.length;
          for (let i = 0; i < rows.length; i++) {
            const row = tableWithLines.insertRow();
            for (let j = 0; j < columns; j++) {
              const cell = row.insertCell();
              if (i === 0) {
                cell.classList.add("pdf-table-header");
              }
              cell.innerHTML = rows[i].cells[j].innerHTML;
            }
          }
          element.appendChild(tableWithLines);
          const opt = {
            margin: [0.5, 0, 1, 0], // M·rgenes superior, derecho, inferior, izquierdo
            filename: "tabla_persona.pdf",
            image: { type: "jpeg", quality: 0.98 },
            html2canvas: { scale: 2 },
            jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
          };
          html2pdf().set(opt).from(element).save();
        }
        document.getElementById("btnExportarPDF").addEventListener("click", exportToPDF);

        </script>
							
							
							<button onclick="exportToPDF()">Exportar a PDF</button>

							<table class="table caption-top">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">Nombre</th>
										<th scope="col">Apellido</th>
										<th scope="col">Tipo Documento</th>
										<th scope="col">N∫ Documento</th>
										<th scope="col">Tipo Usuario</th>
										<th scope="col">Correo ElectrÛnico</th>
										<th scope="col">N∫ Celular</th>
										<th scope="col">AcciÛn</th>
									</tr>
								</thead>
								<tbody id="detalleTabla">
								</tbody>
							</table>
						</div>
					</div>

					<!-- Formulario de edicion de registro -->
					<div class="card" id="divRegistro" style="display: none;">
						<div class="card-header">
							<i class="fa-solid fa-list"></i> Formulario
						</div>
						<div class="card-body">
							<form class="row g-3 needs-validation" novalidate>

								<input type="hidden" id="accion" name="accion">

								<div class="col-md-4 d-none">
									<label for="frmId" class="form-label">ID</label> <input
										type="text" class="form-control" id="frmId" required>
								</div>


								<div class="col-md-4">
									<label for="frmNames" class="form-label">Nombre</label> <input
										type="text" class="form-control" id="frmNames" value=""
										required>
									<div class="valid-feedback">°El nombre es correcto!</div>
									<div class="invalid-feedback">Por favor, coloque un
										nombre v·lido.</div>
								</div>



								<div class="col-md-4">
									<label for="frmLast_name" class="form-label">Apellido</label> <input
										type="text" class="form-control" id="frmLast_name" required>
									<div class="valid-feedback">°El apellido es correcto!</div>
									<div class="invalid-feedback">Por favor, coloque un
										apellido v·lido.</div>
								</div>


								<div class="col-md-3">
									<label for="frmType_document" class="form-label">Tipo
										Documento</label> <select class="form-select" id="frmType_document"
										required>
										<option selected disabled value="">Elige el tipo de
											documento...</option>
										<option value="DNI">DNI</option>
										<option value="CNE">CNE</option>
									</select>
									<div class="invalid-feedback">Seleccione un tipo de
										documento.</div>
								</div>


								<div class="col-md-3">
									<label for="frmNumber_document" class="form-label">N∫
										Documento</label> <input type="text" class="form-control"
										id="frmNumber_document" maxlength="9" required>
									<div class="valid-feedback">°El documento es correcto!</div>
									<div class="invalid-feedback">Por favor, coloque un
										n˙mero de documento v·lido.</div>
								</div>




								<div class="col-md-3">
									<label for="frmType_user" class="form-label">Tipo
										Usuario</label> <select class="form-select" id="frmType_user" required>
										<option selected disabled value="">Elige el tipo de
											Usuario...</option>
										<option value="D" data-fullname="Director">D</option>
										<option value="P" data-fullname="Profesor">P</option>
										<option value="T" data-fullname="Tutor">T</option>
									</select>
									<div class="valid-feedback">Recuerda que (D=Directo,
										P=Profesor, T=Tutor)</div>
									<div class="invalid-feedback">Por favor, seleccione una
										opciÛn v·lida.</div>
								</div>


								<div class="col-md-4">
									<label for="frmEmail" class="form-label">Correo
										Electr√≥nico</label>
									<div class="input-group has-validation">
										<input type="email" class="form-control" id="frmEmail"
											aria-describedby="inputGroupPrepend" required>
										<div class="valid-feedback">°Se ve bien!</div>
										<div class="invalid-feedback">Por favor, ingrese un
											correo electrÛnico v·lido.</div>
									</div>
								</div>


								<div class="col-md-4">
									<label for="frmCellphone" class="form-label">N∫ Celular</label>
									<input type="text" class="form-control" maxlength="9"
										id="frmCellphone" required>
									<div class="valid-feedback">°N˙mero Correcto!</div>
									<div class="invalid-feedback">Por favor, ingrese un
										n˙mero de celular v·lido que empiece por 9.</div>
								</div>



								<div class="col-12">
									<button class="btn btn-primary" id="btnProcesar" type="submit">Enviar
										formulario</button>

								</div>


							</form>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script>
	
	// Constantes del CRUD
	const ACCION_NUEVO = "NUEVO";
	const ACCION_EDITAR = "EDITAR";
	const ACCION_ELIMINAR = "ELIMINAR";

	// Arreglo de registros
	let arreglo = [];
	
	
	// Acceder a los controles
	let btnBuscar = document.getElementById("btnBuscar");
	let btnNuevo = document.getElementById("btnNuevo");
	let btnProcesar = document.getElementById("btnProcesar");
	let btnActualizar = document.getElementById("btnActualizar");
	
	// Campos del formulario
	let accion = document.getElementById('accion');
	let frmId = document.getElementById('frmId');
	let frmNames = document.getElementById('frmNames');
	let frmLast_name = document.getElementById('frmLast_name');
	let frmType_document = document.getElementById('frmType_document');
	let frmNumber_document = document.getElementById('frmNumber_document');
	let frmType_user = document.getElementById('frmType_user');
	let frmEmail = document.getElementById('frmEmail');
	let frmCellphone = document.getElementById('frmCellphone');

	// Programar los controles
	btnBuscar.addEventListener("click", fnBtnBuscar);
	btnNuevo.addEventListener("click", fnBtnNuevo);
	btnProcesar.addEventListener("click", fnBtnProcesar);
	btnActualizar.addEventListener("click", fnBtnActualizar);

	// Funcion fnEditar
	function fnEditar(id) {
		document.getElementById("accion").value = ACCION_EDITAR;
		fnCargarForm(id);
		fnEstadoFormulario(ACCION_EDITAR);
		document.getElementById("divResultado").style.display = "none";
		document.getElementById("divRegistro").style.display = "block";
	}

	// Funcion fnEliminar
	function fnEliminar(id) {
		Swal.fire({
		  title: 'Are you sure?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.isConfirmed) {
		    Swal.fire(
		      'Deleted!',
		      'Your file has been deleted.',
		      'success'
		    )
			document.getElementById("accion").value = ACCION_ELIMINAR;
			fnCargarForm(id);
			fnBtnProcesar();
			setTimeout(fnBtnActualizar, 1000);
		  }
		})
	}

	// Funcion fnBtnProcesar
	function fnBtnProcesar() {
		if(!fnValidar()){
			return;
		}
		let datos = "accion=" + document.getElementById("accion").value;
		datos += "&id=" + document.getElementById("frmId").value;
		datos += "&names=" + document.getElementById("frmNames").value;
		datos += "&last_name=" + document.getElementById("frmLast_name").value;
		datos += "&type_document=" + document.getElementById("frmType_document").value;
		datos += "&number_document=" + document.getElementById("frmNumber_document").value;
		datos += "&type_user=" + document.getElementById("frmType_user").value;
		datos += "&email=" + document.getElementById("frmEmail").value;
		datos += "&cellphone=" + document.getElementById("frmCellphone").value;
		let xhr = new XMLHttpRequest();
		xhr.open("POST", "UsersProcesar", true);
		xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				console.log(xhr.responseText);
			}
		};
		xhr.send(datos);
	}

	function fnBtnNuevo() {
		document.getElementById("accion").value = ACCION_NUEVO;
		fnEstadoFormulario(ACCION_NUEVO);
		document.getElementById("divResultado").style.display = "none";
		document.getElementById("divRegistro").style.display = "block";
	}
	
	

	function fnBtnBuscar() {
		let names = document.getElementById("names").value;
		let last_name = document.getElementById("last_name").value;
		let number_document = document.getElementById("number_document").value;
		let url = "UsersBuscar?names=" + names + "&last_name=" + last_name + "&number_document=" + number_document;
		let xhttp = new XMLHttpRequest();
		xhttp.open("GET", url, true);
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				let respuesta = xhttp.responseText;
				arreglo = JSON.parse(respuesta);
				let detalleTabla = "";
				arreglo.forEach(function(item) {
							detalleTabla += "<tr>";
							detalleTabla += "<td>" + item.id + "</td>";
							detalleTabla += "<td>" + item.names + "</td>";
							detalleTabla += "<td>" + item.last_name + "</td>";
							detalleTabla += "<td>" + item.type_document + "</td>";
							detalleTabla += "<td>" + item.number_document + "</td>";
							detalleTabla += "<td>" + item.type_user + "</td>";
							detalleTabla += "<td>" + item.email + "</td>";
							detalleTabla += "<td>" + item.cellphone + "</td>";
							detalleTabla += "<td>";
							detalleTabla += "<a class='btn btn-success' href='javascript:fnEditar(" + item.id + ");'><i class='fa-solid fa-pen'></i></a> ";
							detalleTabla += "<a class='btn btn-danger' href='javascript:fnEliminar(" + item.id + ");'><i class='fa-solid fa-trash'></i></a>";
							detalleTabla += "</td>";
							detalleTabla += "</tr>";
						});
				document.getElementById("detalleTabla").innerHTML = detalleTabla;
				document.getElementById("divResultado").style.display = "block";
				document.getElementById("divRegistro").style.display = "none";
			}
		};
		xhttp.send();
	}
	
	function fnBtnActualizar() {
		let xhttp = new XMLHttpRequest();
		xhttp.open("GET", "UsersActualizar", true);
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				let respuesta = xhttp.responseText;
				arreglo = JSON.parse(respuesta);
				let detalleTabla = "";
				arreglo.forEach(function(item) {
							detalleTabla += "<tr>";
							detalleTabla += "<td>" + item.id + "</td>";
							detalleTabla += "<td>" + item.names + "</td>";
							detalleTabla += "<td>" + item.last_name + "</td>";
							detalleTabla += "<td>" + item.type_document + "</td>";
							detalleTabla += "<td>" + item.number_document + "</td>";
							detalleTabla += "<td>" + item.type_user + "</td>";
							detalleTabla += "<td>" + item.email + "</td>";
							detalleTabla += "<td>" + item.cellphone + "</td>";
							detalleTabla += "<td>";
							detalleTabla += "<a class='btn btn-success' href='javascript:fnEditar(" + item.id + ");'><i class='fa-solid fa-pen'></i></a> ";
							detalleTabla += "<a class='btn btn-danger' href='javascript:fnEliminar(" + item.id + ");'><i class='fa-solid fa-trash'></i></a>";
							detalleTabla += "</td>";
							detalleTabla += "</tr>";
						});
				document.getElementById("detalleTabla").innerHTML = detalleTabla;
				document.getElementById("divResultado").style.display = "block";
				document.getElementById("divRegistro").style.display = "none";
			}
		};
		xhttp.send();
	}
	
	fnBtnActualizar();
	
	function fnCargarForm(id){
		arreglo.forEach(function(item) {
			if(item.id == id){
				frmId.value = item.id;
				frmNames.value = item.names;
				frmLast_name.value = item.last_name;
				frmType_document.value = item.type_document;
				frmNumber_document.value = item.number_document;
				frmType_user.value = item.type_user;
				frmEmail.value = item.email;
				frmCellphone.value = item.cellphone;
				return true;
			}
		});
	}
	
	function fnEstadoFormulario(estado){
		frmNames.disabled = (estado==ACCION_ELIMINAR)
		frmLast_name.disabled = (estado==ACCION_ELIMINAR)
		frmType_document.disabled = (estado==ACCION_ELIMINAR)
		frmNumber_document.disabled = (estado==ACCION_ELIMINAR)
		frmType_user.disabled = (estado==ACCION_ELIMINAR)
		frmEmail.disabled = (estado==ACCION_ELIMINAR)
		frmCellphone.disabled = (estado==ACCION_ELIMINAR)
		if(estado==ACCION_NUEVO){
			frmId.value = "0";
			frmNames.value = "";
			frmLast_name.value = "";
			frmType_document.value = "";
			frmNumber_document.value = "";
			frmType_user.value = "";
			frmEmail.value = "";
			frmCellphone.value = "";
		}
	}
	
	function fnValidar(){
		
		return true;
	}
	
	
</script>

	<!-- VALIDACION CAMPO NOMBRE -->
	<script>
		// Obtener el campo de entrada de nombres
    	var frmNamesInput = document.getElementById('frmNames');

		// Agregar un event listener para el evento 'input'
		    frmNamesInput.addEventListener('input', function(event) {
			// Obtener el valor actual del campo de nombres
		  	var name = event.target.value;

			// Expresi√≥n regular para validar solo letras y espacios
			var regex = /^[A-Za-z\s]+$/;

			// Validar el valor ingresado
			if (name === '') {
				// El campo est√° vac√≠o
				frmNamesInput.classList.remove('is-valid');
	            frmNamesInput.classList.remove('is-invalid');
			} else if (regex.test(name)) {
				// El valor es v√°lido
				frmNamesInput.classList.remove('is-invalid');
	            frmNamesInput.classList.add('is-valid');
			} else {
				// El valor es inv√°lido
				frmNamesInput.classList.remove('is-valid');
	            frmNamesInput.classList.add('is-invalid');
	        }
		});
	</script>


	<!-- VALIDACION CAMPO APELLIDO -->
	<script>
    // Obtener el campo de entrada de apellido
    var frmLastNameInput = document.getElementById('frmLast_name');

    // Agregar un event listener para el evento 'input'
    frmLastNameInput.addEventListener('input', function(event) {
        // Obtener el valor actual del campo de apellido
        var lastName = event.target.value;

        // Expresi√≥n regular para validar solo letras y espacios
        var regex = /^[A-Za-z\s]+$/;

        // Validar el valor ingresado
        if (lastName === '') {
            // El campo est√° vac√≠o
            frmLastNameInput.classList.remove('is-valid');
            frmLastNameInput.classList.remove('is-invalid');
        } else if (regex.test(lastName)) {
            // El valor es v√°lido
            frmLastNameInput.classList.remove('is-invalid');
            frmLastNameInput.classList.add('is-valid');
        } else {
            // El valor es inv√°lido
            frmLastNameInput.classList.remove('is-valid');
            frmLastNameInput.classList.add('is-invalid');
        }
    });
</script>

	<!-- VALIDACION CAMPO TIPO Y NUMERO DE DOCUMENTO -->
	<script>
    // Obtener los campos de selecci√≥n y entrada
    var frmTypeDocumentSelect = document.getElementById('frmType_document');
    var frmNumberDocumentInput = document.getElementById('frmNumber_document');
    var numberDocument = '';

    // Agregar event listener para el evento 'change' en el campo de tipo de documento
    frmTypeDocumentSelect.addEventListener('change', function(event) {
        // Restablecer las clases de validaci√≥n
        frmNumberDocumentInput.classList.remove('is-valid');
        frmNumberDocumentInput.classList.remove('is-invalid');
        frmTypeDocumentSelect.classList.remove('is-invalid');

        // Obtener el valor actual del campo de tipo de documento seleccionado
        var selectedType = event.target.value;
        numberDocument = frmNumberDocumentInput.value.trim();

        // Validar el valor ingresado solo si se ha seleccionado un tipo de documento y se ha ingresado un n√∫mero de documento
        if (selectedType !== '' && numberDocument !== '') {
            if (selectedType === 'DNI' && numberDocument.length === 8) {
                frmNumberDocumentInput.classList.remove('is-invalid');
                frmNumberDocumentInput.classList.add('is-valid');
            } else if (selectedType === 'CNE' && numberDocument.length === 9) {
                frmNumberDocumentInput.classList.remove('is-invalid');
                frmNumberDocumentInput.classList.add('is-valid');
            } else {
                frmNumberDocumentInput.classList.remove('is-valid');
                frmNumberDocumentInput.classList.add('is-invalid');
            }
        }
    });

    // Agregar event listener para el evento 'input' en el campo de n√∫mero de documento
    frmNumberDocumentInput.addEventListener('input', function(event) {
        // Obtener el valor actual del campo de n√∫mero de documento
        numberDocument = event.target.value.trim();
        var selectedType = frmTypeDocumentSelect.value;

        // Validar el valor ingresado solo si se ha seleccionado un tipo de documento y se ha ingresado un n√∫mero de documento
        if (selectedType !== '' && numberDocument !== '') {
            if (selectedType === 'DNI' && numberDocument.length === 8) {
                frmNumberDocumentInput.classList.remove('is-invalid');
                frmNumberDocumentInput.classList.add('is-valid');
            } else if (selectedType === 'CNE' && numberDocument.length === 9) {
                frmNumberDocumentInput.classList.remove('is-invalid');
                frmNumberDocumentInput.classList.add('is-valid');
            } else {
                frmNumberDocumentInput.classList.remove('is-valid');
                frmNumberDocumentInput.classList.add('is-invalid');
            }
        } else {
            frmNumberDocumentInput.classList.remove('is-valid');
            frmNumberDocumentInput.classList.remove('is-invalid');
        }
    });
</script>

	<!-- VALIDACION CAMPO TIPO DE USUARIO -->
	<script>
    var frmTypeUserInput = document.getElementById('frmType_user');

    frmTypeUserInput.addEventListener('input', function(event) {
        var userType = event.target.value;

        if (userType === '1' || userType === '2' || userType === '3') {
            frmTypeUserInput.classList.remove('is-invalid');
            frmTypeUserInput.classList.add('is-valid');
        } else {
            frmTypeUserInput.classList.remove('is-valid');
            frmTypeUserInput.classList.add('is-invalid');
        }
    });
</script>
	<!-- VALIDACION CAMPO TIPO DE DOCUMENTO -->
	<script>
		// Obtener el campo de entrada de Tipo de Documento
		var tipoDocumentoInput = document.getElementById('frmType_document');

		// Obtener el elemento del check
		var tipoDocumentoIcon = document.getElementById('frmType_document');

		// Agregar un event listener para el evento 'input'
		tipoDocumentoInput.addEventListener('input', function(event) {
			// Obtener el valor actual del campo de Tipo de Documento y eliminar los espacios en blanco
			var tipoDocumento = event.target.value.trim();

			// Validar el valor ingresado
			if (tipoDocumento === '') {
				// El campo est√° vac√≠o
				tipoDocumentoInput.classList.remove('is-valid');
				tipoDocumentoInput.classList.remove('is-invalid');
				tipoDocumentoIcon.style.display = 'none';
			} else if (/^[A-Z]+$/.test(tipoDocumento)) {
				// El valor es v√°lido: solo letras en may√∫scula
				tipoDocumentoInput.classList.remove('is-invalid');
				tipoDocumentoInput.classList.add('is-valid');
				tipoDocumentoIcon.style.display = 'inline-block';
			} else {
				// El valor es inv√°lido
				tipoDocumentoInput.classList.remove('is-valid');
				tipoDocumentoInput.classList.add('is-invalid');
				tipoDocumentoIcon.style.display = 'none';
			}
		});
	</script>



	<!-- VALIDACION CAMPO TIPO DE USUARIO -->
	<script>
    // Obtener el elemento del campo de selecci√≥n de tipo de usuario
    var frmTypeUserSelect = document.getElementById('frmType_user');
    
    // Obtener la opci√≥n seleccionada inicialmente
    var selectedOption = frmTypeUserSelect.options[frmTypeUserSelect.selectedIndex];

    // Agregar un event listener para el evento 'change'
    frmTypeUserSelect.addEventListener('change', function(event) {
        // Obtener la opci√≥n seleccionada
        selectedOption = event.target.options[event.target.selectedIndex];
        validateUserType();
    });

    // Funci√≥n para validar el tipo de usuario seleccionado
    function validateUserType() {
        // Obtener el valor del tipo de usuario
        var userType = selectedOption.value;

        // Validar el tipo de usuario seleccionado
        if (userType === 'D' || userType === 'P' || userType === 'T') {
            // El valor es v√°lido
            frmTypeUserSelect.setCustomValidity('');
            frmTypeUserSelect.classList.remove('is-invalid');
            frmTypeUserSelect.classList.add('is-valid');
        } else {
            // El valor es inv√°lido
            frmTypeUserSelect.setCustomValidity('invalid');
            frmTypeUserSelect.classList.remove('is-valid');
            frmTypeUserSelect.classList.add('is-invalid');
        }
    }

    // Agregar un event listener para el evento 'focus'
    frmTypeUserSelect.addEventListener('focus', function() {
        // Restablecer la validaci√≥n personalizada al enfocar el campo
        frmTypeUserSelect.setCustomValidity('');
    });

    // Agregar un event listener para el evento 'blur'
    frmTypeUserSelect.addEventListener('blur', function() {
        // Validar el tipo de usuario seleccionado al perder el foco
        validateUserType();
    });

    // Agregar un event listener para el evento 'invalid'
    frmTypeUserSelect.addEventListener('invalid', function() {
        // Obtener el elemento de retroalimentaci√≥n de validaci√≥n inv√°lida
        var invalidFeedback = document.getElementById('frmType_user').nextElementSibling;
        
        // Mostrar el mensaje de retroalimentaci√≥n con el nombre completo de la opci√≥n seleccionada
        invalidFeedback.innerHTML = 'Por favor, seleccione una opci√≥n v√°lida: ' + selectedOption.getAttribute('data-fullname') + '.';
    });
</script>

	<!-- VALIDACION CAMPO EMAIL -->

	<script>
    // Obtener el campo de entrada de Email
    var emailInput = document.getElementById('frmEmail');

    // Agregar un event listener para el evento 'input'
    emailInput.addEventListener('input', function(event) {
        // Obtener el valor actual del campo de Email
        var email = event.target.value.trim();

        // Validar el valor ingresado
        if (email === '') {
            // El campo est√° vac√≠o
            emailInput.classList.remove('is-valid');
            emailInput.classList.remove('is-invalid');
        } else if (/^.+@.+\..+$/.test(email)) {
            // El valor es v√°lido (contiene el car√°cter "@" y ".")
            emailInput.classList.remove('is-invalid');
            emailInput.classList.add('is-valid');
        } else {
            // El valor es inv√°lido
            emailInput.classList.remove('is-valid');
            emailInput.classList.add('is-invalid');
        }
    });
</script>

	<!-- VALIDACION CAMPO CELULAR -->
	<script>
    // Obtener el campo de entrada de Celular
    var celularInput = document.getElementById('frmCellphone');

    // Agregar un event listener para el evento 'input'
    celularInput.addEventListener('input', function(event) {
        // Obtener el valor actual del campo de Celular
        var celular = event.target.value.trim();

        // Validar el valor ingresado
        if (/^9\d{0,8}$/.test(celular) && celular.length === 9) {
            // El valor es v√°lido
            celularInput.classList.remove('is-invalid');
            celularInput.classList.add('is-valid');
        } else {
            // El valor es inv√°lido
            celularInput.classList.remove('is-valid');
            celularInput.classList.add('is-invalid');
        }
    });
</script>
	<script>
  function exportToExcel() {
    // Obtener la referencia a la tabla
    const tabla = document.getElementById('detalleTabla');

    // Crear una nueva hoja de c√°lculo de Excel
    const workbook = XLSX.utils.book_new();

    // Crear una nueva hoja en el libro de Excel
    const sheet = XLSX.utils.table_to_sheet(tabla);

    // Agregar la hoja al libro de Excel
    XLSX.utils.book_append_sheet(workbook, sheet, 'Datos');

    // Generar el archivo Excel
    const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    // Convertir el archivo Excel a un objeto Blob
    const blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

    // Crear un enlace para descargar el archivo Excel
    const enlace = document.createElement('a');
    enlace.href = window.URL.createObjectURL(blob);
    enlace.download = 'datos.xlsx';

    // Simular un clic en el enlace para iniciar la descarga
    enlace.click();
  }
</script>
	<script>
  function exportToCSV() {
    // Obtener la referencia a la tabla
    const tabla = document.getElementById('detalleTabla');

    // Crear una variable para almacenar los datos CSV
    let csv = '';

    // Recorrer las filas de la tabla
    for (let i = 0; i < tabla.rows.length; i++) {
      const fila = tabla.rows[i];

      // Recorrer las celdas de la fila
      for (let j = 0; j < fila.cells.length; j++) {
        const celda = fila.cells[j];

        // Agregar el valor de la celda al CSV, separado por comas
        csv += celda.innerText + ',';
      }

      // Agregar un salto de l√≠nea al final de cada fila
      csv += '\n';
    }

    // Crear un enlace para descargar el archivo CSV
    const enlace = document.createElement('a');
    enlace.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
    enlace.download = 'Alumnado_de_SRC.csv';

    // Simular un clic en el enlace para iniciar la descarga
    enlace.click();
  }
</script>
	<script>
function exportToExcel() {
	  // Obtener la referencia a la tabla
	  var table = document.querySelector('.table');

	  // Obtener todas las filas de la tabla
	  var rows = Array.from(table.querySelectorAll('tbody tr'));

	  // Crear una matriz para almacenar los datos del archivo de Excel
	  var excelData = [];

	  // Iterar sobre cada fila y obtener los datos de las celdas
	  rows.forEach(function(row) {
	    var rowData = [];
	    var cells = Array.from(row.querySelectorAll('td'));

	    // Obtener el texto de cada celda y agregarlo a la matriz de datos
	    cells.forEach(function(cell) {
	      rowData.push(cell.textContent.trim());
	    });

	    // Agregar la fila de datos a la matriz del archivo de Excel
	    excelData.push(rowData);
	  });

	  // Crear una nueva hoja de c√°lculo de Excel
	  var workbook = XLSX.utils.book_new();

	  // Crear una hoja de trabajo
	  var worksheet = XLSX.utils.aoa_to_sheet(excelData);

	  // Agregar la hoja de trabajo al libro de Excel
	  XLSX.utils.book_append_sheet(workbook, worksheet, 'Tabla');

	  // Generar un archivo de Excel binario
	  var excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

	  // Convertir el buffer de Excel a un blob
	  var blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

	  // Crear un enlace temporal para descargar el archivo de Excel
	  var link = document.createElement('a');
	  link.href = window.URL.createObjectURL(blob);
	  link.download = 'Activos.xlsx';

	  // Simular el clic en el enlace para descargar el archivo de Excel
	  link.click();
	}
</script>

	<script>
function exportToCSV() {
	  // Obtener la referencia a la tabla
	  var table = document.querySelector('.table');

	  // Obtener todas las filas de la tabla
	  var rows = Array.from(table.querySelectorAll('tbody tr'));

	  // Crear una matriz para almacenar los datos del CSV
	  var csvData = [];

	  // Iterar sobre cada fila y obtener los datos de las celdas
	  rows.forEach(function(row) {
	    var rowData = [];
	    var cells = Array.from(row.querySelectorAll('td'));

	    // Obtener el texto de cada celda y agregarlo a la matriz de datos
	    cells.forEach(function(cell) {
	      rowData.push(cell.textContent.trim());
	    });

	    // Agregar la fila de datos al CSV
	    csvData.push(rowData.join(','));
	  });

	  // Crear el contenido del archivo CSV
	  var csvContent = csvData.join('\n');

	  // Crear un enlace temporal para descargar el archivo CSV
	  var link = document.createElement('a');
	  link.href = 'data:text/csv;charset=utf-8,' + encodeURI(csvContent);
	  link.target = '_blank';
	  link.download = 'Activos.csv';

	  // Simular el clic en el enlace para descargar el archivo CSV
	  link.click();
}
</script>




</body>
</html>