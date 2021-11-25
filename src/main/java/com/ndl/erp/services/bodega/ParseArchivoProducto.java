package com.ndl.erp.services.bodega;

import com.ndl.erp.dto.ProductoInventarioDTO;
import com.ndl.erp.dto.ProductoInventarioItemDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.util.DateUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.ndl.erp.constants.ParseCargaArchivoConstants.*;
import static com.ndl.erp.util.StringHelper.getValDoubleWihoutDecimalSeparator;

@Component
public class ParseArchivoProducto implements ParserCargaArchivo{

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");


    /*
     Devuelve una  arreglo de elementos delimitados por comma
    */
    public String[]  parseElementLine(String line){
        // parsing de los elementos separados por coma
        String[] data = line.split("\\" + SEPARADOR_LISTA);
        return data;
    }

    public boolean validarSiguienteToken(Iterator<String> it, boolean obligatorio, String mensaje) throws Exception{
        boolean saltarElemento = false;
        if (obligatorio && !it.hasNext()) {
            throw new GeneralInventoryException("Elemento de archivo esperado pero no encontrado: " + mensaje );
        } else if (!obligatorio && !it.hasNext()) {
          saltarElemento = true;
        }
        return saltarElemento;
    }

    public boolean validarStringNulo(String value) {
        boolean esNulo = false;
        if (value == null || value.equals("")) {
            esNulo = true;
        }
        return esNulo;
    }


