/* global Swal */

function mensajePersonal(descripcion,estatus,confirmacionBoton, tiempoEspera) {

    Swal.fire({
        title: descripcion,
        icon: estatus,
        showConfirmButton: confirmacionBoton,
        timer: tiempoEspera,
        showClass: {
            popup: `
                            animate__animated
                            animate__fadeInUp
                            animate__faster
                            `
        },
        hideClass: {
            popup: `
                            animate__animated
                            animate__fadeOutDown
                            animate__faster
                            `
        }
    });
}
