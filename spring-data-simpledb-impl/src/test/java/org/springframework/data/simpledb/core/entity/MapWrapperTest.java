package org.springframework.data.simpledb.core.entity;

import org.junit.Test;
import org.springframework.data.simpledb.core.entity.util.AttributeUtil;
import org.springframework.data.simpledb.repository.support.entityinformation.SimpleDbEntityInformation;
import org.springframework.data.simpledb.repository.support.entityinformation.SimpleDbEntityInformationSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapWrapperTest {


    @Test
    public void maps_of_byte_keys_are_converted_back_as_maps_of_String_keys() {
        SampleCoreMap simpleMap = new SampleCoreMap();
        simpleMap.setMapOfByte(new HashMap<Byte, Byte>());
        simpleMap.getMapOfByte().put(Byte.valueOf("1"), Byte.valueOf("1"));


        EntityWrapper<SampleCoreMap, String> sdbEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class), simpleMap);
        final Map<String, List<String>> attributes = sdbEntity.serialize();

        /* convert back */
        final EntityWrapper<SampleCoreMap, String> convertedEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class));
        convertedEntity.deserialize(attributes);

        SampleCoreMap returnedMap = convertedEntity.getItem();
        assertEquals(returnedMap.getMapOfByte().keySet().iterator().next(),"1");
    }



    @Test
    public void serialize_deserialize_map_of_strings() {
        SampleCoreMap simpleMap = new SampleCoreMap();
        simpleMap.setMapOfStrings( new HashMap<String, String>());
        simpleMap.getMapOfStrings().put("first", "firstValue");

        EntityWrapper<SampleCoreMap, String> sdbEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class), simpleMap);
        final Map<String, List<String>> attributes = sdbEntity.serialize();

        /* convert back */
        final EntityWrapper<SampleCoreMap, String> convertedEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class));
        convertedEntity.deserialize(attributes);

        assertTrue(simpleMap.equals(convertedEntity.getItem()));

    }


    @Test public void deserialize_should_return_null_for_not_instantiated_maps() {
        SampleCoreMap simpleMap = new SampleCoreMap();

        EntityWrapper<SampleCoreMap, String> sdbEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class), simpleMap);
        final Map<String, List<String>> attributes = sdbEntity.serialize();

        /* convert back */
        final EntityWrapper<SampleCoreMap, String> convertedEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class));
        convertedEntity.deserialize(attributes);

        assertTrue(simpleMap.equals(convertedEntity.getItem()));

    }

    @Test
    public void serialize_should_return_attribute_name_key() {
        SampleCoreMap simpleMap = new SampleCoreMap();
        simpleMap.setMapOfStrings( new HashMap<String, String>());
        simpleMap.getMapOfStrings().put("first", "firstValue");
        simpleMap.setMapOfByte(new HashMap<Byte, Byte>());
        simpleMap.getMapOfByte().put(Byte.valueOf("1"), Byte.valueOf("1"));

        /* ----------------------- Serialize Representation ------------------------ */
        EntityWrapper<SampleCoreMap, String> sdbEntity = new EntityWrapper<>(this.<SampleCoreMap>readEntityInformation(SampleCoreMap.class), simpleMap);
        final Map<String, List<String>> attributes = sdbEntity.serialize();

        assertTrue(attributes.size() == 2);

        for(String attributeName : AttributeUtil.<SampleCoreMap>getAttributeNamesThroughReflection(SampleCoreMap.class)) {
            assertTrue(attributes.containsKey(attributeName));
        }

    }

    private <E> SimpleDbEntityInformation<E, String> readEntityInformation(Class<E> clazz) {
        return (SimpleDbEntityInformation<E, String>) SimpleDbEntityInformationSupport.<E>getMetadata(clazz);
    }

    public static class SampleCoreMap {
        private Map<String, String> mapOfStrings;
        private Map<Byte, Byte> mapOfByte;

        public Map<String, String> getMapOfStrings() {
            return mapOfStrings;
        }

        public void setMapOfStrings(Map<String, String> mapOfStrings) {
            this.mapOfStrings = mapOfStrings;
        }

        public Map<Byte, Byte> getMapOfByte() {
            return mapOfByte;
        }

        public void setMapOfByte(Map<Byte, Byte> mapOfByte) {
            this.mapOfByte = mapOfByte;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SampleCoreMap that = (SampleCoreMap) o;

            if (mapOfByte != null ? !mapOfByte.equals(that.mapOfByte) : that.mapOfByte != null) return false;
            if (mapOfStrings != null ? !mapOfStrings.equals(that.mapOfStrings) : that.mapOfStrings != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = mapOfStrings != null ? mapOfStrings.hashCode() : 0;
            result = 31 * result + (mapOfByte != null ? mapOfByte.hashCode() : 0);
            return result;
        }
    }


}
