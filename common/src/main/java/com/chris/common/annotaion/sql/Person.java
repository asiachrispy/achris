package com.chris.common.annotaion.sql;

/**
 * User: chris
 * Date: 12-9-8
 * Time: 下午2:41
 */
@DBTable(name = "tb_person", engine = "InnoDB")
public class Person {
    @SQLInteger(constraints = @Constraints(primaryKey = true))
    long id;

    @SQLString(length = 30)
    String name;
    
    @SQLInteger(length = 3)
    int age;

    @SQLString(length = 11, isFix = true, constraints = @Constraints(allowNull = false))
    String password;

    @Description("{id} person's base info .")
    public String info(@Parameter(value = "id", defaultValue = "0") long id) {
        return null;
    }
}
