$(document).ready(function() {
    try {
        $('#tableProyectos').DataTable({
            order: [[2, 'asc']],
            searching: true,
            paging: true
        });
    }catch (e) {
        
    }
} );
function verProyecto(str) {
    window.open(rutaBase + 'proyecto/' + str, '_blank');
}
function editarProyecto(str) {
    window.open(rutaBase + 'editar_proyecto/' + str, '_blank');
}
function inicializarTab() {
    var tab = $('#tab').val();
    console.log(tab);
}

