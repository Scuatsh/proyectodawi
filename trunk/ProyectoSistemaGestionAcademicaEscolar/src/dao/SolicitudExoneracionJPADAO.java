package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import entidades.Actividad;
import entidades.Boleta;
import entidades.SolicitudExoneracion;

public class SolicitudExoneracionJPADAO implements SolicitudExoneracionDAO{
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void setEntityManagerFactory(EntityManagerFactory emf){
		this.emf = emf;
	}
	
	public boolean CumpleCalendarioExoneracion(int codigoCalendario) throws Exception {
		boolean resultado = false;
		
		try {
			em = emf.createEntityManager();
			
			Actividad actividad = null;
			
			Query q = em.createQuery("SELECT a FROM Actividad a WHERE a.calendarioacademico.strCodcalendario='2011' " +
					                 "and a.intCodigoActividad=1 and (CURRENT_DATE between a.dtFecIni and a.dtFecFin)");
			//q.setParameter(1, codigoCalendario);
			//q.setParameter(2, 1);
			actividad = (Actividad) q.getSingleResult();
			
			em.close();
			
			if(actividad!=null){
				resultado = true;
				return resultado;
			}else{
				resultado = false;
				return resultado;
			}
		} catch (Exception e) {
			resultado = false;
			return resultado;
		}
		
		
	}
	
	public boolean NoExisteDeudas(Boleta boleta) throws Exception{
		
		boolean resultado = false;
		
		
		try {
			
			em = emf.createEntityManager();
			
			Boleta objBoleta = null;
			
			Query q = em.createQuery("select b from Boleta b where b.apoderados.personas.strCodigoPersona=?1 and " +
					                 "b.strEstado='Cancelado'");
			q.setParameter(1, boleta.getApoderados().getPersonas().getStrCodigoPersona());
			
			objBoleta = (Boleta) q.getSingleResult();
			
			em.close();
			
			if(objBoleta!=null){
				resultado = true;
				return resultado;
			}else{
				resultado = false;
				return resultado;
			}

		} catch (Exception e) {
			resultado = false;
			return resultado;
		}

	
	}
	
	public SolicitudExoneracion buscarSolicitudXAsignatura(SolicitudExoneracion exoneracion) throws Exception{
			
		try {
			
			em = emf.createEntityManager();
			
			SolicitudExoneracion objExoneracion = null;
			
			Query q = em.createQuery("select se from SolicitudExoneracion se " +
					                 "where se.asignaturas.intCodigoAsignatura=?1 and " +
					                 "(se.strEstado='Pendiente' or se.strEstado='Aprobado') and " +
					                 "year(se.dtFecReg)=year(CURRENT_DATE)");
			q.setParameter(1, exoneracion.getAsignaturas().getIntCodigoAsignatura());
			
			objExoneracion = (SolicitudExoneracion) q.getSingleResult();
			
			em.close();
			
			if(objExoneracion!=null){
				return objExoneracion;
			}else{
				return null;
			}

		} catch (Exception e) {
			return null;
		}

	
	}
	

}