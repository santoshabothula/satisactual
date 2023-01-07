package com.datawise.satisactual.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Component
public class ReflectionUtil {

    public List<Object> getEntityPropValues(String prop, List<?> entities) {
        List<Map<String, Object>> list = getEntityValues(entities);
        if (Objects.isNull(list)) return null;

        List<Object> values = new ArrayList<>();
        list.forEach(l -> l.forEach((k, v) -> {
            if (prop.equals(k)) {
                values.add(v);
            }
        }));
        return values;
    }

    public List<Map<String, Object>> getEntityValues(List<?> entities) {

        if (Objects.isNull(entities) || entities.isEmpty()) return null;

        List<Map<String, Object>> list = new ArrayList<>();
        entities.forEach(entity -> list.add(getEntityValues(entity.getClass(), entity)));
        return list;
    }

    public Map<String, Object> getEntityValues(Class<?> entity, Object object) {
        Map<String, Object> map = new HashMap<>();
        Arrays.stream(entity.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                if (Objects.nonNull(entity.getSuperclass())) {
                    for(Method m: entity.getSuperclass().getDeclaredMethods()) {
                        if (m.getName().startsWith("get")) {
                            map.put(m.getName(), m.invoke(object));
                        }
                    }
                }
                if (Objects.nonNull(field.getAnnotation(EmbeddedId.class))) {
                    map.putAll(getEntityValues(field.getType(), field.get(object)));
                } else if (Objects.nonNull(field.getAnnotation(Column.class))) {
                    map.put(field.getName(), field.get(object));
                }
            } catch (Exception ex) {
                log.info("Failed to get ENTITY {}, data of filed {}", entity, field.getName());
            }
        });
        return map;
    }

    public String getTableName(Object entity) {
        if (Objects.isNull(entity.getClass().getAnnotation(Table.class))) return Const.EMPTY;
        else return entity.getClass().getAnnotation(Table.class).name();
    }
}
