package utiles;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import entidades.Apoderado;
import entidades.Asignatura;
import entidades.Boleta;
import entidades.Persona;
import entidades.SolicitudExoneracion;

import servicios.ApplicationBusinessDelegate;
import servicios.AsignaturaService;
import servicios.SolicitudExoneracionService;

public class ExoneracionValidatorParte2 implements Validator {

	private static ApplicationBusinessDelegate abd = new ApplicationBusinessDelegate();
	
	private static SolicitudExoneracionService exoneracionService = abd.getExoneracionService();
	private static AsignaturaService asignaturaService = abd.getAsignaturaService();

    public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
    {
        Integer valor = (Integer) value;
        
        String blCondicion = (String) component.getAttributes().get("par");
        
        String numeroBoleta = (String) component.getAttributes().get("bol");
        
        System.out.println("--->" + valor);
        System.out.println("--->" + blCondicion);
        System.out.println("--->" + numeroBoleta);
        
        //HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		//Usuario sessionUsuario = (Usuario)session.getAttribute("b_usuario");
		 
		Persona tmpPersona = new Persona();
		//tmpPersona.setStrCodigoPersona(sessionUsuario.getPersonas().getStrCodigoPersona());
		tmpPersona.setStrCodigoPersona("PE-18181818");
		
		Apoderado tmpApoderado = new Apoderado();
		tmpApoderado.setPersonas(tmpPersona);
		
		Asignatura tmpCodigoAsignatura = new Asignatura();
		tmpCodigoAsignatura.setIntCodigoAsignatura(valor);
		
		Asignatura asignatura = null;
		try {
			asignatura = asignaturaService.obtenerAsignatura(tmpCodigoAsignatura);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Boleta tmpBoleta = new Boleta();
		tmpBoleta.setStrCodigoBoleta(numeroBoleta);
		tmpBoleta.setApoderados(tmpApoderado);
		tmpBoleta.setStrTipo("EXONERACION:" + asignatura.getStrNombreAsignatura());
		
		boolean boletaCondicion = false;
		
		try {
			boletaCondicion = exoneracionService.NoExisteDeudas(tmpBoleta);
		} catch (Exception e) {
			boletaCondicion = false;
			e.printStackTrace();
		}
		
		if(!boletaCondicion){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO,"Nro Boleta","Ingrese el n�mero de boleta correcto."));
		}else{
			    Asignatura tmpAsignatura = new Asignatura();
		        tmpAsignatura.setIntCodigoAsignatura(valor);
		        
		        
		        SolicitudExoneracion tmpExoneracion = new SolicitudExoneracion();
		        tmpExoneracion.setAsignaturas(tmpAsignatura);
		        
		        SolicitudExoneracion condicion = null;
				try {
					condicion = exoneracionService.buscarSolicitudXAsignatura(tmpExoneracion);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (condicion != null && !blCondicion.equalsIgnoreCase("")) {
					if(condicion.getStrEstado().equalsIgnoreCase("Pendiente")){
						throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud PENDIENTE","Existe una solicitud PENDIENTE para este curso."));
					}else if (condicion.getStrEstado().equalsIgnoreCase("Aprobada")) {
						throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO,"Solicitud APROBADA","Su solicitud para este curso " +
		                "ya fue APROBADA. No puede generar otra solicitud para este curso"));
					}
		     
				}
		}
		
    }

}