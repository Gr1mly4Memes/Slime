package gr1mly4memes.slime.config;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface ConfigVerify<E> extends ConfigConvert<E> {

    default String check(E old, E value) {
        return null;
    }

    default List<String> valueSuggest() {
        return List.of("<value>");
    }

    default void runAfterLoader(E value) {
    }

    class BooleanConfigVerify implements ConfigVerify<Boolean> {

        @Override
        public Boolean convert(String value) {
            return Boolean.parseBoolean(value);
        }

        @Override
        public List<String> valueSuggest() {
            return List.of("false", "true");
        }
    }

    class IntConfigVerify implements ConfigVerify<Integer> {
        @Override
        public Integer convert(String value) {
            return Integer.parseInt(value);
        }
    }

    class StringConfigVerify implements ConfigVerify<String> {
        @Override
        public String convert(String value) {
            return value;
        }
    }

    class DoubleConfigVerify implements ConfigVerify<Double> {
        @Override
        public Double convert(String value) {
            return Double.parseDouble(value);
        }
    }

    class ListConfigVerify implements ConfigVerify<List<?>> {
        @Override
        public List<?> convert(String value) {
            throw new IllegalArgumentException("not support"); // TODO
        }
    }

    abstract class EnumConfigVerify<E extends Enum<E>> implements ConfigVerify<Enum<E>> {

        private final Class<E> enumClass;
        private final List<String> enumValues;

        @SuppressWarnings({"unchecked", "unused"})
        public EnumConfigVerify() {
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type[] typeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            this.enumClass = (Class<E>) typeArguments[0];
            // Plain loop: the previous double-brace initialiser created an anonymous inner class
            // per verifier and kept an implicit reference to the enclosing instance.
            List<String> values = new ArrayList<>(enumClass.getEnumConstants().length);
            for (E e : enumClass.getEnumConstants()) {
                values.add(e.name().toLowerCase(Locale.ROOT));
            }
            this.enumValues = List.copyOf(values);
        }

        @Override
        public Enum<E> convert(String value) {
            return Enum.valueOf(enumClass, value.toUpperCase(Locale.ROOT));
        }

        @Override
        public List<String> valueSuggest() {
            return enumValues;
        }

        public abstract String check(E old, E value) throws IllegalArgumentException;
    }
}
