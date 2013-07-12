package com.chris.common.annotaion.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: chris
 * Date: 12-9-8
 * Time: 下午2:57
 */
public class TableCreator {

    public static void main(String[] args) {
        TableCreator tc = new   TableCreator();
        String clazz = "com.bplan.annotaion.sql.Person";
        try {
            System.out.print(tc.parse(clazz));

            tc.parseMethod(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*
        CREATE TABLE tb_person(
        ID INT(11)  PRIMARY KEY ,
        NAME VARCHAR(30) ,
        AGE INT(3) ,
        PASSWORD CHAR(11)  NOT NULL
        );
         */

    }


    /**
     * @param className
     * @return null or create_table_SQL
     * @throws ClassNotFoundException
     */
    public String parse(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        DBTable dbTable = clazz.getAnnotation(DBTable.class);
        if (dbTable == null) {
            System.out.println("no DBTable annotations in class " + className);
            return null;
        }
        
        String tbName = dbTable.name();
        if (tbName.length() < 1) {
            tbName = clazz.getName().toUpperCase(); // tb_name
        }
        
        String engine = dbTable.engine();
        String charset = dbTable.charset();
        
        List<String> columns = new ArrayList<String>();
        Annotation[] annotations = null;
        for (Field field : clazz.getDeclaredFields()) {
            annotations = field.getAnnotations();

            // no annotation
            if (annotations.length < 1) {
                continue;
            }

            //todo loop
            String column = parseFiled(annotations[0], field);
            if (column != null && !column.isEmpty()) {
                columns.add(column);
            }
        }

        return builderTable(tbName, columns, engine, charset);
    }  
    
    private static String builderTable(String tbName, List<String> columns, String engine, String charset) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tbName).append("(");
        for (String column : columns) {
            sb.append("\n").append(column).append(",");
        }
        return sb.toString().substring(0, sb.length() -1) + "\n)ENGINE=" + engine + " DEFAULT CHARSET= " + charset + ";";
    }
    
    private static String parseFiled(Annotation annotation, Field field) {
        String columnName = "";
        int length = 0;
        // integer
        if (annotation instanceof SQLInteger) {
            SQLInteger sin = (SQLInteger) annotation;
            if (sin.name().length() < 1) {
                columnName = field.getName().toUpperCase();// tb_filed
            } else {
                columnName = sin.name();
            }

            length = sin.length();

            return (columnName + " INT(" + length + ") " + getConstraints(sin.constraints()));
        }

        // long
        if (annotation instanceof SQLLong) {
            SQLLong sin = (SQLLong) annotation;
            if (sin.name().length() < 1) {
                columnName = field.getName().toUpperCase();// tb_filed
            } else {
                columnName = sin.name();
            }

            length = sin.length();

            return (columnName + " bigint(" + length + ") " + getConstraints(sin.constraints()));
        }

        // string
        if (annotation instanceof SQLString) {
            SQLString str = (SQLString) annotation;
            if (str.name().length() < 1) {
                columnName = field.getName().toUpperCase();// tb_filed
            } else {
                columnName = str.name();
            }

            String strType = "VARCHAR";
            if (str.isFix()) {
                strType = "CHAR";
            }

            length = str.length();

            return (columnName + " " + strType + "(" + length + ") " + getConstraints(str.constraints()));
        }
        return null;
    }
    
    private static String getConstraints(Constraints constraints) {
        StringBuilder sb= new StringBuilder();
        if (!constraints.allowNull()) {
            sb.append(" NOT NULL ") ;
        }

        if (constraints.primaryKey()) {
            sb.append(" PRIMARY KEY ");
        }

        if (constraints.unique()) {
           sb.append(" unique ");
        }

        return sb.toString();
    }

    /**
     * @param className
     * @return null or create_table_SQL
     * @throws ClassNotFoundException
     */
    public String parseMethod(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();
        Annotation[] annotations = null;
        for (Method m : methods) {
            annotations = m.getDeclaredAnnotations();
            if (annotations.length < 0) {
               System.out.println(className + "'s method " + m.getName() + " has no Annotation .");
               continue;
            }

            System.out.println(className + "'s method " + m.getName() + " has  @Description Annotation ");
            for (Annotation an : annotations) {
                if (an instanceof Description) {
                    Description description = (Description) an;
                    System.out.print(description.value() + " , ");
                }
            }


            //for method parameter annotation
            Annotation[][] mans = m.getParameterAnnotations();
            Class[] parameterTypes = m.getParameterTypes();

            Parameter parameter = null;
            int i = 0;
            for (Annotation[] pans : mans) {
                Class pclazz = parameterTypes[i++];
                for (Annotation pan : pans) {
                   if (pan instanceof Parameter) {
                       parameter = (Parameter)pan;
                       System.out.println("param type: " + pclazz.getName() + " param name: " + parameter.value() + " param default value:" + parameter.defaultValue());
                   }
                }
            }
        }
        
        return "";
    }
}
