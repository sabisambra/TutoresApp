package Mundo;

import java.util.ArrayList;

/**
 * Created by Santiago on 9/16/2015.
 */
public class Usuario
{
    //--------------------------
    //Atributos
    //--------------------------

    /**
     * El nombre con el cual se identifica el usuarios
     */
    private String nombre;

    /**
     * La lista de materias que el usuario esta dispuesto a dar tutorias
     */
    private ArrayList<String> materias;

    /**
     * El telefono de contacto del usuario
     */
    private int telefono;

    /**
     * La clave del usuario
     */
    private String contrasenia;

    /**
     * Constructor
     * @param nombreP el nombre del usuario
     * @param telefonoP el telefono del usuario
     */
    public Usuario(String nombreP, int telefonoP, String clave)
    {
        nombre = nombreP;
        telefono = telefonoP;
        materias = new ArrayList<>();
        contrasenia = clave;
    }

    /**
     * Metodo constructor sin parametros
     */
    public Usuario()
    {
        materias = new ArrayList<>();
    }

    /**
     * Metodo que retorna el nombre del usuario
     * @return el nombre del usuario
     */
    public String darNombre()
    {
        return  nombre;
    }

    /**
     * Metodo que cambia el nombre del usuario
     * @param nombreP el nuevo nombre de usuario
     */
    public void cambiarNombre(String nombreP)
    {
        nombre = nombreP;
    }

    /**
     * Metodo uqe elimina una materia de la lista
     * @param materiaEliminar el nombre de la materia a eliminar
     */
    public void eliminarMateria(String materiaEliminar)
    {
        for(int i=0;i<materias.size();i++)
        {
            String materiaActual = materias.get(i);
            if(materiaActual.equals(materiaEliminar))
            {
                materias.remove(i);
            }
        }
    }

    /**
     * Metodo que agrega una materia a la lista de materias del usuario
     */
    public void agregarMateria(String nuevaMateria)
    {
        materias.add(nuevaMateria);
    }

    /**
     * Metodo que retorna la lista de materias del usuario
     * @return la lista de materias
     */
    public ArrayList<String> darMaterias()
    {
        return materias;
    }

    /**
     * Metodo que retorna el telefono del usuario
     * @return el telefono del usuario
     */
    public int darTelefono()
    {
        return telefono;
    }

    /**
     * metoo que cambia el telefono del usuario por el telefono dado por parametro
     * @param telefonoP el telefono del usuario
     */
    public void cambiarTelefono(int telefonoP)
    {
        telefono = telefonoP;
    }

    /**
     * Metodo que retorna la clave del usuario
     * @return la clave del usuario
     */
    public String darClave()
    {
        return contrasenia;
    }

    /**
     * Metodo que cambia la clave del usuario por la clave dada por parametro
     * @param clave
     */
    public void cambiarClave(String clave)
    {
        contrasenia = clave;
    }

}
