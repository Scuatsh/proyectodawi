package dao;

import java.util.ArrayList;

import entidades.Alumno;

public interface AlumnoDAO {

	public ArrayList<Alumno> obtenerTodos() throws Exception;
	public void insertar(Alumno alumno) throws Exception;
	public void actualizar(Alumno alumno) throws Exception;
	public void inhabilitar(Alumno alumno) throws Exception;
	public Alumno obtenerAlumno(Alumno alumno) throws Exception;
	
}