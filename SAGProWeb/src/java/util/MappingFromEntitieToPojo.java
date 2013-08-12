/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import pojoclass.Objetivo;
import pojoclass.ProduccionDiaria;
import pojoclass.SelectedMaterial;

/**
 *
 * @author Jose
 */
public class MappingFromEntitieToPojo {
    
    /**
     * Mapping entre clase Entities.Material y pojoclass.Material
     * @param materialEntity    Entities.Material a Mappear
     * @return pojoclass.material   Entidad POJO mapeada
     */
    static public pojoclass.Material materialFromEntityToPojo(entities.Material materialEntity){
        pojoclass.Material materialPojo = new pojoclass.Material();
        
        materialPojo.setCodMaterial(materialEntity.getCodMaterial());
        materialPojo.setNombreMaterial(materialEntity.getNombreMaterial());
        materialPojo.setMedidaProduccionMaterial(materialEntity.getMedidaProduccionMaterial());
        materialPojo.setMedidaVentaMaterial(materialEntity.getMedidaVentaMaterial());
        
        return materialPojo;
    }
    
    /**
     * 
     * @param userEntity
     * @return 
     */
    static public pojoclass.Usuario usuarioFromEntityToPojo(entities.User userEntity){
        pojoclass.Usuario usuarioPojo = new pojoclass.Usuario();
        
        usuarioPojo.setRut(userEntity.getRutUser());
        usuarioPojo.setNombre(userEntity.getNombreUser());
        usuarioPojo.setApellido(userEntity.getApellidoUser());
        usuarioPojo.setCorreo(userEntity.getEmailUser());
        usuarioPojo.setTipo(userEntity.getRoleUser());
        
        return usuarioPojo;
    }

    public static Objetivo objetivoFromEntityToPojo(entities.Objetivo objEntity) {
        pojoclass.Objetivo objetivoPojo = new pojoclass.Objetivo();
        
        objetivoPojo.setCodObjetivo(objEntity.getCodObjetivo());
        objetivoPojo.setNombre(objEntity.getNombreObjetivo());
        objetivoPojo.setPrioridad(objEntity.getPrioridadObjetivo());
        objetivoPojo.setFechaInicial(objEntity.getFechaInicial());
        objetivoPojo.setFechaLimite(objEntity.getFechaLimite());
        objetivoPojo.setDescripcion(objEntity.getDescripcionObjetivo());
        
        return objetivoPojo;
    }
    
    public static SelectedMaterial selectedMaterialFromMaterialObjetivoToPojo(entities.ObjetivoMaterial om){
        pojoclass.SelectedMaterial sm = new SelectedMaterial();
        sm.setCodMaterial(om.getMaterial().getCodMaterial());
        sm.setNombreMaterial(om.getMaterial().getNombreMaterial());
        sm.setCantidad(om.getCantidadObjetivo());
        return sm;
    }
    
    public static ProduccionDiaria produccionFromEntityToPojo(entities.ProduccionDiaria prdEntity){
        pojoclass.ProduccionDiaria producPojo = new ProduccionDiaria();
        producPojo.setFecha(prdEntity.getProduccionDiariaPK().getFechaDiariaEstadistica());
        producPojo.setCodMaterial(prdEntity.getMaterial().getCodMaterial());
        producPojo.setCantidad(prdEntity.getProduccionMaterial());
        producPojo.setNombreMaterial(prdEntity.getMaterial().getNombreMaterial());
        producPojo.setNombreUnidadMaterial(prdEntity.getMaterial().getMedidaProduccionMaterial());
        return producPojo;
    }
            
}
