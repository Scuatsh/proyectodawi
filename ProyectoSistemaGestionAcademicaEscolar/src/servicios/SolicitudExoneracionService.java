package servicios;

import entidades.Boleta;
import entidades.SolicitudExoneracion;


public interface SolicitudExoneracionService {

	public boolean CumpleCalendarioExoneracion(int codigoCalendario) throws Exception;
	public boolean NoExisteDeudas(Boleta boleta) throws Exception;
	public SolicitudExoneracion buscarSolicitudXAsignatura(SolicitudExoneracion exoneracion) throws Exception;
	public void registrarSolictud(SolicitudExoneracion exoneracion) throws Exception;
}