    public boolean esNumerico(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    /*
       Devuelve una  arreglo de elementos delimitados  por punto y coma
     */
    public String[]  parsePropertyList(String propertyList){
        // parsing de los elementos separados por punto y  coma
        String[] data = propertyList.split("\\" + SEPARADOR_DE_SUBLISTA );
        return data;
    }

    ProductoInventarioItemDTO cargarProductoInventarioItem(String[] linea, int numeroLinea) throws Exception {
        ProductoInventarioItemDTO productoInventarioItemDTO = new ProductoInventarioItemDTO();
        List<String> elementos = null;
        elementos =   Arrays.asList(linea);

        System.out.println("------>>> LINEA " + numeroLinea);

        // vienen 13 datos fijos  dependiento del tipo vienen propiedades en grupos de dos
        ListIterator<String> it = null;
        it =  elementos.listIterator();

        validarSiguienteToken(it, true, "Falta el tipo  de elemento." + " Linea: " + numeroLinea);
        String tipo = it.next().trim();  //obtener el  campo Tipo de la linea
        if (tipo.equals(TIPO_LINEA_ARTICULO_VARIABLE)) {
            productoInventarioItemDTO.setTipo(tipo);
            validarSiguienteToken(it, true, "EL codigo debe venir nulo: ,, " + " Linea: " + numeroLinea);
            String codigo = it.next().trim();

            validarSiguienteToken(it, true, "El Sku es un dato requerido"+ " Linea: " + numeroLinea);
            String sku = it.next().trim();
            if(validarStringNulo(sku)) {
                throw new GeneralInventoryException("El sku del sku es requerido");
            }
            productoInventarioItemDTO.setSku(sku);
            validarSiguienteToken(it, true, "El nombre de la familia es un dato requerido" + " Linea: " + numeroLinea);
            String nombre = it.next().trim();
            if(validarStringNulo(nombre)) {
                throw new GeneralInventoryException("El nombre de la familia es un dato  requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setNombre(nombre);
            validarSiguienteToken(it, true, "La cantidad de inventario debe venir en nulo: ,, " + " Linea: " + numeroLinea);
            it.next(); //Saltarse la cantidad de inventario
            validarSiguienteToken(it, true, "El precio de costo debe venir en nulo: ,, " + " Linea: " + numeroLinea);
            it.next(); //Saltarse el precio de costo
            validarSiguienteToken(it, true, "El margen de utilidad debe venir en nulo: ,, " + " Linea: " + numeroLinea);
            it.next(); //Saltarse el margen de utilidad
            validarSiguienteToken(it, true, "Las categorias deben venir en nulo: ,, " + " Linea: " + numeroLinea);
            it.next(); //Saltarse las categorias
            validarSiguienteToken(it, true, "Las unidad de medida es un dato requerido" + " Linea: " + numeroLinea);
            String unidadMedida = it.next().trim();
            validarSiguienteToken(it, true, "El precio de lista debe venir en nulo ,, " + " Linea: " + numeroLinea);
            it.next(); //Saltarse el precio de lista
            validarSiguienteToken(it, true, "El precio de lista debe venir en nulo" + " Linea: " + numeroLinea);
            String codigoCabys = it.next().trim(); //Saltarse el cabys
            validarSiguienteToken(it, true, "El modelo/catalogo debe venir en nulo ,, " + " Linea: " + numeroLinea);
            it.next();//saltarse modelo/catalogo;
            validarSiguienteToken(it, true, "El fabricante debe venir en nulo" + " Linea: " + numeroLinea);
            String fabricante = it.next().trim();


            //Cargar los atributos en el map mas allá del treceavo elemento
            String value = "";
            String llave = "";
            while (it.hasNext()){
               llave = it.next().trim();
               if (it.hasNext()) {
                   value = it.next().trim();
               } else {
                   value = "";
               }
                productoInventarioItemDTO.getAtributos().add(llave);
                productoInventarioItemDTO.getAtributos().add(value);
               //Bloque de propiedades es opcional

//               if (!validarSiguienteToken(it, false, "La propiedad al menos debe venir nula: ;; " + llave + "viene incompleta")) {
//                   String[] propiedades = null;
//                   propiedades = this.parsePropertyList(it.next());
//                   List<String> propiedadesVariable = null;
//                   propiedadesVariable = Arrays.asList(propiedades);
//                   //Meter al map en formato llave, pl
//                   ListIterator<String> pl = null;
//                   pl =  propiedadesVariable.listIterator();
//                    //Procesar sublista de atributos si la trae
//                    while(pl.hasNext()) {
//                        String valorAtributo = pl.next().trim();
//                        if ((llave != null && !llave.equals("")) && (valorAtributo != null && !valorAtributo.equals(""))) {
//                            productoInventarioItemDTO.getAtributos().put(llave, valorAtributo);
//                        }
//                   }
//               }

            }
       } else if(tipo.equals(TIPO_LINEA_ARTICULO_VARIATION) || tipo.equals(TIPO_LINEA_ARTICULO_SIMPLE)) {
            // vienen 13 datos fijos
            productoInventarioItemDTO.setTipo(tipo);
            validarSiguienteToken(it, true, "El codigo es un dato requerido" + " Linea: " + numeroLinea);
            String codigo = it.next().trim();
            if(validarStringNulo(codigo)) {
                throw new GeneralInventoryException("El codigo del producto es requerido" + " Linea: " + numeroLinea);
            }

            productoInventarioItemDTO.setCodigo(codigo);
            validarSiguienteToken(it, true, "El sku es un dato requerido" + " Linea: " + numeroLinea);
            String sku = it.next().trim();
            if(validarStringNulo(sku)) {
                throw new GeneralInventoryException("Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el sku del producto es requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setSku(sku);
            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() + " el nombre es un dato requerido" + " Linea: " + numeroLinea);
            String nombre = it.next().trim();
            if(validarStringNulo(nombre)) {
                throw new GeneralInventoryException("Error producto: "  +  productoInventarioItemDTO.getCodigo() +  ",  el nombre del producto es requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setNombre(nombre);
            validarSiguienteToken(it, true, "Error producto: "  +  productoInventarioItemDTO.getCodigo() + " ,a cantidad de inventario debe venir al menos nula: ,, " + " Linea: " + numeroLinea);
            String cantInventarioString = it.next().trim();
            if (!validarStringNulo(cantInventarioString) && !esNumerico(cantInventarioString)) {
             throw  new  GeneralInventoryException("Error producto: " +  productoInventarioItemDTO.getCodigo() +  ", tiene el valor Inventario erroneo: " + cantInventarioString + " Linea: " + numeroLinea);
            }
            Double cantidadInventario = (cantInventarioString == null || cantInventarioString.equals("")) ? 0d : getValDoubleWihoutDecimalSeparator(cantInventarioString);
            productoInventarioItemDTO.setInventario(cantidadInventario);
            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el precio de costo debe venir al menos nulo: ,, " + " Linea: " + numeroLinea);
            String precioCostoString = it.next().trim();
            if (!validarStringNulo(precioCostoString) && !esNumerico(precioCostoString)) {
                throw  new  GeneralInventoryException("Error producto: " +  productoInventarioItemDTO.getCodigo() + ", tiene el valor  Precio Costo  erroneo: " + precioCostoString + " Linea: " + numeroLinea);
            }
            Double precioCosto = (precioCostoString == null || precioCostoString.equals("")) ? 0d : getValDoubleWihoutDecimalSeparator(precioCostoString);
            productoInventarioItemDTO.setPrecioCosto(precioCosto);
            validarSiguienteToken(it, true,  "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el margen de utilidad debe venir al menos nulo: ,, " + " Linea: " + numeroLinea);
            String margenUtilidadString = it.next().trim();
            if (!validarStringNulo(margenUtilidadString) && !esNumerico(margenUtilidadString)) {
                throw  new  GeneralInventoryException("Error producto: " +  productoInventarioItemDTO.getCodigo() +  ", tiene el valor  Margen Utilidad  erroneo: " + margenUtilidadString + " Linea: " + numeroLinea);
            }

            Double margenUtilidad = (margenUtilidadString == null || margenUtilidadString.equals("")) ? 0d : getValDoubleWihoutDecimalSeparator(margenUtilidadString);
            productoInventarioItemDTO.setMargenUtilidad(margenUtilidad);
            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", la categoria debe venir al menos nula: ,, " + " Linea: " + numeroLinea);
            String categoria = it.next().trim();
            productoInventarioItemDTO.setCategoria(categoria);
            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", la unidad de medida es una dato requerido" + " Linea: " + numeroLinea);
            String unidadMedida = it.next().trim();
            if(validarStringNulo(unidadMedida)) {
                throw new GeneralInventoryException("La unidad de medida es un dato requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setUnidadMedida(unidadMedida);
            validarSiguienteToken(it, false, "El precio de lista debe venir al menos nulo: ,, " + " Linea: " + numeroLinea);
            String precioListaString = it.next().trim();
            if (!validarStringNulo(precioListaString) && !esNumerico(precioListaString)) {
                throw  new  GeneralInventoryException("El producto " + productoInventarioItemDTO.getSku() + ", " + "tiene el valor  Precio Lista  erroneo: " + precioListaString + " Linea: " + numeroLinea);
            }

            Double precioLista = (precioListaString == null || precioListaString.equals(""))   ? 0d : getValDoubleWihoutDecimalSeparator(precioListaString);
            productoInventarioItemDTO.setPrecioLista(precioLista);

            validarSiguienteToken(it, false, "El cabys  debe venir al menos nulo: ,, " + " Linea: " + numeroLinea);
            String codigoCabys = it.next().trim();
            if (codigoCabys!=null) {
                codigoCabys = codigoCabys.replaceAll("[^0-9.]", "");
                if (codigoCabys.length() > 13) {
                    throw new GeneralInventoryException("La línea excede el tamaño máximo permitido en la línea " + numeroLinea);
                }
            }
            productoInventarioItemDTO.setCodigoCabys(codigoCabys);

            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el modelo/catalogo es un dato requerido" + " Linea: " + numeroLinea);
            String modeloCatalogo = it.next().trim();
            if(validarStringNulo(modeloCatalogo)) {
                throw new GeneralInventoryException( "Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el modelo/catalogo es un dato requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setModeloCatalogo(modeloCatalogo);
            validarSiguienteToken(it, true, "Error producto: " +  productoInventarioItemDTO.getCodigo() +  ", el fabricante es un dato requerido" + " Linea: " + numeroLinea);
            String fabricante = it.next().trim();
            if(validarStringNulo(fabricante)) {
                throw new GeneralInventoryException("Error producto: " +  productoInventarioItemDTO.getCodigo() + ", el fabricante es un dato requerido" + " Linea: " + numeroLinea);
            }
            productoInventarioItemDTO.setFabricante(fabricante);
            String value,llave;

            //Cargar los atributos en el map mas allá del treceavo elemento
            while (it.hasNext()) {
                llave = it.next().trim();

                if (it.hasNext()) {
                    value = it.next().trim();
                } else {
                    value = "";
                }
                productoInventarioItemDTO.getAtributos().add(llave);
                productoInventarioItemDTO.getAtributos().add(value);
//                if (!validarSiguienteToken(it, false, "La propiedad al menos debe venir nula: ;; " + llave + "viene incompleta")) {
//
//                    String[] propiedades = null;
//                    propiedades =  this.parsePropertyList(it.next());
//                    List<String> propiedadesProducto = null;
//                    propiedadesProducto = Arrays.asList(propiedades);
//                    //Meter al map en formato llave, ppl
//                    ListIterator<String> ppl = null;
//                    ppl = propiedadesProducto.listIterator();
//                    //Procesar sublista de atributos si la trae
//                    while (ppl.hasNext()) {
//                        String valorAtributo = ppl.next().trim();
//                        if ((llave != null && !llave.equals("")) && (valorAtributo != null && !valorAtributo.equals(""))) {
//                            productoInventarioItemDTO.getAtributos().put(llave, valorAtributo);
//                        }
//                    }
//               }
           }

      } else {

        throw new GeneralInventoryException("Tipo de atributo o producto desconocido: " + tipo + " Linea: " + numeroLinea);
    }
       return productoInventarioItemDTO;
    }

    public ProductoInventarioDTO parserCargaArchivoCSV(String ruta) throws Exception{
        Date date = new Date(DateUtil.getCurrentCalendar().getTime().getTime());
        ProductoInventarioDTO productoInventarioDTO = new ProductoInventarioDTO();
        productoInventarioDTO.setUriFileName(ruta);
        BufferedReader br = null;
        try{
            String strLine;
            String[] datosElementos;
            //FileReader instance wrapped in a BufferedReader
            br = new BufferedReader(new FileReader(ruta));

            //Remover la primera linea del archivo que son los nombres de las columnas
            if (br.readLine() == null){
                throw new GeneralInventoryException("El archivo viene sin el encabezado!");
            }
            int totalSize;
            ProductoInventarioItemDTO result;
            int lineaArchivo = 2; //Comienza en 2 ya que toma en cuanta la linea de los titulos
            while((strLine = br.readLine()) != null){
                strLine = strLine.replaceAll("[\uFEFF-\uFFFF]", "");
                datosElementos = parseElementLine(strLine);
                if (datosElementos.length > 0){
                    result = this.cargarProductoInventarioItem(datosElementos, lineaArchivo);
                    if (result.getAtributos()!=null &&
                        result.getAtributos().size()>productoInventarioDTO.getAttributesMax()) {
                        productoInventarioDTO.setAttributesMax(result.getAtributos().size());
                    }
                    productoInventarioDTO.getItems().add(this.cargarProductoInventarioItem(datosElementos, lineaArchivo));

                }
              lineaArchivo++;
            }
        }catch(IOException exp){
            System.out.println("Error while reading file " + exp.getMessage());
        }finally {
            try {
                // Close the stream
                if(br != null){
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return productoInventarioDTO;
    }

}
