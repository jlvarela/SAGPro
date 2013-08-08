/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import pojoclass.Objetivo;

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
        
        objetivoPojo.setNombre(objEntity.getNombreObjetivo());
        
        return objetivoPojo;
    }
            
}
