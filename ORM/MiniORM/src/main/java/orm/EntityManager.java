package orm;

import orm.annotations.Column;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.IntStream;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_STATEMENT = "INSERT INTO `%s`" +
            "(%s) VALUES(%s)";
    private static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_FIRST_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s LIMIT 1";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idField = getId(entity.getClass());
        idField.setAccessible(true);
        Object idValue = idField.get(entity);

        if(idValue == null || (int)idValue <= 0){
            return doInsert(entity, idField);
        }
        return doUpdate(entity, idField);
    }



    @Override
    public Iterable<E> find(Class<E> table) {
        return this.find(table, "1 = 1");
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
    //    findFirst(table, null);
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException {
        String tableName = this.getTableName(table);
        Statement statement = connection.createStatement();

        String query = String.format(SELECT_FIRST_WITH_WHERE,
                tableName, where == null ? "" : "AND" + where);

        ResultSet rs = statement.executeQuery(query);
        rs.next();

        return this.mapResultToEntity(rs, table);
    }

    private E mapResultToEntity(ResultSet rs, Class<E> table) {
        E entity = table.
    }

    private Field getId(Class entity){
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow( () ->
                        new UnsupportedOperationException(
                                "Entity does not have primary key")
                );
    }

    private boolean doInsert(E entity, Field idField) throws SQLException {
        String tableName = this.getTableName(entity.getClass());

        String[] tableFields = this.getTableFields(entity);
        String[] tableValues = this.getTableValues(entity);

        String query = String.format(INSERT_STATEMENT,
                tableName, String.join(", ", tableFields),
                String.join(", ", tableValues));


        return this.connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field idField) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());

        String[] tableFields = this.getTableFields(entity);
        String[] tableValues = this.getTableValues(entity);

        String[] fieldValuePairs = IntStream.range(0, tableFields.length)
                .mapToObj(i -> "`" + tableFields[i] + "`=" + tableValues[i])
                .toArray(String[]::new);

        String whereId = "`" + idField.getName() + "`='" + idField.get(entity) + "'";

        String query = String.format(UPDATE_STATEMENT,
                tableName,
                String.join(", ", fieldValuePairs),
                whereId);

        return this.connection.prepareStatement(query).execute();
    }




    private String[] getTableValues(E entity) {
        return Arrays.stream(entity
                .getClass()
                .getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        Object value = f.get(entity);
                        return "'" + value.toString() + "'";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                }).toArray(String[]::new);
    }

    private String[] getTableFields(E entity) {
        return Arrays.stream(entity
                        .getClass()
                        .getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    f.setAccessible(true);
                    return f.getName();
                }).toArray(String[]::new);
    }

    private String getTableName(Class<?> aClass) {
        return aClass.getSimpleName().toLowerCase().concat("s");
    }




}
