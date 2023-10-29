package nutricionista73group;

import Conexion.ComidaDAO;
import Conexion.DietaComidaDAO;
import Conexion.DietaDAO;
import Conexion.PacienteDAO;
import Entidades.Comida;
import Entidades.Dieta;
import Entidades.DietaComida;
import Entidades.Horario;
import Entidades.Paciente;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Nutricionista73Group {

    public static void main(String[] args) {

//////////////////////////////////////////////////////////////////////////////////////
/////////                     Prueba Crud clase Comida                   /////////////
/////////                                                                /////////////
/////////////////////////////////////////////////////////////////////////////////////
//        // Crea instancia de ComidaDAO
//        ComidaDAO comidaDAO = new ComidaDAO();
//
//        // inserción de comida
//        Comida nuevaComida = new Comida(250, "desayuno", "tostadas con mermelada", true);
//        comidaDAO.insertar(nuevaComida);
//
//        // búsqueda de comida por nombre
//        String nombreComidaABuscar = "Desayuno"; 
//        Comida comidaEncontrada = comidaDAO.buscarPorNombre(nombreComidaABuscar,1);
//        if (comidaEncontrada != null) {
//            System.out.println("Comida encontrada: " + comidaEncontrada.getNombre());
//        } else {
//            System.out.println("Comida no encontrada.");
//        }
//
//        // modificación de una comida
//        if (comidaEncontrada != null) {
//            comidaEncontrada.setDetalle("Tostadas con mantequilla y mermelada");
//            comidaDAO.modificar(comidaEncontrada);
//            System.out.println("Comida modificada con éxito.");
//        }
//
//        // eliminación de comida
//        if (comidaEncontrada != null) {
//            comidaDAO.borrar(comidaEncontrada.getIdComida());
//            System.out.println("Comida eliminada.");
//        }
//
//        // listado de todas las comidas
//        System.out.println("Listado de todas las comidas:");
//        for (Comida comida : comidaDAO.listarComidas(5)) {
//            System.out.println(comida.getNombre());
//        }
/////////////////////////////////////////////////////////////////////////////////////
////////                      Prueba Crud clase Paciente                 ////////////
////////                                                                 ////////////
/////////////////////////////////////////////////////////////////////////////////////
//        // Crea instancia de PacienteDAO
//        PacienteDAO pacienteDAO = new PacienteDAO();
//
//        // inserción de paciente
//        Paciente nuevoPaciente = new Paciente("Juan Pérez", 123456789, "Calle 123", 555555, 70.0, true);
//        pacienteDAO.guardarPaciente(nuevoPaciente);
//
//        // búsqueda de paciente por DNI
//        int dniABuscar = 123456789; 
//        Paciente pacienteEncontrado = pacienteDAO.buscarPacientePorDni(dniABuscar, 1); // 1 para pacientes activos, 0  inactivos otro numero ambos...
//        if (pacienteEncontrado != null) {
//            System.out.println("Paciente encontrado: " + pacienteEncontrado.getNombre());
//        } else {
//            System.out.println("Paciente no encontrado.");
//        }
//
//        // modificación de paciente
//        if (pacienteEncontrado != null) {
//            pacienteEncontrado.setNombre("Juan Pérez Modificado");
//            pacienteDAO.modificarPaciente(pacienteEncontrado);
//            System.out.println("Paciente modificado con éxito.");
//        }
//
//        // listado de pacientes activos
//        System.out.println("Listado de pacientes activos:");
//        for (Paciente paciente : pacienteDAO.listarPaciente(1)) {
//            System.out.println(paciente.getNombre());
//        }
//
//        // eliminación física de un paciente
//        if (pacienteEncontrado != null) {
//            int idPacienteAEliminar = pacienteEncontrado.getIdPaciente();
//            pacienteDAO.eliminarPacienteFisico(idPacienteAEliminar);
//            System.out.println("Paciente eliminado físicamente.");
//        }
/////////////////////////////////////////////////////////////////////////////////////
////////                      Prueba Crud clase Consulta                 ////////////
////////                                                                 ////////////
/////////////////////////////////////////////////////////////////////////////////////
//        ConsultaDAO consultaDAO = new ConsultaDAO();
//        PacienteDAO pacienteDAO = new PacienteDAO();
//
//        // Obtiene una lista de todas las consultas
//        ArrayList<Consulta> consultas = consultaDAO.buscar();
//        for (Consulta consulta : consultas) {
//            System.out.println(consulta);
//        }
//
//        // Crea un nuevo paciente para insertar una nueva consulta
//        Paciente paciente = new Paciente("Juan", 123456789, "Calle 123", 555555, 1, 70.5, true);
//        pacienteDAO.guardarPaciente(paciente);
//        
//        // Crea una nueva consulta
//        Consulta nuevaConsulta = new Consulta(paciente, LocalDate.now(), 75.0);
//
//        // Inserta la nueva consulta en la base de datos
//        consultaDAO.insertar(nuevaConsulta);
//        System.out.println("Nueva consulta insertada: " + nuevaConsulta);
//
//        // Actualiza la consulta
//        nuevaConsulta.setPesoActual(76.0);
//        consultaDAO.actualizar(nuevaConsulta);
//        System.out.println("Consulta actualizada: " + nuevaConsulta);
//
//        // Busca una consulta por paciente
//        Consulta consultaPorPaciente = consultaDAO.buscar(paciente);
//        System.out.println("Consulta encontrada por paciente: " + consultaPorPaciente);
//
//        // Elimina la consulta
//        int idConsultaAEliminar = nuevaConsulta.getIdConsulta();
//        consultaDAO.eliminar(idConsultaAEliminar);
//        System.out.println("Consulta eliminada con ID: " + idConsultaAEliminar);
//
/////////////////////////////////////////////////////////////////////////////////////
////////                      Prueba Crud clase Dieta                    ////////////
////////                                                                 ////////////
/////////////////////////////////////////////////////////////////////////////////////
//        // Crea una instancia de DietaDAO
//        DietaDAO dietaDAO = new DietaDAO();
//        // Crea una instancia de PacienteDAO
//        PacienteDAO pacienteDAO = new PacienteDAO();
//
//        // Crea  paciente
//        Paciente paciente = new Paciente("Maria Rodriguez", 987654321, "Avenida 456", 555555, 60.0, true);
//        // inserción de un paciente
//        pacienteDAO.guardarPaciente(paciente);        
//
//        // inserción de una dieta
//        Dieta nuevaDieta = new Dieta("Dieta de prueba", paciente, LocalDate.now(), LocalDate.now(), 55.0, true);
//        dietaDAO.insertar(nuevaDieta);
//        // listado de dietas 
//        System.out.println("Listado de dietas activas:");
//        for (Dieta dieta : dietaDAO.buscar(3)) {
//            System.out.println(dieta.getNombre());
//        }
//
//        // búsqueda de una dieta por ID
//        int idDietaABuscar = nuevaDieta.getIdDieta();
//        Dieta dietaEncontrada = dietaDAO.buscarPorId(idDietaABuscar, 3);
//        if (dietaEncontrada != null) {
//            System.out.println("Dieta encontrada: " + dietaEncontrada.getNombre());
//        } else {
//            System.out.println("Dieta no encontrada.");
//        }
//
//        // modificación de una dieta
//        if (dietaEncontrada != null) {
//            dietaEncontrada.setNombre("Dieta Modificada");
//            dietaDAO.actualizar(dietaEncontrada);
//            System.out.println("Dieta modificada con éxito.");
//        }
//
//        // eliminación lógica de una dieta (cambio de estado)
//        if (dietaEncontrada != null) {
//            dietaDAO.anularDieta(dietaEncontrada.getIdDieta());
//            System.out.println("Dieta anulada.");
//        }
//
///////////////////////////////////////////////////////////////////////////////////////
////////                      Prueba Crud clase DietaComida              //////////////
////////                                                                //////////////
//////////////////////////////////////////////////////////////////////////////////////
//        DietaComidaDAO dietaComidaDAO = new DietaComidaDAO();
//        ComidaDAO comidaDAO = new ComidaDAO();
//        DietaDAO dietaDAO = new DietaDAO();
//        PacienteDAO pacienteDAO = new PacienteDAO();
//
//        // inserción de  comida
        Comida nuevaComida = new Comida(250, "desayuno", "tostadas con mermelada", true);//("Desayuno", "Tostadas con mermelada", 250.0);
//       comidaDAO.insertar(nuevaComida);
//
////        // Crea  paciente 
        Paciente paciente = new Paciente("Maria Rodriguez", 987654321, "Avenida 456", 555555, 60.0, true);
//        // inserción de un paciente
//        pacienteDAO.guardarPaciente(paciente);        
//
//        // inserción de una dieta
        Dieta nuevaDieta = new Dieta("Dieta de prueba", paciente, LocalDate.now(), LocalDate.now(), 55.0, true);
//        dietaDAO.insertar(nuevaDieta);        
//        
//        // inserción de una relación DietaComida
        Horario miHorario=Horario.DESAYUNO;
        DietaComida nuevaDietaComida = new DietaComida(nuevaComida,nuevaDieta, 250,miHorario,true);
//       dietaComidaDAO.insertar(nuevaDietaComida);
    System.out.println((nuevaDietaComida.getHorario()+"").getClass()) ;
//        // búsqueda de comidas para una dieta específica
//        int idDietaABuscar = nuevaDieta.getIdDieta(); 
//        System.out.println("Comidas para la Dieta con ID " + idDietaABuscar + ":");
//        for (DietaComida dietaComida : dietaComidaDAO.buscarPorIdDieta(idDietaABuscar,1)) {
//            int idComida = dietaComida.getComida().getIdComida();
//            System.out.println("ID de Comida: " + idComida);
//        }
//
//        // eliminación 
//        if (nuevaDietaComida.getId() != 0) {
//            dietaComidaDAO.eliminarDietaComida(nuevaDietaComida.getId());
//            System.out.println("DietaComida eliminada.");
//        }
//
//        // listado de todas las DietaComida
//        System.out.println("Listado de todas las relaciones DietaComida:");
//        for (DietaComida dietaComida : dietaComidaDAO.buscar(3)) {
//            System.out.println("ID de Dieta: " + dietaComida.getDieta().getIdDieta()+ ", ID de Comida: " + dietaComida.getComida().getIdComida());
//        }
//

    }
}
