package org.springframework.data.simpledb.util.marshaller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonMarshallerTest {

    private JsonMarshaller cut;

    @Before
    public void setUp() {
        cut = JsonMarshaller.getInstance();
    }

    @Test
    public void marshal_should_marshal_Object() throws IOException {

        // Prepare
        User newUser = createSampleUser();
        Object object = newUser;

        // Exercise
        String marshalledUser = cut.marshall(object);
        User returnedUser = cut.unmarshall(marshalledUser, User.class);

        // Verify
        assertNotNull(returnedUser);
        assertEquals(newUser.getName().getFirst(), returnedUser.getName().getFirst());
        assertEquals(newUser.getName().getLast(), returnedUser.getName().getLast());
    }

    @Test
    public void marshal_should_marshal_Object_as_lists() throws IOException {

        // Prepare
        List<User> listOfUsers = new LinkedList<>();
        User sampleUser = createSampleUser();
        listOfUsers.add(sampleUser);
        Object object = listOfUsers;

        // Exercise
        String marshalledUser = cut.marshall(object);
        List<User> returnedList = cut.unmarshall(marshalledUser, List.class);

        // Verify
        assertNotNull(returnedList);
        User returnedUser = returnedList.get(0);
        assertEquals(sampleUser.getName().getFirst(), returnedUser.getName().getFirst());
    }


    @Test
    public void should_marshal_unmarshal_maps_of_Strings(){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("TestKey", "Test Value");

        String marsahledMap = cut.marshall(map);

        Map<String, String> unmarshaledMap = (Map<String, String>)cut.unmarshall(marsahledMap, Map.class);

        assertEquals(map.keySet(), unmarshaledMap.keySet());

        String ret = unmarshaledMap.get("TestKey");
        assertEquals("Test Value", ret);

    }


    private User createSampleUser() {
        User newUser = new User();
        newUser.setName(new User.Name());
        newUser.getName().setFirst("Joe");
        newUser.getName().setLast("Sixpack");
        return newUser;
    }


}

class User {
    public enum Gender {MALE}

    public static class Name {
        private String _first;

        private String _last;

        public String getFirst() {
            return _first;
        }

        public String getLast() {
            return _last;
        }

        public void setFirst(String s) {
            _first = s;
        }

        public void setLast(String s) {
            _last = s;
        }
    }

    private Gender _gender;
    private Name _name;
    private boolean _isVerified;
    private byte[] _userImage;

    public Name getName() {
        return _name;
    }

    public Gender getGender() {
        return _gender;
    }

    public byte[] getUserImage() {
        return _userImage;
    }

    public void setName(Name n) {
        _name = n;
    }

    public void setGender(Gender _gender) {
        this._gender = _gender;
    }

    public boolean isVerified() {
        return _isVerified;
    }

    public void setVerified(boolean _isVerified) {
        this._isVerified = _isVerified;
    }

    public void setUserImage(byte[] _userImage) {
        this._userImage = _userImage;
    }
}