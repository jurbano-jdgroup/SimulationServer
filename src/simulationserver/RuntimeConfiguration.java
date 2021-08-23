
package simulationserver;

/**
 *
 * @author Ernesto
 */
public class RuntimeConfiguration {
    
    public static final String UNSIGNER_INT_TYPE = "[0-9]+";
    public static final String SIGNED_FLOAT = "[+\\-]?[0-9]*\\.?[0-9]+";
    public static final String UNSIGNED_FLOAT = "\\+?[0-9]*\\.?[0-9]+";
    public static final String TEXT_TYPE = "[a-zA-Z0-9]+";
    public static final String NONE_TYPE = "";
    
    /**
     * options array
     * option field order
     * 1st: name, 2nd: has value, 3rd: is required, 4th: description, 5th: type
     */
    protected final Object[][] options = {
        {"port", true, false, "Puerto del servidor, por defecto 10080", RuntimeConfiguration.UNSIGNER_INT_TYPE},
        {"maxconnections", true, false, "Maximo numero de conexiones, por defecto 5", RuntimeConfiguration.UNSIGNER_INT_TYPE},
        {"inputsampling", true, false, "e.g: 10, 5, 200... Muestreo de entrada, por defecto 10 ms", RuntimeConfiguration.UNSIGNER_INT_TYPE},
        {"outputsampling", true, false, "Muestreo de salida, por defecto 1 ms", RuntimeConfiguration.UNSIGNER_INT_TYPE},
        {"service", true, false, "Servicio", RuntimeConfiguration.TEXT_TYPE},
        {"system", true, false, "Obligatorio. Dinamica, puede ser: [firstorder] para un sistema de primer orden,"+
            " [secondorder] para un sistema de segundo order, [pid] para un controlador, "+
            "[thirdorder] para un sistema de tercer order predefinido, [freqres] o [planta1], [planta2], "+
            "[planta3], [planta4] para usar las dinámicas predefinidas", RuntimeConfiguration.TEXT_TYPE},
        {"group", true, false, "Grupo de trabajo: 1, 2, 3, ...", RuntimeConfiguration.UNSIGNER_INT_TYPE},
        {"k", true, false, "e.g -1.3, 4.5, 100.444... Parámetro de la función de transferencia, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"a", true, false, "e.g -1.3, 4.5, 100.444... Parámetro de la función de transferencia, por defecto 0.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"b", true, false, "e.g -1.3, 4.5, 100.444... Parámetro de la función de transferencia, por defecto 1.0.", RuntimeConfiguration.UNSIGNED_FLOAT},
        {"c", true, false, "e.g -1.3, 4.5, 100.444... Parámetro de la función de transferencia, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"d", true, false, "e.g -1.3, 4.5, 100.444... Parámetro de la función de transferencia, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"t", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del tiempo muerto, por defecto 0.0.", RuntimeConfiguration.UNSIGNED_FLOAT},
        {"kp", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, constante proporcional, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"ki", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, constante integral, por defecto 1.0.", RuntimeConfiguration.UNSIGNED_FLOAT},
        {"kd", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, constante derivativa, por defecto 1.0.", RuntimeConfiguration.UNSIGNED_FLOAT},
        {"N", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, coeficiente del filtro derivativo, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"bias", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, bias inicial, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"direction", true, false, "'inverse' o 'directed'. Parámetro del controlador, por defecto 'directed'", RuntimeConfiguration.TEXT_TYPE},
        {"outputMax", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, saturación superior, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"outputMin", true, false, "e.g -1.3, 4.5, 100.444... Parámetro del controlador, saturación inferior, por defecto 1.0.", RuntimeConfiguration.SIGNED_FLOAT},
        {"setpoint", true, false, "e.g 2.3, 100.0, -67.4... Parámetro del controlador, valor de consigna. Por defecto 50.0", RuntimeConfiguration.SIGNED_FLOAT},
        {"setpointMax", true, false, "e.g 2.3, 100.0, -67.4... Parámetro del controlador, valor de consigna. Por defecto 0.0", RuntimeConfiguration.SIGNED_FLOAT},
        {"setpointMin", true, false, "e.g 2.3, 100.0, -67.4... Parámetro del controlador, valor de consigna. Por defecto 100.0", RuntimeConfiguration.SIGNED_FLOAT},
        {"setpointGui", true, false, "Show a gui for handling the setpoint value", RuntimeConfiguration.TEXT_TYPE},
        {"help", false, false, "Para mostrar la ayuda", RuntimeConfiguration.NONE_TYPE}
    };
    
    protected final java.util.Map<String, Object> paramsRecovered;
    
    public RuntimeConfiguration () {
        this.paramsRecovered = new java.util.HashMap<>(32);
    }
    
    public boolean parseArgs (String[] args, boolean deb) {
        this.paramsRecovered.clear();
        
        if (deb) System.out.println("Args count: "+args.length);
        
        java.util.regex.Pattern pattern;
        java.util.regex.Matcher matcher;
        boolean foundFlag;
        
        // checking help
        for (int i=0; i<args.length; ++i) {
            try{
                pattern = java.util.regex.Pattern.compile("-help");
                matcher = pattern.matcher(args[i]);
                
                if (matcher.find()) {
                    System.out.println("Help command found");
                    return false;
                }
                
            }catch(Exception ex) {
                if (deb) System.out.println(ex.getMessage());
            }
        }
        
        // check for the other options
        for(int k=0; k<this.options.length; ++k) 
        {
            Object[] optionItem = this.options[k];
            foundFlag = false;
            
            for(int i=0; i<args.length; ++i)
            {
                final String argItem = args[i].trim();
                
                String patternString = "^(-"+optionItem[0]+"=)("+optionItem[4]+")";
                //System.out.println("pattern: "+patternString+", on "+argItem);
                
                try{
                    pattern = java.util.regex.Pattern.compile(patternString);
                    matcher = pattern.matcher(argItem);
                    
                    if (matcher.find() && ((Boolean)optionItem[1])) {
                        final String value = matcher.replaceAll("$2");
                        Object finalValue;
                        // check if unsigned integer type
                        if (optionItem[4]==RuntimeConfiguration.UNSIGNER_INT_TYPE) {
                            finalValue = Integer.parseUnsignedInt(value);
                        } else if (optionItem[4] == RuntimeConfiguration.TEXT_TYPE) {
                            finalValue = value;
                        } else if (optionItem[4] == RuntimeConfiguration.SIGNED_FLOAT) {
                            finalValue = Double.parseDouble(value);
                        } else if (optionItem[4] == RuntimeConfiguration.UNSIGNED_FLOAT) {
                            finalValue = Double.parseDouble(value);
                            // check final Value is greather than zero
                            if ((Double)finalValue < 0.0) {
                                throw new Exception("Value: "+value+" must be an unsigned float");
                            }
                        }
                        else {
                            throw new Exception("Error resolving type");
                        }
                        
                        if(deb && ((Boolean)optionItem[1])) System.out.println("Param "+
                                optionItem[0]+" setted to "+value+", on arg item: "+argItem);
                        
                        this.paramsRecovered.put((String)optionItem[0], finalValue);
                        foundFlag = true;
                        break;
                    } else if (matcher.find()) {
                        if (deb) System.out.println("Command "+optionItem[0]+" found");
                        
                        this.paramsRecovered.put((String)optionItem[0], null);
                        foundFlag = true;
                        break;
                    }
                    
                }catch(Exception ex) {
                    System.out.println("Exception: "+ex.getMessage());
                }
            }
            // check if required and not found
            if((!foundFlag) && ((Boolean)optionItem[2])) return false;
        }
        
        if (deb && this.paramsRecovered.size()>0) {
            final java.util.Set<String> keys = this.paramsRecovered.keySet();
            final java.util.Iterator it = keys.iterator();
            
            while (it.hasNext()) {
                final String key = (String) it.next();
                System.out.println("Option: "+key+", value: "+this.paramsRecovered.get(key));
            }
        }
        
        return true;
    }
    
    public void printUSage () {
        String usageText = "\nusage: java -jar [path to jar file]/SimulationServer.jar [option]={value}... \n"+
                "options: \n";
        for (int i=0; i<this.options.length; ++i) {
            usageText += "-"+this.options[i][0]+": "+this.options[i][3]+"\n\n";
        }
        
        System.out.println(usageText);
    }
    
    /**
     * Print options recovered from the args list
     */
    public void dumpOptions () {
        if (!this.paramsRecovered.isEmpty()) {
            final java.util.Set<String> keys = this.paramsRecovered.keySet();
            final java.util.Iterator it = keys.iterator();
            
            System.out.println("\nOptions list:");
            while (it.hasNext()) {
                final String key = (String) it.next();
                System.out.println("Option: "+key+" setted to value: "+this.paramsRecovered.get(key));
            }
            System.out.println();
        } else {
            System.out.println("No options in map");
        }
    }
    
    public int paramsCount () {
        return this.paramsRecovered.size();
    }
    
    /**
     * Get the params map
     * 
     * @return Params dictionary
     */
    public java.util.Map<String, Object> getParamsMap () {
        return this.paramsRecovered;
    }
}