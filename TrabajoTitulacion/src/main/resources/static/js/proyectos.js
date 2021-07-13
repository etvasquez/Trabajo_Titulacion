$(document).ready(function() {
    try {
        let rutaBase = 'http://localhost/';
        const arrSelected = [];
        $('#tableProyectos').DataTable({
            order: [[2, 'asc']],
            searching: true,
            paging: true,
            language: {
                info: "Mostrando _PAGE_ de _PAGES_ registros",
                paginate: {
                    previous: "Anterior",
                    next: "Siguiente"
                }
            }
        });
    }catch (e) {
        
    }
    try {
        $('#tableRepository').DataTable({
            ordering: false,
            searching: false,
            paging: true,
            pageLength: 3,
            lengthChange: false,
            language: {
                info: "Mostrando _PAGE_ de _PAGES_ registros",
                paginate: {
                    previous: "Anterior",
                    next: "Siguiente"
                }
            }
        });
    }catch (e) {

    }
    try {
        $('#filtro').select2({
            width: '100%',
            placeholder:"Seleccione una área ...",
        });
        $('#filtro').on('change', function(){
            filtroavanzada();
        });
    }
    catch (e) {

    }
} );
function verProyecto(str) {
    window.open(rutaBase + 'proyecto/' + str, '_blank');
}
function editarProyecto(str) {
    window.open(rutaBase + 'editar_proyecto/' + str, '_blank');
}
function inicializarTab() {
    const tab = $('#tab').val();
    console.log(tab);
}

function filtroavanzada() {
    let busqueda = $('#filtroBusqueda').val();
    let selected = $('#filtro').val();
    if(selected.length===0){
        selected=['todo'];
    }
    if(busqueda.length===0){
        busqueda="vacio";
    }
    if(busqueda.length>4 || busqueda.length===0){
        const ruta = rutaBase + "repositorio/" + selected + "/" + busqueda;
        console.log("ruta: "+ruta);
        $.get(ruta,
            function (res) {
                console.log(res);
                //limpiar tabla
                const table = $('#tableRepository').DataTable();
                table.clear().draw();
                table.ordering = false,
                    table.searching = true,
                    table.paging = true,
                    table.pageLength = 3,
                    table.ordering = false,
                    table.language = {
                        search: "Buscador: ",
                        info: "Mostrando _PAGE_ de _PAGES_ registros",
                        paginate: {
                            previous: "Anterior",
                            next: "Siguiente"
                        }
                    };
                for (let i = 0; i < res.length; i++) {
                    if(res[i].nodo2.id===""){
                        table.row.add([
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo1.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo1.group + "</span></p>" +
                            "<a style='color: #0049c3' href='/proyecto/"+res[i].nodo1.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo1.title + "</a></div></div>","",""
                        ]).draw(false);
                    }else if(res[i].nodo3.id===""){
                        table.row.add([
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo1.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo1.group + "</span></p>" +
                            "<a style='color: #0049c3' href='/proyecto/"+res[i].nodo1.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo1.title + "</a></div></div>",
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo2.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo2.group + "</span></p>" +
                            "<a style='color: #0049c3' href='/proyecto/"+res[i].nodo2.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo2.title + "</a></div></div>",""
                        ]).draw(false);
                    }else{
                        table.row.add([
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo1.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo1.group + "</span></p>" +
                            "<a href='/proyecto/"+res[i].nodo1.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo1.title + "</a></div></div>",
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo2.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo2.group + "</span></p>" +
                            "<a href='/proyecto/"+res[i].nodo2.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo2.title + "</a></div></div>",
                            "<div class='card' style='height: 250px; width: 400px'><div class='card-body text-center'><h6>" + res[i].nodo3.label + "</h6>" +
                            "<p><img class='img1' src='../static/img/user.png' alt='coordinador'><span>" + res[i].nodo3.group + "</span></p>" +
                            "<a href='/proyecto/"+res[i].nodo3.id+"' class='btn btn-primary stretched-link mb-2'>" + res[i].nodo3.title + "</a></div></div>",
                        ]).draw(false);
                    }
                }
            });
    }
}

