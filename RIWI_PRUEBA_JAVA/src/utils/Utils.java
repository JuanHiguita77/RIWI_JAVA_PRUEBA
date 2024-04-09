package utils;

import java.util.List;

public class Utils
{
    //Podemos definir un metodo que retorna cualquier dato y recibe cualquier dato
    //Comunemente se usa T para referirse a genericos


    //Este metodo se usa principalmente para pasarle un arreglo al JOptionPane
    public static <T> T[] listToarray(List<T> list)
    {
        T[] array = (T[]) new Object[list.size()];

        int i = 0;
        for(T object: list)
        {
            array[i++] = object;
        }

        return array;
    }
}
